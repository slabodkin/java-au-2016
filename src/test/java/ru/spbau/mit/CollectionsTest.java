package ru.spbau.mit;

import static org.junit.Assert.*;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
/**
 * Created by vbv on 22.03.16.
 */
public class CollectionsTest {

    @Test
    public void test1() {
        Function1<Integer, Integer> plus1 = new Function1<Integer, Integer>() {
            @Override
            public Integer apply(Integer x) {
                return x + 1;
            }
        };

        Predicate<Integer> isEven = new Predicate<Integer>() {
            @Override
            public Boolean apply(Integer x) {
                return x % 2 == 0;
            }
        };

        final ArrayList<Integer> toTest = new ArrayList<Integer>(Arrays.asList(1, 2));

        Iterable<Integer> toMap = Collections.map(plus1, toTest);
        final int listSecond = 3;
        assertEquals(toMap, Arrays.asList(2, listSecond));

        Iterable<Integer> toFilter = Collections.filter(isEven, toTest);
        assertEquals(toFilter, Arrays.asList(2));
    }

    @Test
    public void test2() {
        final ArrayList<Integer> toTest = new ArrayList<Integer>(Arrays.asList(2, 6, 1));

        Predicate<Integer> isEven = new Predicate<Integer>() {
            @Override
            public Boolean apply(Integer x) {
                return x % 2 == 0;
            }
        };

        Predicate<Integer> isMoreThan5 = new Predicate<Integer>() {
            private final int threshold = 5;
            @Override
            public Boolean apply(Integer x) {
                return x > threshold;
            }
        };

        Iterable<Integer> toTakeWhile = Collections.takeWhile(isEven, toTest);
        final int x = 6;
        assertEquals(toTakeWhile, Arrays.asList(2, x));

        Iterable<Integer> toTakeUnless = Collections.takeUnless(isMoreThan5, toTest);
        assertEquals(toTakeUnless, Arrays.asList(2));
    }

    @Test
    public void test3() {
        Function2<String, String, String> conc = new Function2<String, String, String>() {
            @Override
            public String apply(String x, String y) {
                StringBuilder s = new StringBuilder();
                s.append(x);
                s.append(y);
                return s.toString();
            }
        };


        final ArrayList<String> toTest = new ArrayList<String>(Arrays.asList("a", "b"));
        final String foldled = Collections.foldl(conc, "c", toTest);
        final String answerl = "cab";
        assertEquals(foldled, answerl);

        final String foldred = Collections.foldr(conc, "c", toTest);
        final String answerr = "abc";
        assertEquals(foldred, answerr);
    }

}
