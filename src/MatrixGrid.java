import java.util.ArrayList;

//@Author Troy Perez
//
//
//@class Grid
//
//represents the grid used in the Needleman Wunsch algorithm to calculate pairwise alignment
//uses a 2d array of Cells to represent grid
//implements matrices for protein alignment


public class MatrixGrid extends Grid{

    //class variables
    //
    public ArrayList<Matrix> matrices;
    public int currentMatrix;

    //values for BLOSUM62 matrix
    public final char[] BLOSUM62Chars = new char[]{'a', 'r', 'n', 'd', 'c', 'q', 'e', 'g', 'h', 'i', 'l', 'k', 'm', 'f', 'p', 's', 't', 'w', 'y', 'v', 'b', 'z', 'x'};
    public final String BLOSUM62Values = "4 -1 -2 -2  0 -1 -1  0 -2 -1 -1 -1 -1 -2 -1  1  0 -3 -2  0 -2 -1  0 -1  5  0 -2 -3  1  0 -2  0 -3 -2  2 -1 -3 -2 -1 -1 -3 -2 -3 -1  0 -1 -2  0  6  1 -3  0  0  0  1 -3 -3  0 -2 -3 -2  1  0 -4 -2 -3  3  0 -1 -2 -2  1  6 -3  0  2 -1 -1 -3 -4 -1 -3 -3 -1  0 -1 -4 -3 -3  4  1 -1 0 -3 -3 -3  9 -3 -4 -3 -3 -1 -1 -3 -1 -2 -3 -1 -1 -2 -2 -1 -3 -3 -2 -1  1  0  0 -3  5  2 -2  0 -3 -2  1  0 -3 -1  0 -1 -2 -1 -2  0  3 -1 -1  0  0  2 -4  2  5 -2  0 -3 -3  1 -2 -3 -1  0 -1 -3 -2 -2  1  4 -1 0 -2  0 -1 -3 -2 -2  6 -2 -4 -4 -2 -3 -3 -2  0 -2 -2 -3 -3 -1 -2 -1  -2  0  1 -1 -3  0  0 -2  8 -3 -3 -1 -2 -1 -2 -1 -2 -2  2 -3  0  0 -1 -1 -3 -3 -3 -1 -3 -3 -4 -3  4  2 -3  1  0 -3 -2 -1 -3 -1  3 -3 -3 -1 -1 -2 -3 -4 -1 -2 -3 -4 -3  2  4 -2  2  0 -3 -2 -1 -2 -1  1 -4 -3 -1 -1  2  0 -1 -3  1  1 -2 -1 -3 -2  5 -1 -3 -1  0 -1 -3 -2 -2  0  1 -1 -1 -1 -2 -3 -1  0 -2 -3 -2  1  2 -1  5  0 -2 -1 -1 -1 -1  1 -3 -1 -1 -2 -3 -3 -3 -2 -3 -3 -3 -1  0  0 -3  0  6 -4 -2 -2  1  3 -1 -3 -3 -1 -1 -2 -2 -1 -3 -1 -1 -2 -2 -3 -3 -1 -2 -4  7 -1 -1 -4 -3 -2 -2 -1 -2 1 -1  1  0 -1  0  0  0 -1 -2 -2  0 -1 -2 -1  4  1 -3 -2 -2  0  0  0 0 -1  0 -1 -1 -1 -1 -2 -2 -1 -1 -1 -1 -2 -1  1  5 -2 -2  0 -1 -1  0 -3 -3 -4 -4 -2 -2 -3 -2 -2 -3 -2 -3 -1  1 -4 -3 -2 11  2 -3 -4 -3 -2 -2 -2 -2 -3 -2 -1 -2 -3  2 -1 -1 -2 -1  3 -3 -2 -2  2  7 -1 -3 -2 -1 0 -3 -3 -3 -1 -2 -2 -3 -3  3  1 -2  1 -1 -2 -2  0 -3 -1  4 -3 -2 -1 -2 -1  3  4 -3  0  1 -1  0 -3 -4  0 -3 -3 -2  0 -1 -4 -3 -3  4  1 -1 -1  0  0  1 -3  3  4 -2  0 -3 -3  1 -1 -3 -1  0 -1 -3 -2 -2  1  4 -1 0 -1 -1 -1 -2 -1 -1 -1 -1 -1 -1 -1 -1 -1 -2  0  0 -2 -1 -1 -1 -1 -1";

    public MatrixGrid() throws Exception {

        super();
        matrices = new ArrayList<>();

        //store BLOSUM62 matrix values in array of ints
        String[] v = BLOSUM62Values.trim().split("\\s+");
        int[] scoresArray = new int[v.length];
        for (int i = 0; i < v.length; i++) {
            scoresArray[i] = Integer.parseInt(v[i]);
        }
        //set up BLOSUM62 matrix
        setUpNewMatrix("BLOSUM62", BLOSUM62Chars, scoresArray);
    }


    //@method getMatrixScore
    //@param match
    //@return the score from the matrix map
    //
    //gets the score of the 2 characters from the matrix
    public int getMatrixScore(String match) throws Exception{
        //error check
        //checks if the string is backwards
        if(!matrices.get(currentMatrix).matrixMap.containsKey(match)){
            match = "" + match.charAt(1) + match.charAt(0);
            if(!matrices.get(currentMatrix).matrixMap.containsKey(match)){
                System.out.println("Key not found in Matrix");
                return Integer.MIN_VALUE;
                //throw new Exception("Key not found in Matrix");
            }
        }
        return matrices.get(currentMatrix).matrixMap.get(match);
    }

    //@override method getMatchScore
    //@param match
    //@return the score
    //
    //if currently using matrix, gets matrix score
    //otherwise calls super method
    @Override
    public int getMatchScore(String match) throws Exception {
        if(this.useMatrix){
            return getMatrixScore(match);
        }
        else{
            return super.getMatchScore(match);
        }
    }

    //@method setUpNewMatrix
    //@param matrix name
    //@param amino acids
    //@param scores
    //
    //sets up a new matrix
    //add new matrix to matrices array
    public void setUpNewMatrix(String name, char[] c, int[] scores) throws Exception {

        Matrix m = new Matrix(name, c, scores);
        matrices.add(m);
    }

    public void printMatrices(){
        matrices.forEach(matrix->matrix.printMatrixMap());
    }

    public void setCurrentMatrix(int matrix){
        currentMatrix = matrix;
    }
}
