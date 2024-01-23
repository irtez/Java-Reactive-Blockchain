import java.util.concurrent.*;
import java.util.Random;

public class Sum2 {

    public static int findSum(int[] arr) throws InterruptedException, ExecutionException {
        int numThreads = Runtime.getRuntime().availableProcessors();
        ExecutorService executor = Executors.newFixedThreadPool(numThreads);

        int chunkSize = arr.length / numThreads;
        int sum = 0;

        Future<Integer>[] futures = new Future[numThreads];

        for (int i = 0; i < numThreads; i++) {
            final int start = i * chunkSize;
            final int end = (i == numThreads - 1) ? arr.length : (i + 1) * chunkSize;

            futures[i] = executor.submit(() -> {
                int localSum = 0;
                for (int j = start; j < end; j++) {
                    localSum += arr[j];
                    Thread.sleep(1);
                }
                return localSum;
            });
        }

        for (int i = 0; i < numThreads; i++) {
            int threadSum = futures[i].get();
            sum += threadSum;
        }

        executor.shutdown();
        return sum;
    }

    public static void main(String[] args) {
        Random random = new Random();
        int[] array = random.ints(1000, 10,1000).toArray();
        long startTime = System.nanoTime();
        try {
            int sum = findSum(array);
            System.out.println("Сумма элементов в массиве: " + sum);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        long endTime = System.nanoTime();
        long duration = (endTime - startTime) / 1000000; // Преобразование в миллисекунды
        System.out.println("Время выполнения (мс): " + duration);
    }
}
