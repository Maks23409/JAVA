import java.util.Scanner;

public class TestAutomaton {

    // S = 0; 1 = T; 2 = TE; 3 = TES; F = 4.
    public static String recognize(String text) {
        int state = 0;

        for (char symbol : text.toCharArray()) {
            if (state == 4) {
                break; // Після знаходження TEST автомат залишається в стані F.
            }

            switch (state) {
                case 0 -> state = (symbol == 'T') ? 1 : 0;
                case 1 -> {
                    if (symbol == 'E') {
                        state = 2;
                    } else if (symbol == 'T') {
                        state = 1; // Нова літера T може починати нове TEST.
                    } else {
                        state = 0;
                    }
                }
                case 2 -> {
                    if (symbol == 'S') {
                        state = 3;
                    } else if (symbol == 'T') {
                        state = 1; // Не пропускаємо новий початок TEST.
                    } else {
                        state = 0;
                    }
                }
                case 3 -> state = (symbol == 'T') ? 4 : 0;
                default -> throw new IllegalStateException("Невідомий стан: " + state);
            }
        }

        if (state == 0) return "S";
        if (state == 4) return "F";
        return String.valueOf(state);
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Введіть рядок: ");
        String text = scanner.nextLine();
        System.out.println("Кінцевий стан: " + recognize(text));
    }
}
