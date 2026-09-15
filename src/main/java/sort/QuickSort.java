package sort;

import collection.CustomCollection;

import java.util.Comparator;
import java.util.Objects;

public final class QuickSort {

    private QuickSort() {
    }

    public static <T> void sort(CustomCollection<T> collection, Comparator<? super T> comparator) {
        Objects.requireNonNull(collection, "error collection");
        Objects.requireNonNull(comparator, "error comparator");
        quickSort(collection, 0, collection.size() - 1, comparator);
    }

    public static <T> void quickSort(CustomCollection<T> collection, int low, int high, Comparator<? super T> comparator) {
        if (low < high) {

            T pivot = collection.get(high);
            int i = low - 1;

            for (int j = low; j < high; j++) {
                if (comparator.compare(collection.get(j), pivot) < 0) {
                    i++;

                    T temp = collection.get(i);
                    collection.set(collection.get(j), i);
                    collection.set(temp, j);
                }
            }

            T temp = collection.get(i + 1);
            collection.set(collection.get(high), i + 1);
            collection.set(temp, high);

            quickSort(collection, low, i, comparator);
            quickSort(collection, i + 2, high, comparator);
        }
    }
}