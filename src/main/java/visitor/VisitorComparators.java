package visitor;

import collection.CustomCollection;
import java.util.Comparator;

public final class VisitorComparators {

    private VisitorComparators() {
        throw new AssertionError("error instance");
    }

    public enum VisitorComparator implements Comparator<ConventionVisitor> {
        NAME_SORT,
        TICKET_TYPE_SORT,
        COSPLAY_CHARACTER_SORT,
        ALL_FIELDS_SORT;

        @Override
        public int compare(ConventionVisitor first, ConventionVisitor second) {
            switch (this) {
                case NAME_SORT:
                    return first.getName().compareTo(second.getName());

                case TICKET_TYPE_SORT:
                    return Integer.compare(
                            first.getTicketType().getPriority(),
                            second.getTicketType().getPriority()
                    );

                case COSPLAY_CHARACTER_SORT:
                    return first.getCosplayCharacter().compareTo(second.getCosplayCharacter());

                case ALL_FIELDS_SORT:
                    int result = NAME_SORT.compare(first, second);
                    if (result != 0) {
                        return result;
                    }

                    result = TICKET_TYPE_SORT.compare(first, second);

                    if (result != 0) {
                        return result;
                    }
                    return COSPLAY_CHARACTER_SORT.compare(first, second);

                default:
                    throw new IllegalStateException("Неизвестный компаратор");
            }
        }
    }

    public static void sortTicketPriorityNewOrder(
            CustomCollection<ConventionVisitor> visitors
    ) {
        for (int i = 0; i < visitors.size() - 1; i++) {

            ConventionVisitor first = visitors.get(i);

            int firstPriority = first.getTicketType().getPriority();

            if (firstPriority % 2 != 0) {
                continue;
            }

            for (int j = i + 1; j < visitors.size(); j++) {

                int secondPriority = visitors.get(j).getTicketType().getPriority();

                if (secondPriority % 2 != 0) {
                    continue;
                }

                if (firstPriority > secondPriority) {
                    visitors.set(visitors.get(j), i);
                    visitors.set(first, j);

                    first = visitors.get(i);
                    firstPriority = first.getTicketType().getPriority();
                }
            }
        }
    }
}