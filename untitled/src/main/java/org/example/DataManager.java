package org.example;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public final class DataManager {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    public static void exportPizzas(Collection<Pizza> pizzas, Path file) throws IOException {
        exportPizzasSorted(pizzas, file, Comparator.comparing(Pizza::getName));
    }

    public static void exportPizzasSorted(Collection<Pizza> pizzas, Path file, Comparator<Pizza> comparator) throws IOException {
        List<Pizza> sorted = new ArrayList<>(pizzas);
        sorted.sort(comparator);
        try (Writer w = Files.newBufferedWriter(file)) {
            GSON.toJson(sorted, w);
        }
    }

    public static List<Pizza> importPizzas(Path file) throws IOException {
        Type listType = new TypeToken<List<Pizza>>(){}.getType();
        try (Reader r = Files.newBufferedReader(file)) {
            return GSON.fromJson(r, listType);
        }
    }

    public static void exportCustomers(Collection<Customer> customers, Path file) throws IOException {
        try (Writer w = Files.newBufferedWriter(file)) {
            GSON.toJson(customers, w);
        }
    }

    public static List<Customer> importCustomers(Path file) throws IOException {
        Type listType = new TypeToken<List<Customer>>(){}.getType();
        try (Reader r = Files.newBufferedReader(file)) {
            return GSON.fromJson(r, listType);
        }
    }
}