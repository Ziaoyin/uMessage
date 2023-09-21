package datastructures.worklists;

import cse332.exceptions.NotYetImplementedException;
import cse332.interfaces.worklists.PriorityWorkList;

import java.util.NoSuchElementException;

/**
 * See cse332/interfaces/worklists/PriorityWorkList.java
 * for method specifications.
 */
public class MinFourHeapComparable<E extends Comparable<E>> extends PriorityWorkList<E> {
    /* Do not change the name of this field; the tests rely on it to work correctly. */
    private E[] data;
    private int size;

    public MinFourHeapComparable() {
        data = (E[]) new Comparable[10];
    }

    @Override
    public boolean hasWork() {
        return size != 0;
    }

    @Override
    public void add(E work) {
        if (data.length-1 == size) {
            E[] newData = (E[]) new Comparable[size*2];
            for (int i = 0; i < data.length; i++) {
                newData[i] = data[i];
            }
            this.data = newData;
        }
        int i = percolateUp(size, work);
        data[i] = work;
        size++;
    }

    @Override
    public E peek() {
        if (size == 0) {
            throw new NoSuchElementException();
        }
        return data[0];
    }

    @Override
    public E next() {
        if (size == 0) {
            throw new NoSuchElementException();
        }

        E toReturn = data[0];
        size--;
        int hole = percolateDown(0, data[size]);
        data[hole] = data[size];
        return toReturn;
    }

    private int percolateUp(int hole, E value) {
        while (hole > 0 && (value.compareTo(data[(hole-1)/4]) < 0)) {
            data[hole] = data[(hole-1)/4];
            hole = (hole-1)/4;
        }
        return hole;
    }

    private int percolateDown(int hole, E value) {
        while ((4*hole)+1 <= size) {
            int first = (4*hole)+ 1;
            int second = first + 1;
            int third = second + 1;
            int fourth = third + 1;

            int minChild = first;

            for (int i = first; i <= Math.min(fourth, size); i++) {
                if (data[minChild].compareTo(data[i]) > 0) {
                    minChild = i;
                }
            }

            if (data[minChild].compareTo(value) < 0) {
                data[hole] = data[minChild];
                hole = minChild;
            } else {
                break;
            }

        }
        return hole;
    }

//    private void percolateUp(int index) {
//        while (data[index].compareTo(data[(int)Math.floor((index-1)/4)]) < 0) {
//            int parentIndex = (int)Math.floor((index-1)/4);
//
//            E parentValue = data[parentIndex];
//            E currValue = data[index];
//
//            data[parentIndex] = currValue;
//            data[index] = parentValue;
//
//            index = parentIndex;
//        }
//    }
//
//    private void percolateDown(int index) {
//        int childOne = Math.min(size, (4*index)+ 1);
//        int childTwo = Math.min(size, (4*index)+ 2);
//        int childThree = Math.min(size, (4*index)+ 3);
//        int childFour = Math.min(size, (4*index)+ 4);
//
//        while ((data[index].compareTo(data[childOne]) > 0) && (data[index].compareTo(data[childTwo]) > 0)
//                && (data[index].compareTo(data[childThree]) > 0) && (data[index].compareTo(data[childFour]) >= 0)) {
//
//            int minIndex = childOne;
//            if (data[childOne].compareTo(data[childTwo]) > 0) {
//                minIndex = childTwo;
//            }
//
//            if (data[minIndex].compareTo(data[childThree]) > 0) {
//                minIndex = childThree;
//            }
//
//            if (data[minIndex].compareTo(data[childFour]) > 0) {
//                minIndex = childFour;
//            }
//
//            E replaced = data[minIndex];
//            data[minIndex] = data[index];
//            data[index] = replaced;
//
//            index = minIndex;
//            childOne = Math.min(size, (4*index)+ 1);
//            childTwo = Math.min(size, (4*index)+ 2);
//            childThree = Math.min(size, (4*index)+ 3);
//            childFour = Math.min(size, (4*index)+ 4);
//        }
//    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void clear() {
        size = 0;
    }
}
