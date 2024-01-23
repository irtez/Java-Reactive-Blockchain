package Second;

import java.io.*;
import java.nio.file.*;

public class FilesClass {
    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();
        try {
            Files.copy(Paths.get("100MB.txt"), Paths.get("copy.txt"), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }
        long endTime = System.currentTimeMillis();
        System.out.println("Time taken by Files class: " + (endTime - startTime) + " ms");
    }
}