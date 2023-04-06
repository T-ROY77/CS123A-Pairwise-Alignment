//@Author Troy Perez
//
//@class Cell
//
//represents a single cell in a pairwise algorithm grid
//dcdc
public class Cell {
    public int score;
    public char nucleotide = ' ';
    //direction that the arrow points to in the grid
    //-1 = unset; 0 = north; 1 = north west; 2 = west
    int arrowDir = -1;
    public boolean set = false;

    public String toString(){
        String s = "";
        if(nucleotide == ' ') {
            s = s + score;
        }
        else{
            s = s + nucleotide;
        }
        return s;
    }

}
