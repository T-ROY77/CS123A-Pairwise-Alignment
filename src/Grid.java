import java.util.ArrayList;
import java.util.Collections;

//@Author Troy Perez
//
//
//@class Grid
//
//represents the grid used in the Needleman Wunsch algorithm to calculate pairwise alignment
//uses a 2d array of Cells to represent grid
//


public class Grid {

    //class variables
    //
    public Cell[][] cells;
    public int gridLength;
    public int gridHeight;
    public char[] querySequence1;
    public char[] querySequence2;
    public ArrayList<Character> alignedSequence1;
    public ArrayList<Character> alignedSequence2;
    public String sequence1Name = "";
    public String sequence2Name = "";
    public int maxScore;
    public boolean useMatrix;


    //constants
    //
    public int INDELPENALTY = 2;
    //padding for sequence and initial score cells
    public final int PADDING = 2;

    //arrow direction values
    public final int NORTH = 0;
    public final int NORTHWEST = 1;
    public final int WEST = 2;



    //@constructor
    //
    public Grid(){

        maxScore = Integer.MIN_VALUE;
        useMatrix = false;

        //calculate grid dimensions
        gridLength = PADDING;
        gridHeight = PADDING;

    }


    //@method getMatchScore
    //@param match the string to score
    //@return the score
    //
    //returns 1 if the 2 characters match and -1 otherwise
    public int getMatchScore(String match) throws Exception {
        if(match.charAt(0) == match.charAt(1)){
            return 1;
        }
        else{
            return -1;
        }
    }

    //@method setUpGrid
    //
    //adds the query sequences to the grid
    //calculates the initial scores
    public void setUpGrid() throws Exception {
        //error check
        if(querySequence1 == null || querySequence2 == null) {
            throw new Exception("Query sequences not set");
        }
        //calculate grid dimensions
        gridLength = querySequence1.length + PADDING;
        gridHeight = querySequence2.length + PADDING;


        //initialize grid array
        cells = new Cell[gridHeight][gridLength];

        //initialize cells inside grid
        for (int i = 0; i < gridHeight; i++) {
            for (int j = 0; j < gridLength; j++)
                cells[i][j] = new Cell();
        }

        //set the top three empty cells
        cells[0][0].isSet = true;
        cells[0][0].character = '/';
        cells[0][0].arrowDir = -1;

        cells[1][0].isSet = true;
        cells[1][0].character = '/';
        cells[1][0].arrowDir = -1;

        cells[0][1].isSet = true;
        cells[0][1].character = '/';
        cells[0][1].arrowDir = -1;

        //add sequences to grid
        //
        //sequence1
        for(int i = 0; i < gridLength - PADDING; i++){
            cells[0][PADDING + i].character = querySequence1[i];
            cells[0][PADDING + i].isSet = true;
            cells[0][PADDING + i].arrowDir = -1;
        }
        //sequence2
        for(int i = 0; i < gridHeight - PADDING; i++){
            cells[PADDING + i][0].character = querySequence2[i];
            cells[PADDING + i][0].isSet = true;
            cells[PADDING + i][0].arrowDir = -1;
        }


        //initial score cells
        //
        //row values
        for(int i = 0; i < gridLength-1; i++){
            cells[1][1 + i].score = -2 * i;
            cells[1][1 + i].arrowDir = WEST;
            cells[1][1 + i].isSet = true;
        }
        //column values
        for(int i = 0; i < gridHeight-1; i++){
            cells[1+i][1].score = -2 * i;
            cells[1+i][1].arrowDir = NORTH;
            cells[1+i][1].isSet = true;
        }

        //stop condition
        //"0" cell
        cells[1][1].arrowDir = -1;
        cells[1][1].score = 0;
        cells[1][1].isSet = true;
        cells[1][1].character = ' ';
    }

    //@method calcGrid
    //
    //calculates the grid and sets values of all cells
    //assumes setUpGrid has finished
    //ties will default to north west arrow
    public void calcGrid() throws Exception {
        //error check
        if(cells == null){
            System.out.println("Grid has not been set up");
        }
        else {
            //initialize variables
            int highest, north, west, nw;

            //loop through all cells
            for (int i = 0; i < gridHeight; i++) {
                for (int j = 0; j < gridLength; j++)

                    //skip all cells that are already set
                    if (!cells[i][j].isSet) {

                        //calculate all north and west score based on indel penalty
                        north = cells[i - 1][j].score - INDELPENALTY;
                        west = cells[i][j - 1].score - INDELPENALTY;

                        //get the chars to compare
                        String s = "" + cells[0][j].character + cells[i][0].character;
                        //calculate north west score
                        nw = cells[i - 1][j - 1].score + getMatchScore(s);

                        //get max score from all three values
                        highest = Math.max(north, west);
                        highest = Math.max(highest, nw);

                        //set the arrow direction
                        if (highest == north) {
                            cells[i][j].arrowDir = NORTH;
                        } else if (highest == west) {
                            cells[i][j].arrowDir = WEST;
                        }
                        //if there is a tie, default to northwest arrow
                        if (highest == nw) {
                            cells[i][j].arrowDir = NORTHWEST;
                        }

                        cells[i][j].score = highest;
                        cells[i][j].isSet = true;
                    }
            }
            //after grid is calculated, stores the max score from the bottom right cell
            maxScore = cells[gridHeight - 1][gridLength - 1].score;
        }
    }

