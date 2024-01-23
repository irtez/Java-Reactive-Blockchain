package Second;

import org.apache.commons.io.FileUtils;

import java.io.*;

public class ApacheCommons {
    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();
        try {
            FileUtils.copyFile(new File("100MB.txt"), new File("copy.txt"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        long endTime = System.currentTimeMillis();
        System.out.println("Time taken by Apache Commons IO: " + (endTime - startTime) + " ms");
    }
}