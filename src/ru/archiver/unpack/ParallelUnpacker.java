//package ru.archiver.unpack;
//
//import ru.archiver.config.Constants;
//
//import java.io.FileInputStream;
//import java.nio.ByteBuffer;
//import java.util.Arrays;
//import java.util.LinkedList;
//
//public class ParallelUnpacker extends Thread {
//
//    private FileInputStream inputStream;
//    private SynchronizedIO synchronizedIO;
//    private int unpackerID;
//    private int previousID;
//    private byte[] buff;
//    private int size;
//
//    public ParallelUnpacker(FileInputStream inputStream, SynchronizedIO synchronizedIO) {
//        this.inputStream = inputStream;
//        this.synchronizedIO = synchronizedIO;
//    }
//
//    @Override
//    public void run () {
//
//        buff = new byte[Constants.BUFF_SIZE];
//
//        while (synchronizedIO.read(this) != 0) {
//            ByteBuffer buffer = ByteBuffer.allocate(Constants.BUFF_SIZE);
//            Arrays.fill(buff, (byte) 0);
//            readBlock(buffer);
//            //file.buffers.add(buffer);
//        }
//    }
//
//    private void readBlock(ByteBuffer buffer) {
//
//        int pos = 0;
//        int addr;
//
//        while (pos < size)
//        {
//            if (buff[pos] > 0)
//            {
//                buffer.put(buff, pos + 1, buff[pos]);
//                pos += buff[pos] + 1;
//            }
//            else
//            {
//                addr = getAddr(buff, pos + 1);
//                System.out.printf("addr: %d, pos: %d, buff[pos]: %d\n", addr, pos, buff[pos]);
//                if (addr < 0 || addr > Constants.BUFF_SIZE)
//                {
//                    System.out.printf("Invalid addr: %d\n pos: %d\n", addr, pos);
//                    System.exit(0);
//                }
//                buffer.put(buff, addr, -buff[pos]);
//                pos += 3;
//            }
//        }
//    }
//
//
//
//    private int getAddr(byte arr[], int pos) {
//
//        ByteBuffer buffer = ByteBuffer.allocate(2);
//        buffer.put(arr, pos, 2);
//
//        int res = buffer.getShort(0);
//        return res;
//    }
//
//    public int getUnpackerID() {
//        return unpackerID;
//    }
//
//    public void setUnpackerID(int unpackerID) {
//        this.unpackerID = unpackerID;
//    }
//
//    public int getPreviousID() {
//        return previousID;
//    }
//
//    public void setPreviousID(int previousID) {
//        this.previousID = previousID;
//    }
//}
