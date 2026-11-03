import java.util.InputMismatchException;
import java.util.Scanner;

// 1. Власне виключення
class InvalidInputException extends Exception {
    public InvalidInputException(String message) {
        super(message);
    }
}

// 2. Клас Калькулятора
class Calculator {

    public double add(double a, double b) {
        return a + b;
    }

    public double subtract(double a, double b) {
        return a - b;
    }

    public double multiply(double a, double b) {
        return a * b;
    }

    public double divide(double a, double b) throws ArithmeticException {
        if (b == 0) {
            throw new ArithmeticException("Помилка: Ділення на нуль неможливе!");
        }
        return a / b;
    }

    public double sqrt(double value) throws InvalidInputException {
        if (value < 0) {
            throw new InvalidInputException("Помилка вхідних даних: Квадратний корінь з від'ємного числа (" + value + ") не існує!");
        }
        return Math.sqrt(value);
    }
}

// 3. Головний клас з інтерфейсом користувача та автоматичними тестами
public class Main {

    public static void main(String[] args) {
        Calculator calculator = new Calculator();

        // --- БЛОК 1: Автоматичне тестування всіх випадків ---
        System.out.println("========================================");
        System.out.println("      ЗАПУСК АВТОМАТИЧНИХ ТЕСТІВ       ");
        System.out.println("========================================");
        runTests(calculator);

        // --- БЛОК 2: Взаємодія з користувачем ---
        System.out.println("\n========================================");
        System.out.println("         ІНТЕРАКТИВНИЙ КАЛЬКУЛЯТОР      ");
        System.out.println("========================================");
        runInteractiveCalculator(calculator);
    }

    // Метод для інтерактивної роботи з користувачем (try-catch-finally)
    private static void runInteractiveCalculator(Calculator calculator) {
        Scanner scanner = new Scanner(System.in);

        try {
            System.out.print("Оберіть операцію (+, -, *, /, sqrt): ");
            String operation = scanner.next();

            if (operation.equalsIgnoreCase("sqrt")) {
                System.out.print("Введіть число: ");
                double num = scanner.nextDouble();
                double result = calculator.sqrt(num);
                System.out.println("Результат: √" + num + " = " + result);
            } else {
                System.out.print("Введіть перше число: ");
                double a = scanner.nextDouble();
                System.out.print("Введіть друге число: ");
                double b = scanner.nextDouble();

                double result = 0;
                switch (operation) {
                    case "+":
                        result = calculator.add(a, b);
                        break;
                    case "-":
                        result = calculator.subtract(a, b);
                        break;
                    case "*":
                        result = calculator.multiply(a, b);
                        break;
                    case "/":
                        result = calculator.divide(a, b);
                        break;
                    default:
                        throw new InvalidInputException("Невідома операція: '" + operation + "'");
                }
                System.out.println("Результат: " + result);
            }

        } catch (InputMismatchException e) {
            System.err.println("ПОМИЛКА ВВОДУ: Введено текстові дані замість числа!");
        } catch (ArithmeticException e) {
            System.err.println("АРИФМЕТИЧНА ПОМИЛКА: " + e.getMessage());
        } catch (InvalidInputException e) {
            System.err.println("ВЛАСНЕ ВИКЛЮЧЕННЯ: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("НЕПЕРЕДБАЧЕНА ПОМИЛКА: " + e.getMessage());
        } finally {
            System.out.println("----------------------------------------");
            System.out.println("Завершення обробки запиту користувача.");
            scanner.close();
        }
    }

    // Автоматизовані тести під вимоги завдання
    private static void runTests(Calculator calculator) {
        // Тест 1: Ділення на нуль
        System.out.println("\n[ТЕСТ 1] Спроба ділення 10 / 0:");
        try {
            calculator.divide(10, 0);
        } catch (ArithmeticException e) {
            System.out.println("-> Успішно перехоплено ArithmeticException: " + e.getMessage());
        }

        // Тест 2: Власне виключення InvalidInputException (sqrt від від'ємного)
        System.out.println("\n[ТЕСТ 2] Спроба обчислити sqrt(-16):");
        try {
            calculator.sqrt(-16);
        } catch (InvalidInputException e) {
            System.out.println("-> Успішно перехоплено InvalidInputException: " + e.getMessage());
        }

        // Тест 3: Успішні обчислення
        System.out.println("\n[ТЕСТ 3] Базові коректні операції:");
        try {
            System.out.println("5 + 3 = " + calculator.add(5, 3));
            System.out.println("10 - 4 = " + calculator.subtract(10, 4));
            System.out.println("4 * 2.5 = " + calculator.multiply(4, 2.5));
            System.out.println("15 / 3 = " + calculator.divide(15, 3));
            System.out.println("sqrt(25) = " + calculator.sqrt(25));
        } catch (Exception e) {
            System.out.println("Помилка під час базових тестів: " + e.getMessage());
        }
    }
}