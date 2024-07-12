package com.coding;

import org.junit.Assert;
import org.junit.Test;

public class LetterTest {
    @Test
    public void testReplaceChars(){
        String testString = "aabcccbbadzzz";
        String result = Letter.replaceChars(testString);
        Assert.assertEquals(result,"d");
    }

    @Test
    public void testReplaceChars2(){
        String testString = "abcccbad";
        String result = Letter.replaceChars2(testString);
        Assert.assertEquals(result,"d");
    }
}
