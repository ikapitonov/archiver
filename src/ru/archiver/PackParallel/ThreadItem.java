package ru.archiver.PackParallel;

import ru.archiver.compression.Compressor;
import ru.archiver.compression.Squeezing;
import ru.archiver.compression.utils.ResultCompression;
import ru.archiver.config.Constants;
import ru.archiver.file.FileHandler;

import java.io.IOException;

public class ThreadItem implements Runnable {
    private FileHandler.FileInfo fileInfo;
    private Thread[] relatedThreads;
    private int index;

    public ThreadItem(FileHandler.FileInfo fileInfo, int index, Thread[] relatedThreads) {
        this.relatedThreads = relatedThreads;
        this.fileInfo = fileInfo;
        this.index = index;
    }

    @Override
    public void run() {
        byte[][] buffer = fileInfo.getBuffer();
        byte[] tmp;

        commonReader(0);

        for (int i = index; i < buffer.length; i += Constants.MAX_THREAD) {
            tmp = buffer[i];

            Compressor compressor = new Compressor(tmp, tmp.length);
            compressor.run();

            Squeezing squeezing = new Squeezing(compressor.getResult(), tmp, tmp.length);
            squeezing.run();
            ResultCompression result =  squeezing.getResult();

            fileInfo.addResult(i, result);

            commonReader(i);
        }
    }

    private synchronized void commonReader(int indexCycle) {
        if (index != 0) { // first thread
            try {
                this.wait();
            }
            catch (InterruptedException e) {
                System.out.println(Constants.FATAL_ERROR);
                e.printStackTrace();
                System.exit(1);
            }
        }
        else {
            try {
                readFile(indexCycle);
            }
            catch (IOException e) {
                e.printStackTrace();
                System.out.println("ldfklkfldkldflkfd");
                System.exit(1);
            }

            for (int i = 1; i < relatedThreads.length; i++) {
                try {
                    relatedThreads[i].notify();
                }
                catch (IllegalMonitorStateException e) {
                    e.printStackTrace();
                    System.exit(1);
                }
            }
        }
    }

    private void readFile(int indexCycle) throws IOException {
        for (int i = indexCycle, j = 0; i < fileInfo.getBuffer().length && j < relatedThreads.length; ++i, ++j) {
            fileInfo.getFileInputStream().read(fileInfo.getBuffer()[i], 0, fileInfo.getBuffer()[i].length);
        }
    }
}
