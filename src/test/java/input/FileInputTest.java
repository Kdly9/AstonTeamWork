package input;

import collection.CustomCollection;
import org.junit.jupiter.api.Test;
import visitor.ConventionVisitor;

import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class FileInputTest {

    @Test
    void shouldReadAllLines() throws Exception {
        Path path = Files.createTempFile("visitors", ".txt");

        Files.writeString(
                path,
                """
                Иван;VIP;Наруто
                Анна;OPEN_DATE;Луффи
                Дмитрий;THREE_DAYS;Бэтмен
                """
        );

        FileInput input = new FileInput(path);

        CustomCollection<ConventionVisitor> result = input.fill();

        assertEquals(3, result.size());

        Files.deleteIfExists(path);
    }

    @Test
    void shouldReadRequestedCount() throws Exception {
        Path path = Files.createTempFile("visitors", ".txt");

        Files.writeString(
                path,
                """
                Иван;VIP;Наруто
                Анна;OPEN_DATE;Луффи
                Дмитрий;THREE_DAYS;Бэтмен
                """
        );

        FileInput input = new FileInput(path, 2);

        CustomCollection<ConventionVisitor> result = input.fill();

        assertEquals(2, result.size());

        Files.deleteIfExists(path);
    }

    @Test
    void shouldReturnEmptyWhenCountIsZero() throws Exception {
        Path path = Files.createTempFile("visitors", ".txt");

        Files.writeString(
                path,
                "Иван;VIP;Наруто"
        );

        FileInput input = new FileInput(path, 0);

        CustomCollection<ConventionVisitor> result = input.fill();

        assertEquals(0, result.size());

        Files.deleteIfExists(path);
    }

    @Test
    void shouldThrowOnInvalidFormat() throws Exception {
        Path path = Files.createTempFile("visitors", ".txt");

        Files.writeString(
                path,
                "Иван;VIP"
        );

        FileInput input = new FileInput(path);

        assertThrows(
                IllegalArgumentException.class,
                input::fill
        );

        Files.deleteIfExists(path);
    }

    @Test
    void shouldThrowOnInvalidTicketType() throws Exception {
        Path path = Files.createTempFile("visitors", ".txt");

        Files.writeString(
                path,
                "Иван;INVALID;Наруто"
        );

        FileInput input = new FileInput(path);

        assertThrows(
                IllegalArgumentException.class,
                input::fill
        );

        Files.deleteIfExists(path);
    }

    @Test
    void shouldThrowOnEmptyCharacter() throws Exception {
        Path path = Files.createTempFile("visitors", ".txt");

        Files.writeString(
                path,
                "Иван;VIP;"
        );

        FileInput input = new FileInput(path);

        assertThrows(
                IllegalArgumentException.class,
                input::fill
        );

        Files.deleteIfExists(path);
    }

    @Test
    void shouldThrowOnNullPath() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new FileInput(null)
        );
    }

    @Test
    void shouldThrowOnNegativeCount() throws Exception {
        Path path = Files.createTempFile("visitors", ".txt");

        assertThrows(
                IllegalArgumentException.class,
                () -> new FileInput(path, -1)
        );

        Files.deleteIfExists(path);
    }
}