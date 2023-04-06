import java.util.HashMap;

public class BLOSUMGrid extends Grid{

    //need to error check that all chars are on matrix
    //in constructor?

    @Override
    public int getMatchScore(char[] match) throws Exception {
        if(BLOSUM.containsKey(match)){
            return BLOSUM.get(match);
        }
        else{
            throw new Exception("Key not found in Matrix");
        }
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
        int[] scores = new int[]{1,2};
        char[] match = new char[2];
        match[0] = nucleotides[0];
        match[1] = nucleotides[0];
        BLOSUM.put(match, new Integer(scores[0]));

    }
}
