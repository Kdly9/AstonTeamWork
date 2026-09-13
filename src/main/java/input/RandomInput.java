package input;

import collection.CustomCollection;
import visitor.ConventionVisitor;
import visitor.TicketType;

import java.util.Random;
import java.util.stream.IntStream;

public class RandomInput implements InputStrategy {

    private final int count;

    private final Random random = new Random();

    private static final String[] NAMES = {
            "Алексей",
            "Мария",
            "Иван",
            "Анна",
            "Дмитрий"
    };

    private static final String[] CHARACTERS = {
            "Наруто",
            "Луффи",
            "Бэтмен",
            "Человек-паук",
            "Ведьмак"
    };

    public RandomInput(int count) {

        if (count < 0) {
            throw new IllegalArgumentException(
                    "Количество не может быть отрицательным"
            );
        }

        this.count = count;
    }

    @Override
    public CustomCollection<ConventionVisitor> fill() {

        CustomCollection<ConventionVisitor> data = new CustomCollection<>();

        TicketType[] ticketTypes = TicketType.values();

        IntStream.range(0, count)
                .mapToObj(i -> new ConventionVisitor.Builder()
                        .setName(NAMES[random.nextInt(NAMES.length)])
                        .setCosplayCharacter(CHARACTERS[random.nextInt(CHARACTERS.length)])
                        .setTicketType(ticketTypes[random.nextInt(ticketTypes.length)])
                        .build())
                .forEach(data::add);

        return data;
    }
}