    //@method calcAlignment
    //
    //calculates an optimal alignment based on the grid
    //assumes calcGrid has finished
    public void calcAlignment() throws Exception {
        //error check
        if(cells == null){
            System.out.println("Grid has not been set up");
        }
        else {

            alignedSequence1 = new ArrayList<>();
            alignedSequence2 = new ArrayList<>();

            //start with the bottom right cell
            int currentHeight = gridHeight - 1;
            int currentLength = gridLength - 1;


            //loop until the "0" cell is reached
            while (cells[currentHeight][currentLength].arrowDir != -1) {
                //error check
                if (currentHeight < 0 || currentLength < 0) {
                    System.out.println("bad index");
                    System.out.println(currentHeight);
                    System.out.println(currentLength);
                    currentHeight = currentHeight - 1;
                    currentLength = currentLength - 1;
                } else {

                    //arrow points north
                    if (cells[currentHeight][currentLength].arrowDir == NORTH) {

                        //add indel to the north sequence
                        alignedSequence1.add('-');
                        //add character to west sequence
                        alignedSequence2.add(querySequence2[currentHeight - PADDING]);
                        //move to the north cell
                        currentHeight = currentHeight - 1;
                    }

                    //arrow points west
                    else if (cells[currentHeight][currentLength].arrowDir == WEST) {

                        //add indel to west sequence
                        alignedSequence2.add('-');
                        //add character to north sequence
                        alignedSequence1.add(querySequence1[currentLength - PADDING]);
                        //move to the west cell
                        currentLength = currentLength - 1;
                    }
                    //arrow points north west
                    else if (cells[currentHeight][currentLength].arrowDir == NORTHWEST) {

                        //add character to both sequences
                        alignedSequence1.add(querySequence1[currentLength - PADDING]);
                        alignedSequence2.add(querySequence2[currentHeight - PADDING]);

                        //move to north west cell
                        currentHeight = currentHeight - 1;
                        currentLength = currentLength - 1;
                    }
                }
            }
            //sequences are calculated in reverse, so reverse them to correct order
            Collections.reverse(alignedSequence1);
            Collections.reverse(alignedSequence2);
        }
    }

    //@Method findAlignment
    //
    //calls all necessary functions in order to find an optimal alignment of querySequence1 and querySequence2
    public void findAlignment() throws Exception {
        setUpGrid();
        calcGrid();
        calcAlignment();
    }

    //@method getMaxScore
    //@return int max alignment score
    //
    //returns the max alignment score
    public int getMaxScore() throws Exception {
        if(maxScore > Integer.MIN_VALUE) {
            return maxScore;
        }
        else{
            throw new Exception("Grid has not been calculated yet");
        }
    }

    //@method getLongestDim
    //@return int longest dimension of the grid
    //
    //returns the value of the longest dimension of the grid
    public int getLongestDim(){
        return Math.max(gridHeight, gridLength);
    }

    //@method setNames
    //@param name1
    //@param name2
    //
    //sets the names of the sequences
    public void setNames(String name1, String name2){
        sequence1Name = name1;
        sequence2Name = name2;
    }

    //@method setSequences
    //@param seq1
    //@param seq2
    //
    //sets the sequences
    public void setSequences(String seq1, String seq2){
        querySequence1 = seq1.toCharArray();
        querySequence2 = seq2.toCharArray();
    }

    //@method printAlignment
    //
    //prints the alignment of the 2 sequences
    //assumes calcAlignment has finished
    public void printAlignment() throws Exception {
        //error check
        if(alignedSequence1 == null || alignedSequence1 ==  null) {
            System.out.println("Alignment has not been calculated yet");
        }
        else {
            System.out.println();
            System.out.println("Calculated optimal alignment");
            System.out.println();

            alignedSequence1.forEach(elem ->{
                System.out.print(elem);
            });
            System.out.println();
            alignedSequence2.forEach(elem ->{
                System.out.print(elem);
            });
        }
    }

    //@method printGrid
    //
    //prints the values of the grid formatted
    public void printGrid(){
        if(cells == null){
            System.out.println("Grid has not been set up");
        }
        else {
            for (int i = 0; i < gridHeight; i++) {
                System.out.println("\n");
                for (int j = 0; j < gridLength; j++) {
                    System.out.format("%5s", cells[i][j].toString());
                }
            }
        }
    }

    //@method printArrowGrid
    //
    //prints the arrow direction of the cells
    //^ = NORTH
    //<- = WEST
    //^<- = NORTH WEST
    public void printArrowGrid(){
        if(cells == null){
            System.out.println("Grid has not been set up");
        }
        else {
            String s = "";
            for (int i = 0; i < gridHeight; i++) {
                System.out.println("\n");
                for (int j = 0; j < gridLength; j++) {
                    if (cells[i][j].arrowDir == NORTH) {
                        s = "^";
                    } else if (cells[i][j].arrowDir == WEST) {
                        s = "<-";
                    } else if (cells[i][j].arrowDir == NORTHWEST) {
                        s = "^<-";
                    } else {
                        s = "/";
                    }
                    System.out.format("%5s", s);
                    s = "";
                }
            }
            System.out.println();
        }
    }
}
