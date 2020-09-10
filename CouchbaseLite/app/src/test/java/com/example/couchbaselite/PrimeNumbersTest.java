package com.example.couchbaselite;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class PrimeNumbersTest {
    MainActivity ma = new MainActivity();
    @Test
    public void numberOfPrimesBetween5and5(){
        assertEquals(0, ma.getCount(5,5));
    }
    @Test void isOneAPrimeNumber(){
        assertEquals(0, ma.getCount(0,2));
    }
    @Test
    public void numberOfPrimesBetween1and29(){
        assertEquals(9, ma.getCount(1,29));
    }
    @Test
    public void numberOfPrimesBetween0and29(){
        assertEquals(9, ma.getCount(0,29));
    }
    @Test
    public void numberOfPrimesBetween29and0(){
        assertEquals(9, ma.getCount(29,0));
    }

}
