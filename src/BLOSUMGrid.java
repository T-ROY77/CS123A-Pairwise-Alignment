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
        char[] match = new char[2];
        match[0] = 'a';
        match[1] = 'b';
        BLOSUM = new HashMap<char[], Integer>();
        BLOSUM.put(match, new Integer(2));
    }
}
