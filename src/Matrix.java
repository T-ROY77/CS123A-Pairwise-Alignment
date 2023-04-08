import java.util.HashMap;

public class Matrix {

    int[] scores;
    char[] acids;
    String name;
    public HashMap<String, Integer> matrixMap;


    public Matrix(String n, char[] c, int[] v) throws Exception {
        acids = c;
        scores = v;
        name = n;
        createMap();
    }

    public void createMap() throws Exception {


        if(acids.length == 0 || scores.length == 0){
            throw new Exception("values not initialized");
        }



        //initialize matrixMap
        matrixMap = new HashMap<>();

        //builds the matrix based on the chars and values
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

    public void printMatrixMap() {
        System.out.println(name);
        for (String variableName : matrixMap.keySet()) {
            String variableKey = variableName;
            Integer variableValue = matrixMap.get(variableName);
            System.out.println(" " + variableKey + " = " + variableValue);
        }
    }


    public String toString(){
        return name;
    }


}
