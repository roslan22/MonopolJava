package com.monopoly.logic.model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class CubesResultTest
{
    public static final int LEGAL_CUBE_RESULT = 3;
    public static final int NEGATIVE_ILLEGAL_RESULT = -1;
    public static final int ZERO_ILLEGAL_RESULT = 0;
    public static final int TOO_HIGH_RESULT = 7;

    private CubesResult fourFiveCubeResult;

    @Before
    public void setUp() throws Exception
    {
        fourFiveCubeResult = new CubesResult(4, 5);
    }

    @Test
    public void testCreateNewCubeResult_shouldNotThrowsException() throws Exception
    {
        for (int i = 1; i <= 6; i++)
        {
            for (int j = 1; j <= 6; j++)
            {
                new CubesResult(i,j);
            }
        }
    }

    @Test
    public void testCreateNewCubeResult_shouldThrowsIllegalArgumentException() throws Exception
    {
        int[] availableIllegalCubeResults = {NEGATIVE_ILLEGAL_RESULT, ZERO_ILLEGAL_RESULT, TOO_HIGH_RESULT};
        for (int availableIllegalCubeResult : availableIllegalCubeResults)
        {
            testIllegalCubeResult(availableIllegalCubeResult, LEGAL_CUBE_RESULT);
            testIllegalCubeResult(LEGAL_CUBE_RESULT, availableIllegalCubeResult);
            for (int secondAvailableIllegalCubeResult : availableIllegalCubeResults)
            {
                testIllegalCubeResult(secondAvailableIllegalCubeResult, availableIllegalCubeResult);
            }
        }
    }

    private void testIllegalCubeResult(int firstCubeResult, int secondCubeResult)
    {
        try
        {
            new CubesResult(firstCubeResult, secondCubeResult);
            fail("Illegal cube result was expected");
        }
        catch (IllegalArgumentException e) {}
    }

    @Test
    public void testGetFirstCubeResult() throws Exception
    {
        assertEquals("getFirstCuberResult didn't returned the right result", fourFiveCubeResult.getFirstCubeResult(), 4);
    }


    @Test
    public void testGetSecondCuberResult() throws Exception
    {
        assertEquals("getSecondCuberResult didn't returned the right result", fourFiveCubeResult.getSecondCuberResult(), 5);
    }


    @Test
    public void testGetResult() throws Exception
    {
        assertEquals("testGetResult didn't returned the right result", fourFiveCubeResult.getResult(), 9);
    }
}