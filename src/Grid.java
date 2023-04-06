//@Author Troy Perez
//
//
//@class Grid
//
//represents the grid used to calculate a pairwise alignment
//uses a 2d array of Cells to represent grid
//

import java.util.HashMap;
import java.util.Map;

public class Grid {


    public HashMap<char[], Integer> BLOSUM;


    //class variables
    //
    public Cell[][] cells;
    public int gridLength;
    public int gridHeight;
    public char[] querySequence1;
    public char[] querySequence2;
    public char[] alignedSequence1;
    public char[] alignedSequence2;
    public int maxScore;

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
    //@param querySequence1
    //@param querySequence2
    //
    //calculates height and length of grid
    //initializes grid and cells
    public Grid(char[] seq1, char[] seq2){

        maxScore = Integer.MIN_VALUE;

        querySequence1 = seq1;
        querySequence2 = seq2;

        //calculate grid dimensions
        gridLength = seq1.length + PADDING;
        gridHeight = seq2.length + PADDING;

        //initialize grid
        cells = new Cell[gridHeight][gridLength];

        //initialize cells inside grid
        for (int i = 0; i < gridHeight; i++) {
            for (int j = 0; j < gridLength; j++)
                cells[i][j] = new Cell();
        }

        //initialize BLOSUM matrix
        setUpMatrix();
    }

    public Grid(){}


    public void setUpMatrix() {
    }

    public int getMatchScore(char[] match){
        if(match[0] == match[1]){
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

        //set the top three empty cells
        cells[0][0].set = true;
        cells[0][0].nucleotide = '/';

        cells[1][0].set = true;
        cells[1][0].nucleotide = '/';

        cells[0][1].set = true;
        cells[0][1].nucleotide = '/';


        //add sequences to grid
        //
        //sequence1
        for(int i = 0; i < gridLength - PADDING; i++){
            cells[0][PADDING + i].nucleotide = querySequence1[i];
            cells[0][PADDING + i].set = true;
        }
        //sequence2
        for(int i = 0; i < gridHeight - PADDING; i++){
            cells[PADDING + i][0].nucleotide = querySequence2[i];
            cells[PADDING + i][0].set = true;
        }


        //initial score cells
        //
        //row values
        for(int i = 0; i < gridLength-1; i++){
            cells[1][1 + i].score = -2 * i;
            cells[1][1 + i].arrowDir = WEST;
            cells[1][1 + i].set = true;
        }
        //column values
        for(int i = 0; i < gridHeight-1; i++){
            cells[1+i][1].score = -2 * i;
            cells[1+i][1].arrowDir = NORTH;
            cells[1+i][1].set = true;
        }

        //stop condition
        //"0" cell
        cells[1][1].arrowDir = -1;
        cells[1][1].score = 0;
        cells[1][1].set = true;
        cells[1][1].nucleotide = ' ';


    }

    //@method calcGrid
    //
    //calculates the grid and sets values of all cells
    //assumes setUpGrid has finished
    //-1 for mismatch and +1 for match
    //ties will default to north west arrow
    public void calcGrid() throws Exception {
        //error check
        if(!cells[0][0].set) {
            throw new Exception("Grid has not been set up");
        }
        //initialize variables
        int highest, north, west, nw;

        //loop through all cells
        for (int i = 0; i < gridHeight; i++) {
            for (int j = 0; j < gridLength; j++)

                //skip all cells that are already set
                if(!cells[i][j].set){

                    //calculate all three values(North, West, and NW cell)
                    north = cells[i-1][j].score - INDELPENALTY;
                    west = cells[i][j-1].score - INDELPENALTY;



                    //check nucleotide column for match/mismatch
                    //make method to calc match/mismatch score?
                    //use matrices?
                    //
                    //getMatchScore()
                    if(cells[0][j].nucleotide == cells[i][0].nucleotide){
                        nw = cells[i-1][j-1].score+1;
                    }
                    else{
                        nw = cells[i-1][j-1].score-1;
                    }

                    //get max score from all three values
                    highest = Math.max(north, west);
                    highest = Math.max(highest, nw);

                    //set the arrow direction
                    if(highest == north){
                        cells[i][j].arrowDir = NORTH;
                    }
                    else if(highest == west){
                        cells[i][j].arrowDir = WEST;
                    }
                    //if there is a tie, default to northwest arrow
                    if(highest == nw){
                        cells[i][j].arrowDir = NORTHWEST;
                    }

                    cells[i][j].score = highest;
                    cells[i][j].set = true;
                }
        }
        //after grid is calculated, stores the max score from the bottom right cell
        maxScore = cells[gridHeight-1][gridLength-1].score;
    }

    //@method calcAlignment
    //
    //calculates an optimal alignment based on the grid
    //assumes calcGrid has finished
    //starts at the bottom right cell
    //follows the arrow direction until the "0" cell is reached
    //fills the aligned Sequence with the query sequence or indels accordingly
    public void calcAlignment() throws Exception {
        //error check
        if(getMaxScore() != cells[gridHeight-1][gridLength-1].score) {
            throw new Exception("Grid has not been calculated");
        }

        //initialize the aligned sequences with the correct length
        alignedSequence1 = new char[getLongestDim() - PADDING];
        alignedSequence2 = new char[getLongestDim() - PADDING];

        //build the aligned sequences backwards
        int index = getLongestDim() - PADDING - 1;

        //start with the bottom right cell
        int currentHeight = gridHeight-1;
        int currentLength = gridLength-1;

        //loop until the "0" cell is reached
        while(cells[currentHeight][currentLength].arrowDir != -1){
            //arrow points north
            if(cells[currentHeight][currentLength].arrowDir == NORTH) {
                //add indel to the north sequence
                alignedSequence1[index] = '-';
                //add character to west sequence
                alignedSequence2[index] = querySequence2[currentHeight - PADDING];
                //move to the north cell
                currentHeight = currentHeight -1;
            }
            //arrow points west
            else if(cells[currentHeight][currentLength].arrowDir == WEST) {
                //add indel to west sequence
                alignedSequence2[index] = '-';
                //add character to north sequence
                alignedSequence1[index] = querySequence1[currentLength - PADDING];
                //move to the west cell
                currentLength = currentLength -1;
            }
            else if(cells[currentHeight][currentLength].arrowDir == NORTHWEST) {
                //add character to north sequence
                alignedSequence1[index] = querySequence1[currentLength-2];
                //add character to west sequence
                alignedSequence2[index] = querySequence2[currentHeight-2];
                //move to north west cell
                currentHeight = currentHeight -1;
                currentLength = currentLength -1;
            }
            index--;
        }
    }

    //@Method findAlignment
    //
    //calls all necessary functions in order to find an optimal alignment of querySequence1 and querySequence2
    public void findAlignment() throws Exception {
        try {
            setUpGrid();
            calcGrid();
            calcAlignment();
        }
        catch(Exception e) {
            throw new Exception("Alignment was not calculated");
        }
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

    //@method printAlignment
    //
    //prints the alignment of the 2 sequences
    //assumes calcAlignment has finished
    public void printAlignment() throws Exception {
        if(alignedSequence1 == null || alignedSequence1 ==  null) {
            throw new Exception("Alignment has not been calculated yet");
        }
        else {
            System.out.println(alignedSequence1);
            System.out.println(alignedSequence2);
        }
    }

    //@method printGrid
    //
    //prints the values of the grid formatted
    public void printGrid(){
        for (int i = 0; i < gridHeight; i++) {
            System.out.println("\n");
            for (int j = 0; j < gridLength; j++) {
                System.out.format("%5s", cells[i][j].toString());
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
        System.out.println("\n");
    }
}
