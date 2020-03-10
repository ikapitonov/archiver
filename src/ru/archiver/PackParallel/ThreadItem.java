package ru.archiver.PackParallel;

import ru.archiver.compression.Compressor;
import ru.archiver.compression.Squeezing;
import ru.archiver.compression.utils.ResultCompression;
import ru.archiver.config.Constants;
import ru.archiver.file.FileHandler;

public class ThreadItem implements Runnable {
    private FileHandler.FileInfo fileInfo;
    private int index;

    public ThreadItem(FileHandler.FileInfo fileInfo, int index) {
        this.fileInfo = fileInfo;
        this.index = index;
    }

    @Override
    public void run() {
        byte[][] buffer = fileInfo.getBuffer();
        byte[] tmp;

        for (int i = index; i < buffer.length; i += Constants.MAX_THREAD) {
            tmp = buffer[i];

            Compressor compressor = new Compressor(tmp, tmp.length);
            compressor.run();

            Squeezing squeezing = new Squeezing(compressor.getResult(), tmp, tmp.length);
            squeezing.run();
            ResultCompression result =  squeezing.getResult();

            fileInfo.addResult(i, result);
        }
    }
}
 
