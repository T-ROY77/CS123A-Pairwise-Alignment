import java.util.ArrayList;

//@Author Troy Perez
//
//
//@class Grid
//
//represents the grid used in the Needleman Wunsch algorithm to calculate pairwise alignment
//uses a 2d array of Cells to represent grid
//implements matrices for protein alignment


public class MatrixGrid extends Grid{

    //class variables
    //
    public ArrayList<Matrix> matrices;
    public int currentMatrix;

    //amino acid values for BLOSUM matrix
    public final char[] BLOSUMChars = new char[]{'a', 'r', 'n', 'd', 'c', 'q', 'e', 'g', 'h', 'i', 'l', 'k', 'm', 'f', 'p', 's', 't', 'w', 'y', 'v', 'b', 'z', 'x'};
    //scores for BLOSUM62 matrix
    public final String BLOSUM62Values = "4 -1 -2 -2  0 -1 -1  0 -2 -1 -1 -1 -1 -2 -1  1  0 -3 -2  0 -2 -1  0 -1  5  0 -2 -3  1  0 -2  0 -3 -2  2 -1 -3 -2 -1 -1 -3 -2 -3 -1  0 -1 -2  0  6  1 -3  0  0  0  1 -3 -3  0 -2 -3 -2  1  0 -4 -2 -3  3  0 -1 -2 -2  1  6 -3  0  2 -1 -1 -3 -4 -1 -3 -3 -1  0 -1 -4 -3 -3  4  1 -1 0 -3 -3 -3  9 -3 -4 -3 -3 -1 -1 -3 -1 -2 -3 -1 -1 -2 -2 -1 -3 -3 -2 -1  1  0  0 -3  5  2 -2  0 -3 -2  1  0 -3 -1  0 -1 -2 -1 -2  0  3 -1 -1  0  0  2 -4  2  5 -2  0 -3 -3  1 -2 -3 -1  0 -1 -3 -2 -2  1  4 -1 0 -2  0 -1 -3 -2 -2  6 -2 -4 -4 -2 -3 -3 -2  0 -2 -2 -3 -3 -1 -2 -1  -2  0  1 -1 -3  0  0 -2  8 -3 -3 -1 -2 -1 -2 -1 -2 -2  2 -3  0  0 -1 -1 -3 -3 -3 -1 -3 -3 -4 -3  4  2 -3  1  0 -3 -2 -1 -3 -1  3 -3 -3 -1 -1 -2 -3 -4 -1 -2 -3 -4 -3  2  4 -2  2  0 -3 -2 -1 -2 -1  1 -4 -3 -1 -1  2  0 -1 -3  1  1 -2 -1 -3 -2  5 -1 -3 -1  0 -1 -3 -2 -2  0  1 -1 -1 -1 -2 -3 -1  0 -2 -3 -2  1  2 -1  5  0 -2 -1 -1 -1 -1  1 -3 -1 -1 -2 -3 -3 -3 -2 -3 -3 -3 -1  0  0 -3  0  6 -4 -2 -2  1  3 -1 -3 -3 -1 -1 -2 -2 -1 -3 -1 -1 -2 -2 -3 -3 -1 -2 -4  7 -1 -1 -4 -3 -2 -2 -1 -2 1 -1  1  0 -1  0  0  0 -1 -2 -2  0 -1 -2 -1  4  1 -3 -2 -2  0  0  0 0 -1  0 -1 -1 -1 -1 -2 -2 -1 -1 -1 -1 -2 -1  1  5 -2 -2  0 -1 -1  0 -3 -3 -4 -4 -2 -2 -3 -2 -2 -3 -2 -3 -1  1 -4 -3 -2 11  2 -3 -4 -3 -2 -2 -2 -2 -3 -2 -1 -2 -3  2 -1 -1 -2 -1  3 -3 -2 -2  2  7 -1 -3 -2 -1 0 -3 -3 -3 -1 -2 -2 -3 -3  3  1 -2  1 -1 -2 -2  0 -3 -1  4 -3 -2 -1 -2 -1  3  4 -3  0  1 -1  0 -3 -4  0 -3 -3 -2  0 -1 -4 -3 -3  4  1 -1 -1  0  0  1 -3  3  4 -2  0 -3 -3  1 -1 -3 -1  0 -1 -3 -2 -2  1  4 -1 0 -1 -1 -1 -2 -1 -1 -1 -1 -1 -1 -1 -1 -1 -2  0  0 -2 -1 -1 -1 -1 -1";

