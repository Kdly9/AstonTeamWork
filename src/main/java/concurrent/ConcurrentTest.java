package concurrent;

import collection.CustomCollection;
import visitor.ConventionVisitor;
import visitor.TicketType;

public class ConcurrentTest {

    public static void main(String[] args) {

        CustomCollection<ConventionVisitor> visitors = new CustomCollection<>();

        visitors.add(
                new ConventionVisitor.Builder()
                        .setName("Анна")
                        .setTicketType(TicketType.VIP)
                        .setCosplayCharacter("Харли Квинн")
                        .build()
        );

        visitors.add(
                new ConventionVisitor.Builder()
                        .setName("Иван")
                        .setTicketType(TicketType.ONE_DATE)
                        .setCosplayCharacter("Джокер")
                        .build()
        );

        visitors.add(
                new ConventionVisitor.Builder()
                        .setName("Анна")
                        .setTicketType(TicketType.VIP)
                        .setCosplayCharacter("Харли Квинн")
                        .build()
        );

        visitors.add(
                new ConventionVisitor.Builder()
                        .setName("Мария")
                        .setTicketType(TicketType.THREE_DAYS)
                        .setCosplayCharacter("Ванда")
                        .build()
        );

        visitors.add(
                new ConventionVisitor.Builder()
                        .setName("Анна")
                        .setTicketType(TicketType.VIP)
                        .setCosplayCharacter("Харли Квинн")
                        .build()
        );

        visitors.add(
                new ConventionVisitor.Builder()
                        .setName("Елена")
                        .setTicketType(TicketType.OPEN_DATE)
                        .setCosplayCharacter("Рей")
                        .build()
        );

        visitors.add(
                new ConventionVisitor.Builder()
                        .setName("Анна")
                        .setTicketType(TicketType.VIP)
                        .setCosplayCharacter("Харли Квинн")
                        .build()
        );

        System.out.println("Исходная коллекция:");

        for (ConventionVisitor visitor : visitors) {
            System.out.println(visitor);
        }

        ConventionVisitor target =
                new ConventionVisitor.Builder()
                        .setName("Анна")
                        .setTicketType(TicketType.VIP)
                        .setCosplayCharacter("Харли Квинн")
                        .build();

        System.out.println("\nИскали:");
        System.out.println(target);

        ConcurrentCounter.count(
                visitors,
                target,
                3
        );
    }
}