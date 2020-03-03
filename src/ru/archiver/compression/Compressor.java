package ru.archiver.compression;

import ru.archiver.compression.utils.Overlap;
import ru.archiver.config.Constants;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;

public class Compressor {
    private ArrayList <LinkedList <LinkedList<Overlap>>> overlap;
    private Overlap[] busy;
    private byte[] array;
    private int length;

    public Compressor(byte[] array, int length) {
        this.array = array;
        this.length = length;
     //   System.out.println(System.currentTimeMillis());
        busy = new Overlap[length];
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
    //    System.out.println(System.currentTimeMillis());
        test();
    }

    private void start(int howMuch) {
        for (int i = 0; i < length; ++i) {
            search(i, howMuch);
        }
    }

    private void search(int i, int howMuch) {
        int res;

        for (int j = i + howMuch; j < length - howMuch; ++j) {
            res = strstr(i, j, howMuch);

            if (res == 0) {
                includeItem(howMuch, j, i);
                j += howMuch;
            }
            else if (res > 0) {
                j += res;
            }
        }
    }

    private int strstr(int mainBuf, int searchFrom, int howMuch) {
        for (int i = searchFrom, j = 0; i < searchFrom + howMuch && i < length; ++i, ++j) {
            if (busy[i] != null || busy[j] != null) {
                return busy[i].getEnd();
            }
            if (array[i] != array[mainBuf + j]) {
                return -1;
            }
        }

        return 0;
    }

    private void includeItem (int howMuch, int start, int mainStart) {
        Overlap element = new Overlap(start, start + howMuch);
        LinkedList <LinkedList<Overlap>> pointer = overlap.get(howMuch);
        Iterator <LinkedList<Overlap>> iter = pointer.iterator();

        while (iter.hasNext()) {
            LinkedList<Overlap> item = iter.next();
            Overlap lap = item != null && !item.isEmpty() ? item.getFirst() : null;

            if (lap != null && Arrays.equals(array, start, start + howMuch, array, lap.getStart(), lap.getEnd())) {
                item.add(element);
                element.setAddress(lap.getStart());
                busy[element.getStart()] = element;

                return ;
            }
        }

        LinkedList<Overlap> newElement = new LinkedList();
        newElement.add(element);

        pointer.add(newElement);
        busy[element.getStart()] = element;
    }

    private void test () {
        for (int i = 0; i < busy.length; i++) {
            if (busy[i] != null) {
                System.out.println(busy[i].getStart() + " --- " + busy[i].getEnd() + " === " + (busy[i].getEnd() - busy[i].getStart()));
            }
        }
//        System.out.println("--\n\n\n--");
//        int counter;
//        for (int i = 0; i < Constants.COMPRESS_MAX; ++i) {
//            LinkedList <LinkedList<Overlap>> pointer = overlap.get(i);
//
//            Iterator <LinkedList<Overlap>> iter = pointer.iterator();
//            counter = 0;
//            while (iter.hasNext()) {
//                LinkedList<Overlap> item = iter.next();
//
//                if (!item.isEmpty()) {
//                    Iterator <Overlap> lala = item.iterator();
//
//                    while (lala.hasNext()) {
//                        Overlap aa = lala.next();
//
//                        System.out.println("Start= " + aa.getStart() + " -- End= " + aa.getEnd() + " -- Index " + i + " -- Counter= " + counter);
//                    }
//                }
//                ++counter;
//            }
//        }
    }
}
