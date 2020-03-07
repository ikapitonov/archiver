package ru.archiver.unpack;

import ru.archiver.config.Constants;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.util.Iterator;

public class SynchronizedIO {

    private FileInputStream inputStream;
    private FileOutputStream outputStream;
    private File file;
    private ByteBuffer[] bufferArray;
    private volatile int lastReadUser;
    private volatile int lastWriteUser;
    private int lastWriter;
    private int blocksCount;
    private boolean start = true;

    public SynchronizedIO(FileInputStream inputStream, String name, int blocksCount)
    {
        this.inputStream = inputStream;
        this.file = new File("test.unpack/" + name);
        this.bufferArray = new ByteBuffer[Constants.MAX_THREAD];
        lastReadUser = Constants.MAX_THREAD - 1;
        lastWriteUser = Constants.MAX_THREAD - 1;
        this.blocksCount = blocksCount;
        try {
            this.file.createNewFile();
            this.outputStream = new FileOutputStream(this.file);
        }
        catch (Exception e) {
            e.printStackTrace();
            System.exit(0);
        }
    }

    public synchronized int read(int id, int previous, byte[] buff) {
        System.out.println(">> " + id);
        while (lastReadUser != previous) {
            try {
                wait();
            }
            catch (InterruptedException e) {
                e.printStackTrace();
                System.exit(0);
            }
        }

        if (blocksCount == 0) {
            lastReadUser = id;
            notifyAll();
            return 0;
        }

        byte[] arr = new byte[4];
        try {
            inputStream.read(arr);
        }
        catch (Exception e)
        {
            System.out.println("Cannot read int");
            e.printStackTrace();
            System.exit(0);
        }
        System.out.println(id);

        ByteBuffer buffer = ByteBuffer.wrap(arr);

        int ret;
        try {
            ret = inputStream.read(buff, 0, buffer.getInt(0));
            blocksCount--;
        }
        catch (Exception e) {
            e.printStackTrace();
            System.exit(0);
            return  -1;
        }

        if (blocksCount == 0)
            lastWriter = id;

        lastReadUser = id;
        System.out.println("lu: " + lastReadUser);
        notifyAll();
        return ret;
    }

    public synchronized void write(int id, int previous, ByteBuffer buffer) {

        while (lastWriteUser != previous) {
            try {
                wait();
            }
            catch (InterruptedException e) {
                e.printStackTrace();
                System.exit(0);
            }
        }
        //System.out.println("---" + id);
        bufferArray[id] = buffer;
        if ((blocksCount != 0 && !start && id == Constants.MAX_THREAD - 1) ||
            blocksCount == 0 && id == lastWriter)
        {
            int max = blocksCount > 0 ? Constants.MAX_THREAD : lastWriter + 1;
            for (int i = 0; i < max; i++) {
                try {
                    outputStream.write(bufferArray[i].array(), 0, bufferArray[i].position());
                }
                catch (Exception e) {
                    e.printStackTrace();
                    System.exit(0);
                }
            }
        }
        start = false;
        lastWriteUser = id;
        notifyAll();
    }
}
