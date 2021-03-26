import java.util.Arrays;
import java.util.LinkedHashSet;

public class AxialGearConfigurator
{
    public static void main(String [] args)
    {
        //int[] pegs = {2, 1830, 3664, 5498, 7321, 9177, 11065, 12893};
        //int[] pegs = {2000, 7500};
        int[] pegs =  {1, 2324, 4678, 7432};
        System.out.println(Arrays.toString(pegs));
        System.out.println(Solution.solution(pegs)[0] + ", " + Solution.solution(pegs)[1]);
        System.out.println((double)Solution.solution(pegs)[0] / (double)Solution.solution(pegs)[1]);
    }
    public int[] FindAxialFirstGear(int[] pegPositions)
    {
        int[] result = {-1,-1};
        double[][] systemOfEquations;
        try
        {
            systemOfEquations = createSystemOfEquations(pegPositions);
        }
        catch (IllegalArgumentException e)
        {
            if(e.getMessage().contains("radii"))
                return result;
            throw e;
        }
        systemOfEquations = convertToReducedRowEchelonForm(systemOfEquations);
        for(int i = 0 ; i < systemOfEquations.length; i++)
        {
            if (systemOfEquations[i][systemOfEquations[0].length-1] < 1)
                return result;
        }
        double numerator = systemOfEquations[0][systemOfEquations[0].length-1]*2;
        double denominator = 1;
        if(numerator%1!= 0)
        {
            int[] fraction = solveIntoFraction(numerator);
            result[0] = fraction[0];
            result[1] = fraction[1];
        }
        else
        {
            result[0] = (int)numerator;
            result[1] = (int)denominator;
        }
        return result;
    }
    public int[] solveIntoFraction(double numerator)
    {
        int result[] = {0,0};
        double x1 = numerator;
        double x2 = numerator;
        double x3 = x2*10;
        int count = 1;
        while((x3-x1) < 0.000000001)
        {
            x3 *= 10;
            count++;
        }
        x2=Math.round(x3-x1);
        int newNumerator;
        newNumerator =(int)x2;

        int commonFactor = gcd(newNumerator,(int)(Math.pow(10,count))-1);

        result[0] = newNumerator/commonFactor;
        result[1] = ((int)(Math.pow(10,count))-1)/commonFactor;
        return result;
    }
    public int gcd(int first, int second)
    {
        int gcd = -1;
        for(int i = 1; i <= first && i <= second; i++)
        {
            if(first%i==0 && second%i==0)
                gcd = i;
        }
        return gcd;
    }
    public double[][] convertToReducedRowEchelonForm(double[][] systemOfEquations)
    {
        int[] pivot = new int[2];
        int current = 0;
        for(int i = 0; i < systemOfEquations[0].length; i++)
        {
            pivot[1] = i;
            //Step 1
            for(int j = i; j < systemOfEquations[0].length; j++)
            {
                if(!IsColumnZeroes(systemOfEquations, pivot))
                    break;
                else
                    pivot[1] = j;
            }
            //Step 2
            FindPivot(systemOfEquations, pivot);

            if(systemOfEquations[pivot[0]][pivot[1]] == 0)
            {
                pivot[0]++;
                continue;
            }

            if(pivot[0] != current)
            {

                int[] toSwap = {current, pivot[1]};
                Swap(systemOfEquations,toSwap,pivot);
            }

            if(systemOfEquations[pivot[0]][pivot[1]] != 1)
            {
                ScaleRow(systemOfEquations, pivot);
            }
            //Step 3
            SetZeroBelowPivot(systemOfEquations, pivot);
            //Step 5
            SetNumbersLeftOfLeadingOneToZero(systemOfEquations, pivot);
            //Step 4
            int[] rowToSearch = {pivot[0]+1, pivot[1]};
            if(pivot[0]+1 >= systemOfEquations.length || IsRowZeroes(systemOfEquations, rowToSearch))
                break;
            current++;
            pivot[0]++;
        }
        return systemOfEquations;
    }


    private void SetNumbersLeftOfLeadingOneToZero(double[][] systemOfEquations, int[] pivot)
    {
        for(int k = pivot[0]; k >= 0; k--)
        {
            if(k == pivot[0])
            {
                if (systemOfEquations[pivot[0]][pivot[1]] != 1)
                    ScaleRow(systemOfEquations, pivot);
                continue;
            }

            int[] abovePivot = {k, pivot[1]};
            double complement = -systemOfEquations[abovePivot[0]][abovePivot[1]] / systemOfEquations[pivot[0]][pivot[1]];
            systemOfEquations[abovePivot[0]] = AddToAnotherRowMultipliedByOriginalPivot(systemOfEquations, pivot, abovePivot, complement);
        }
    }
    private void SetZeroBelowPivot(double[][] systemOfEquations, int[] pivot)
    {
        for(int k = pivot[0]; k < systemOfEquations.length; k++)
        {
            if(k == pivot[0])
                continue;
            int[] belowPivot = {k, pivot[1]};
            double complement = -systemOfEquations[belowPivot[0]][belowPivot[1]] / systemOfEquations[pivot[0]][pivot[1]];
            systemOfEquations[belowPivot[0]] = AddToAnotherRowMultipliedByOriginalPivot(systemOfEquations, pivot, belowPivot, complement);
        }
    }

