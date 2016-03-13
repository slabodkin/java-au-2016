package ru.spbau.mit;

import static org.junit.Assert.*;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class StringSetTest {

    @Test
    public void testSimple() {

        StringSet stringSet = instance();
        stringSet.add("sa");
        stringSet.add("sb");
        stringSet.remove("sa");
        assertTrue(stringSet.contains("sb"));
        assertTrue(stringSet.remove("sb"));
        assertFalse(stringSet.remove("sb"));
        assertTrue(stringSet.howManyStartsWithPrefix("sb") == 0);
        assertTrue(stringSet.add("saa"));
        assertTrue(stringSet.add("sab"));
        assertTrue(stringSet.howManyStartsWithPrefix("sa") == 2);
        assertFalse(stringSet.contains("sb"));
        assertFalse(stringSet.remove("sb"));
        assertTrue(stringSet.remove("sab"));
        assertTrue(stringSet.howManyStartsWithPrefix("sa") == 1);
    }

    public static StringSet instance() {
        try {
            return (StringSet) Class.forName("ru.spbau.mit.StringSetImpl").newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        throw new IllegalStateException("Error while class loading");
    }
}
