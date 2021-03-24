public class Solution
{
    public static int[] solution(int [] pegs)
    {
        int[] pegPositions = {1,2};
        AxialGearConfigurator gearConfigurator = new AxialGearConfigurator();
        return gearConfigurator.FindAxialFirstGear(pegPositions);
    }
}
