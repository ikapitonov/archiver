package ru.archiver.compression.utils;

public class Overlap {
    private int start;
    private int end;
    private int address = -1;
    private int newStart;

    public Overlap(int start, int end) {
        this.start = start;
        this.end = end;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getEnd() {
            return end;
    }

    public void setEnd(int end) {
        this.end = end;
    }

    public int getAddress() {
        return address;
    }

    public void setAddress(int address) {
        this.address = address;
    }

    public int getNewStart() {
        return newStart;
    }

    public void setNewStart(int newStart) {
        this.newStart = newStart;
    }
}
 
