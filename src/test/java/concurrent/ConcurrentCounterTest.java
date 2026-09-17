package concurrent;

import collection.CustomCollection;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ConcurrentCounterTest {

    @Test
    void shouldCountOccurrences() {
        CustomCollection<String> collection = new CustomCollection<>();

        collection.add("A");
        collection.add("B");
        collection.add("A");
        collection.add("C");
        collection.add("A");

        int result = ConcurrentCounter.count(collection, "A", 2);

        assertEquals(3, result);
    }

    @Test
    void shouldReturnZeroWhenElementIsAbsent() {
        CustomCollection<String> collection = new CustomCollection<>();

        collection.add("A");
        collection.add("B");
        collection.add("C");

        int result = ConcurrentCounter.count(collection, "X", 3);

        assertEquals(0, result);
    }

    @Test
    void shouldThrowWhenThreadCountIsZero() {
        CustomCollection<String> collection = new CustomCollection<>();

        assertThrows(
                IllegalArgumentException.class,
                () -> ConcurrentCounter.count(collection, "A", 0)
        );
    }

    @Test
    void shouldThrowWhenThreadCountIsNegative() {
        CustomCollection<String> collection = new CustomCollection<>();

        assertThrows(
                IllegalArgumentException.class,
                () -> ConcurrentCounter.count(collection, "A", -1)
        );
    }

    @Test
    void shouldThrowWhenCollectionIsNull() {
        assertThrows(
                NullPointerException.class,
                () -> ConcurrentCounter.count(null, "A", 2)
        );
    }

    @Test
    void shouldThrowWhenElementIsNull() {
        CustomCollection<String> collection = new CustomCollection<>();

        assertThrows(
                NullPointerException.class,
                () -> ConcurrentCounter.count(collection, null, 2)
        );
    }
}