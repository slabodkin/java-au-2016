package ru.spbau.mit;

import static org.junit.Assert.*;
import org.junit.Test;
/**
 * Created by vbv on 22.03.16.
 */
public class Function1Test {
    @Test
    public void test1() {
        Function1<Integer, Integer> plus1 = new Function1<Integer, Integer>() {
            @Override
            public Integer apply(Integer arg) {
                return arg + 1;
            }
        };

        Function1<Integer, Integer> minus1 = new Function1<Integer, Integer>() {
            @Override
            public Integer apply(Integer arg) {
                return arg - 1;
            }
        };

        int x = 2;
        int y = plus1.compose(minus1).apply(x);
        assertEquals(x, y);
    }
}
