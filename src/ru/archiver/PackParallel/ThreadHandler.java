package ru.archiver.PackParallel;

import ru.archiver.config.Constants;
import ru.archiver.file.FileHandler;

public class ThreadHandler {
    private Thread[] items;
    private FileHandler.FileInfo fileInfo;
    private int threadCount;

    public ThreadHandler(FileHandler.FileInfo fileInfo) {
        this.fileInfo = fileInfo;
        threadCount = getCountThreads();
        items = new Thread[threadCount];
    }

    public void init() {
        for (int i = 0; i < threadCount; i++) {
            items[i] = new Thread(new ThreadItem(fileInfo, i, items));

            items[i].start();
        }

        try {
            for (int i = 0; i < threadCount; i++) {
                items[i].join();
            }
        }
        catch (InterruptedException e) {
            System.out.println(Constants.FATAL_ERROR);
            System.exit(1);
        }
    }

    private int getCountThreads() {
        return fileInfo.getBuffer().length >= Constants.MAX_THREAD ? Constants.MAX_THREAD : fileInfo.getBuffer().length;
    }
}
