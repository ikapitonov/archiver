package ru.archiver.file;

import ru.archiver.unpack.Unpack;

import java.io.File;
import java.io.FileInputStream;

public class UnpackFileHandler {

    public static void unpack(String[] args) {

        File file;

        for (int i = 0; i < args.length; i++) {
            file = new File(args[i]);
            if (file.isFile()) {
                try {
                    FileInputStream inputStream = new FileInputStream(file);
                    new Unpack(inputStream).run();
                    inputStream.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else
                System.out.printf("%s is not a file", file.getName());
        }
    }
}
