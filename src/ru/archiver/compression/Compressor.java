package ru.archiver.compression;

import ru.archiver.compression.utils.Overlap;
import ru.archiver.config.Constants;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;

public class Compressor {
    private final byte[] array;
    private final int length;

    private ArrayList <LinkedList <LinkedList<Overlap>>> overlap;
    private boolean[] busy;

    public Compressor(final byte[] array, final int length) {
        this.array = array;
        this.length = length;

        busy = new boolean[length];
        overlap = new ArrayList(Constants.COMPRESS_MAX);

        for (int i = 0; i < Constants.COMPRESS_MAX; ++i) {
            LinkedList <LinkedList<Overlap>> list = new LinkedList();

            overlap.add(i, list);
            list.add(new LinkedList());
        }
    }

    public void run () {
        for (int i = Constants.COMPRESS_MAX - 1; i >= Constants.COMPRESS_MIN; --i) {
            start(i);
        }


        // ТЕСТЫ
//        Overlap[] over = getResult();
//
//        test();
//        testPovtor(over);
//        testPovtorShow(over);
    }

    public Overlap[] getResult () {
        Overlap[] result = new Overlap[length];

        for (int i = 0; i < Constants.COMPRESS_MAX; ++i) {
            LinkedList <LinkedList<Overlap>> pointer = overlap.get(i);

            Iterator <LinkedList<Overlap>> iter = pointer.iterator();
            while (iter.hasNext()) {
                LinkedList<Overlap> item = iter.next();

                if (!item.isEmpty()) {
                    Iterator <Overlap> iterator = item.iterator();

                    while (iterator.hasNext()) {
                        Overlap over = iterator.next();

                        result[over.getStart()] = over;
                    }
                }
            }
        }
        setNewIndexes(result);

        return result;
    }

    private int getEmptyLenght(int start, Overlap[] overlaps) {
        int i = 0;

        while (i + start < length && overlaps[i + start] == null) {
            ++i;
        }
        return i;
    }

    private void setNewIndexes(Overlap[] overlaps) {
        int shift = 0;
        int tmp;

        for (int i = 0; i < length; i++) {
            if (overlaps[i] == null) {
                tmp = getEmptyLenght(i, overlaps);
                i += tmp - 1;
                shift += tmp % Constants.MAX_BYTE > 0 ? tmp / Constants.MAX_BYTE + 1 : tmp / Constants.MAX_BYTE;
                shift += tmp;
            }
            else if (overlaps[i] != null && overlaps[i].getAddress() != -1) {
                shift += 1 + Constants.LENGTH_ADDRESS;
                i = overlaps[i].getEnd() - 1;
            }
            else if (overlaps[i] != null && overlaps[i].getAddress() == -1) {
                overlaps[i].setNewStart(shift + 1);
                shift += 1 + (overlaps[i].getEnd() - overlaps[i].getStart());
                i = overlaps[i].getEnd() - 1;
            }
            else {
                System.out.println("ERRRRRRROOOOOORRR");
            }
        }
    }

    private void start(int howMuch) {
        for (int i = 0; i < length; ++i) {
            i = getStartIndex(howMuch, i);

            if (i >= length - howMuch) {
                return ;
            }
            search(i, howMuch);
        }
    }

    private int getStartIndex(int howMuch, int i) {
        for (int k = i; k < howMuch + i && k < length - howMuch; ++k) {
            if (busy[k]) {
                while (busy[k]) {
                    ++k;
                }
                return getStartIndex(howMuch, k);
            }
        }

        return i;
    }

    private void search(int i, int howMuch) {
        for (int j = i + howMuch; j < length - howMuch; ++j)
        {
            if (strstr(i, j, howMuch)) {
                includeItem(howMuch, j, i);
                j += howMuch - 1;
            }
        }
    }

    private boolean strstr(int mainBuf, int searchFrom, int howMuch) {
        for (int i = searchFrom, j = 0; i < searchFrom + howMuch && i < length; ++i, ++j) {
            if (array[i] != array[mainBuf + j] || busy[i]) {
                return false;
            }
        }
        return true;
    }

    private void includeItem (int howMuch, int start, int parentStart) {
        Overlap element = new Overlap(start, start + howMuch);
        Overlap parentElement = new Overlap(parentStart, parentStart + howMuch);

        LinkedList <LinkedList<Overlap>> pointer = overlap.get(howMuch);

        for (LinkedList<Overlap> item : pointer) {
            Overlap lap = item != null && !item.isEmpty() ? item.getFirst() : null;

            if (lap != null && equalsBytes(start, lap.getStart(), howMuch)) {
                item.add(element);
                element.setAddress(lap.getStart());
                setBusy(element.getStart(), element.getEnd());

                return ;
            }
        }

        LinkedList<Overlap> newElement = new LinkedList();
        newElement.addFirst(parentElement);
        newElement.add(element);
        element.setAddress(parentElement.getStart());

        pointer.add(newElement);
        setBusy(element.getStart(), element.getEnd());
        setBusy(parentElement.getStart(), parentElement.getEnd());
    }

    private void setBusy(int start, int end) {
        for (int i = start; i < end; i++) {
            busy[i] = true;
        }
    }

    private boolean equalsBytes(int start, int parentStart, int howMuch) {
        for (int i = 0; i < howMuch; ++i) {
            if (array[start + i] != array[parentStart + i]) {
                return false;
            }
        }
        return true;
    }

    private void test () {
        System.out.println("--\n\n\n--");
        int counter;

        for (int i = 0; i < Constants.COMPRESS_MAX; ++i) {
            LinkedList <LinkedList<Overlap>> pointer = overlap.get(i);

            Iterator <LinkedList<Overlap>> iter = pointer.iterator();
            counter = 0;
            while (iter.hasNext()) {
                LinkedList<Overlap> item = iter.next();

                if (!item.isEmpty()) {
                    Iterator <Overlap> lala = item.iterator();

                    while (lala.hasNext()) {
                        Overlap aa = lala.next();

                        System.out.println("Start= " + aa.getStart() + " -- End= " + aa.getEnd() + " -- Index " + i + " -- Counter= " + counter + " RELATION " + aa.getAddress());
                    }
                }
                ++counter;
            }
        }
    }

    private void testPovtor (Overlap[] over) {
        for (int i = 0; i < length; i++) {
            if (over[i] != null) {

                for (int j = over[i].getStart() + 1; j < over[i].getEnd(); j++) {
                    if (over[j] != null) {
                        System.out.println("ERROR " + over[j].getStart());
                    }
                }
            }
        }
    }

    private void testPovtorShow(Overlap[] over) {
        for (int i = 0; i < over.length; i++) {
            if (over[i] != null) {
                System.out.println(over[i].getStart() + " --- " + over[i].getEnd() + " === " + (over[i].getEnd() - over[i].getStart()) + " RELATION " + over[i].getAddress());
            }
        }
    }
}
