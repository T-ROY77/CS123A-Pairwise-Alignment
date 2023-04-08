import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MatrixGrid extends Grid{
    public ArrayList<Matrix> matrices;
    public int currentMatrix;
    public Map<String, Integer> matrix;

    public Matrix BLOSUM62;

    char[] BLOSUM62Chars = new char[]{'a', 'r', 'n', 'd', 'c', 'q', 'e', 'g', 'h', 'i', 'l', 'k', 'm', 'f', 'p', 's', 't', 'w', 'y', 'v', 'b', 'z', 'x'};
    String BLOSUM62Values = "4 -1 -2 -2  0 -1 -1  0 -2 -1 -1 -1 -1 -2 -1  1  0 -3 -2  0 -2 -1  0 -1  5  0 -2 -3  1  0 -2  0 -3 -2  2 -1 -3 -2 -1 -1 -3 -2 -3 -1  0 -1 -2  0  6  1 -3  0  0  0  1 -3 -3  0 -2 -3 -2  1  0 -4 -2 -3  3  0 -1 -2 -2  1  6 -3  0  2 -1 -1 -3 -4 -1 -3 -3 -1  0 -1 -4 -3 -3  4  1 -1 0 -3 -3 -3  9 -3 -4 -3 -3 -1 -1 -3 -1 -2 -3 -1 -1 -2 -2 -1 -3 -3 -2 -1  1  0  0 -3  5  2 -2  0 -3 -2  1  0 -3 -1  0 -1 -2 -1 -2  0  3 -1 -1  0  0  2 -4  2  5 -2  0 -3 -3  1 -2 -3 -1  0 -1 -3 -2 -2  1  4 -1 0 -2  0 -1 -3 -2 -2  6 -2 -4 -4 -2 -3 -3 -2  0 -2 -2 -3 -3 -1 -2 -1  -2  0  1 -1 -3  0  0 -2  8 -3 -3 -1 -2 -1 -2 -1 -2 -2  2 -3  0  0 -1 -1 -3 -3 -3 -1 -3 -3 -4 -3  4  2 -3  1  0 -3 -2 -1 -3 -1  3 -3 -3 -1 -1 -2 -3 -4 -1 -2 -3 -4 -3  2  4 -2  2  0 -3 -2 -1 -2 -1  1 -4 -3 -1 -1  2  0 -1 -3  1  1 -2 -1 -3 -2  5 -1 -3 -1  0 -1 -3 -2 -2  0  1 -1 -1 -1 -2 -3 -1  0 -2 -3 -2  1  2 -1  5  0 -2 -1 -1 -1 -1  1 -3 -1 -1 -2 -3 -3 -3 -2 -3 -3 -3 -1  0  0 -3  0  6 -4 -2 -2  1  3 -1 -3 -3 -1 -1 -2 -2 -1 -3 -1 -1 -2 -2 -3 -3 -1 -2 -4  7 -1 -1 -4 -3 -2 -2 -1 -2 1 -1  1  0 -1  0  0  0 -1 -2 -2  0 -1 -2 -1  4  1 -3 -2 -2  0  0  0 0 -1  0 -1 -1 -1 -1 -2 -2 -1 -1 -1 -1 -2 -1  1  5 -2 -2  0 -1 -1  0 -3 -3 -4 -4 -2 -2 -3 -2 -2 -3 -2 -3 -1  1 -4 -3 -2 11  2 -3 -4 -3 -2 -2 -2 -2 -3 -2 -1 -2 -3  2 -1 -1 -2 -1  3 -3 -2 -2  2  7 -1 -3 -2 -1 0 -3 -3 -3 -1 -2 -2 -3 -3  3  1 -2  1 -1 -2 -2  0 -3 -1  4 -3 -2 -1 -2 -1  3  4 -3  0  1 -1  0 -3 -4  0 -3 -3 -2  0 -1 -4 -3 -3  4  1 -1 -1  0  0  1 -3  3  4 -2  0 -3 -3  1 -1 -3 -1  0 -1 -3 -2 -2  1  4 -1 0 -1 -1 -1 -2 -1 -1 -1 -1 -1 -1 -1 -1 -1 -2  0  0 -2 -1 -1 -1 -1 -1";

    public MatrixGrid(char[] seq1, char[] seq2) throws Exception {

        super(seq1, seq2);
        BLOSUM62 = new Matrix("BLOSUM62", BLOSUM62Chars, BLOSUM62Values);
        matrices = new ArrayList<>();
        matrices.add(BLOSUM62);
        currentMatrix = 0;
    }

    //empty constructor
    public MatrixGrid(){};

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
                throw new Exception("Key not found in Matrix");
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
    //
    //sets up matrix
    public void setUpNewMatrix() throws Exception {

        char[] chars = BLOSUM62Chars;
        String values = BLOSUM62Values;

        //store matrix values in array of ints
        String[] v = values.trim().split("\\s+");
        int[] scores = new int[v.length];
        for (int i = 0; i < v.length; i++) {
            scores[i] = Integer.parseInt(v[i]);
        }

        //initialize matrix
        matrix = new HashMap<String, Integer>();

        //builds the matrix based on the chars and values
        //builds the matrix left to right; top to bottom
        int scoreIndex = 0;
        for(int i = 0; i < chars.length; i++){
            for(int j = 0; j < chars.length; j++){
                String s = "" + chars[i] + chars[j];
                matrix.put(s, new Integer(scores[scoreIndex]));
                scoreIndex++;
            }
        }

        //print matrix for error checking
//        for (String variableName : BLOSUM.keySet())
//        {
//            String variableKey = variableName;
//            Integer variableValue = BLOSUM.get(variableName);
//            System.out.println(" " + variableKey + " = " + variableValue);
//        }
    }
}
