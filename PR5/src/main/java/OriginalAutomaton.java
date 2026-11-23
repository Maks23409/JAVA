/**
 * Автомат точно за переходами на схемі з завдання (до виправлення).
 * Неправильно обробляє перекриття, наприклад TTEST та TETEST.
 */
public class OriginalAutomaton {
    public static String recognize(String text) {
        int state = 0;

        for (char symbol : text.toCharArray()) {
            if (state == 4) break;

            switch (state) {
                case 0 -> state = symbol == 'T' ? 1 : 0;
                case 1 -> state = symbol == 'E' ? 2 : 0; // Помилка: на T треба 1.
                case 2 -> state = symbol == 'S' ? 3 : 0; // Помилка: на T треба 1.
                case 3 -> state = symbol == 'T' ? 4 : 0;
                default -> throw new IllegalStateException("Невідомий стан: " + state);
            }
        }

        if (state == 0) return "S";
        if (state == 4) return "F";
        return String.valueOf(state);
    }
}
