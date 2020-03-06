package ru.archiver.file.Utils;

import ru.archiver.config.Constants;

public class Calc {

    public static int byteBufferSize (long size) {
        if (size == 0) {
            return 0;
        }

        return size % Constants.BUFF_SIZE > 0 ?
                (int) (size / Constants.BUFF_SIZE + 1) :
                (int) (size / Constants.BUFF_SIZE);
    }

    public static int byteBufferPiece(long size, long counter) {
        long res = size - counter;
        int ret = res > Constants.BUFF_SIZE ? Constants.BUFF_SIZE : (int) res;

        return ret;
    }
}
