package PR2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class Main {

    // 1. Модель транзакції
    public static class Transaction {
        private String date;
        private double amount;
        private String description;

        public Transaction(String date, double amount, String description) {
            this.date = date;
            this.amount = amount;
            this.description = description;
        }

        public String getDate() { return date; }
        public double getAmount() { return amount; }
        public String getDescription() { return description; }

        @Override
        public String toString() {
            return "Transaction{" +
                    "date='" + date + '\'' +
                    ", amount=" + amount +
                    ", description='" + description + '\'' +
                    '}';
        }
    }

    // 2. Читання CSV-даних з локального текстового рядка
    public static class TransactionCSVReader {
        private static final String LOCAL_CSV_DATA =
                "Дата,Сума,Категорія\n" +
                        "05-12-2023,-450,Сільпо\n" +
                        "07-12-2023,-120,Аптека\n" +
                        "10-12-2023,8000,Зарплата\n" +
                        "12-12-2023,1500,Авторські винагороди\n" +
                        "14-12-2023,-3200,Оренда квартири\n" +
                        "16-12-2023,2500,Фріланс\n" +
                        "18-12-2023,-200,Кав'ярня\n" +
                        "20-12-2023,-750,Подарунки\n" +
                        "22-12-2023,-600,Бензин\n" +
                        "24-12-2023,-950,Ресторан\n" +
                        "26-12-2023,3000,Премія\n" +
                        "28-12-2023,-400,Кінотеатр\n" +
                        "30-12-2023,-1200,Новорічні прикраси\n" +
                        "05-01-2024,-500,Сільпо\n" +
                        "10-01-2024,-8800,Подарунки\n" +
                        "12-01-2024,-7850,Сільпо\n" +
                        "15-01-2024,-6000,Бензин\n" +
                        "18-01-2024,-3300,Комунальні послуги\n" +
                        "20-01-2024,-9100,Інші витрати";

        public List<Transaction> readTransactions() {
            List<Transaction> transactions = new ArrayList<>();
            try (BufferedReader br = new BufferedReader(new StringReader(LOCAL_CSV_DATA))) {
                String line;
                boolean isHeader = true;
                while ((line = br.readLine()) != null) {
                    if (isHeader) {
                        isHeader = false;
                        continue;
                    }
                    String[] values = line.split(",");
                    if (values.length >= 3) {
                        transactions.add(new Transaction(
                                values[0].trim(),
                                Double.parseDouble(values[1].trim()),
                                values[2].trim()
                        ));
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return transactions;
        }
    }

    // 3. Аналіз транзакцій
    public static class TransactionAnalyzer {
        private final List<Transaction> transactions;
        private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

        public TransactionAnalyzer(List<Transaction> transactions) {
            this.transactions = transactions;
        }

        public double calculateTotalBalance() {
            return transactions.stream().mapToDouble(Transaction::getAmount).sum();
        }

        public int countTransactionsByMonth(String monthYear) {
            int count = 0;
            for (Transaction t : transactions) {
                LocalDate date = LocalDate.parse(t.getDate(), dateFormatter);
                if (date.format(DateTimeFormatter.ofPattern("MM-yyyy")).equals(monthYear)) {
                    count++;
                }
            }
            return count;
        }

        public List<Transaction> findTopExpenses() {
            return transactions.stream()
                    .filter(t -> t.getAmount() < 0)
                    .sorted(Comparator.comparing(Transaction::getAmount))
                    .limit(10)
                    .collect(Collectors.toList());
        }

        public Map<String, Double> calculateExpensesByCategory() {
            Map<String, Double> categoryMap = new HashMap<>();
            for (Transaction t : transactions) {
                if (t.getAmount() < 0) {
                    categoryMap.put(t.getDescription(),
                            categoryMap.getOrDefault(t.getDescription(), 0.0) + Math.abs(t.getAmount()));
                }
            }
            return categoryMap;
        }
    }

    // 4. Генерація звітів
    public static class TransactionReportGenerator {
        public void printBalanceReport(double totalBalance) {
            System.out.printf("Загальний баланс: %.2f грн%n", totalBalance);
        }

        public void printTransactionsCountByMonth(String monthYear, int count) {
            System.out.println("Кількість транзакцій за " + monthYear + ": " + count);
        }

        public void printTopExpensesReport(List<Transaction> topExpenses) {
            System.out.println("\n--- 10 найбільших витрат ---");
            for (Transaction expense : topExpenses) {
                System.out.printf("%s: %.2f грн%n", expense.getDescription(), expense.getAmount());
            }
        }

        public void printCategoryVisualReport(Map<String, Double> categoryExpenses) {
            System.out.println("\n--- Візуальний звіт витрат по категоріях (* = 1000 грн) ---");
            for (Map.Entry<String, Double> entry : categoryExpenses.entrySet()) {
                int stars = (int) (entry.getValue() / 1000);
                String bar = "*".repeat(Math.max(1, stars));
                System.out.printf("%-20s | %-15s (%.2f грн)%n", entry.getKey(), bar, entry.getValue());
            }
        }
    }

    // 5. Запуск програми
    public static void main(String[] args) {
        TransactionCSVReader csvReader = new TransactionCSVReader();
        List<Transaction> transactions = csvReader.readTransactions();

        TransactionAnalyzer analyzer = new TransactionAnalyzer(transactions);
        TransactionReportGenerator reportGenerator = new TransactionReportGenerator();

        reportGenerator.printBalanceReport(analyzer.calculateTotalBalance());
        reportGenerator.printTransactionsCountByMonth("01-2024", analyzer.countTransactionsByMonth("01-2024"));
        reportGenerator.printTopExpensesReport(analyzer.findTopExpenses());
        reportGenerator.printCategoryVisualReport(analyzer.calculateExpensesByCategory());
    }
}