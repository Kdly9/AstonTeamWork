package visitor;

import java.util.Objects;

public class ConventionVisitor {
    private final String name;
    private final TicketType ticketType;

    private final String cosplayCharacter;

    private ConventionVisitor(Builder builder) {
        this.name = builder.name;
        this.ticketType = builder.ticketType;
        this.cosplayCharacter = builder.cosplayCharacter;
    }

    public String getName() {
        return name;
    }

    public TicketType getTicketType() {
        return ticketType;
    }

    public String getCosplayCharacter() {
        return cosplayCharacter;
    }

    @Override
    public String toString() {
        return "Билет " +
                "на имя '" + name + '\'' +
                ", тип билета '" + ticketType + '\'' +
                ", персонаж '" + cosplayCharacter + '\'';
    }

    public static class Builder {
        private String name;
        private TicketType ticketType;
        private String cosplayCharacter;

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder setTicketType(TicketType ticketType) {
            this.ticketType = ticketType;
            return this;
        }

        public Builder setCosplayCharacter(String cosplayCharacter) {
            this.cosplayCharacter = cosplayCharacter;
            return this;
        }

        public ConventionVisitor build() {
            Objects.requireNonNull(name, "Имя не должно быть null");
            Objects.requireNonNull(ticketType, "Тип билета не должен быть null");
            Objects.requireNonNull(cosplayCharacter, "Персонаж не должен быть null");

            if (name.isBlank()) {
                throw new IllegalArgumentException("Имя не должно быть пустым");
            }

            return new ConventionVisitor(this);
        }
    }
}
