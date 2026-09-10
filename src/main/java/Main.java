import visitor.ConventionVisitor;
import visitor.TicketType;

public class Main {
    public static void main(String[] args) {
        try {
            ConventionVisitor visitor = new ConventionVisitor.Builder()
                    .setName("Анна")
                    .setTicketType(TicketType.VIP)
                    .setCosplayCharacter("Бэтмен")
                    .build();

            System.out.println(visitor);
        } catch (NullPointerException | IllegalArgumentException exception) {
            System.out.println("Не удалось создать посетителя: " + exception.getMessage());
        }
    }
}