    //scores for BLOSUM30 matrix
    public final String BLOSUM30Values = "4 -1  0  0 -3  1  0  0 -2  0 -1  0  1 -2 -1  1  1 -5 -4  1  0  0  0 -1  8 -2 -1 -2  3 -1 -2 -1 -3 -2  1  0 -1 -1 -1 -3  0  0 -1 -2  0 -1    0 -2  8  1 -1 -1 -1  0 -1  0 -2  0  0 -1 -3  0  1 -7 -4 -2  4 -1  0  0 -1  1  9 -3 -1  1 -1 -2 -4 -1  0 -3 -5 -1  0 -1 -4 -1 -2  5  0 -1   -3 -2 -1 -3 17 -2  1 -4 -5 -2  0 -3 -2 -3 -3 -2 -2 -2 -6 -2 -2  0 -2    1  3 -1 -1 -2  8  2 -2  0 -2 -2  0 -1 -3  0 -1  0 -1 -1 -3 -1  4  0    0 -1 -1  1  1  2  6 -2  0 -3 -1  2 -1 -4  1  0 -2 -1 -2 -3  0  5 -1    0 -2  0 -1 -4 -2 -2  8 -3 -1 -2 -1 -2 -3 -1  0 -2  1 -3 -3  0 -2 -1   -2 -1 -1 -2 -5  0  0 -3 14 -2 -1 -2  2 -3  1 -1 -2 -5  0 -3 -2  0 -1    0 -3  0 -4 -2 -2 -3 -1 -2  6  2 -2  1  0 -3 -1  0 -3 -1  4 -2 -3  0  -1 -2 -2 -1  0 -2 -1 -2 -1  2  4 -2  2  2 -3 -2  0 -2  3  1 -1 -1  0    0  1  0  0 -3  0  2 -1 -2 -2 -2  4  2 -1  1  0 -1 -2 -1 -2  0  1  0    1  0  0 -3 -2 -1 -1 -2  2  1  2  2  6 -2 -4 -2  0 -3 -1  0 -2 -1  0   -2 -1 -1 -5 -3 -3 -4 -3 -3  0  2 -1 -2 10 -4 -1 -2  1  3  1 -3 -4 -1  -1 -1 -3 -1 -3  0  1 -1  1 -3 -3  1 -4 -4 11 -1  0 -3 -2 -4 -2  0 -1    1 -1  0  0 -2 -1  0  0 -1 -1 -2  0 -2 -1 -1  4  2 -3 -2 -1  0 -1  0    1 -3  1 -1 -2  0 -2 -2 -2  0  0 -1  0 -2  0  2  5 -5 -1  1  0 -1  0   -5  0 -7 -4 -2 -1 -1  1 -5 -3 -2 -2 -3  1 -3 -3 -5 20  5 -3 -5 -1 -2   -4  0 -4 -1 -6 -1 -2 -3  0 -1  3 -1 -1  3 -2 -2 -1  5  9  1 -3 -2 -1    1 -1 -2 -2 -2 -3 -3 -3 -3  4  1 -2  0  1 -4 -1  1 -3  1  5 -2 -3  0    0 -2  4  5 -2 -1  0  0 -2 -2 -1  0 -2 -3 -2  0  0 -5 -3 -2  5  0 -1    0  0 -1  0  0  4  5 -2  0 -3 -1  1 -1 -4  0 -1 -1 -1 -2 -3  0  4  0   0 -1  0 -1 -2  0 -1 -1 -1  0  0  0  0 -1 -1  0  0 -2 -1  0 -1  0 -1 ";

