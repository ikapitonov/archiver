package ru.archiver.compression.utils;

public class ResultCompression {
    private int lenght;
    private byte[] array;

    public ResultCompression(byte[] array, int lenght) {
        this.lenght = lenght;
        this.array = array;
    }

    public int getLenght() {
        return lenght;
    }

    public void setLenght(int lenght) {
        this.lenght = lenght;
    }

    public byte[] getArray() {
        return array;
    }

    public void setArray(byte[] array) {
        this.array = array;
    }
}
