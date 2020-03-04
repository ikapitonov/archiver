package ru.archiver.compression;

import ru.archiver.compression.utils.Helpers;
import ru.archiver.compression.utils.Overlap;
import ru.archiver.compression.utils.ResultCompression;
import ru.archiver.config.Constants;

public class Squeezing {
    private final Overlap[] overlap;
    private final byte[] array;
    private final int length;

    private byte[] result;
    private int index = 0;

    public Squeezing(Overlap[] overlap, byte[] array, int length) {
        this.overlap = overlap;
        this.array = array;
        this.length = length;

        result = new byte[length * 2];
    }

    public void run () {
        int res;

        for (int i = 0; i < length; i++) {
            res = getEmptyBytes(i);

            if (res == 0) {
                insertOverlap(i);
                i = overlap[i].getEnd() - 1;
            }
            else {
                insertEmptyBytes(i, res);
                i += res - 1;
            }
        }
    }

    public ResultCompression getResult() {
        return new ResultCompression(result, index);
    }

    private int getEmptyBytes(int start) {
        int i = 0;

        while (i + start < length && i < Constants.MAX_BYTE && overlap[i + start] == null) {
            ++i;
        }
        return i;
    }

    private void insertEmptyBytes(int indexOfArray, int howMuch) {
        byte info = (byte) (howMuch);
        int i = index;

        result[i] = info;
        ++i;

        for (int j = indexOfArray; j < howMuch; j++) {
            result[i] = array[j];
            ++i;
        }

        this.index = i;
    }

    private void insertOverlap (int indexOfOverlap) {
        int i = index;
        byte info = (byte) (overlap[indexOfOverlap].getEnd() - overlap[indexOfOverlap].getStart());
        int url;
        byte[] tmp;

        if (overlap[indexOfOverlap].getAddress() != -1) {
            url = recursiveSearch(overlap[indexOfOverlap].getAddress());

            info *= -1;

            result[i] = info;
            ++i;

            tmp = Helpers.getBytesFromInt(url);

            for (int j = 0; j < 4; ++j) {
                result[i] = tmp[j];
                ++i;
            }
            this.index = i;
        }
        else {
            result[i] = info;
            ++i;

            for (int j = overlap[indexOfOverlap].getStart(); j < overlap[indexOfOverlap].getEnd(); ++j) {
                result[i] = array[j];
                ++i;
            }

            this.index = i;
        }
    }

    private int recursiveSearch(int address) {
        int currentAddress = overlap[address].getAddress();

        return currentAddress != -1 ? recursiveSearch(currentAddress) : overlap[address].getStart();
    }
}
