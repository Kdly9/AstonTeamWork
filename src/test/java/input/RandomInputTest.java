package input;

import collection.CustomCollection;
import org.junit.jupiter.api.Test;
import visitor.ConventionVisitor;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class RandomInputTest {

    @Test
    void shouldCreateVisitors() {
        RandomInput input = new RandomInput(5);

        CustomCollection<ConventionVisitor> result = input.fill();

        assertEquals(5, result.size());
    }

    @Test
    void shouldReturnEmpty() {
        RandomInput input = new RandomInput(0);

        CustomCollection<ConventionVisitor> result = input.fill();

        assertEquals(0, result.size());
    }

    @Test
    void shouldThrowOnNegativeCount() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new RandomInput(-1)
        );
    }

    @Test
    void shouldCreateVisitorsWithAllFields() {
        RandomInput input = new RandomInput(10);

        CustomCollection<ConventionVisitor> result = input.fill();

        for (ConventionVisitor visitor : result) {
            assertNotNull(visitor.getName());
            assertNotNull(visitor.getTicketType());
            assertNotNull(visitor.getCosplayCharacter());
        }
    }
}