    //scores for BLOSUM45 matrix
    public final String BLOSUM45Values = "5 -2 -1 -2 -1 -1 -1  0 -2 -1 -1 -1 -1 -2 -1  1  0 -2 -2  0 -1 -1  0   -2  7  0 -1 -3  1  0 -2  0 -3 -2  3 -1 -2 -2 -1 -1 -2 -1 -2 -1  0 -1  -1  0  6  2 -2  0  0  0  1 -2 -3  0 -2 -2 -2  1  0 -4 -2 -3  4  0 -1   -2 -1  2  7 -3  0  2 -1  0 -4 -3  0 -3 -4 -1  0 -1 -4 -2 -3  5  1 -1   -1 -3 -2 -3 12 -3 -3 -3 -3 -3 -2 -3 -2 -2 -4 -1 -1 -5 -3 -1 -2 -3 -2   -1  1  0  0 -3  6  2 -2  1 -2 -2  1  0 -4 -1  0 -1 -2 -1 -3  0  4 -1   -1  0  0  2 -3  2  6 -2  0 -3 -2  1 -2 -3  0  0 -1 -3 -2 -3  1  4 -1    0 -2  0 -1 -3 -2 -2  7 -2 -4 -3 -2 -2 -3 -2  0 -2 -2 -3 -3 -1 -2 -1  -2  0  1  0 -3  1  0 -2 10 -3 -2 -1  0 -2 -2 -1 -2 -3  2 -3  0  0 -1   -1 -3 -2 -4 -3 -2 -3 -4 -3  5  2 -3  2  0 -2 -2 -1 -2  0  3 -3 -3 -1   -1 -2 -3 -3 -2 -2 -2 -3 -2  2  5 -3  2  1 -3 -3 -1 -2  0  1 -3 -2 -1   -1  3  0  0 -3  1  1 -2 -1 -3 -3  5 -1 -3 -1 -1 -1 -2 -1 -2  0  1 -1   -1 -1 -2 -3 -2  0 -2 -2  0  2  2 -1  6  0 -2 -2 -1 -2  0  1 -2 -1 -1   -2 -2 -2 -4 -2 -4 -3 -3 -2  0  1 -3  0  8 -3 -2 -1  1  3  0 -3 -3 -1   -1 -2 -2 -1 -4 -1  0 -2 -2 -2 -3 -1 -2 -3  9 -1 -1 -3 -3 -3 -2 -1 -1    1 -1  1  0 -1  0  0  0 -1 -2 -3 -1 -2 -2 -1  4  2 -4 -2 -1  0  0  0  0 -1  0 -1 -1 -1 -1 -2 -2 -1 -1 -1 -1 -1 -1  2  5 -3 -1  0  0 -1  0   -2 -2 -4 -4 -5 -2 -3 -2 -3 -2 -2 -2 -2  1 -3 -4 -3 15  3 -3 -4 -2 -2   -2 -1 -2 -2 -3 -1 -2 -3  2  0  0 -1  0  3 -3 -2 -1  3  8 -1 -2 -2 -1    0 -2 -3 -3 -1 -3 -3 -3 -3  3  1 -2  1  0 -3 -1  0 -3 -1  5 -3 -3 -1   -1 -1  4  5 -2  0  1 -1  0 -3 -3  0 -2 -3 -2  0  0 -4 -2 -3  4  2 -1   -1  0  0  1 -3  4  4 -2  0 -3 -2  1 -1 -3 -1  0 -1 -2 -2 -3  2  4 -1    0 -1 -1 -1 -2 -1 -1 -1 -1 -1 -1 -1 -1 -1 -1  0  0 -2 -1 -1 -1 -1 -1 ";

    //scores for BLOSUM80 matrix
    public final String BLOSUM80Values = "7 -3 -3 -3 -1 -2 -2  0 -3 -3 -3 -1 -2 -4 -1  2  0 -5 -4 -1 -3 -2 -1   -3  9 -1 -3 -6  1 -1 -4  0 -5 -4  3 -3 -5 -3 -2 -2 -5 -4 -4 -2  0 -2   -3 -1  9  2 -5  0 -1 -1  1 -6 -6  0 -4 -6 -4  1  0 -7 -4 -5  5 -1 -2   -3 -3  2 10 -7 -1  2 -3 -2 -7 -7 -2 -6 -6 -3 -1 -2 -8 -6 -6  6  1 -3   -1 -6 -5 -7 13 -5 -7 -6 -7 -2 -3 -6 -3 -4 -6 -2 -2 -5 -5 -2 -6 -7 -4   -2  1  0 -1 -5  9  3 -4  1 -5 -4  2 -1 -5 -3 -1 -1 -4 -3 -4 -1  5 -2   -2 -1 -1  2 -7  3  8 -4  0 -6 -6  1 -4 -6 -2 -1 -2 -6 -5 -4  1  6 -2    0 -4 -1 -3 -6 -4 -4  9 -4 -7 -7 -3 -5 -6 -5 -1 -3 -6 -6 -6 -2 -4 -3  -3  0  1 -2 -7  1  0 -4 12 -6 -5 -1 -4 -2 -4 -2 -3 -4  3 -5 -1  0 -2   -3 -5 -6 -7 -2 -5 -6 -7 -6  7  2 -5  2 -1 -5 -4 -2 -5 -3  4 -6 -6 -2   -3 -4 -6 -7 -3 -4 -6 -7 -5  2  6 -4  3  0 -5 -4 -3 -4 -2  1 -7 -5 -2   -1  3  0 -2 -6  2  1 -3 -1 -5 -4  8 -3 -5 -2 -1 -1 -6 -4 -4 -1  1 -2   -2 -3 -4 -6 -3 -1 -4 -5 -4  2  3 -3  9  0 -4 -3 -1 -3 -3  1 -5 -3 -2   -4 -5 -6 -6 -4 -5 -6 -6 -2 -1  0 -5  0 10 -6 -4 -4  0  4 -2 -6 -6 -3   -1 -3 -4 -3 -6 -3 -2 -5 -4 -5 -5 -2 -4 -6 12 -2 -3 -7 -6 -4 -4 -2 -3    2 -2  1 -1 -2 -1 -1 -1 -2 -4 -4 -1 -3 -4 -2  7  2 -6 -3 -3  0 -1 -1  0 -2  0 -2 -2 -1 -2 -3 -3 -2 -3 -1 -1 -4 -3  2  8 -5 -3  0 -1 -2 -1   -5 -5 -7 -8 -5 -4 -6 -6 -4 -5 -4 -6 -3  0 -7 -6 -5 16  3 -5 -8 -5 -5   -4 -4 -4 -6 -5 -3 -5 -6  3 -3 -2 -4 -3  4 -6 -3 -3  3 11 -3 -5 -4 -3   -1 -4 -5 -6 -2 -4 -4 -6 -5  4  1 -4  1 -2 -4 -3  0 -5 -3  7 -6 -4 -2   -3 -2  5  6 -6 -1  1 -2 -1 -6 -7 -1 -5 -6 -4  0 -1 -8 -5 -6  6  0 -3   -2  0 -1  1 -7  5  6 -4  0 -6 -5  1 -3 -6 -2 -1 -2 -5 -4 -4  0  6 -1   -1 -2 -2 -3 -4 -2 -2 -3 -2 -2 -2 -2 -2 -3 -3 -1 -1 -5 -3 -2 -3 -1 -2 ";

