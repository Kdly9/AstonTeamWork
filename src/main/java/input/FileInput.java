package input;

import collection.CustomCollection;
import visitor.ConventionVisitor;
import visitor.TicketType;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

public class FileInput implements InputStrategy {

    private final Path path;

    private final int count;

    public FileInput(Path path, int count) {

        if (count < 0) {
            throw new IllegalArgumentException(
                    "Количество не может быть отрицательным"
            );
        }

        if (path == null) {
            throw new IllegalArgumentException(
                    "Путь не должен быть null"
            );
        }

        this.path = path;
        this.count = count;
    }

    public FileInput(Path path) {

        if (path == null) {
            throw new IllegalArgumentException(
                    "Путь не должен быть null"
            );
        }

        this.path = path;
        this.count = -1;
    }

    @Override
    public CustomCollection<ConventionVisitor> fill() {

        CustomCollection<ConventionVisitor> data = new CustomCollection<>();

        try (Stream<String> lines = Files.lines(path)) {
            if (count >= 0) {
                lines.limit(count).map(this::parseVisitor).forEach(data::add);
            } else {
                lines.map(this::parseVisitor).forEach(data::add);
            }

        } catch (IOException e) {
            throw new IllegalStateException("Не удалось прочитать файл: " + path, e);
        }

        return data;
    }


    private ConventionVisitor parseVisitor(String line) {
        String[] parts = line.split(";", -1);

        if (parts.length != 3) {
            throw new IllegalArgumentException("Неверный формат строки: " + line);
        }

        String name = parts[0].trim();
        String ticketTypeName = parts[1].trim();
        String cosplayCharacter = parts[2].trim();

        TicketType ticketType;

        try {
            ticketType = TicketType.valueOf(ticketTypeName);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Неизвестный тип билета: " + ticketTypeName);
        }

        if (cosplayCharacter.isBlank()) {
            throw new IllegalArgumentException("Персонаж не должен быть пустым");
        }

        return new ConventionVisitor.Builder()
                .setName(name)
                .setTicketType(ticketType)
                .setCosplayCharacter(cosplayCharacter)
                .build();
    }
}
