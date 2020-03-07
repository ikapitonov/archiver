package ru.archiver.file;

import ru.archiver.config.Constants;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Iterator;
import java.util.LinkedList;

public class Pack {
    private String[] args;
    private LinkedList<FileHandler> list = new LinkedList<>();
    private int count;

    public Pack(final String[] args) {
        this.args = args;
    }

    public void run () {
        if (args.length == 1 && args[0].equals("-all")) {
            if (!AddAllFiles(new File(Constants.PATH))) {
                System.out.println(Constants.EMPTY_FILES_IN_DIR);
                System.exit(1);
            }
        }
        else {
            if (!ReadAllFiles(args)) {
                System.out.println(Constants.NOT_FOUNT_FILES_IN_ARGS);
                System.exit(1);
            }
        }
        startPacking();
    }

    private void startPacking() {
        Iterator<FileHandler> iterator = list.iterator();
        int counter = 0;

        try {
            File fileW = new File(Constants.FILE_NAME + "." + Constants.FILE_EXTENSION);
            BufferedOutputStream bos;
            FileOutputStream fos = new FileOutputStream(fileW);
            bos = new BufferedOutputStream(fos);

            if (bos != null) {

                while (iterator.hasNext()) {
                    if (!iterator.next().run(bos))
                        ++counter;
                }

                if (counter == list.size()) {
                    System.out.println("\n" + Constants.INVALIDE_PACK);
                    System.exit(1);
                }

                try  {
                    bos.flush();
                    bos.close();
                }
                catch(Exception e) {
                    System.out.println(Constants.FATAL_ERROR);
                    System.exit(1);
                }
            }
            else {
                System.out.println(Constants.FATAL_ERROR);
                System.exit(1);
            }
        }
        catch (Exception e) {
            System.out.println(Constants.FATAL_ERROR);
            System.exit(1);
        }
    }

    private boolean ReadAllFiles(final String[] filenames) {
        File file;
        int counter = 0;

        for (int i = 0; i < filenames.length; i++) {
            file = new File(filenames[i]);

            if (file.isFile()) {
                if (!validateFileSize(file) || !validateReader(file))
                    continue ;

                list.add(new FileHandler(file));
                ++counter;
            }
            else {
                itsNotFilePrint(file);
            }
        }

        this.count = counter;
        return counter > 0;
    }

    private boolean AddAllFiles (final File folder) {
        int counter = 0;
        final File files[] = folder.listFiles();

        for (final File file : files) {
            if (file.isFile() && validateFileSize(file)) {
                if (!validateFileSize(file) || !validateReader(file))
                    continue ;

                list.add(new FileHandler(file));
                ++counter;
            }
            else {
                itsNotFilePrint(file);
            }
        }

        this.count = counter;
        return count > 0;
    }

    private boolean validateReader(File file) {
        if (!file.canRead()) {
            System.out.println("Файл: " + file.getName() + " нельзя считать. Проверьте chmod");

            return false;
        }
        return true;
    }

    private boolean validateFileSize(File file) {
        if (!((int) file.length() < Constants.FILE_MAX_LENGTH)) {
            System.out.println("Файл: " + file.getName() + " превышает максимальный допустимый размер: " + (Constants.FILE_MAX_LENGTH - 1));
            return false;
        }
        return true;
    }

    private void itsNotFilePrint(File file) {
        System.out.println(file.getName() + " не является файлом");
    }
}