    //scores for BLOSUM100 matrix
    public final String BLOSUM100Values = "8 -3 -4 -5 -2 -2 -3 -1 -4 -4 -4 -2 -3 -5 -2  1 -1 -6 -5 -2 -4 -2 -2  -3 10 -2 -5 -8  0 -2 -6 -1 -7 -6  3 -4 -6 -5 -3 -3 -7 -5 -6 -4 -1 -3   -4 -2 11  1 -5 -1 -2 -2  0 -7 -7 -1 -5 -7 -5  0 -1 -8 -5 -7  5 -2 -3   -5 -5  1 10 -8 -2  2 -4 -3 -8 -8 -3 -8 -8 -5 -2 -4 -10 -7 -8  6  0 -4  -2 -8 -5 -8 14 -7 -9 -7 -8 -3 -5 -8 -4 -4 -8 -3 -3 -7 -6 -3 -7 -8 -5   -2  0 -1 -2 -7 11  2 -5  1 -6 -5  2 -2 -6 -4 -2 -3 -5 -4 -5 -2  5 -2  -3 -2 -2  2 -9  2 10 -6 -2 -7 -7  0 -5 -8 -4 -2 -3 -8 -7 -5  0  7 -3   -1 -6 -2 -4 -7 -5 -6  9 -6 -9 -8 -5 -7 -8 -6 -2 -5 -7 -8 -8 -3 -5 -4   -4 -1  0 -3 -8  1 -2 -6 13 -7 -6 -3 -5 -4 -5 -3 -4 -5  1 -7 -2 -1 -4   -4 -7 -7 -8 -3 -6 -7 -9 -7  8  2 -6  1 -2 -7 -5 -3 -6 -4  4 -8 -7 -3   -4 -6 -7 -8 -5 -5 -7 -8 -6  2  8 -6  3  0 -7 -6 -4 -5 -4  0 -8 -6 -3   -2  3 -1 -3 -8  2  0 -5 -3 -6 -6 10 -4 -6 -3 -2 -3 -8 -5 -5 -2  0 -3   -3 -4 -5 -8 -4 -2 -5 -7 -5  1  3 -4 12 -1 -5 -4 -2 -4 -5  0 -7 -4 -3  -5 -6 -7 -8 -4 -6 -8 -8 -4 -2  0 -6 -1 11 -7 -5 -5  0  4 -3 -7 -7 -4   -2 -5 -5 -5 -8 -4 -4 -6 -5 -7 -7 -3 -5 -7 12 -3 -4 -8 -7 -6 -5 -4 -4    1 -3  0 -2 -3 -2 -2 -2 -3 -5 -6 -2 -4 -5 -3  9  2 -7 -5 -4 -1 -2 -2  -1 -3 -1 -4 -3 -3 -3 -5 -4 -3 -4 -3 -2 -5 -4  2  9 -7 -5 -1 -2 -3 -2   -6 -7 -8 -10 -7 -5 -8 -7 -5 -6 -5 -8 -4  0 -8 -7 -7 17  2 -5 -9 -7 -6  -5 -5 -5 -7 -6 -4 -7 -8  1 -4 -4 -5 -5  4 -7 -5 -5  2 12 -5 -6 -6 -4  -2 -6 -7 -8 -3 -5 -5 -8 -7  4  0 -5  0 -3 -6 -4 -1 -5 -5  8 -7 -5 -3   -4 -4  5  6 -7 -2  0 -3 -2 -8 -8 -2 -7 -7 -5 -1 -2 -9 -6 -7  6  0 -4   -2 -1 -2  0 -8  5  7 -5 -1 -7 -6  0 -4 -7 -4 -2 -3 -7 -6 -5  0  6 -2   -2 -3 -3 -4 -5 -2 -3 -4 -4 -3 -3 -3 -3 -4 -4 -2 -2 -6 -4 -3 -4 -2 -3  ";

