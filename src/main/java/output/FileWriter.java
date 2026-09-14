package output;

import collection.CustomCollection;
import visitor.ConventionVisitor;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

public final class FileWriter {

    private FileWriter() {
    }

    public static void write(Path path, CustomCollection<ConventionVisitor> visitors) {

        try (BufferedWriter writer = Files.newBufferedWriter(path, StandardOpenOption.CREATE,
                StandardOpenOption.APPEND)) {

            for (ConventionVisitor visitor : visitors) {
                String toWrite = visitor.getName() + ";"
                        + visitor.getTicketType().name() + ";"
                        + visitor.getCosplayCharacter();

                writer.write(toWrite);
                writer.newLine();
            }
        } catch (IOException e) {
            throw new IllegalStateException("Не удалось записать в файл: " + path, e);
        }
    }
}
