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
    }


    public void run () {
        int res = Calc.byteBufferSize(fileInfo.getFile().length());
        long counter;

        if (res == 0) {
            isEmpty = true;
            return ;
        }
        fileInfo.setBuffer(new byte[res][]);

        for (int i = 0; i < res; i++) {
//            fileInfo.setBuffer(fileInfo.getBuffer()[i][]);
//
//            counter;
        }
    }

    public FileInfo getFileInfo() {
        return fileInfo;
    }
}
