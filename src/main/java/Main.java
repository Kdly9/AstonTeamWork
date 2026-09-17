import collection.CustomCollection;
import concurrent.ConcurrentCounter;
import input.ConsoleInput;
import input.FileInput;
import input.InputStrategy;
import input.RandomInput;
import output.FileWriter;
import visitor.ConventionVisitor;
import visitor.VisitorComparators;

import java.nio.file.Path;
import java.util.Scanner;

public class Main {

    private static final Scanner SCANNER = new Scanner(System.in);
    private static final String DEFAULT_FILE = "src/main/resources/visitors.txt";

    private static CustomCollection<ConventionVisitor> visitors = new CustomCollection<>();

    public static void main(String[] args) {
        System.out.println("=== Управление посетителями конвента ===");

        boolean running = true;
        while (running) {
            printMainMenu();
            int choice = readInt("Ваш выбор: ");

            switch (choice) {
                case 1 -> loadData();
                case 2 -> displayAll();
                case 3 -> displaySortedBy(VisitorComparators.VisitorComparator.NAME_SORT,
                                          "Сортировка по имени");
                case 4 -> displaySortedBy(VisitorComparators.VisitorComparator.TICKET_TYPE_SORT,
                                          "Сортировка по типу билета");
                case 5 -> displaySortedBy(VisitorComparators.VisitorComparator.COSPLAY_CHARACTER_SORT,
                                          "Сортировка по персонажу");
                case 6 -> displaySortedBy(VisitorComparators.VisitorComparator.ALL_FIELDS_SORT,
                                          "Сортировка по всем полям");
                case 7 -> displayEvenOddSortedByPriority();
                case 8 -> saveData();
                case 9 -> countOccurrences();
                case 0 -> {
                    running = false;
                    System.out.println("Выход. До встречи!");
                }
                default -> System.out.println("Неверный пункт меню.");
            }
        }
        SCANNER.close();
    }

    // Меню

    private static void printMainMenu() {
        System.out.println();
        System.out.println("--- Главное меню ---");
        System.out.println("1. Загрузить данные");
        System.out.println("2. Показать всех посетителей");
        System.out.println("3. Сортировка по имени");
        System.out.println("4. Сортировка по типу билета");
        System.out.println("5. Сортировка по персонажу");
        System.out.println("6. Сортировка по всем полям");
        System.out.println("7. Чётно-нечётная сортировка по приоритету");
        System.out.println("8. Сохранить данные в файл");
        System.out.println("9. Подсчёт вхождений элемента (многопоточный)");
        System.out.println("0. Выход");
    }

    // Загрузка

    private static void loadData() {
        System.out.println("\n--- Способ загрузки ---");
        System.out.println("1. Случайные данные");
        System.out.println("2. Ввод с консоли");
        System.out.println("3. Чтение из файла");

        int choice = readInt("Ваш выбор: ");

        try {
            InputStrategy strategy = switch (choice) {
                case 1 -> new RandomInput(readNonNegativeCount());
                case 2 -> new ConsoleInput(readNonNegativeCount());
                case 3 -> createFileInput();
                default -> null;
            };

            if (strategy == null) {
                System.out.println("Неверный способ загрузки.");
                return;
            }

            visitors = strategy.fill();
            System.out.println("Загружено посетителей: " + visitors.size());
        } catch (IllegalArgumentException | IllegalStateException e) {
            System.out.println("Ошибка загрузки: " + e.getMessage());
        }
    }

    private static InputStrategy createFileInput() {
        System.out.print("Путь к файлу (Enter — по умолчанию): ");
        String path = SCANNER.nextLine().trim();
        Path filePath = path.isEmpty() ? Path.of(DEFAULT_FILE) : Path.of(path);

        System.out.println("1. Прочитать весь файл");
        System.out.println("2. Прочитать определённое количество");
        int mode = readInt("Ваш выбор: ");

        return mode == 1 ? new FileInput(filePath)
                         : new FileInput(filePath, readNonNegativeCount());
    }

    // Вывод с сортировкой

    private static void displayAll() {
        if (checkEmpty()) return;
        System.out.println("\n--- Все посетители ---");
        printVisitors();
    }

    private static void displaySortedBy(
            VisitorComparators.VisitorComparator comparator,
            String title
    ) {
        if (checkEmpty()) return;

        visitors.sort(comparator);

        System.out.println("\n--- " + title + " ---");
        printVisitors();
    }

    private static void displayEvenOddSortedByPriority() {
        if (checkEmpty()) return;

        System.out.println("\n--- До чётно-нечётной сортировки ---");
        printVisitorsWithPriority();

        VisitorComparators.sortTicketPriorityNewOrder(visitors);

        System.out.println("\n--- После чётно-нечётной сортировки ---");
        printVisitorsWithPriority();
    }

    // Сохранение

    private static void saveData() {
        if (checkEmpty()) return;

        System.out.print("Путь для сохранения (Enter — " + DEFAULT_FILE + "): ");
        String path = SCANNER.nextLine().trim();
        Path filePath = path.isEmpty() ? Path.of(DEFAULT_FILE) : Path.of(path);

        try {
            FileWriter.write(filePath, visitors);
            System.out.println("Данные сохранены в " + filePath);
        } catch (IllegalStateException e) {
            System.out.println("Ошибка сохранения: " + e.getMessage());
        }
    }

    // Многопоточный подсчёт

    private static void countOccurrences() {
    if (checkEmpty()) return;

    System.out.println("\n--- Подсчёт вхождений элемента ---");

    // Показываем коллекцию, чтобы пользователь понимал, что можно ввести
    System.out.println("Текущая коллекция:");
    printVisitors();

    System.out.println("\nСоздайте посетителя, вхождения которого нужно подсчитать:");
    ConventionVisitor target = readVisitorManually();

    System.out.println("\nИскомый посетитель: " + target);

    int threadCount = readInt("Введите количество потоков: ");
    if (threadCount <= 0) {
        System.out.println("Количество потоков должно быть больше нуля.");
        return;
    }

    ConcurrentCounter.count(visitors, target, threadCount);
}

private static ConventionVisitor readVisitorManually() {
    ConsoleInput input = new ConsoleInput(1);
    return input.fill().get(0);
}

    // Утилиты вывода

    private static boolean checkEmpty() {
        if (visitors.isEmpty()) {
            System.out.println("Список пуст. Сначала загрузите данные.");
            return true;
        }
        return false;
    }

    private static void printVisitors() {
        for (int i = 0; i < visitors.size(); i++) {
            System.out.printf("%d. %s%n", i, visitors.get(i));
        }
    }

    private static void printVisitorsWithPriority() {
        for (ConventionVisitor visitor : visitors) {
            System.out.println(visitor + " | priority = "
                    + visitor.getTicketType().getPriority());
        }
    }

    // Утилиты ввода

    private static int readNonNegativeCount() {
        while (true) {
            int value = readInt("Введите количество: ");
            if (value >= 0) {
                return value;
            }
            System.out.println("Количество не может быть отрицательным.");
        }
    }

    private static int readInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            String line = SCANNER.nextLine().trim();
            try {
                return Integer.parseInt(line);
            } catch (NumberFormatException e) {
                System.out.println("Введите целое число.");
            }
        }
    }
}