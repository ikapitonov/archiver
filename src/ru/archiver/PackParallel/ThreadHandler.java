package ru.archiver.PackParallel;

import ru.archiver.config.Constants;
import ru.archiver.file.FileHandler;

public class ThreadHandler {
    FileHandler.FileInfo fileInfo;

    public ThreadHandler(FileHandler.FileInfo fileInfo) {
        this.fileInfo = fileInfo;
    }

    public void init() {
        int threadCount = getCountThreads();
        Thread[] items = new Thread[threadCount];

        for (int i = 0; i < threadCount; i++) {
            items[i] = new Thread(new ThreadItem(fileInfo, i));

            items[i].start();
        }

        try {
            for (int i = 0; i < threadCount; i++) {
                items[i].join();
            }
        }
        catch (InterruptedException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    private int getCountThreads() {
        return fileInfo.getBuffer().length >= Constants.MAX_THREAD ? Constants.MAX_THREAD : fileInfo.getBuffer().length;
    }
}
