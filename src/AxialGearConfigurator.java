public class AxialGearConfigurator
{
    public static int[] solution(int [] pegs)
    {
        AxialGearConfigurator gearConfigurator = new AxialGearConfigurator();
        return gearConfigurator.FindAxialFirstGear(pegs);
    }
   public int[] FindAxialFirstGear(int[] pegPositions)
   {
       int[] result = {-1,-1};
       int[][]systemOfEquations = createSystemOfEquations(pegPositions);
       systemOfEquations = convertToReducedRowEchelonForm(systemOfEquations);
       return result;
   }

    private int[][] convertToReducedRowEchelonForm(int[][] systemOfEquations)
    {
        int[] pivot = new int[2];
        int current = 0;
        for(int i = 0; i < systemOfEquations[0].length; i++)
        {
            pivot[1] = i;
            //Step 1
            for(int j = i; j < systemOfEquations[0].length; j++)
            {
                if(IsColumnZeroes(systemOfEquations, pivot))
                    break;
                else
                    pivot[1] = j;
            }
            //Step 2
            int[] newPivot = {pivot[0], pivot[1]};
            int initialRow = pivot[0];
            pivot = FindPivot(systemOfEquations, pivot, newPivot, systemOfEquations.length - initialRow);

            if(systemOfEquations[pivot[0]][pivot[1]] == 0)
            {
                pivot[0]++;
                continue;
            }

            if(pivot[0] != current)
            {
                current = SwapToTop(systemOfEquations, pivot, current);
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
            if(pivot[0]+1 >= systemOfEquations.length)
            {
                break;
            }
            boolean skip = false;
            for(int k = 0; k < systemOfEquations[0].length; k++)
            {
                if(systemOfEquations[pivot[0]+1][k] != 0)
                {
                    skip = true;
                    break;
                }
            }
            if(skip)
                break;
            current++;
            pivot[0]++;
        }
        return systemOfEquations;
    }

    private void SetNumbersLeftOfLeadingOneToZero(int[][] systemOfEquations, int[] pivot) {
        for(int k = pivot[0]; k >= 0; k--)
        {
            if(k == pivot[0])
            {
                if (systemOfEquations[pivot[0]][pivot[1]] != 1)
                    ScaleRow(systemOfEquations, pivot);
                continue;
            }

            int[] abovePivot = {k, pivot[1]};
            int complement = -systemOfEquations[abovePivot[0]][abovePivot[1]] / systemOfEquations[pivot[0]][pivot[1]];
            AddToAnotherRowMultipliedByOriginalPivot(systemOfEquations[0], pivot, abovePivot, complement);
        }
    }

    private int SwapToTop(int[][] systemOfEquations, int[] pivot, int current) {
        int[] temp = systemOfEquations[current];
        systemOfEquations[current] = systemOfEquations[pivot[0]];
        systemOfEquations[pivot[0]] = temp;
        int t = current;
        current = pivot[0];
        pivot[0] = t;
        return current;
    }

    private int[] FindPivot(int[][] systemOfEquations, int[] pivot, int[] newPivot, int i1) {
        for(int k = pivot[0]; k < i1; k++)
        {
            pivot[0] = k;
            if(systemOfEquations[pivot[0]][pivot[1]] != 0)
            {
                newPivot[0] = k;
                break;
            }
        }
        pivot = newPivot;
        return pivot;
    }

    private boolean IsColumnZeroes(int[][] systemOfEquations, int[] pivot)
    {
        for(int k = 0; k < systemOfEquations.length; k++)
        {
            if( systemOfEquations[k][pivot[1]] != 0)
                return false;
        }
        return true;

    }

    private void SetZeroBelowPivot(int[][] systemOfEquations, int[] pivot) {
        for(int k = pivot[0]; k < systemOfEquations.length; k++)
        {
            if(k == pivot[0])
                continue;
            int[] belowPivot = {k, pivot[1]};
            int complement = -systemOfEquations[belowPivot[0]][belowPivot[1]] / systemOfEquations[pivot[0]][pivot[1]];
            AddToAnotherRowMultipliedByOriginalPivot(systemOfEquations[0], pivot, belowPivot, complement);
        }
    }

    private void AddToAnotherRowMultipliedByOriginalPivot(int[] systemOfEquation, int[] pivot, int[] belowPivot, int complement)
    {
        for(int l = 0; l < systemOfEquation.length; l++)
            belowPivot[l] += pivot[l]*complement;
    }

    private void ScaleRow(int[][] systemOfEquations, int[] pivot)
    {
        int divider = systemOfEquations[pivot[0]][pivot[1]];
        for(int k = 0; k < systemOfEquations[0].length; k++)
        {
            int[] temp = systemOfEquations[pivot[0]];
            for(int l = 0; l < temp.length; l++)
            {
               temp[l] /= divider;
            }
        }
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
