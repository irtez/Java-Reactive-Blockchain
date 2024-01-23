import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class Third {
    public static short calculateChecksum(String filePath) {
        short checksum = 0;

        try (FileInputStream fis = new FileInputStream(filePath);
             FileChannel channel = fis.getChannel()) {

            ByteBuffer buffer = ByteBuffer.allocate(2); // 2 байта для хранения 16-битной контрольной суммы
            int bytesRead;

            while ((bytesRead = channel.read(buffer)) != -1) {
                buffer.flip(); // Переключаем буфер в режим чтения
                while (buffer.remaining() >= 2) {
                    checksum ^= buffer.getShort(); // Вычисляем XOR сумму 16-битных значений в буфере
                }
                buffer.compact(); // Переключаем буфер в режим записи для следующего чтения
            }

            buffer.flip();
            while (buffer.hasRemaining()) {
                checksum ^= buffer.get(); // Если остались одиночные байты, обработаем их
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return checksum;
    }

    public static void main(String[] args) {
        String filePath = "test.txt"; // Замените путь к файлу на путь к вашему файлу
        short checksum = calculateChecksum(filePath);
        System.out.println("16-битная контрольная сумма файла: " + checksum);
    }
}
