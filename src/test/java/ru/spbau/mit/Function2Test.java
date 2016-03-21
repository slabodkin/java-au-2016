package ru.spbau.mit;

import static org.junit.Assert.*;
import org.junit.Test;
/**
 * Created by vbv on 22.03.16.
 */
public class Function2Test {
    @Test
    public void test1() {
        Function2<Integer, Integer, Integer> substraction = new Function2<Integer, Integer, Integer>() {
            @Override
            public Integer apply(Integer x, Integer y) {
                return x - y;
            }
        };

        Function1<Integer, Integer> plus1 = new Function1<Integer, Integer>() {
            @Override
            public Integer apply(Integer x) {
                return x + 1;
            }
        };

        int x = 2;
        int y = 1;
        int z = substraction.compose(plus1).apply(x, y);
        assertEquals(z, 2);
    }

    @Test
    public void test2() {
        Function2<Integer, Integer, Integer> substraction = new Function2<Integer, Integer, Integer>() {
            @Override
            public Integer apply(Integer x, Integer y) {
                return x - y;
            }
        };


        int x = 2;
        int y = 1;
        int z = substraction.bind1(x).apply(y);
        assertEquals(z, 1);
        int t = substraction.bind2(x).apply(y);
        assertEquals(t, -1);
    }

    @Test
    public void test3() {
        Function2<Integer, Integer, Integer> substraction = new Function2<Integer, Integer, Integer>() {
            @Override
            public Integer apply(Integer x, Integer y) {
                return x - y;
            }
        };


        int x = 2;
        int y = 1;
        int z = substraction.curry().apply(x).apply(y);
        assertEquals(z, 1);
    }
}
