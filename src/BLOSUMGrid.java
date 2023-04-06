import java.util.HashMap;

public class BLOSUMGrid extends Grid{
    @Override
    public int getMatchScore(char[] match) {
        return BLOSUM.get(match);
    }

    public void setUpMatrix() {
        System.out.println("test map");
        char[] match = new char[2];
        match[0] = 'a';
        match[1] = 'b';
        BLOSUM = new HashMap<char[], Integer>();
        BLOSUM.put(match, new Integer(2));
        System.out.println(BLOSUM.get(match));
    }
}
