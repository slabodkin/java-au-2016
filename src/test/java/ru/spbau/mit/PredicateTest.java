package ru.spbau.mit;


import static org.junit.Assert.*;
import org.junit.Test;
/**
 * Created by vbv on 22.03.16.
 */
public class PredicateTest {
    @Test
    public void test1() {
        Predicate<Integer> isEven = new Predicate<Integer>() {
            @Override
            public Boolean apply(Integer x) {
                return x % 2 == 0;
            }
        };

        Predicate<Integer> isMoreThan1 = new Predicate<Integer>() {
            @Override
            public Boolean apply(Integer x) {
                return x > 1;
            }
        };

        final int x = 3;
        final int y = 4;
        final int z = 1;
        assertFalse(isEven.and(isMoreThan1).apply(x));
        assertTrue(isEven.and(isMoreThan1).apply(y));
        assertTrue(isMoreThan1.or(isEven).apply(x));
        assertFalse(isMoreThan1.or(isEven).apply(z));
    }

    @Test
    public void test2() {
        Predicate<Integer> isEven = new Predicate<Integer>() {
            @Override
            public Boolean apply(Integer x) {
                return x % 2 == 0;
            }
        };
        final int x = 3;
        assertTrue(isEven.not().apply(x));
    }

    @Test
    public void test3() {
        final int x = 1;
        assertTrue(Predicate.ALWAYS_TRUE.apply(x));
        assertFalse(Predicate.ALWAYS_FALSE.apply(x));
    }

}
