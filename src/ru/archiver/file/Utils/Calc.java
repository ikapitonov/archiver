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
}
