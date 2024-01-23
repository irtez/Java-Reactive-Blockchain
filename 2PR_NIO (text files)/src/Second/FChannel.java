package Second;

import java.io.*;
import java.nio.channels.*;

public class FChannel {
    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();
        try (FileChannel sourceChannel = new FileInputStream("100MB.txt").getChannel();
             FileChannel destChannel = new FileOutputStream("copy.txt").getChannel()) {
            destChannel.transferFrom(sourceChannel, 0, sourceChannel.size());
        } catch (IOException e) {
            e.printStackTrace();
        }
        long endTime = System.currentTimeMillis();
        System.out.println("Time taken by FileChannel: " + (endTime - startTime) + " ms");
    }
}