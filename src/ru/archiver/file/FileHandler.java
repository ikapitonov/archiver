package ru.archiver.file;

import ru.archiver.file.Utils.Calc;

import java.io.File;

public class FileHandler {
    private final FileInfo fileInfo;
    private boolean isEmpty;

    public FileHandler(File file) {
        this.fileInfo = new FileInfo(file);
    }

    public class FileInfo {
        private File file;
        private byte[][] buffer;

        public FileInfo (File file) {
            this.file = file;
        }

        public File getFile() {
            return file;
        }

        public byte[][] getBuffer() {
            return buffer;
        }

        public void setBuffer(byte[][] buffer) {
            this.buffer = buffer;
        }

        public void setBufferPiece(int index, int howMuch) {
            buffer[index] = new byte[howMuch];
        }

        public void testBuf () {
            System.out.println("BUF main " + buffer.length);
            for (int i = 0; i < buffer.length; i++) {
                System.out.println("BUF size " + buffer[i].length);
            }
        }
    }


    public void run () {
        long length = fileInfo.getFile().length();
        int res = Calc.byteBufferSize(length);
        long counter = 0;
        int tmp;

        if (res == 0) {
            isEmpty = true;
            return ;
        }
        fileInfo.setBuffer(new byte[res][]);

        for (int i = 0; i < res; ++i) {
            tmp = Calc.byteBufferPiece(length, counter);
            counter += tmp;
            fileInfo.setBufferPiece(i, tmp);
        }
        
    }

    public FileInfo getFileInfo() {
        return fileInfo;
    }
}