    public MatrixGrid() throws Exception {

        super();
        matrices = new ArrayList<>();
        int[] scoresArray;
        String[] values;

        //store BLOSUM62 matrix values in array of ints
        values = BLOSUM62Values.trim().split("\\s+");
        scoresArray = new int[values.length];
        for (int i = 0; i < values.length; i++) {
            scoresArray[i] = Integer.parseInt(values[i]);
        }
        //set up BLOSUM62 matrix
        setUpNewMatrix("BLOSUM62", BLOSUMChars, scoresArray);


        //store BLOSUM30 matrix values in array of ints
        values = BLOSUM30Values.trim().split("\\s+");
        scoresArray = new int[values.length];
        for (int i = 0; i < values.length; i++) {
            scoresArray[i] = Integer.parseInt(values[i]);
        }

        //set up BLOSUM30 matrix
        setUpNewMatrix("BLOSUM30", BLOSUMChars, scoresArray);


        //store BLOSUM45 matrix values in array of ints
        values = BLOSUM45Values.trim().split("\\s+");
        scoresArray = new int[values.length];
        for (int i = 0; i < values.length; i++) {
            scoresArray[i] = Integer.parseInt(values[i]);
        }

        //set up BLOSUM30 matrix
        setUpNewMatrix("BLOSUM45", BLOSUMChars, scoresArray);


        //store BLOSUM80 matrix values in array of ints
        values = BLOSUM80Values.trim().split("\\s+");
        scoresArray = new int[values.length];
        for (int i = 0; i < values.length; i++) {
            scoresArray[i] = Integer.parseInt(values[i]);
        }

        //set up BLOSUM80 matrix
        setUpNewMatrix("BLOSUM80", BLOSUMChars, scoresArray);



        //store BLOSUM100 matrix values in array of ints
        values = BLOSUM100Values.trim().split("\\s+");
        scoresArray = new int[values.length];
        for (int i = 0; i < values.length; i++) {
            scoresArray[i] = Integer.parseInt(values[i]);
        }

        //set up BLOSUM100 matrix
        setUpNewMatrix("BLOSUM100", BLOSUMChars, scoresArray);
    }


    //@method getMatrixScore
    //@param match
    //@return the score from the matrix map
    //
    //gets the score of the 2 characters from the matrix
    public int getMatrixScore(String match) throws Exception{
        //error check
        //checks if the string is backwards
        if(!matrices.get(currentMatrix).matrixMap.containsKey(match)){
            match = "" + match.charAt(1) + match.charAt(0);
            if(!matrices.get(currentMatrix).matrixMap.containsKey(match)){
                throw new Exception("Key not found in Matrix");
            }
        }
        return matrices.get(currentMatrix).matrixMap.get(match);
    }

    //@override method getMatchScore
    //@param match
    //@return the score
    //
    //if currently using matrix, gets matrix score
    //otherwise calls super method
    @Override
    public int getMatchScore(String match) throws Exception {
        if(this.useMatrix){
            return getMatrixScore(match);
        }
        else{
            return super.getMatchScore(match);
        }
    }

    //@method setUpNewMatrix
    //@param matrix name
    //@param amino acids
    //@param scores
    //
    //sets up a new matrix
    //add new matrix to matrices array
    public void setUpNewMatrix(String name, char[] c, int[] scores) throws Exception {

        Matrix m = new Matrix(name, c, scores);
        matrices.add(m);
    }

    public void printMatrices(){
        matrices.forEach(matrix->matrix.printMatrixMap());
    }

    public void setCurrentMatrix(int matrix){
        currentMatrix = matrix;
    }
}
