package Second;

import java.io.*;

public class Inputoutput {
    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();
        try (FileInputStream fis = new FileInputStream("100MB.txt");
             FileOutputStream fos = new FileOutputStream("copy.txt")) {
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = fis.read(buffer)) != -1) {
                fos.write(buffer, 0, bytesRead);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        long endTime = System.currentTimeMillis();
        System.out.println("Time taken by FileInputStream/FileOutputStream: " + (endTime - startTime) + " ms");
    }
}