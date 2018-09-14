package com.example.vitalyyurenya.appetizer;

import com.example.vitalyyurenya.appetizer.utils.UrlConverter;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void converter_isCorrect() {assertEquals("http", UrlConverter.convert("http"));}
}