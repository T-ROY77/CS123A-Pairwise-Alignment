//@Author Troy Perez
//
//@class Cell
//
//represents a single cell in a pairwise algorithm grid
//
public class Cell {

    //class variables
    //
    public int score;
    public char character = ' ';
    public boolean isSet = false;

    //direction that the arrow points to in the grid
    //0 = north; 1 = north west; 2 = west; -1 = unset/control
    int arrowDir = -1;

    public String toString(){
        String s = "";
        if(character == ' ') {
            s = s + score;
        }
        else{
            s = s + character;
        }
        return s;
    }
}
