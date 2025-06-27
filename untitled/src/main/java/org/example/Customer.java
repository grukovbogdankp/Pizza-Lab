package org.example;


import java.util.Objects;

/** Замовник піцерії. */
public class Customer {
    private String name;
    private String email;

    public Customer(String name, String email) {
        if (!email.matches(".+@.+")) throw new IllegalArgumentException("Невалідний email");
        this.name = name;
        this.email = email;
    }

    // Гетери/сетери
    public String getName()  { return name;  }
    public String getEmail() { return email; }

    @Override public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Customer)) return false;
        Customer that = (Customer) o;
        return Objects.equals(email, that.email);
    }
    @Override public int hashCode() { return Objects.hash(email); }

    @Override public String toString() {
        return "%s <%s>".formatted(name, email);
    }
}