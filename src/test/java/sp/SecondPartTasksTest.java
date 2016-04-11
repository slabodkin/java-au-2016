package sp;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import static sp.SecondPartTasks.*;
import java.util.Map;
import java.util.HashMap;

import static org.junit.Assert.*;

public class SecondPartTasksTest {

    @Test
    public void testFindQuotes() {
        List<String> paths = Arrays.asList(
                "src/test/resources/paint_it_black.txt",
                "src/test/resources/thick_as_a_brick.txt");
        List<String> answer = Arrays.asList(
                "I see a red door and I want it painted black",
                "I see a line of cars and they're all painted black",
                "I see my red door, I must have it painted black",
                "I see a red door and I want it painted black",
                "I want to see it painted, painted black",
                "I want to see it painted, painted, painted, painted black, yeah",
                "The poet and the painter casting shadows on the water ");
        assertEquals(answer, findQuotes(paths, "paint"));
    }

    @Test
    public void testPiDividedBy4() {
        final double answer = Math.PI / 4;
        assertTrue(Math.abs(answer - piDividedBy4()) < 0.01);
    }

    @Test
    public void testFindPrinterAndFindQuotes() {
        Map<String, List<String>> compositions = new HashMap<>();
        compositions.put("Jethro Tull", findQuotes(
                Arrays.asList("src/test/resources/thick_as_a_brick.txt"), " "));
        compositions.put("Rolling Stones", findQuotes(
                Arrays.asList("src/test/resources/paint_it_black.txt"), " "));
        assertEquals(findPrinter(compositions), "Jethro Tull");
    }

    @Test
    public void testCalculateGlobalOrder() {
        Map<String, Integer> order1 = new HashMap<>();
        order1.put("LG G5", 3);
        order1.put("HTC One M10", 1);
        order1.put("Samsung Galaxy s7", 4);
        Map<String, Integer> order2 = new HashMap<>();
        order2.put("LG G5", 2);
        order2.put("iPhone 7", 6);
        order2.put("Samsung Galaxy s7", 1);
        List<Map<String, Integer>> orders = Arrays.asList(order1, order2);
        Map<String, Integer> ordersSum = new HashMap<>();
        ordersSum.put("LG G5", 5);
        ordersSum.put("HTC One M10", 1);
        ordersSum.put("Samsung Galaxy s7", 5);
        ordersSum.put("iPhone 7", 6);
        assertEquals(calculateGlobalOrder(orders), ordersSum);
    }
}
