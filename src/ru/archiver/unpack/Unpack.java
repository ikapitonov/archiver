package ru.archiver.unpack;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.util.LinkedList;
import java.util.Scanner;

public class Unpack {

    private FileInputStream inputStream;
    private FileOutputStream outputStream;
    private File outFile;
    private int blocksCount;
    private LinkedList<ByteBuffer> bufferList;

    public Unpack(FileInputStream inputStream) {

        this.inputStream = inputStream;

    }

    public void run() {

        blocksCount = readInt(inputStream);
        
    }

    private int readInt(FileInputStream inputStream) {
        Scanner scanner = new Scanner(inputStream);
        int num = scanner.nextInt();
        return num;
    }
}
