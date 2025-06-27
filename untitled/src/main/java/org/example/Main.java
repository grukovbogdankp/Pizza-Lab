package org.example;

import java.nio.file.Path;
import java.util.*;

public class Main {
    private static final Scanner SC = new Scanner(System.in);
    private static final Pizzeria pizzeria = new Pizzeria();

    public static void main(String[] args) {
        seedDemoData();

        while (true) {
            System.out.println("""
                    ===== Головне меню =====
                    1. Керування піцами
                    2. Керування клієнтами
                    3. Зробити замовлення
                    4. Зберегти дані
                    5. Завантажити дані
                    0. Вихід
                    """);
            System.out.print("Обрати: ");
            switch (SC.nextLine()) {
                case "1" -> pizzaMenu();
                case "2" -> customerMenu();
                case "3" -> makeOrder();
                case "4" -> save();
                case "5" -> load();
                case "0" -> { System.out.println("Бувай!"); return; }
                default -> System.out.println("Невідома команда.");
            }
        }
    }

    private static void pizzaMenu() {
        while (true) {
            System.out.println("""
                    --- Піци ---
                    1. Переглянути всі
                    2. Додати
                    3. Змінити ціну
                    4. Виготовити партію
                    5. Видалити
                    0. Назад
                    """);
            System.out.print(">> ");
            switch (SC.nextLine()) {
                case "1" -> pizzeria.listPizzas().forEach(System.out::println);
                case "2" -> {
                    System.out.print("Назва: ");     String name = SC.nextLine();
                    System.out.print("Ціна: ");      double price = Double.parseDouble(SC.nextLine());
                    System.out.print("У наявності: ");int qty = Integer.parseInt(SC.nextLine());
                    pizzeria.addPizza(new Pizza(name, price, qty));
                    System.out.println("Додано.");
                }
                case "3" -> {
                    System.out.print("Назва піци: "); String name = SC.nextLine();
                    System.out.print("Нова ціна: ");  double price = Double.parseDouble(SC.nextLine());
                    if (pizzeria.updatePizzaPrice(name, price)) System.out.println("Оновлено.");
                    else System.out.println("Піцу не знайдено.");
                }
                case "4" -> {
                    System.out.print("Назва піци: ");  String name = SC.nextLine();
                    System.out.print("Кількість +: "); int qty = Integer.parseInt(SC.nextLine());
                    if (pizzeria.producePizza(name, qty)) System.out.println("Склад оновлено.");
                    else System.out.println("Піцу не знайдено.");
                }
                case "5" -> {
                    System.out.print("Назва піци для видалення: "); String name = SC.nextLine();
                    pizzeria.removePizza(name);
                    System.out.println("Видалено (якщо існувала).");
                }
                case "0" -> { return; }
                default -> System.out.println("??");
            }
        }
    }


    private static void customerMenu() {
        while (true) {
            System.out.println("""
                    --- Клієнти ---
                    1. Переглянути всі
                    2. Додати
                    3. Змінити ім’я
                    4. Видалити
                    0. Назад
                    """);
            System.out.print(">> ");
            switch (SC.nextLine()) {
                case "1" -> pizzeria.listCustomers().forEach(System.out::println);
                case "2" -> {
                    System.out.print("Ім’я: ");   String name = SC.nextLine();
                    System.out.print("Email: ");  String email = SC.nextLine();
                    pizzeria.addCustomer(new Customer(name, email));
                    System.out.println("Додано.");
                }
                case "3" -> {
                    System.out.print("Email: ");   String email = SC.nextLine();
                    System.out.print("Нове ім’я: "); String newName = SC.nextLine();
                    if (pizzeria.updateCustomerName(email, newName)) System.out.println("Оновлено.");
                    else System.out.println("Клієнта не знайдено.");
                }
                case "4" -> {
                    System.out.print("Email для видалення: "); String email = SC.nextLine();
                    pizzeria.removeCustomer(email);
                    System.out.println("Видалено (якщо існував).");
                }
                case "0" -> { return; }
                default -> System.out.println("??");
            }
        }
    }


    private static void makeOrder() {
        System.out.print("Email клієнта: ");
        String email = SC.nextLine();

        Map<String,Integer> items = new HashMap<>();
        while (true) {
            System.out.print("Назва піци (Enter — завершити): ");
            String name = SC.nextLine();
            if (name.isBlank()) break;
            System.out.print("Кількість: ");
            int count = Integer.parseInt(SC.nextLine());
            items.put(name, count);
        }
        try {
            double total = pizzeria.placeOrder(email, items);
            System.out.printf("Замовлення оформлено. Сума: %.2f ₴%n", total);
        } catch (Exception ex) {
            System.out.println("⚠ " + ex.getMessage());
        }
    }

    private static void save() {
        System.out.println("""
            --- Експорт даних ---
            Виберіть спосіб сортування піц перед експортом:
            1. За назвою (А–Я)
            2. За ціною (дешеві спочатку)
            3. За кількістю проданих
            """);
        System.out.print("Сортування: ");
        String opt = SC.nextLine();

        Comparator<Pizza> comparator = switch (opt) {
            case "2" -> Comparator.comparing(Pizza::getPrice);
            case "3" -> Comparator.comparing(Pizza::getSold).reversed();
            default  -> Comparator.comparing(Pizza::getName); // За назвою
        };

        try {
            DataManager.exportPizzasSorted(pizzeria.listPizzas(), Path.of("pizzas.json"), comparator);
            DataManager.exportCustomers(pizzeria.listCustomers(), Path.of("customers.json"));
            System.out.println("✅ Дані збережено (pizzas.json, customers.json).");
        } catch (Exception e) {
            System.out.println("❌ Помилка збереження: " + e.getMessage());
        }
    }
    private static void load() {
        try {
            DataManager.importPizzas(Path.of("pizzas.json")).forEach(pizzeria::addPizza);
            DataManager.importCustomers(Path.of("customers.json")).forEach(pizzeria::addCustomer);
            System.out.println("✅ Дані завантажено.");
        } catch (Exception e) {
            System.out.println("Помилка завантаження: " + e.getMessage());
        }
    }

    private static void seedDemoData() {
        pizzeria.addPizza(new Pizza("Маргарита", 180, 10));
        pizzeria.addPizza(new Pizza("Пепероні", 220, 8));
        pizzeria.addCustomer(new Customer("Анна", "anna@example.com"));
    }
}