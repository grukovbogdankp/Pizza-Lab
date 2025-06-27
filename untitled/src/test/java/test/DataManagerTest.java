package test;

import org.example.*;


import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import java.io.*;
import java.nio.file.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class DataManagerTest {
    @Test void export_sorted() throws Exception {
        var list=List.of(new Pizza("Б",2,1),new Pizza("А",1,1));
        StringWriter mem=new StringWriter();
        Files fs=mock(Files.class);
        when(fs.newBufferedWriter(any())).thenReturn(new BufferedWriter(new StringWriter()));
            DataManager.exportPizzasSorted(list,Path.of("x"),java.util.Comparator.comparing(Pizza::getName));
        assertTrue(mem.toString().indexOf("\"А\"") < mem.toString().indexOf("\"Б\""));
    }

    @Test void import_ok() throws Exception {
        String json="[{\\\\\"name\\\\\":\\\\\"Маргарита\\\\\",\\\\\"price\\\\\":180.0,\\\\\"available\\\\\":5,\\\\\"sold\\\\\":0}]";
        Files fs=mock(Files.class);
            when(fs.newBufferedReader(any())).thenReturn(new BufferedReader(new StringReader(json)));
            assertEquals("Маргарита", DataManager.importPizzas(Path.of("x")).get(0).getName());
    }

    @Test void export_cust() throws Exception {
        StringWriter mem=new StringWriter();
        Files fs=mock(Files.class);
        when(fs.newBufferedWriter(any())).thenReturn(new BufferedWriter(new StringWriter()));
            DataManager.exportCustomers(List.of(new Customer("A","a@x")),Path.of("x"));
        assertTrue(mem.toString().contains("a@x"));
    }
}