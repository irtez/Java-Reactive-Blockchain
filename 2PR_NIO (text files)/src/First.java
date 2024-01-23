import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class First {
    public static void main(String[] args) {
        // Путь к файлу
        String filePath = "test.txt";

        try {
            Path file = Paths.get(filePath);
            // Чтение содержимого файла и вывод на стандартный поток вывода
            List<String> fileContent = Files.readAllLines(file);
            for (String line : fileContent) {
                System.out.println(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}