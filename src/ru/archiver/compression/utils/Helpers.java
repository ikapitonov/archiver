package ru.archiver.compression.utils;

import java.math.BigInteger;

public class Helpers {

    public static byte[] getBytesFromInt(int num) {
        BigInteger bigInt;

        bigInt = BigInteger.valueOf(num);
        return bigInt.toByteArray();
    }
}
