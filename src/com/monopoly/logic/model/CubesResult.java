package com.monopoly.logic.model;

public class CubesResult
{
    private int firstCubeResult;
    private int secondCuberResult;

    public CubesResult(int firstCubeResult, int secondCuberResult)
    {
        validateCube(firstCubeResult);
        validateCube(secondCuberResult);

        this.firstCubeResult = firstCubeResult;
        this.secondCuberResult = secondCuberResult;
    }

    private void validateCube(int cubeResult)
    {
        if (cubeResult < 1 || cubeResult > 6)
        {
            throw new IllegalArgumentException("Cube result must be between 1 and 6");
        }
    }

    public int getFirstCubeResult() 
    {
        return firstCubeResult;
    }

    public int getSecondCuberResult() 
    {
        return secondCuberResult;
    }
    
    public int getResult()
    {
        return firstCubeResult + secondCuberResult;
    }
}
