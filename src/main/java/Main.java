import collection.CustomCollection;
import input.ConsoleInput;
import input.FileInput;
import input.InputStrategy;
import input.RandomInput;
import output.FileWriter;
import visitor.ConventionVisitor;
import visitor.TicketType;

import java.nio.file.Path;
import java.util.Comparator;
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
                case 3 -> displaySortedByTicketPriority();
                case 4 -> displayFilteredByTicketType();
                case 5 -> saveData();
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
        System.out.println("3. Показать отсортированных по приоритету билета");
        System.out.println("4. Фильтр по типу билета");
        System.out.println("5. Сохранить данные в файл");
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

        return mode == 1 ? new FileInput(filePath) : new FileInput(filePath, readNonNegativeCount());
    }

    // Вывод

    private static void displayAll() {
        if (visitors.isEmpty()) {
            System.out.println("Список пуст. Сначала загрузите данные.");
            return;
        }

        System.out.println("\n--- Все посетители ---");
        int index = 1;
        for (ConventionVisitor visitor : visitors) {
            System.out.printf("%d. %s%n", index++, visitor);
        }
    }

    private static void displaySortedByTicketPriority() {
        if (visitors.isEmpty()) {
            System.out.println("Список пуст. Сначала загрузите данные.");
            return;
        }

        System.out.println("\n--- Посетители (сортировка по приоритету билета) ---");
        visitors.stream()
                .sorted(Comparator.comparingInt(v -> v.getTicketType().getPriority()))
                .forEach(System.out::println);
    }

    private static void displayFilteredByTicketType() {
        if (visitors.isEmpty()) {
            System.out.println("Список пуст. Сначала загрузите данные.");
            return;
        }

        System.out.println("\n--- Фильтр по типу билета ---");
        TicketType[] types = TicketType.values();
        for (int i = 0; i < types.length; i++) {
            System.out.printf("%d. %s%n", i + 1, types[i].getDisplayName());
        }

        int choice = readInt("Ваш выбор: ");
        if (choice < 1 || choice > types.length) {
            System.out.println("Неверный выбор.");
            return;
        }

        TicketType selected = types[choice - 1];
        System.out.println("\nПосетители с билетом '" + selected.getDisplayName() + "':");

        boolean[] found = {false};
        visitors.stream()
                .filter(v -> v.getTicketType() == selected)
                .forEach(v -> {
                    System.out.println(v);
                    found[0] = true;
                });

        if (!found[0]) {
            System.out.println("Нет посетителей с таким типом билета.");
        }
    }

    // Сохранение

    private static void saveData() {
        if (visitors.isEmpty()) {
            System.out.println("Нечего сохранять. Сначала загрузите данные.");
            return;
        }

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