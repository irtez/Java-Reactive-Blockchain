import java.util.Random;

public class Sum1 {
    public static void main(String[] args) {
        Random random = new Random();
        int[] array = random.ints(1000, 10,1000).toArray();
        int sum = 0; 
        long startTime = System.nanoTime();
        try {
            for (int i = 1; i < array.length; i++) {
                sum += array[i];
                Thread.sleep(1);
            }
        }
        catch (Exception e) {
            System.out.println(e);
        }

        System.out.println("Сумма элементов в массиве: " + sum);
        long endTime = System.nanoTime();
        long duration = (endTime - startTime) / 1000000; // Преобразование в миллисекунды
        System.out.println("Время выполнения (мс): " + duration);
    }
}