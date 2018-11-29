package com.yoie.com.clockapp.test;

import com.yoie.com.clockapp.Caculator;

import org.junit.Assert;
import org.junit.Test;

public class CaculatorTest {
    @Test
    public void testAdd(){
        Caculator caculator = new Caculator();
        Assert.assertEquals(3, caculator.add(2,1));

    }
}
