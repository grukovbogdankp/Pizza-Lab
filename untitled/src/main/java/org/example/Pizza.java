package org.example;

import java.util.Objects;

/** Модель піци. */
public class Pizza {
    private String name;
    private double price;
    private int number;
    private int sold;

    public Pizza(String name, double price, int number) {
        this.name = name;
        this.price = price;
        if (number < 0) throw new IllegalArgumentException("Кількість не може бути від'ємною");
        this.number = number;
        this.sold = 0;
    }

    public void produce(int count) {
        if (count <= 0) throw new IllegalArgumentException("Кількість має бути > 0");
        number += count;
    }

    public double sell(int count) {
        if (count <= 0) throw new IllegalArgumentException("Кількість має бути > 0");
        if (count > number) throw new IllegalStateException("Недостатньо піц на складі");
        number -= count;
        sold += count;
        return count * price;
    }


    public String getName()         { return name; }
    public double getPrice()        { return price; }
    public int getNumber()       { return number; }
    public int getSold()            { return sold; }

    public void setPrice(double p)  { this.price = p; }

    @Override public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Pizza)) return false;
        Pizza pizza = (Pizza) o;
        return Objects.equals(name, pizza.name);
    }
    @Override public int hashCode() { return Objects.hash(name); }

    @Override public String toString() {
        return "%s (%.2f ₴) — в наявності: %d, продано: %d"
                .formatted(name, price, number, sold);
    }
}