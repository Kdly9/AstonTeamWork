package visitor;

import collection.CustomCollection;
import input.RandomInput;
import java.util.Comparator;

public class SortTest {

    public static void main(String[] args) {

        CustomCollection<ConventionVisitor> visitors = new RandomInput(10).fill();

        printVisitors("Исходный набор", visitors);

        sortAndPrint("Сортировка по имени", visitors, VisitorComparators.VisitorComparator.NAME_SORT);

        sortAndPrint("Сортировка по типу билета", visitors,
                VisitorComparators.VisitorComparator.TICKET_TYPE_SORT);

        sortAndPrint("Сортировка по персонажу", visitors,
                VisitorComparators.VisitorComparator.COSPLAY_CHARACTER_SORT);

        sortAndPrint("Сортировка по всем полям", visitors,
                VisitorComparators.VisitorComparator.ALL_FIELDS_SORT);

        CustomCollection<ConventionVisitor> newOrderVisitors =
                new RandomInput(10).fill();

        printVisitorsWithPriority("До  четной-нечетной сортировки", newOrderVisitors);

        VisitorComparators.sortTicketPriorityNewOrder(
                newOrderVisitors
        );

        printVisitorsWithPriority("После четной-нечетной сортировки", newOrderVisitors);
    }

    private static void sortAndPrint(
            String title,
            CustomCollection<ConventionVisitor> visitors,
            Comparator<ConventionVisitor> comparator
    ) {
        visitors.sort(comparator);
        printVisitors(title, visitors);
    }

    private static void printVisitors(
            String title,
            CustomCollection<ConventionVisitor> visitors
    ) {
        System.out.println("\n" + title + ":");

        for (ConventionVisitor visitor : visitors) {
            System.out.println(visitor);
        }
    }

    private static void printVisitorsWithPriority(
            String title,
            CustomCollection<ConventionVisitor> visitors
    ) {
        System.out.println("\n" + title + ":");

        for (ConventionVisitor visitor : visitors) {
            System.out.println(visitor + " | priority = "
                    + visitor.getTicketType().getPriority());
        }
    }
}