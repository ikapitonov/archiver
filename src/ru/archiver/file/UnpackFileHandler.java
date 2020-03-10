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
                System.out.println(Constants.OPEN_ERROR);
                System.exit(1);
            }
        } else {
            System.out.printf("%s не является файлом\n", file.getName());
            System.exit(1);
        }
    }
}
