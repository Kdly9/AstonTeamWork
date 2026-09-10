package visitor;

public enum TicketType {
    VIP(1, "VIP"),
    OPEN_DATE(2, "Билет с открытой датой"),
    THREE_DAYS(3, "Билет на три дня"),
    ONE_DATE(4, "Однодневный билет"),
    SUPER_VIP(0, "Super VIP");

    private final int priority;
    private final String displayName;

    TicketType(int priority, String displayName) {
        this.priority = priority;
        this.displayName = displayName;
    }

    public int getPriority() {
        return priority;
    }

    public String getDisplayName() {
        return displayName;
    }
}