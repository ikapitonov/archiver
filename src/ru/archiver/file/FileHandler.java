package ru.archiver.file;

import ru.archiver.PackParallel.ThreadHandler;
import ru.archiver.compression.utils.Helpers;
import ru.archiver.compression.utils.ResultCompression;
import ru.archiver.config.Constants;
import ru.archiver.file.Utils.Calc;

import java.io.*;

public class FileHandler {
    private final FileInfo fileInfo;
    private BufferedOutputStream bos;

    public FileHandler(File file) {
        this.fileInfo = new FileInfo(file);
    }

    public class FileInfo {
        private File file;
        private String fileName;
        private byte[][] buffer;
        private ResultCompression[] results;
        private long fileLen;

        public FileInfo (File file) {
            this.file = file;
            fileLen = file.length();
            fileName = file.getName();
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

        public ResultCompression[] getResults() {
            return results;
        }

        public void setResults(ResultCompression[] results) {
            this.results = results;
        }

        public void initResultArray() {
            results = new ResultCompression[buffer.length];
        }

        public String getFileName() {
            return fileName;
        }

        public long getFileLen() {
            return fileLen;
        }

        public void addResult(int index, ResultCompression result) {
            synchronized (this) {
                results[index] = result;
            }
        }

        // test
        public  void testResult () {
            int tmp;

            for (int i = 0; i < results.length; i++) {
                tmp = results[i] != null ? results[i].getLenght() : 0;

                System.out.println(tmp);
            }
        }
    }

    public boolean run(BufferedOutputStream bos) {
        this.bos = bos;
        long length = fileInfo.getFileLen();
        int res = Calc.byteBufferSize(length);
        long counter = 0;
        int tmp;

        if (res == 0) {
            System.out.println("Пустой файл: " + fileInfo.getFileName());
            writeInFile(true);
            return true;
        }
        fileInfo.setBuffer(new byte[res][]);

        for (int i = 0; i < res; ++i) {
            tmp = Calc.byteBufferPiece(length, counter);
            counter += tmp;
            fileInfo.setBufferPiece(i, tmp);
        }
        try {
            readFile();
        }
        catch (IOException e) {
            System.out.println(Constants.INVALIDE_READ + " " + fileInfo.getFileName());
            return false;
        }
        fileInfo.initResultArray();

        new ThreadHandler(fileInfo).init();

        writeInFile(false);
        return true;
    }

    private void writeInFile (boolean isEmpty) {
        try {
            bos.write(Helpers.getBytesFromInt((short) (fileInfo.getFileName().length())));
            bos.write((fileInfo.getFileName()).getBytes());

            if (isEmpty) {
                bos.write(Helpers.getBytesFromInt2(0));
            }
            else {
                bos.write(Helpers.getBytesFromInt2(fileInfo.getResults().length));
            }

            if (!isEmpty)
            {
                for (int index = 0; index < fileInfo.getResults().length; index++) {
                    bos.write(Helpers.getBytesFromInt2(fileInfo.getResults()[index].getLenght()));

                    bos.write(fileInfo.getResults()[index].getArray(), 0 , fileInfo.getResults()[index].getLenght());
                }
            }
        }
        catch (Exception e) {
            System.out.println(Constants.INVALIDE_WRITE);
            System.out.println(Constants.FATAL_ERROR);
            System.exit(1);
        }
    }

    private void readFile() throws IOException {
        FileInputStream fileInputStream = new FileInputStream(getFileInfo().getFile());

        for (int i = 0; i < fileInfo.getBuffer().length; i++) {
            fileInputStream.read(fileInfo.getBuffer()[i], 0, fileInfo.getBuffer()[i].length);
        }
        fileInputStream.close();
    }

    private FileInfo getFileInfo() {
        return fileInfo;
    }
}
 
