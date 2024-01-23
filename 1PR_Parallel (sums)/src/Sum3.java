import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;
import java.util.Random;

public class Sum3 {

    static class SumFinderTask extends RecursiveTask<Integer> {
        private int[] arr;
        private int start;
        private int end;

        public SumFinderTask(int[] arr, int start, int end) {
            this.arr = arr;
            this.start = start;
            this.end = end;
        }

        @Override
        protected Integer compute() {
            if (end - start <= 10) { // Пороговое значение для выполнения последовательного поиска
                int sum = 0;
                try {
                    for (int i = start; i < end; i++) {
                    Thread.sleep(1);
                    sum += arr[i];
                    }
                }
                catch (Exception e) {
                    System.out.println(e);
                }
                
                return sum;
            } else {
                int mid = (start + end) / 2;
                SumFinderTask leftTask = new SumFinderTask(arr, start, mid);
                SumFinderTask rightTask = new SumFinderTask(arr, mid, end);

                leftTask.fork();
                int rightSum = rightTask.compute();
                int leftSum = leftTask.join();

                return leftSum + rightSum;
            }
        }
    }

    public static int findSum(int[] arr) {
        ForkJoinPool pool = new ForkJoinPool();
        return pool.invoke(new SumFinderTask(arr, 0, arr.length));
    }

    public static void main(String[] args) {
        Random random = new Random();
        int[] array = random.ints(1000, 10,1000).toArray();

        long startTime = System.nanoTime();
        int max = findSum(array);
        long endTime = System.nanoTime();
        long duration = (endTime - startTime) / 1000000; // Преобразование в миллисекунды

        System.out.println("Сумма элементов в массиве: " + max);
        System.out.println("Время выполнения (мс): " + duration);
    }

}
