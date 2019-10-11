package com.cjremshaw.statsutil;

import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.OptionalDouble;
import java.util.Random;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class StatisticsUtilTest {
    private Random r = new Random();

    @Test
    public void testMedianEmptyList() {
        assertEquals(OptionalDouble.empty(), StatisticsUtil.median());
    }

    @Test
    public void testMedianSingleEntry() {
        int i = r.nextInt();
        assertEquals(OptionalDouble.of(i), StatisticsUtil.median(i));
    }

    @Test
    public void testMedianSetValues() {
        assertEquals(OptionalDouble.of(3), StatisticsUtil.median(8, 4, 3, 0, 1));
    }

    @Test
    public void testModeEmptyList() {
        assertEquals(OptionalDouble.empty(), StatisticsUtil.median());
    }

    @Test
    public void testModeSimple() {
        Integer firstNumber = r.nextInt();
        Integer secondNumber = r.nextInt();
        List<Integer> expected = Collections.singletonList(secondNumber);
        List<Integer> actual = StatisticsUtil.mode(firstNumber, secondNumber, secondNumber, firstNumber, secondNumber);
        assertNotNull( actual );
        assertEquals( 1, actual.size() );
        assertEquals( expected.get(0), actual.get(0) );
    }

    @Test
    public void testMultiMode() {
        Integer[] numbers = new Integer[] { r.nextInt(), r.nextInt(), r.nextInt() };
        List<Integer> expected = Arrays.asList( numbers[1], numbers[0] );
        List<Integer> actual = StatisticsUtil.mode( numbers[2],
                numbers[1], numbers[0], numbers[1], numbers[0] );
        assertEquals( 2, actual.size() );
        assertTrue( actual.contains( expected.get(0) ));
        assertTrue( actual.contains( expected.get(1) ));
    }

    @Test
    public void testNoMode() {
        Integer[] numbers = new Integer[] { r.nextInt(), r.nextInt(), r.nextInt() };
        List<Integer> actual = StatisticsUtil.mode( numbers[0], numbers[1], numbers[2]);
        assertEquals( 0, actual.size() );
    }
}
