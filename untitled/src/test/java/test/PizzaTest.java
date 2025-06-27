package test;

import org.example.Pizza;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PizzaTest {
    @Test void produce_ok()           { var p=new Pizza("M",1,5); p.produce(2); assertEquals(7,p.getNumber()); }
    @Test void produce_bad()          { assertThrows(IllegalArgumentException.class,()->new Pizza("M",1,1).produce(0)); }
    @Test void sell_ok()              { var p=new Pizza("M",10,3); assertEquals(20,p.sell(2)); }
    @Test void sell_tooMany()         { assertThrows(IllegalStateException.class,()->new Pizza("M",1,1).sell(5)); }
}
