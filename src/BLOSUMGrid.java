import java.util.HashMap;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

public class BLOSUMGrid extends Grid{
    public Map<String, Integer> BLOSUM;


    public BLOSUMGrid(char[] seq1, char[] seq2){
        //need to error check that all chars are on matrix

        super(seq1, seq2);
        setUpMatrix();

    }

    public BLOSUMGrid(){};
    public int getMatchScore(String match) throws Exception {
        //get only works when string is in correct order
//        if(BLOSUM.containsKey(match)){
//            return BLOSUM.get(match);
//        }
//        else{
//            throw new Exception("Key not found in Matrix");
//        }
        //if(!BLOSUM.containsKey(match))
        int score = BLOSUM.get(match);
        System.out.println(score);
        return score;
    }

    public void setUpMatrix() {
        System.out.println("test map");
//        char[] match = new char[2];
//        match[0] = 'a';
//        match[1] = 'b';
//        BLOSUM = new HashMap<char[], Integer>();
//        BLOSUM.put(match, new Integer(2));



        //enter scores from top left going down
        //loop through nucleotides 'cc', 'cs', 'ct', 'cp'
        //after one loop, start one more index later(skip c)

        char[] nucleotides = new char[]{'c', 's', 't', 'p', 'a', 'g', 'n', 'd', 'e', 'q', 'h', 'r', 'k', 'm', 'i', 'l', 'v', 'f', 'y', 'w'};
        int[] scores = new int[211];
        for(int i = 0; i < 211; i++){
            scores[i] = i;
        }
        BLOSUM = new HashMap<String, Integer>();

        int start = 0;
        int scoreIndex = 0;
        for(int i = 0; i < nucleotides.length; i++){
            for(int j = start; j < nucleotides.length; j++){
                char[] match = new char[2];
                match[0] = nucleotides[i];
                match[1] = nucleotides[j];
                String s = "" + nucleotides[i] + nucleotides[j];
                BLOSUM.put(s, new Integer(scores[scoreIndex]));
                scoreIndex++;
            }
            start++;
        }
        System.out.println("number of scores: " + scoreIndex);


        for (String variableName : BLOSUM.keySet())
        {
            String variableKey = variableName;
            Integer variableValue = BLOSUM.get(variableName);
            System.out.println(" " + variableKey + " = " + variableValue);
        }
    }
}
