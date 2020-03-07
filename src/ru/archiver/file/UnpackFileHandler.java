package ru.archiver.file;

import ru.archiver.config.Constants;
import ru.archiver.unpack.Unpack;

import java.io.File;
import java.io.FileInputStream;

public class UnpackFileHandler {

    public static void unpack(String name) {

        File file = new File(name);
        if (file.isFile()) {
            try {
                FileInputStream inputStream = new FileInputStream(file);
                new Unpack(inputStream).run();
                inputStream.close();
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println(Constants.OPEN_ERROR);
            }
        } else
            System.out.printf("%s is not a file", file.getName());
    }
}
