import java.util.Scanner;
import java.util.concurrent.*;

public class SecondApp {

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        CompletionService<Integer> completionService = new ExecutorCompletionService<>(executorService);

        try {
            Scanner scanner = new Scanner(System.in);

            while (true) {
                System.out.print("Введите число (или 'exit' для выхода): ");
                String userInput = scanner.nextLine();

                if ("exit".equalsIgnoreCase(userInput)) {
                    break;
                }

                try {
                    int number = Integer.parseInt(userInput);

                    // Отправляем задачу на выполнение и получаем Future
                    Future<Integer> future = completionService.submit(new SquareCalculator(number));

                    // Временно блокируем основной поток, пока не получим результат
                    while (!future.isDone()) {
                        System.out.println("Обработка запроса...");
                        Thread.sleep(1000);
                    }

                    // Получаем и выводим результат выполнения задачи
                    int result = future.get();
                    System.out.println("Результат: " + result);
                } catch (NumberFormatException e) {
                    System.out.println("Ошибка: Введите корректное число.");
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                }
            }
        } finally {
            executorService.shutdown();
        }
    }
}

class SquareCalculator implements Callable<Integer> {
    private final int number;

    public SquareCalculator(int number) {
        this.number = number;
    }

    @Override
    public Integer call() throws Exception {
        // Имитация задержки от 1 до 5 секунд
        int delay = 1000 + (int) (Math.random() * 4000);
        Thread.sleep(delay);

        // Возводим число в квадрат и возвращаем результат
        return number * number;
    }
}