import java.io.*;
import java.nio.file.*;

public class Fourth {
    public static void main(String[] args) throws IOException, InterruptedException {
        String directoryPath = "C:\\Users\\1645286\\Desktop\\7 сем\\java\\second"; 

        WatchService watchService = FileSystems.getDefault().newWatchService();
        Path path = Paths.get(directoryPath);

        path.register(
                watchService,
                StandardWatchEventKinds.ENTRY_CREATE,
                StandardWatchEventKinds.ENTRY_MODIFY,
                StandardWatchEventKinds.ENTRY_DELETE
        );

        System.out.println("Наблюдение за каталогом: " + directoryPath);

        while (true) {
            WatchKey key = watchService.take();

            for (WatchEvent<?> event : key.pollEvents()) {
                WatchEvent.Kind<?> kind = event.kind();

                if (kind == StandardWatchEventKinds.ENTRY_CREATE) {
                    Path fileName = (Path) event.context();
                    System.out.println("Создан новый файл: " + fileName);
                } else if (kind == StandardWatchEventKinds.ENTRY_MODIFY) {
                    Path fileName = (Path) event.context();
                    System.out.println("Файл изменен: " + fileName);
                    
                } else if (kind == StandardWatchEventKinds.ENTRY_DELETE) {
                    Path fileName = (Path) event.context();
                    System.out.println("Файл удален: " + fileName);
                }
            }

            key.reset();
        }
    }
}