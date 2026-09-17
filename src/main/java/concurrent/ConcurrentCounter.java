package concurrent;

import collection.CustomCollection;

import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

public final class ConcurrentCounter {

    private ConcurrentCounter() {
        throw new AssertionError("error instance");
    }

    public static <T> int count(
            CustomCollection<T> collection,
            T element,
            int threadCount
    ) {
        Objects.requireNonNull(collection, "Коллекция не должна быть null");
        Objects.requireNonNull(element, "Элемент не должен быть null");

        if (threadCount <= 0) {
            throw new IllegalArgumentException("Количество потоков должно быть больше нуля");
        }

        AtomicInteger count = new AtomicInteger();

        int partSize = (collection.size() + threadCount - 1) / threadCount;

        try (ExecutorService executor = Executors.newFixedThreadPool(threadCount)) {

            for (int i = 0; i < threadCount; i++) {
                int start = i * partSize;
                int end = Math.min(
                        start + partSize,
                        collection.size()
                );

                if (start >= collection.size()) {
                    break;
                }

                executor.submit(() -> {
                    for (int j = start; j < end; j++) {
                        if (Objects.equals(collection.get(j), element)) {
                            count.incrementAndGet();
                        }
                    }
                });
            }
        }

        System.out.println("Количество вхождений элемента " + element
                + ": " + count.get());

        return count.get();
    }
}