    private void Swap(double[][] systemOfEquations, int[] pivot, int[] current)
    {
        double[] temp = systemOfEquations[current[0]];
        systemOfEquations[current[0]] = systemOfEquations[pivot[0]];
        systemOfEquations[pivot[0]] = temp;
        int t = current[0];
        current[0] = pivot[0];
        pivot[0] = t;

    }

    private void FindPivot(double[][] systemOfEquations, int[] pivot)
    {
        int[] newPivot = {pivot[0], pivot[1]};
        int initialRow = pivot[0];
        for(int k = pivot[0]; k < systemOfEquations.length - initialRow; k++)
        {
            newPivot[0] = k;
            if(systemOfEquations[newPivot[0]][newPivot[1]] == 1)
            {
                Swap(systemOfEquations, newPivot, pivot);
            }
        }
        newPivot[0] = pivot[0];
        for(int k = pivot[0]; k < systemOfEquations.length - initialRow; k++)
        {
            newPivot[0] = k;
            if(systemOfEquations[newPivot[0]][newPivot[1]] != 0)
            {
                pivot[0] = k;
                break;
            }
        }
    }

    private boolean IsColumnZeroes(double[][] systemOfEquations, int[] pivot)
    {
        for(int k = 0; k < systemOfEquations.length; k++)
        {
            if( systemOfEquations[k][pivot[1]] != 0)
                return false;
        }
        return true;

    }
    private boolean IsRowZeroes(double[][] systemOfEquations, int[] pivot)
    {
        for(int k = 0; k < systemOfEquations.length; k++)
        {
            if( systemOfEquations[k][pivot[1]] != 0)
                return false;
        }
        return true;

    }


    private double[] AddToAnotherRowMultipliedByOriginalPivot(double[][] systemOfEquation, int[] pivot, int[] pivotToChange, double complement)
    {
        double[] addingAndMultiplyingFrom = systemOfEquation[pivot[0]];
        double[] addedAndMultipliedTo = systemOfEquation[pivotToChange[0]];
        for(int l = 0; l < systemOfEquation[0].length; l++)
            addedAndMultipliedTo[l] += addingAndMultiplyingFrom[l]*complement;
        return addedAndMultipliedTo;
    }

    private void ScaleRow(double[][] systemOfEquations, int[] pivot)
    {
        double divider = systemOfEquations[pivot[0]][pivot[1]];
        for(int k = 0; k < systemOfEquations[0].length; k++)
        {

            systemOfEquations[pivot[0]][k] /= divider;

        }
    }

    public double[][] createSystemOfEquations(int[] pegPositions)
    {

        if(HasDuplicates(pegPositions))
            pegPositions = RemoveDuplicates(pegPositions);
        if(pegPositions.length > 20)
        {
            throw new IllegalArgumentException("Peg array may not consist more than 20 pegs.");
        }
        if(pegPositions.length <= 1)
            throw new IllegalArgumentException("Peg array must have more than 1 peg.");
        int smallest = 0;
        for(int j = 0; j<pegPositions.length; j++)
        {
            if(pegPositions[j]>smallest)
            {
                smallest = pegPositions[j];
            }
            else
                Arrays.sort(pegPositions);
        }
        int i = 1;
        double[][]equationArray = new double[pegPositions.length-1][];
        while(i < pegPositions.length)
        {
            double[] equation = new double[pegPositions.length];
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

            equation[equation.length-1] = pegPositions[i]- pegPositions[i-1]; //total
            if(equation[equation.length-1] < 2)
            {
                throw new IllegalArgumentException("Distances between pegs must greater than or equal to two, since cog radii must be at least 1.");
            }
            equationArray[i-1] = equation;
            i++;
        }
        return equationArray;
    }
    private  boolean HasDuplicates(int[] pegPositions)
    {
        int[] withoutDuplicates;
        LinkedHashSet<Integer> linkedHashSet = new LinkedHashSet<>( Arrays.asList(Arrays.stream(pegPositions).boxed().toArray(Integer[]::new)));
        Integer[] numbersWithoutDuplicates = linkedHashSet.toArray(new Integer[] {});
        withoutDuplicates = new int[numbersWithoutDuplicates.length];
        return withoutDuplicates.length != pegPositions.length;
    }
    private  int[] RemoveDuplicates(int[] pegPositions)
    {
        int[] withoutDuplicates;
        LinkedHashSet<Integer> linkedHashSet = new LinkedHashSet<>( Arrays.asList(Arrays.stream(pegPositions).boxed().toArray(Integer[]::new)));
        Integer[] numbersWithoutDuplicates = linkedHashSet.toArray(new Integer[] {});
        withoutDuplicates = new int[numbersWithoutDuplicates.length];
        for(int i = 0; i < numbersWithoutDuplicates.length; i++)
            withoutDuplicates[i] = numbersWithoutDuplicates[i];
        return withoutDuplicates;
    }
}
