import java.util.HashMap;

//@Author Troy Perez
//
//@class Matrix
//
//represents a single matrix used to score a protein pairwise alignment
//
public class Matrix {

    //class variables
    //
    int[] scores;
    char[] acids;
    String name;
    public HashMap<String, Integer> matrixMap;

    //@constructor
    public Matrix(String n, char[] c, int[] v) throws Exception {
        acids = c;
        scores = v;
        name = n;
        createMap();
    }

    //@method createMap
    //
    //builds the map that represents the matrix
    public void createMap() throws Exception {

        //error check
        if(acids.length == 0 || scores.length == 0){
            throw new Exception("values not initialized");
        }
        //initialize matrixMap
        matrixMap = new HashMap<>();

        //builds the matrix based on the acids and scores
        //builds the matrix left to right; top to bottom
        int scoreIndex = 0;
        for(int i = 0; i < acids.length; i++){
            for(int j = 0; j < acids.length; j++){
                String s = "" + acids[i] + acids[j];
                matrixMap.put(s, new Integer(scores[scoreIndex]));
                scoreIndex++;
            }
        }
    }


    //@method printMatrixMap
    //
    //prints the values of each protein pairing
    public void printMatrixMap() {
        System.out.println(name);
        for (String variableName : matrixMap.keySet()) {
            String variableKey = variableName;
            Integer variableValue = matrixMap.get(variableName);
            System.out.print(" " + variableKey + " = " + variableValue);
            System.out.print(", ");
        }
        System.out.println();
    }


    public String toString(){
        return name;
    }
}
