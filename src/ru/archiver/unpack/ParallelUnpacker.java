package ru.archiver.unpack;

import ru.archiver.config.Constants;

import java.nio.ByteBuffer;
import java.util.Arrays;

public class ParallelUnpacker extends Thread {

    private SynchronizedIO synchronizedIO;
    private int unpackerID;
    private int previousID;
    private byte[] buff;
    private int size;

    public ParallelUnpacker(SynchronizedIO synchronizedIO, int unpackerID) {
        this.synchronizedIO = synchronizedIO;
        this.unpackerID = unpackerID;
        this.previousID = unpackerID > 0 ? unpackerID - 1 : Constants.MAX_THREAD - 1;
    }

    @Override
    public void run () {

        buff = new byte[Constants.BUFF_SIZE_UNPACK];

        while ((size = synchronizedIO.read(unpackerID, previousID, buff)) > 0) {
            ByteBuffer buffer = ByteBuffer.allocate(Constants.BUFF_SIZE_UNPACK);
            readBlock(buffer);
            synchronizedIO.write(unpackerID, previousID, buffer);
            Arrays.fill(buff, (byte) 0);
        }
    }

    private void readBlock(ByteBuffer buffer) {

        int pos = 0;
        int addr;

        while (pos < size)
        {
            if (buff[pos] > 0)
            {
                buffer.put(buff, pos + 1, buff[pos]);
                pos += buff[pos] + 1;
            }
            else
            {
                addr = getAddr(buff, pos + 1);
                if (addr < 0 || addr > Constants.BUFF_SIZE_UNPACK)
                {
                    System.out.printf("Invalid addr: %d\n pos: %d\n", addr, pos);
                    System.exit(0);
                }
                buffer.put(buff, addr, -buff[pos]);
                pos += 3;
            }
        }
    }

    private int getAddr(byte arr[], int pos) {

        ByteBuffer buffer = ByteBuffer.allocate(2);
        buffer.put(arr, pos, 2);

        int res = buffer.getShort(0);
        return res;
    }
}
 
