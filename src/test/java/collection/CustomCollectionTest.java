package collection;

import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CustomCollectionTest {

    @Test
    void shouldAddElement() {
        CustomCollection<String> collection = new CustomCollection<>();

        collection.add("String");

        assertEquals(1, collection.size());
        assertEquals("String", collection.get(0));
    }

    @Test
    void shouldAddMultipleElements() {
        CustomCollection<String> collection = new CustomCollection<>();

        collection.add("One");
        collection.add("Two");
        collection.add("Three");

        assertEquals(3, collection.size());
        assertEquals("One", collection.get(0));
        assertEquals("Two", collection.get(1));
        assertEquals("Three", collection.get(2));
    }

    @Test
    void shouldSetElement() {
        CustomCollection<String> collection = new CustomCollection<>();

        collection.add("One");
        collection.add("Two");

        collection.set("Updated", 1);

        assertEquals("One", collection.get(0));
        assertEquals("Updated", collection.get(1));
    }

    @Test
    void shouldRemoveElement() {
        CustomCollection<String> collection = new CustomCollection<>();

        collection.add("One");
        collection.add("Two");
        collection.add("Three");

        collection.remove(1);

        assertEquals(2, collection.size());
        assertEquals("One", collection.get(0));
        assertEquals("Three", collection.get(1));
    }

    @Test
    void shouldBeEmpty() {
        CustomCollection<String> collection = new CustomCollection<>();

        assertTrue(collection.isEmpty());
    }

    @Test
    void shouldNotBeEmpty() {
        CustomCollection<String> collection = new CustomCollection<>();

        collection.add("One");

        assertFalse(collection.isEmpty());
    }

    @Test
    void shouldThrowOnInvalidGetIndex() {
        CustomCollection<String> collection = new CustomCollection<>();

        collection.add("One");

        assertThrows(
                IndexOutOfBoundsException.class,
                () -> collection.get(1)
        );
    }

    @Test
    void shouldThrowOnNegativeGetIndex() {
        CustomCollection<String> collection = new CustomCollection<>();

        assertThrows(
                IndexOutOfBoundsException.class,
                () -> collection.get(-1)
        );
    }

    @Test
    void shouldThrowOnInvalidSetIndex() {
        CustomCollection<String> collection = new CustomCollection<>();

        collection.add("One");

        assertThrows(
                IndexOutOfBoundsException.class,
                () -> collection.set("Two", 1)
        );
    }

    @Test
    void shouldThrowOnInvalidRemoveIndex() {
        CustomCollection<String> collection = new CustomCollection<>();

        collection.add("One");

        assertThrows(
                IndexOutOfBoundsException.class,
                () -> collection.remove(1)
        );
    }

    @Test
    void shouldIterateElements() {
        CustomCollection<String> collection = new CustomCollection<>();

        collection.add("One");
        collection.add("Two");
        collection.add("Three");

        StringBuilder result = new StringBuilder();

        for (String element : collection) {
            result.append(element);
        }

        assertEquals("OneTwoThree", result.toString());
    }

    @Test
    void shouldThrowWhenIteratorEnds() {
        CustomCollection<String> collection = new CustomCollection<>();

        collection.add("One");

        var iterator = collection.iterator();

        iterator.next();

        assertThrows(
                NoSuchElementException.class,
                iterator::next
        );
    }

    @Test
    void shouldCreateStream() {
        CustomCollection<String> collection = new CustomCollection<>();

        collection.add("One");
        collection.add("Two");
        collection.add("Three");

        long count = collection.stream().count();

        assertEquals(3, count);
    }

    @Test
    void shouldGrowCapacity() {
        CustomCollection<Integer> collection = new CustomCollection<>();

        for (int i = 0; i < 11; i++) {
            collection.add(i);
        }

        assertEquals(11, collection.size());

        for (int i = 0; i < 11; i++) {
            assertEquals(i, collection.get(i));
        }
    }
}