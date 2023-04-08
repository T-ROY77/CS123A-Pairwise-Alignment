import java.util.HashMap;

public class Matrix {

    String scores;
    char[] acids;
    String name;
    public HashMap<String, Integer> matrixMap;


    public Matrix(String n, char[] c, String v) throws Exception {
        acids = c;
        scores = v;
        name = n;
        createMap();
    }

    public void createMap() throws Exception {


        if(acids.length == 0 || scores.length() == 0){
            throw new Exception("values not initialized");
        }
        //store matrix values in array of ints
        String[] v = scores.trim().split("\\s+");
        int[] scoresArray = new int[v.length];
        for (int i = 0; i < v.length; i++) {
            scoresArray[i] = Integer.parseInt(v[i]);
        }

        //initialize matrixMap
        matrixMap = new HashMap<>();

        //builds the matrix based on the chars and values
        //builds the matrix left to right; top to bottom
        int scoreIndex = 0;
        for(int i = 0; i < acids.length; i++){
            for(int j = 0; j < acids.length; j++){
                String s = "" + acids[i] + acids[j];
                matrixMap.put(s, new Integer(scoresArray[scoreIndex]));
                scoreIndex++;
            }
        }
    }
    public String toString(){
        return name;
    }


}
