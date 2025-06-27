package org.example;

import java.util.*;

public class Pizzeria {
    private final Map<String, Pizza> pizzas = new HashMap<>();
    private final Map<String, Customer> customers = new HashMap<>();

    // ---------- CRUD піц ----------
    public void addPizza(Pizza p)            { pizzas.put(p.getName(), p); }
    public Pizza getPizza(String name)       { return pizzas.get(name); }
    public Collection<Pizza> listPizzas()    { return pizzas.values(); }
    public void removePizza(String name)     { pizzas.remove(name); }

    /** Змінити ціну піци. Повертає true, якщо знайдено. */
    public boolean updatePizzaPrice(String name, double newPrice) {
        Pizza p = pizzas.get(name);
        if (p == null) return false;
        p.setPrice(newPrice);
        return true;
    }

    /** Додати на склад певну кількість піц. */
    public boolean producePizza(String name, int qty) {
        Pizza p = pizzas.get(name);
        if (p == null) return false;
        p.produce(qty);
        return true;
    }

    // ---------- CRUD клієнтів ----------
    public void addCustomer(Customer c)          { customers.put(c.getEmail(), c); }
    public Customer getCustomer(String email)    { return customers.get(email); }
    public Collection<Customer> listCustomers()  { return customers.values(); }
    public void removeCustomer(String email)     { customers.remove(email); }

    /** Оновити ім’я клієнта. */
    public boolean updateCustomerName(String email, String newName) {
        Customer c = customers.get(email);
        if (c == null) return false;
        // створюємо новий об’єкт, щоб email залишився ключем
        customers.put(email, new Customer(newName, email));
        return true;
    }

    // ---------- Замовлення ----------
    public double placeOrder(String email, Map<String,Integer> items) {
        Customer customer = customers.get(email);
        if (customer == null) throw new IllegalArgumentException("Клієнта не знайдено: " + email);

        double total = 0;
        for (var e : items.entrySet()) {
            Pizza pizza = pizzas.get(e.getKey());
            if (pizza == null) throw new IllegalArgumentException("Невідома піца: " + e.getKey());
            total += pizza.sell(e.getValue());
        }
        return total;
    }
}
