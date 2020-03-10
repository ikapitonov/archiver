package ru.archiver.unpack;

import java.nio.ByteBuffer;
import java.util.LinkedList;

public class Output {

    public String name;
    public LinkedList<ByteBuffer> buffers;

    public Output(String name) {
        this.name = name;
        this.buffers = new LinkedList<>();
    }
}
 
