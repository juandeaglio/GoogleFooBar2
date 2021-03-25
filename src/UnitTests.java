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
    void GivenThreePegsShouldReturnCorrectResult()
    {
        int[] pegPositions = {4,30,50};
        int[] solutionExpected = {12,1};
        AxialGearConfigurator configurator = new AxialGearConfigurator();
        Assertions.assertArrayEquals(solutionExpected, configurator.FindAxialFirstGear(pegPositions));
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

        String expectedMessage = "20 peg";
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
}
