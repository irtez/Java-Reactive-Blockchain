import java.util.Random;
import java.util.concurrent.*;
import java.util.Scanner;


public class ThirdApp {

    // Генератор файлов
    static class FileGenerator implements Runnable {
        private BlockingQueue<File> queue;

        public FileGenerator(BlockingQueue<File> queue) {
            this.queue = queue;
        }

        @Override
        public void run() {
            Random random = new Random();
            while (true) {
                try {
                    // Генерируем случайный тип файла и размер
                    String fileType = generateRandomFileType();
                    int fileSize = random.nextInt(91) + 10; // от 10 до 100

                    File file = new File(fileType, fileSize);
                    System.out.println("Сгенерирован файл: " + file);
                    queue.put(file); // Помещаем файл в очередь
                    Thread.sleep(random.nextInt(901) + 100); // Задержка от 100 до 1000 мс
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        }

        private String generateRandomFileType() {
            String[] fileTypes = {"XML", "JSON", "XLS"};
            Random random = new Random();
            return fileTypes[random.nextInt(fileTypes.length)];
        }
    }

    // Обработчик файлов
    static class FileProcessor implements Runnable {
        private String supportedFileType;
        private BlockingQueue<File> queue;

        public FileProcessor(String supportedFileType, BlockingQueue<File> queue) {
            this.supportedFileType = supportedFileType;
            this.queue = queue;
        }

        @Override
        public void run() {
            while (true) {
                try {
                    File file = queue.take(); // Получаем файл из очереди
                    if (file.getFileType().equals(supportedFileType)) {
                        int processingTime = file.getFileSize() * 7;
                        System.out.println("Обработчик типа " + supportedFileType + " начал обработку файла: " + file);
                        Thread.sleep(processingTime);
                        System.out.println("Обработчик типа " + supportedFileType + " завершил обработку файла: " + file);
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        }
    }

    static class File {
        private String fileType;
        private int fileSize;

        public File(String fileType, int fileSize) {
            this.fileType = fileType;
            this.fileSize = fileSize;
        }

        public String getFileType() {
            return fileType;
        }

        public int getFileSize() {
            return fileSize;
        }

        @Override
        public String toString() {
            return "File{" +
                    "fileType='" + fileType + '\'' +
                    ", fileSize=" + fileSize +
                    '}';
        }
    }

    public static void main(String[] args) {
        BlockingQueue<File> queue = new LinkedBlockingQueue<>(5); // Очередь вместимостью 5 файлов
        ExecutorService executor = Executors.newFixedThreadPool(3); // 3 потока: генератор и 2 обработчика

        executor.submit(new FileGenerator(queue));

        // Запускаем два обработчика, один для XML и один для JSON файлов
        executor.submit(new FileProcessor("XML", queue));
        executor.submit(new FileProcessor("JSON", queue));

        // Завершаем работу после нажатия Enter
        Scanner scanner = new Scanner(System.in);
        scanner.nextLine();
        executor.shutdownNow();
    }
}
