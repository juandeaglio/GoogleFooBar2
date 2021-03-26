import java.util.Arrays;
import java.util.LinkedHashSet;

public class Solution
{
    public static int[] solution(int[] pegs)
    {
        // Your code here
        int[] newPegs = pegs;
        return FindAxialFirstGear(newPegs);
    }
    public static int[] FindAxialFirstGear(int[] pegPositions)
    {
        int[] result = {-1,-1};
        double[][] systemOfEquations;
        //try
        //{
            systemOfEquations = createSystemOfEquations(pegPositions);
       //}
        //catch (IllegalArgumentException e)
        //{
            //return result;
        //}
        systemOfEquations = convertToReducedRowEchelonForm(systemOfEquations);
        for(int i = 0 ; i < systemOfEquations.length; i++)
        {
            if (systemOfEquations[i][systemOfEquations[0].length-1] < 1)
                return result;
        }
        double numerator = systemOfEquations[0][systemOfEquations[0].length-1]*2;
        double denominator = 1;
        int[]fraction = solveIntoFraction(numerator, denominator);
        result[0] = fraction[0];
        result[1] = fraction[1];
        return result;
    }
    public static int[] solveIntoFraction(double numerator, double denominator)
    {
        int result[] = {0,0};
        double x1 = numerator;
        double x2 = numerator*10;
        x2=x2-x1;
        int x3 =(int)x2;
        int commonFactor = gcd(x3,9);

        result[0] = (int)x3/commonFactor;
        result[1] = (int)9/commonFactor;
        return result;
    }
    public static int gcd(int first, int second)
    {
        int gcd = -1;
        for(int i = 1; i <= first && i <= second; i++)
        {
            if(first%i==0 && second%i==0)
                gcd = i;
        }
        return gcd;
    }

    public static double[][] convertToReducedRowEchelonForm(double[][] systemOfEquations)
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

    private static void SetNumbersLeftOfLeadingOneToZero(double[][] systemOfEquations, int[] pivot)
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
            double complement = (-1*systemOfEquations[abovePivot[0]][abovePivot[1]]) / systemOfEquations[pivot[0]][pivot[1]];
            systemOfEquations[abovePivot[0]] = AddToAnotherRowMultipliedByOriginalPivot(systemOfEquations, pivot, abovePivot, complement);
        }
    }
    private static void SetZeroBelowPivot(double[][] systemOfEquations, int[] pivot)
    {
        for(int k = pivot[0]; k < systemOfEquations.length; k++)
        {
            if(k == pivot[0])
                continue;
            int[] belowPivot = {k, pivot[1]};
            double num1 = systemOfEquations[belowPivot[0]][belowPivot[1]];
            double num2 = systemOfEquations[pivot[0]][pivot[1]];
            double complement = (-1*num1) / num2;
            systemOfEquations[belowPivot[0]] = AddToAnotherRowMultipliedByOriginalPivot(systemOfEquations, pivot, belowPivot, complement);
        }
    }

    private static void Swap(double[][] systemOfEquations, int[] pivot, int[] current)
    {
        double[] temp = systemOfEquations[current[0]];
        systemOfEquations[current[0]] = systemOfEquations[pivot[0]];
        systemOfEquations[pivot[0]] = temp;
        int t = current[0];
        current[0] = pivot[0];
        pivot[0] = t;

    }

    private static void FindPivot(double[][] systemOfEquations, int[] pivot)
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

    private static boolean IsColumnZeroes(double[][] systemOfEquations, int[] pivot)
    {
        for(int k = 0; k < systemOfEquations.length; k++)
        {
            if( systemOfEquations[k][pivot[1]] != 0)
                return false;
        }
        return true;

    }
    private static boolean IsRowZeroes(double[][] systemOfEquations, int[] pivot)
    {
        for(int k = 0; k < systemOfEquations.length; k++)
        {
            if( systemOfEquations[k][pivot[1]] != 0)
                return false;
        }
        return true;

    }


    private static double[] AddToAnotherRowMultipliedByOriginalPivot(double[][] systemOfEquation, int[] pivot, int[] pivotToChange, double complement)
    {
        double[] addingAndMultiplyingFrom = systemOfEquation[pivot[0]];
        double[] addedAndMultipliedTo = systemOfEquation[pivotToChange[0]];
        for(int l = 0; l < systemOfEquation[0].length; l++)
            addedAndMultipliedTo[l] += addingAndMultiplyingFrom[l]*complement;
        return addedAndMultipliedTo;
    }

    private static void ScaleRow(double[][] systemOfEquations, int[] pivot)
    {
        double divider = systemOfEquations[pivot[0]][pivot[1]];
        for(int k = 0; k < systemOfEquations[0].length; k++)
        {
            systemOfEquations[pivot[0]][k] /= divider;
        }
    }

    public static double[][] createSystemOfEquations(int[] pegPositions)
    {

        if(HasDuplicates(pegPositions))
            pegPositions = RemoveDuplicates(pegPositions);
        if(pegPositions.length > 20)
        {
           // throw new IllegalArgumentException("Peg array may not consist more than 20 pegs.");
        }
        //if(pegPositions.length <= 1)
            //throw new IllegalArgumentException("Peg array must have more than 1 peg.");
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
                //throw new IllegalArgumentException("Peg must be greater than or equal to 1.");
            }
            if(pegPositions[i-1] > 10000 || pegPositions[i] > 10000)
            {
               // throw new IllegalArgumentException("Peg must be less than 10000.");
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
               // throw new IllegalArgumentException("Distances between pegs must greater than or equal to two, since cog radii must be at least 1.");
            }
            equationArray[i-1] = equation;
            i++;
        }
        return equationArray;
    }

    private static boolean HasDuplicates(int[] pegPositions)
    {
        int[] withoutDuplicates;
        LinkedHashSet<Integer> linkedHashSet = new LinkedHashSet<>( Arrays.asList(Arrays.stream(pegPositions).boxed().toArray(Integer[]::new)));
        Integer[] numbersWithoutDuplicates = linkedHashSet.toArray(new Integer[] {});
        withoutDuplicates = new int[numbersWithoutDuplicates.length];
        return withoutDuplicates.length != pegPositions.length;
    }
    private static int[] RemoveDuplicates(int[] pegPositions)
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