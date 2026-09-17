package sort;

import collection.CustomCollection;
import org.junit.jupiter.api.Test;
import visitor.ConventionVisitor;
import visitor.TicketType;
import visitor.VisitorComparators;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class QuickSortTest {

    @Test
    void shouldSortCollection() {
        CustomCollection<Integer> collection = new CustomCollection<>();

        collection.add(5);
        collection.add(2);
        collection.add(8);
        collection.add(1);
        collection.add(3);

        QuickSort.sort(collection, Integer::compareTo);

        assertEquals(1, collection.get(0));
        assertEquals(2, collection.get(1));
        assertEquals(3, collection.get(2));
        assertEquals(5, collection.get(3));
        assertEquals(8, collection.get(4));
    }

    @Test
    void shouldSortEmptyCollection() {
        CustomCollection<Integer> collection = new CustomCollection<>();

        QuickSort.sort(collection, Integer::compareTo);

        assertEquals(0, collection.size());
    }

    @Test
    void shouldSortCollectionWithOneElement() {
        CustomCollection<Integer> collection = new CustomCollection<>();

        collection.add(10);

        QuickSort.sort(collection, Integer::compareTo);

        assertEquals(1, collection.size());
        assertEquals(10, collection.get(0));
    }

    @Test
    void shouldThrowWhenCollectionIsNull() {
        assertThrows(
                NullPointerException.class,
                () -> QuickSort.sort(null, Integer::compareTo)
        );
    }

    @Test
    void shouldThrowWhenComparatorIsNull() {
        CustomCollection<Integer> collection = new CustomCollection<>();

        collection.add(1);

        assertThrows(
                NullPointerException.class,
                () -> QuickSort.sort(collection, null)
        );
    }

    @Test
    void shouldSortVisitorsByName() {
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

        visitors.add(new ConventionVisitor.Builder()
                .setName("Дмитрий")
                .setTicketType(TicketType.THREE_DAYS)
                .setCosplayCharacter("Бэтмен")
                .build());

        QuickSort.sort(
                visitors,
                VisitorComparators.VisitorComparator.NAME_SORT
        );

        assertEquals("Анна", visitors.get(0).getName());
        assertEquals("Дмитрий", visitors.get(1).getName());
        assertEquals("Иван", visitors.get(2).getName());
    }

    @Test
    void shouldSortVisitorsByTicketType() {
        CustomCollection<ConventionVisitor> visitors = new CustomCollection<>();

        visitors.add(new ConventionVisitor.Builder()
                .setName("Иван")
                .setTicketType(TicketType.THREE_DAYS)
                .setCosplayCharacter("Наруто")
                .build());

        visitors.add(new ConventionVisitor.Builder()
                .setName("Анна")
                .setTicketType(TicketType.SUPER_VIP)
                .setCosplayCharacter("Луффи")
                .build());

        visitors.add(new ConventionVisitor.Builder()
                .setName("Дмитрий")
                .setTicketType(TicketType.VIP)
                .setCosplayCharacter("Бэтмен")
                .build());

        QuickSort.sort(
                visitors,
                VisitorComparators.VisitorComparator.TICKET_TYPE_SORT
        );

        assertEquals(TicketType.SUPER_VIP, visitors.get(0).getTicketType());
        assertEquals(TicketType.VIP, visitors.get(1).getTicketType());
        assertEquals(TicketType.THREE_DAYS, visitors.get(2).getTicketType());
    }

    @Test
    void shouldSortVisitorsByCosplayCharacter() {
        CustomCollection<ConventionVisitor> visitors = new CustomCollection<>();

        visitors.add(new ConventionVisitor.Builder()
                .setName("Иван")
                .setTicketType(TicketType.VIP)
                .setCosplayCharacter("Наруто")
                .build());

        visitors.add(new ConventionVisitor.Builder()
                .setName("Анна")
                .setTicketType(TicketType.OPEN_DATE)
                .setCosplayCharacter("Бэтмен")
                .build());

        visitors.add(new ConventionVisitor.Builder()
                .setName("Дмитрий")
                .setTicketType(TicketType.THREE_DAYS)
                .setCosplayCharacter("Луффи")
                .build());

        QuickSort.sort(
                visitors,
                VisitorComparators.VisitorComparator.COSPLAY_CHARACTER_SORT
        );

        assertEquals("Бэтмен", visitors.get(0).getCosplayCharacter());
        assertEquals("Луффи", visitors.get(1).getCosplayCharacter());
        assertEquals("Наруто", visitors.get(2).getCosplayCharacter());
    }

    @Test
    void shouldSortVisitorsWithEmptyCharacter() {
        CustomCollection<ConventionVisitor> visitors = new CustomCollection<>();

        visitors.add(new ConventionVisitor.Builder()
                .setName("Иван")
                .setTicketType(TicketType.VIP)
                .setCosplayCharacter("Наруто")
                .build());

        visitors.add(new ConventionVisitor.Builder()
                .setName("Анна")
                .setTicketType(TicketType.OPEN_DATE)
                .setCosplayCharacter("")
                .build());

        visitors.add(new ConventionVisitor.Builder()
                .setName("Дмитрий")
                .setTicketType(TicketType.THREE_DAYS)
                .setCosplayCharacter("Луффи")
                .build());

        QuickSort.sort(
                visitors,
                VisitorComparators.VisitorComparator.COSPLAY_CHARACTER_SORT
        );

        assertEquals("", visitors.get(0).getCosplayCharacter());
        assertEquals("Луффи", visitors.get(1).getCosplayCharacter());
        assertEquals("Наруто", visitors.get(2).getCosplayCharacter());
    }

    @Test
    void shouldSortVisitorsByAllFields() {
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

        visitors.add(new ConventionVisitor.Builder()
                .setName("Дмитрий")
                .setTicketType(TicketType.THREE_DAYS)
                .setCosplayCharacter("Бэтмен")
                .build());

        QuickSort.sort(
                visitors,
                VisitorComparators.VisitorComparator.ALL_FIELDS_SORT
        );

        assertEquals("Анна", visitors.get(0).getName());
        assertEquals("Дмитрий", visitors.get(1).getName());
        assertEquals("Иван", visitors.get(2).getName());
    }
}
