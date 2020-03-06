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
                return ;
            }
        }
        else {
            if (!ReadAllFiles(args)) {
                System.out.println(Constants.NOT_FOUNT_FILES_IN_ARGS);
                return ;
            }
        }
        startPacking();
    }

    private void startPacking() {
        Iterator<FileHandler> iterator = list.iterator();
        try {
            File fileW = new File("text." + Constants.FILE_EXTENSION);
            BufferedOutputStream bos = null;
            FileOutputStream fos = new FileOutputStream(fileW);
            bos = new BufferedOutputStream(fos);
            while (iterator.hasNext()) {
                iterator.next().run(bos);
            }
            if(bos != null) {
                try  {
                    bos.flush();
                    bos.close();
                } catch(Exception e){
                    e.printStackTrace();
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    private boolean ReadAllFiles(final String[] filenames) {
        File file;
        int counter = 0;

        for (int i = 0; i < filenames.length; i++) {
            file = new File(filenames[i]);

            if (file.isFile()) {
                list.add(new FileHandler(file));
                ++counter;
            }
        }

        this.count = counter;
        return counter > 0;
    }

    private boolean AddAllFiles (final File folder) {
        int counter = 0;
        final File files[] = folder.listFiles();

        for (final File file : files) {
            if (file.isFile()) {
                list.add(new FileHandler(file));
                ++counter;
            }
        }

        this.count = counter;
        return count > 0;
    }
}
