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
        Assertions.assertArrayEquals(configurator.FindAxialFirstGear(pegPositions),solutionExpected);
    }
}
