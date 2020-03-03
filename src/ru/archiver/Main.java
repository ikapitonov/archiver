package ru.archiver;

import ru.archiver.compression.Compressor;

import java.io.File;
import java.io.FileInputStream;

public class Main {

    public static void main (String[] argc) {
        try {
            File file = new File("/Users/sjamie/Desktop/push_swap.zip");

            byte[] bytes = new byte[(int) file.length()];

            FileInputStream fileInputStream = new FileInputStream(file);
            fileInputStream.read(bytes);
            fileInputStream.close();
            new Compressor(bytes, (int) 4096).run();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
