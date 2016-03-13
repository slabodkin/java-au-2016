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



    @Test
    public void testSimpleSerialization() {
        StringSet stringSet = instance();

        assertTrue(stringSet.add("abc"));
        assertTrue(stringSet.add("cde"));

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ((StreamSerializable) stringSet).serialize(outputStream);

        ByteArrayInputStream inputStream = new ByteArrayInputStream(outputStream.toByteArray());
        StringSet newStringSet = instance();
        ((StreamSerializable) newStringSet).deserialize(inputStream);

        assertTrue(newStringSet.contains("abc"));
        assertTrue(newStringSet.contains("cde"));
    }

    @Test
    public void testEmptySerialization() {
        StringSet stringSet = instance();

        assertTrue(stringSet.size() == 0);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ((StreamSerializable) stringSet).serialize(outputStream);

        ByteArrayInputStream inputStream = new ByteArrayInputStream(outputStream.toByteArray());
        StringSet newStringSet = instance();
        ((StreamSerializable) newStringSet).deserialize(inputStream);

        assertFalse(newStringSet.contains("abc"));
        assertTrue(newStringSet.size() == 0);
    }

    @Test
    public void testNonEmptyDeserialization() {
        StringSet stringSet = instance();
        StringSet newStringSet = instance();

        assertTrue(stringSet.add("abc"));
        assertTrue(newStringSet.add("abd"));

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ((StreamSerializable) stringSet).serialize(outputStream);

        ByteArrayInputStream inputStream = new ByteArrayInputStream(outputStream.toByteArray());

        ((StreamSerializable) newStringSet).deserialize(inputStream);

        assertTrue(newStringSet.contains("abc"));
        assertFalse(newStringSet.contains("abd"));
        assertTrue(newStringSet.size() == 1);
    }


    @Test(expected=SerializationException.class)
    public void testSimpleSerializationFails() {
        StringSet stringSet = instance();

        assertTrue(stringSet.add("abc"));
        assertTrue(stringSet.add("cde"));

        OutputStream outputStream = new OutputStream() {
            @Override
            public void write(int b) throws IOException {
                throw new IOException("Fail");
            }
        };

        ((StreamSerializable) stringSet).serialize(outputStream);
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
