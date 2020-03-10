package ru.archiver.compression.utils;

public class ResultCompression {
    private int length;
    private byte[] array;

    public ResultCompression(byte[] array, int length) {
        this.length = length;
        this.array = array;
    }

    public int getLenght() {
        return length;
    }

    public void setLenght(int lenght) {
        this.length = lenght;
    }

    public byte[] getArray() {
        return array;
    }

    public void setArray(byte[] array) {
        this.array = array;
    }
}
 
