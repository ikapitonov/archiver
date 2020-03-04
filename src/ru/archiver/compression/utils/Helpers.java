package ru.archiver.compression.utils;

import java.nio.ByteBuffer;

public class Helpers {

    public static byte[] getBytesFromInt(final short num) {
        return ByteBuffer.allocate(2).putShort(num).array();
    }
}
