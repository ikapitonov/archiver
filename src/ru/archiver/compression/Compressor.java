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
        test();
    }

    public Overlap[] getResult () {
        return busy;// 1175
    }

    private void start(int howMuch) {
        for (int i = 0; i < length; ++i) {
            if (i > 0 && busy[i - 1] != null) {
                --i;
            }
            i = getStartIndex(howMuch, i);

            if (i == -1) {
                return ;
            }
            search(i, howMuch);
        }
    }

    private int getStartIndex(int howMuch, int i) {
        for (int k = i; k < howMuch + i && k < length; ++k) {
            if (busy[k] != null) {
                return getStartIndex(howMuch, busy[k].getEnd() + 1);
            }
        }

        return i;
    }

    private void search(int i, int howMuch) {
        int res;

        for (int j = i + howMuch; j < length - howMuch; ++j) {
            res = strstr(i, j, howMuch);

            if (res == 0) {
                includeItem(howMuch, j, i);
                j += howMuch - 1;
            }
            else if (res > 0) {
                j += res - 1;
            }
        }
    }

    private int strstr(int mainBuf, int searchFrom, int howMuch) {
        for (int i = searchFrom, j = 0; i < searchFrom + howMuch && i < length; ++i, ++j) {
            if (busy[i] != null) {
                return busy[i].getEnd();
            }
            if (array[i] != array[mainBuf + j]) {
                return -1;
            }
        }
        return 0;
    }

    private void includeItem (int howMuch, int start, int parentStart) {
        Overlap element = new Overlap(start, start + howMuch);
        Overlap parentElement = new Overlap(parentStart, parentStart + howMuch);

        LinkedList <LinkedList<Overlap>> pointer = overlap.get(howMuch);

        for (LinkedList<Overlap> item : pointer) {
            Overlap lap = item != null && !item.isEmpty() ? item.getFirst() : null;

            if (lap != null && equalsBytes(start, lap.getStart(), howMuch)) {
                item.add(parentElement);
                item.add(element);
                element.setAddress(lap.getStart());
                busy[element.getStart()] = element;

                return ;
            }
        }

        LinkedList<Overlap> newElement = new LinkedList();
        newElement.addFirst(parentElement);
        newElement.add(element);
        element.setAddress(parentElement.getStart());

        pointer.add(newElement);
        busy[element.getStart()] = element;
        busy[parentElement.getStart()] = parentElement;
    }

    public boolean equalsBytes(int start, int parentStart, int howMuch) {
        for (int i = 0; i < howMuch; ++i) {
            if (array[start + i] != array[parentStart + i]) {
                return false;
            }
        }
        return true;
    }

    private void test () {
        for (int i = 0; i < busy.length; i++) {
            if (busy[i] != null) {
                System.out.println(busy[i].getStart() + " --- " + busy[i].getEnd() + " === " + (busy[i].getEnd() - busy[i].getStart()) + " RELATION " + busy[i].getAddress());
            }
        }
        System.out.println("--\n\n\n--");
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
//                        System.out.println("Start= " + aa.getStart() + " -- End= " + aa.getEnd() + " -- Index " + i + " -- Counter= " + counter + " RELATION " + aa.getAddress());
//                    }
//                }
//                ++counter;
//            }
//        }
    }
}
