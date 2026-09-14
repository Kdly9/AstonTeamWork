package input;

import collection.CustomCollection;
import visitor.ConventionVisitor;

import java.nio.file.Path;
import java.util.Scanner;

public class InputStrategyManualTest {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        System.out.println("Выберите способ заполнения:");
        System.out.println("1. Случайные данные");
        System.out.println("2. Ввод с консоли");
        System.out.println("3. Чтение из файла");
        System.out.print("Ваш выбор: ");

        int choice = Integer.parseInt(scanner.nextLine());

        InputStrategy strategy;
        CustomCollection<ConventionVisitor> visitors;

        switch (choice) {

            case 1 -> {
                System.out.print("Введите количество объектов: ");
                int count = Integer.parseInt(scanner.nextLine());

                strategy = new RandomInput(count);
                visitors = strategy.fill();
            }

            case 2 -> {
                System.out.print("Введите количество объектов: ");
                int count = Integer.parseInt(scanner.nextLine());

                strategy = new ConsoleInput(count);
                visitors = strategy.fill();
            }

            case 3 -> {
                System.out.print("Введите путь к файлу: ");
                Path path = Path.of(scanner.nextLine());

                System.out.println("Выберите режим чтения:");
                System.out.println("1. Весь файл");
                System.out.println("2. Определенное количество");
                System.out.print("Ваш выбор: ");

                int fileChoice = Integer.parseInt(scanner.nextLine());

                if (fileChoice == 1) {

                    strategy = new FileInput(path);
                    visitors = strategy.fill();

                } else if (fileChoice == 2) {

                    System.out.print("Введите количество объектов: ");
                    int count = Integer.parseInt(scanner.nextLine());

                    strategy = new FileInput(path, count);
                    visitors = strategy.fill();

                } else {
                    System.out.println("Неверный выбор.");
                    return;
                }
            }

            default -> {
                System.out.println("Неверный выбор.");
                return;
            }
        }

        System.out.println("\nПолученные данные:");

        for (ConventionVisitor visitor : visitors) {
            System.out.println(visitor);
        }
    }
}
