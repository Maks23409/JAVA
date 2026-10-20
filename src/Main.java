import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// ==========================================
// 1. Клас Category
// ==========================================
class Category {
    private int id;
    private String name;

    public Category(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Категорія{" +
                "id=" + id +
                ", назва='" + name + '\'' +
                '}';
    }
}

// ==========================================
// 2. Клас Product
// ==========================================
class Product {
    private int id;
    private String name;
    private double price;
    private String description;
    private Category category;

    public Product(int id, String name, double price, String description, Category category) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.description = description;
        this.category = category;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return "Товар{" +
                "id=" + id +
                ", назва='" + name + '\'' +
                ", ціна=" + price +
                ", опис='" + description + '\'' +
                ", категорія='" + (category != null ? category.getName() : "Без категорії") + '\'' +
                '}';
    }
}

// ==========================================
// 3. Клас Cart
// ==========================================
class Cart {
    private List<Product> products;

    public Cart() {
        this.products = new ArrayList<>();
    }

    public void addProduct(Product product) {
        products.add(product);
    }

    // Видалення товару за ID
    public boolean removeProductById(int id) {
        return products.removeIf(p -> p.getId() == id);
    }

    public List<Product> getProducts() {
        return new ArrayList<>(products);
    }

    public double getTotalPrice() {
        double total = 0;
        for (Product product : products) {
            total += product.getPrice();
        }
        return total;
    }

    public void clear() {
        products.clear();
    }

    @Override
    public String toString() {
        if (products.isEmpty()) {
            return "Кошик порожній.";
        }
        StringBuilder sb = new StringBuilder("Кошик містить:\n");
        for (Product product : products) {
            sb.append(product.toString()).append("\n");
        }
        sb.append("Загальна вартість: ").append(getTotalPrice());
        return sb.toString();
    }
}

// ==========================================
// 4. Клас Order
// ==========================================
class Order {
    private List<Product> products;
    private double totalPrice;
    private String status;

    public Order(Cart cart) {
        this.products = new ArrayList<>(cart.getProducts());
        this.totalPrice = cart.getTotalPrice();
        this.status = "Нове";
    }

    public List<Product> getProducts() {
        return products;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Замовлення:\n");
        for (Product product : products) {
            sb.append(product.toString()).append("\n");
        }
        sb.append("Загальна вартість: ").append(totalPrice).append("\n");
        sb.append("Статус: ").append(status);
        return sb.toString();
    }
}

// ==========================================
// 5. Головний клас Main з меню (CLI)
// ==========================================
public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Створення категорій
        Category electronics = new Category(1, "Електроніка");
        Category smartphones = new Category(2, "Смартфони");
        Category accessories = new Category(3, "Аксесуари");

        // Каталог товарів
        List<Product> catalog = new ArrayList<>();
        catalog.add(new Product(1, "Ноутбук", 19999.99, "Високопродуктивний ноутбук для роботи та ігор", electronics));
        catalog.add(new Product(2, "Смартфон", 12999.50, "Смартфон з великим екраном та високою автономністю", smartphones));
        catalog.add(new Product(3, "Навушники", 2499.00, "Бездротові навушники з шумозаглушенням", accessories));

        Cart cart = new Cart();
        List<Order> orderHistory = new ArrayList<>();

        while (true) {
            System.out.println("\n--- МЕНЮ ---");
            System.out.println("1 - Переглянути список товарів");
            System.out.println("2 - Додати товар до кошика");
            System.out.println("3 - Видалити товар з кошика");
            System.out.println("4 - Переглянути кошик");
            System.out.println("5 - Зробити замовлення");
            System.out.println("6 - Переглянути історію замовлень");
            System.out.println("7 - Пошук товарів");
            System.out.println("0 - Вийти");
            System.out.print("Виберіть опцію: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Очищення буфера після зачитування числа

            switch (choice) {
                case 1:
                    System.out.println("\n--- Каталог товарів ---");
                    for (Product p : catalog) {
                        System.out.println(p);
                    }
                    break;

                case 2:
                    System.out.print("Введіть ID товару для додавання до кошика: ");
                    int addId = scanner.nextInt();
                    Product productToAdd = null;
                    for (Product p : catalog) {
                        if (p.getId() == addId) {
                            productToAdd = p;
                            break;
                        }
                    }
                    if (productToAdd != null) {
                        cart.addProduct(productToAdd);
                        System.out.println("Товар '" + productToAdd.getName() + "' додано до кошика!");
                    } else {
                        System.out.println("Товар з таким ID не знайдено.");
                    }
                    break;

                case 3:
                    System.out.print("Введіть ID товару для видалення з кошика: ");
                    int removeId = scanner.nextInt();
                    if (cart.removeProductById(removeId)) {
                        System.out.println("Товар успішно видалено з кошика.");
                    } else {
                        System.out.println("Товар з таким ID не знайдено у кошику.");
                    }
                    break;

                case 4:
                    System.out.println("\n--- Вміст кошика ---");
                    System.out.println(cart);
                    break;

                case 5:
                    if (cart.getProducts().isEmpty()) {
                        System.out.println("Кошик порожній. Додайте товари перед оформленням замовлення.");
                    } else {
                        Order order = new Order(cart);
                        orderHistory.add(order);
                        System.out.println("\nЗамовлення успішно оформлено:");
                        System.out.println(order);
                        cart.clear();
                    }
                    break;

                case 6:
                    System.out.println("\n--- Історія замовлень ---");
                    if (orderHistory.isEmpty()) {
                        System.out.println("Історія замовлень порожня.");
                    } else {
                        for (int i = 0; i < orderHistory.size(); i++) {
                            System.out.println("--- Замовлення #" + (i + 1) + " ---");
                            System.out.println(orderHistory.get(i));
                        }
                    }
                    break;

                case 7:
                    System.out.print("Введіть назву або категорію для пошуку: ");
                    String query = scanner.nextLine().toLowerCase();
                    boolean found = false;
                    System.out.println("\n--- Результати пошуку ---");
                    for (Product p : catalog) {
                        if (p.getName().toLowerCase().contains(query) ||
                                p.getCategory().getName().toLowerCase().contains(query)) {
                            System.out.println(p);
                            found = true;
                        }
                    }
                    if (!found) {
                        System.out.println("Товарів за вашим запитом не знайдено.");
                    }
                    break;

                case 0:
                    System.out.println("Дякуємо, що використовували наш магазин!");
                    scanner.close();
                    return;

                default:
                    System.out.println("Невідома опція. Спробуйте ще раз.");
                    break;
            }
        }
    }
}