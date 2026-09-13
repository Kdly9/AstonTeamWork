package input;

import collection.CustomCollection;
import visitor.ConventionVisitor;
import visitor.TicketType;

import java.util.Scanner;
import java.util.stream.IntStream;

public class ConsoleInput implements InputStrategy {

    private final Scanner scanner = new Scanner(System.in);

    private final int count;

    public ConsoleInput(int count) {

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

        IntStream.range(0, count)
                .mapToObj(i -> readVisitor())
                .forEach(data::add);

        return data;
    }

    private ConventionVisitor readVisitor() {
        String name = readName();
        TicketType ticketType = readTicketType();
        String cosplayCharacter = readCosplayCharacter();

        return new ConventionVisitor.Builder()
                .setName(name)
                .setTicketType(ticketType)
                .setCosplayCharacter(cosplayCharacter)
                .build();
    }

    private String readName() {
        String name;
        while (true) {
            System.out.print("Введите имя: ");
            name = scanner.nextLine();
            if (name.isBlank()) {
                System.out.println("Имя не должно быть пустым");
            } else {
                return name;
            }
        }
    }

    private TicketType readTicketType() {
        while (true) {
            System.out.println("Выберите тип билета:");
            System.out.println("1. VIP");
            System.out.println("2. Билет с открытой датой");
            System.out.println("3. Билет на три дня");
            System.out.println("4. Однодневный билет");
            System.out.println("5. Super VIP");
            System.out.print("Ваш выбор: ");

            String input = scanner.nextLine();

            try {
                int choice = Integer.parseInt(input);

                if (choice >= 1 && choice <= TicketType.values().length) {
                    return TicketType.values()[choice - 1];
                }

                System.out.println("Такого типа билета нет.");
            } catch (NumberFormatException e) {
                System.out.println("Введите число от 1 до "
                        + TicketType.values().length + ".");
            }
        }
    }

    private String readCosplayCharacter() {
        String character;
        while (true) {
            System.out.print("Введите персонажа: ");
            character = scanner.nextLine();
            if (character.isBlank()) {
                System.out.println("Персонаж не должен быть пустым");
            } else {
                return character;
            }
        }
    }
}
