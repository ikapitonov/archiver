package ru.archiver.unpack;

import ru.archiver.config.Constants;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Scanner;

public class Unpack {

    private FileInputStream inputStream;
    private LinkedList<Output> files;

    public Unpack(FileInputStream inputStream) {

        this.inputStream = inputStream;
        files = new LinkedList<>();
    }

    public void run() {

        int length;
        boolean flag = true;
        String name;

        while ((name = getName(inputStream)) != null)
            readFile(name);
        for (Output file : files) {
            writeFile(file);
        }
    }

    private void readFile(String name) {

        byte[] buff = new byte[Constants.BUFF_SIZE];
        Output file  = new Output(name);
        files.add(file);

        int countBlocks = readInt(inputStream);
        if (countBlocks < 0)
        {
            System.out.println("countBlocks < 0");
            System.exit(0);
        }
        for (int i = 0; i < countBlocks; i++) {

            ByteBuffer buffer = ByteBuffer.allocate(Constants.BUFF_SIZE);
            file.buffers.add(buffer);
            int size = readInt(inputStream);
            if (size > Constants.BUFF_SIZE)
            {
                System.out.printf("Size = %d > BUFF_SIZE\n", size);
                System.exit(0);
            }
            Arrays.fill(buff, (byte) 0);
            read(inputStream, buff, size);
            readBlock(buff, buffer, size);
            System.out.println("Hello");
        }
    }

    private void readBlock(byte[] buff, ByteBuffer buffer, int size) {

        int pos = 0;
        int addr;

        while (pos < size)
        {
//            System.out.printf("pos: %d, buff[pos]: %d\n", pos, buff[pos]);
            if (buff[pos] > 0)
            {
                buffer.put(buff, pos + 1, buff[pos]);
                pos += buff[pos] + 1;
            }
            else
            {
                addr = getAddr(buff, pos + 1);
                System.out.printf("addr: %d, pos: %d, buff[pos]: %d\n", addr, pos, buff[pos]);
                if (addr < 0 || addr > Constants.BUFF_SIZE)
                {
                    System.out.printf("Invalid addr: %d\n pos: %d\n", addr, pos);
                    System.exit(0);
                }
                buffer.put(buff, addr, -buff[pos]);
//                for (int i = 0; i < -buff[pos]; i++) {
//                    System.out.printf("%c", buff[addr + i]);
//                }
//                System.out.print(new String(buffer.array()));
                pos += 3;
            }

        }
    }

    private void writeFile(Output file) {

        File newFile = new File(file.name);
        FileOutputStream out;
        try {
            newFile.createNewFile();
            out = new FileOutputStream(newFile);

            for (ByteBuffer buffer : file.buffers) {

                out.write(buffer.array(), 0, buffer.position());
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            System.exit(0);
        }
    }

    private int readInt(FileInputStream inputStream) {

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

//        for (byte b : arr)
//            System.out.printf("%x\n", b);

        ByteBuffer buff = ByteBuffer.wrap(arr);

        int res = buff.getInt();
        return res;
    }

    private int readShort(FileInputStream inputStream) {
        Scanner scanner = new Scanner(inputStream);
        int num = scanner.nextInt();
        return num;
    }

    private int read(FileInputStream in, byte[] buff, int size) {

        int ret;

        try {
            ret = in.read(buff, 0, size);
        }
        catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
        return ret;
    }

    private int getAddr(byte arr[], int pos) {

        ByteBuffer buffer = ByteBuffer.allocate(2);
        buffer.put(arr, pos, 2);

        int res = buffer.getShort(0);
        return res;
    }

    private String getName(FileInputStream in) {

        byte[] str = new byte[9];
        int count;

        try {
            count = in.read(str);
            if (count < 0)
                return null;
        }
        catch (Exception e){
            e.printStackTrace();
            System.exit(0);
        }
        str[8] = 'x';
        return new String(str);
    }
}
