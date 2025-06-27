package test;

import org.example.*;
import org.junit.jupiter.api.*;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class PizzeriaTest {
    Pizzeria p;
    @BeforeEach void init(){ p=new Pizzeria(); p.addPizza(new Pizza("M",10,5)); p.addCustomer(new Customer("A","a@x")); }

    @Test void order_ok(){ assertEquals(30,p.placeOrder("a@x", Map.of("M",3))); }
    @Test void order_noPizza(){ assertThrows(IllegalArgumentException.class,()->p.placeOrder("a@x",Map.of("X",1))); }
    @Test void order_noCust(){ assertThrows(IllegalArgumentException.class,()->p.placeOrder("z@x",Map.of("M",1))); }
    @Test void crud_updates(){ assertTrue(p.updatePizzaPrice("M",15)); assertEquals(15,p.getPizza("M").getPrice()); }
}
