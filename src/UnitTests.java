import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

public class UnitTests
{
    @Test
    void GivenTwoPegsTooCloseTogetherShouldReturnFirstGear()
    {
        int[] pegPositions = {1,2};
        int[] solutionExpected = {-1,-1};
        AxialGearConfigurator configurator = new AxialGearConfigurator();
        Assertions.assertArrayEquals(configurator.FindAxialFirstGear(pegPositions),solutionExpected);
    }
    @Test
    void GivenABunchOf0PegsShouldFail()
    {
        int[] pegPositions = {0,0,0,0};
        AxialGearConfigurator configurator = new AxialGearConfigurator();

        Exception exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            configurator.FindAxialFirstGear(pegPositions);
        });

        String expectedMessage = "than 1";
        String actualMessage = exception.getMessage();

        Assertions.assertTrue(actualMessage.contains(expectedMessage));
    }
    @Test
    void GivenANewArrayof0PegsShouldFail()
    {
        int[] pegPositions = new int[5];
        AxialGearConfigurator configurator = new AxialGearConfigurator();

        Exception exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            configurator.FindAxialFirstGear(pegPositions);
        });

        String expectedMessage = "than 1";
        String actualMessage = exception.getMessage();

        Assertions.assertTrue(actualMessage.contains(expectedMessage));
    }
    @Test
    void GivenVeryFarPegsShouldFail()
    {
        int[] pegPositions = new int[5];
        for(int i = 0; i < pegPositions.length; i++)
        {
            pegPositions[i] = Integer.MAX_VALUE-(pegPositions.length-i);
        }
        AxialGearConfigurator configurator = new AxialGearConfigurator();

        Exception exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            configurator.FindAxialFirstGear(pegPositions);
        });

        String expectedMessage = "less than 10000";
        String actualMessage = exception.getMessage();

        Assertions.assertTrue(actualMessage.contains(expectedMessage));
    }
    @Test
    void GivenThreePegsShouldReturnCorrectResult()
    {
        int[] pegPositions = {4,30,50};
        int[] solutionExpected = {12,1};
        AxialGearConfigurator configurator = new AxialGearConfigurator();
        Assertions.assertArrayEquals(solutionExpected, configurator.FindAxialFirstGear(pegPositions));
    }
    @Test
    void GivenThreeShortestPegsShouldReturnCorrectResult()
    {
        int[] pegPositions = {1,4,6};
        int[] solutionExpected = {2,1};
        AxialGearConfigurator configurator = new AxialGearConfigurator();
        Assertions.assertArrayEquals(solutionExpected, configurator.FindAxialFirstGear(pegPositions));
    }
    @Test
    void GivenThreeLargestPegsShouldReturnCorrectResult()
    {
        int[] pegPositions = {1,4996, 9989};
        int[] solutionExpected = {4,1};
        AxialGearConfigurator configurator = new AxialGearConfigurator();
        int[] result = configurator.FindAxialFirstGear(pegPositions);
        double finalRatio = (double)result[0]/(double) result[1];
        Assertions.assertEquals(solutionExpected[0], (int)Math.round(finalRatio));
    }
    @Test
    void GivenFourPegsShouldReturnCorrectResult()
    {
        int[] pegPositions = {2,16,35,55};
        int[] solutionExpected = {10,1};
        AxialGearConfigurator configurator = new AxialGearConfigurator();
        Assertions.assertArrayEquals(solutionExpected, configurator.FindAxialFirstGear(pegPositions));
    }
    @Test
    void GivenFourPegsShouldReturnCorrectResultWithDenominator()
    {
        int[] pegPositions = {1 ,2988, 5964 ,8939};
        double solutionExpected = 1990 + (double)2/(double)3;
        AxialGearConfigurator configurator = new AxialGearConfigurator();
        int[] result = configurator.FindAxialFirstGear(pegPositions);
        double finalRatio = (double)result[0]/(double) result[1];
        Assertions.assertEquals(solutionExpected, finalRatio);
    }
    @Test
    void GivenAnotherFourPegsShouldReturnCorrectResultWithDenominator()
    {
        int[] pegPositions = {1, 2324, 4678, 7432};
        double solutionExpected = 1815 + (double)1/(double)3;
        AxialGearConfigurator configurator = new AxialGearConfigurator();
        int[] result = configurator.FindAxialFirstGear(pegPositions);
        double finalRatio = (double)result[0]/(double) result[1];
        Assertions.assertEquals(solutionExpected, finalRatio);
    }

    @Test
    void GivenPegsTooCloseTogetherShouldReturnInvalidResult()
    {
        int[] pegPositions = {1,2,3,4,5};
        int[] solutionExpected = {-1,-1};
        AxialGearConfigurator configurator = new AxialGearConfigurator();
        Assertions.assertArrayEquals(solutionExpected, configurator.FindAxialFirstGear(pegPositions));
    }
    @Test
    void GivenMoreThan20PegsShouldThrowException()
    {
        int[] pegPositions = new int[21];
        Arrays.fill(pegPositions,2);
        AxialGearConfigurator configurator = new AxialGearConfigurator();

           Exception exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
                configurator.FindAxialFirstGear(pegPositions);
                    });

        String expectedMessage = "than 1";
        String actualMessage = exception.getMessage();

        Assertions.assertTrue(actualMessage.contains(expectedMessage));
    }
    @Test
    void GivenPegGreaterThan10000ShouldThrowException()
    {
        int[] pegPositions = {4,10001};
        AxialGearConfigurator configurator = new AxialGearConfigurator();

        Exception exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            configurator.FindAxialFirstGear(pegPositions);
        });

        String expectedMessage = "less than 10000";
        String actualMessage = exception.getMessage();

        Assertions.assertTrue(actualMessage.contains(expectedMessage));
    }
    @Test
    void GivenPegLessThan1ShouldThrowException()
    {
        int[] pegPositions = {0,10000};
        AxialGearConfigurator configurator = new AxialGearConfigurator();

        Exception exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            configurator.FindAxialFirstGear(pegPositions);
        });

        String expectedMessage = "greater than or equal to 1";
        String actualMessage = exception.getMessage();

        Assertions.assertTrue(actualMessage.contains(expectedMessage));
    }
    @Test
    void GivenPegsShouldProduceRREF()
    {
        int[] pegPositions = {4,12,25,48};
        AxialGearConfigurator configurator = new AxialGearConfigurator();
        double[][] expectedResult = {{1,0,0,6},{0,1,0,-4},{0,0,1,17}};
        double[][] result = configurator.convertToReducedRowEchelonForm(configurator.createSystemOfEquations(pegPositions));
        Assertions.assertArrayEquals(expectedResult,result);
    }
    @Test
    void GivenPegsThenSolvingForRadiusShouldProvideTotalLength()
    {

        AxialGearConfigurator configurator = new AxialGearConfigurator();
        int[] pegPositions = {1 ,2988, 5964 ,8939};
        double solutionExpected = 1990 + (double)(2)/(double) (3);
        int totalLength = pegPositions[pegPositions.length-1]-pegPositions[0];
        double[][] result = configurator.convertToReducedRowEchelonForm(configurator.createSystemOfEquations(pegPositions));
        float sum = 0;
        for(int i = 0; i < result.length; i++)
        {
            if(i == 0)
            {
                sum += result[i][result[0].length-1]*2;
                sum += result[i][result[0].length-1];
            }
            else
                sum += result[i][result[0].length-1]*2;

        }
        Assertions.assertEquals(totalLength,sum);
        Assertions.assertEquals(solutionExpected,(double)(configurator.FindAxialFirstGear(pegPositions)[0])/ (double)(configurator.FindAxialFirstGear(pegPositions)[1]));
    }
}
