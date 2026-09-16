package collection;

import sort.QuickSort;

import java.util.*;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class CustomCollection<T> implements Iterable<T> {

    private static final int DEFAULT_CAPACITY = 10;

    private int size;

    private Object[] array;

    public CustomCollection() {
        this.array = new Object[DEFAULT_CAPACITY];
    }

    private void grow() {
        Object[] newArray = new Object[array.length * 2];
        System.arraycopy(array, 0, newArray, 0, size);
        this.array = newArray;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public void add(T o) {
        if (size >= array.length) {
            grow();
        }
        array[size++] = o;
    }

    @SuppressWarnings("unchecked")
    public T get(int index) {
        if (index < size && index >= 0) {
            return (T) array[index];
        }
        throw new IndexOutOfBoundsException();
    }

    public void set(T o, int index) {
        if (index < size && index >= 0) {
            array[index] = o;
        } else {
            throw new IndexOutOfBoundsException();
        }
    }

    public void remove(int index) {
        if (index >= 0 && index < size) {
            for (int i = index; i < size - 1; i++) {
                array[i] = array[i + 1];
            }
            size--;
            array[size] = null;
        } else {
            throw new IndexOutOfBoundsException();
        }
    }

    public void sort(Comparator<? super T> comparator) {
        QuickSort.sort(this, comparator);
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<>() {

            int index = 0;

            @Override
            public boolean hasNext() {
                return index < size;
            }

            @Override
            @SuppressWarnings("unchecked")
            public T next() {
                if (hasNext()) {
                    return (T) array[index++];
                }
                throw new NoSuchElementException();
            }
        };
    }

    public Stream<T> stream() {
        return StreamSupport.stream(spliterator(), false);
    }
}
