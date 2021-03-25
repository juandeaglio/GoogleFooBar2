public class AxialGearConfigurator
{
   public int[] FindAxialFirstGear(int[] pegPositions)
   {
       int[] result = {-1,-1};
       int[][]systemOfEquations = createSystemOfEquations(pegPositions);

       return result;
   }

    private int[][] createSystemOfEquations(int[] pegPositions)
    {
        int i = 1;
        int[][]equationArray = new int[pegPositions.length-1][];
        if(pegPositions.length > 20)
        {
            throw new IllegalArgumentException("Peg array may not consist more than 20 pegs.");
        }
        while(i < pegPositions.length)
        {
            int[] equation = new int[pegPositions.length];
            if(pegPositions[i-1] < 1 || pegPositions[i] < 1 )
            {
                throw new IllegalArgumentException("Peg must be greater than or equal to 1.");
            }
            if(pegPositions[i-1] > 10000 || pegPositions[i] > 10000)
            {
                throw new IllegalArgumentException("Peg must be less than 10000.");
            }
            if(i==1)
            {
                equation[0] = 2;
                equation[i] = 1;
            }
            else if(i == pegPositions.length-1)
            {
                equation[0] = 1;
                equation[i-1] = 1;
            }
            else
            {
                equation[i - 1] = 1;
                equation[i] = 1;
            }

            equation[pegPositions.length-1] = pegPositions[i]- pegPositions[i-1]; //total
            equationArray[i-1] = equation;
            i++;
        }
        return equationArray;
    }
}
