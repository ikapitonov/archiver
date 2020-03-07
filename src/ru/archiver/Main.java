package ru.archiver;

import ru.archiver.compression.Compressor;
import ru.archiver.compression.Squeezing;
import ru.archiver.compression.utils.Helpers;
import ru.archiver.compression.utils.ResultCompression;
import ru.archiver.config.Constants;
import ru.archiver.file.Pack;
import ru.archiver.unpack.Unpack;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Main {

    public static void main(String[] argc) {


        if (argc.length == 1 && isUnpack(argc[0])) {
            unpack(argc);
        }
        else if (argc.length > 0) {
            printDate();
            new Pack(argc).run();
            printDate();
        }
        else {
            System.out.println(Constants.INVALIDE_ARGS);
        }
    }

    private static boolean isUnpack(String name) {
        String[] arr = name.split("\\.", -1);

        return arr.length < 2 ? false : arr[arr.length - 1].equals(Constants.FILE_EXTENSION);
    }

    public static void unpack(String[] args) {
        try {
            File file = new File(args[0]);

            FileInputStream fileInputStream = new FileInputStream(file);
            new Unpack(fileInputStream).run();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void printDate (){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss.SSS");
        LocalDateTime now = LocalDateTime.now();
        System.out.println(dtf.format(now));
    }
}