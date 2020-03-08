package ru.archiver;

import ru.archiver.config.Constants;
import ru.archiver.file.Pack;
import ru.archiver.file.UnpackFileHandler;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Main {

    public static void main(String[] argc) {

        if (argc.length == 1 && isUnpack(argc[0])) {
            System.out.print("Начинаю распаковывать. Время: ");
            printDate();

            UnpackFileHandler.unpack(argc[0]);

            System.out.print("Распаковка окончена. Время: ");
            printDate();
        }
        else if (argc.length > 0) {
            System.out.print("Начинаю сжимать. Время: ");
            printDate();

            new Pack(argc).run();

            System.out.print("Сжатие окончено. Время: ");
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

    public static void printDate() {
        DateTimeFormatter datef = DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm:ss.SSS");

        System.out.println(datef.format(LocalDateTime.now()));
    }
}