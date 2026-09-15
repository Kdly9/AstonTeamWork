package output;

import collection.CustomCollection;
import org.junit.jupiter.api.Test;
import visitor.ConventionVisitor;
import visitor.TicketType;

import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FileWriterTest {

    @Test
    void shouldWriteVisitorsToFile() throws Exception {
        Path path = Files.createTempFile("visitors", ".txt");

        CustomCollection<ConventionVisitor> visitors = new CustomCollection<>();

        visitors.add(new ConventionVisitor.Builder()
                .setName("Иван")
                .setTicketType(TicketType.VIP)
                .setCosplayCharacter("Наруто")
                .build());

        FileWriter.write(path, visitors);

        String content = Files.readString(path);

        assertEquals(
                "Иван;VIP;Наруто" + System.lineSeparator(),
                content
        );

        Files.deleteIfExists(path);
    }

    @Test
    void shouldWriteMultipleVisitors() throws Exception {
        Path path = Files.createTempFile("visitors", ".txt");

        CustomCollection<ConventionVisitor> visitors = new CustomCollection<>();

        visitors.add(new ConventionVisitor.Builder()
                .setName("Иван")
                .setTicketType(TicketType.VIP)
                .setCosplayCharacter("Наруто")
                .build());

        visitors.add(new ConventionVisitor.Builder()
                .setName("Анна")
                .setTicketType(TicketType.OPEN_DATE)
                .setCosplayCharacter("Луффи")
                .build());

        FileWriter.write(path, visitors);

        String content = Files.readString(path);

        assertEquals(
                "Иван;VIP;Наруто" + System.lineSeparator()
                        + "Анна;OPEN_DATE;Луффи" + System.lineSeparator(),
                content
        );

        Files.deleteIfExists(path);
    }

    @Test
    void shouldAppendToFile() throws Exception {
        Path path = Files.createTempFile("visitors", ".txt");

        Files.writeString(
                path,
                "Петр;SUPER_VIP;Бэтмен" + System.lineSeparator()
        );

        CustomCollection<ConventionVisitor> visitors = new CustomCollection<>();

        visitors.add(new ConventionVisitor.Builder()
                .setName("Иван")
                .setTicketType(TicketType.VIP)
                .setCosplayCharacter("Наруто")
                .build());

        FileWriter.write(path, visitors);

        String content = Files.readString(path);

        assertEquals(
                "Петр;SUPER_VIP;Бэтмен" + System.lineSeparator()
                        + "Иван;VIP;Наруто" + System.lineSeparator(),
                content
        );

        Files.deleteIfExists(path);
    }

    @Test
    void shouldCreateEmptyFileForEmptyCollection() throws Exception {
        Path path = Files.createTempFile("visitors", ".txt");

        CustomCollection<ConventionVisitor> visitors = new CustomCollection<>();

        FileWriter.write(path, visitors);

        assertEquals("", Files.readString(path));

        Files.deleteIfExists(path);
    }
}