//package ru.archiver.unpack;
//
//import java.io.FileInputStream;
//import java.nio.ByteBuffer;
//
//public class SynchronizedIO {
//
//    private FileInputStream inputStream;
//    private int lastUser;
//    public int readInt() {
//
//
//    }
//
//    public synchronized int read(ParallelUnpacker unpacker) {
//
//
//        if (lastUser != unpacker.getPreviousID()) {
//            try {
//                wait();
//            }
//            catch (InterruptedException e) {
//                e.printStackTrace();
//                System.exit(0);
//            }
//        }
//
//        byte[] arr = new byte[4];
//
//        try {
//            inputStream.read(arr);
//        }
//        catch (Exception e)
//        {
//            System.out.println("Cannot read int");
//            e.printStackTrace();
//            System.exit(0);
//        }
//
//        ByteBuffer buffer = ByteBuffer.wrap(arr);
//
//        int size = buffer.getInt();
//
//        int ret;
//
//        try {
//            ret = inputStream.read(unpacker.buff, 0, size);
//        }
//        catch (Exception e) {
//            e.printStackTrace();
//            return -1;
//        }
//        return ret;
//    }
//}
