import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;

public class Main {
    public static MatrixGrid grid;

    public static Scanner input;
    public static String seq1 = "";
    public static String seq2 = "";
    public static boolean proteinMode;

    //@method checkSeq
    //@param seq1
    //@param sed2
    //@return sequences are valid
    //
    //returns true if sequences are valid for current mode
    //return false otherwise
    public static boolean checkSeq(String seq1, String seq2){
        if(proteinMode){
            //all amino acids(all alphabet letters except o, u, and j)
            return (seq1.matches("[a-ik-np-tv-z]+") && seq2.matches("[a-ik-np-tv-z]+"));
        }
        else{
            //only alphabet letters
            return (seq1.matches("[a-z]+") && seq2.matches("[a-z]+"));
        }
    }


    public static void main(String[] args) throws Exception {

        //initialize variables
        grid = new MatrixGrid();
        input = new Scanner(System.in);
        proteinMode = false;
        System.out.println();
        System.out.println("Pairwise Alignment Tool");

        //ui loop
        while (true) {

            //print sequences
            System.out.println();
            System.out.println();
            System.out.println();

            //sequences are empty
            if(seq1.equals("") || seq2.equals("")){
                System.out.println("Sequence 1:");
                System.out.println("(Enter sequence)");


                System.out.println("Sequence 2:");
                System.out.println("(Enter sequence)");
            }
            //sequences have no name
            else if(grid.sequence1Name.equals("") || grid.sequence2Name.equals("")){
                System.out.println("Sequence 1:");
                System.out.println(seq1);

                System.out.println("Sequence 2:");
                System.out.println(seq2);
            }
            //sequences have a name
            else{
                System.out.println(">" + grid.sequence1Name);
                System.out.println(seq1);

                System.out.println(">" + grid.sequence2Name);
                System.out.println(seq2);
            }

            //print choices
            System.out.println();
            System.out.println("1: Enter new query sequences");
            System.out.println("2: Enter new query sequences with filepath");
            System.out.println("3: Calculate an Optimal Alignment");
            System.out.println("4: Print grid");
            System.out.println("5: Print arrow grid");
            if(proteinMode){
                System.out.println("6: Change mode (Protein mode)");
            }
            else{
                System.out.println("6: Change mode (Nucleotide mode)");
            }
            System.out.println("7: Create a new Matrix");
            System.out.println("8: Print all Matrices");
            System.out.println("9: quit");

            String choice = input.nextLine();

            try {
                int inputNumber = Integer.parseInt(choice);
                //input tree
                //

                //calc alignment
                //print optimal alignment
                //print max score
                if (inputNumber == 3) {
                    //error check
                    if(seq1.equals("") || seq2.equals("")){
                        System.out.println("Enter sequences first");
                    }
                    else {
                        if(proteinMode){
                            grid.useMatrix = true;
                            int m = 0;
                            int matrixNumber = 0;
                            //allow user to select matrix at runtime
                            System.out.println("Choose matrix: ");
                            //print all saved matrices
                            for(int i = 0; i < grid.matrices.size(); i++){
                                System.out.println("" + i + ": " + grid.matrices.get(i).toString());
                            }

                            String s = input.nextLine();
                            m = Integer.parseInt(s);

                            //check that input is a valid matrix
                            if(m < grid.matrices.size()){
                                grid.setCurrentMatrix(m);
                            }
                            else{
                                System.out.println("Invalid matrix");
                                System.out.println("Using matrix 0");
                                grid.setCurrentMatrix(0);
                            }
                        }
                        //nulceotide mode doesnt use a matrix
                        else{
                            grid.useMatrix = false;
                        }
                        grid.findAlignment();
                        grid.printAlignment();
                        System.out.println();
                        System.out.println("Max score: " + grid.getMaxScore());
                        System.out.println();
                    }
                }

                //print current grid
                else if(inputNumber == 4){
                    //error check
                    if(seq1.equals("") || seq2.equals("")){
                        System.out.println("Enter sequences first");
                    }
                    else {
                        grid.printGrid();
                    }
                }

                //print current arrow grid
                else if(inputNumber == 5) {
                    //error check
                    if(seq1.equals("") || seq2.equals("")){
                        System.out.println("Enter sequences first");
                    }
                    else {
                        grid.printArrowGrid();
                    }
                }

                //change mode
                else if(inputNumber == 6){
                    proteinMode = !proteinMode;
                    //resets sequences
                    seq1 = "";
                    seq2 = "";
                    grid.resetNames();
                    grid.resetGrid();
                    System.out.print("mode changed");
                }

                //manual sequence input
                else if (inputNumber == 1) {
                    //take UI
                    System.out.print("Enter the first sequence name: ");
                    String firstName = input.nextLine();
                    System.out.print("Enter the first sequence: ");
                    String firstInput = input.nextLine();
                    System.out.print("Enter the second sequence name: ");
                    String secondName = input.nextLine();
                    System.out.print("Enter the second sequence: ");
                    String secondInput = input.nextLine();

                    firstInput = firstInput.toLowerCase();
                    secondInput = secondInput.toLowerCase();


                    //check that sequence is valid
                    if(Main.checkSeq(firstInput, secondInput)) {
                        //create the new grid
                        grid.resetGrid();
                        grid.setSequences(firstInput, secondInput);
                        grid.setNames(firstName, secondName);
                        seq1 = firstInput;
                        seq2 = secondInput;
                        System.out.println();
                        System.out.println("Sequences saved");
                    }
                    else{
                        System.out.println("Invalid sequence");
                    }
                }

                //file path sequence input
                else if(inputNumber == 2){
                    System.out.println("Enter filepath: ");
                    String filepath = input.nextLine();
                    try(BufferedReader read = new BufferedReader(new FileReader(filepath))){
                        //read input from file
                        //
                        String firstName = read.readLine();
                        String firstInput = read.readLine();
                        String current = "";
                        //read into first sequence until second sequence reached
                        while(!current.contains(">")){
                            firstInput = firstInput + current;
                            current = read.readLine();
                        }
                        String secondName = current;
                        String secondInput = read.readLine();
                        current = "";
                        //read into second sequence until end of file reached
                        while(current != null){
                            secondInput = secondInput + current;
                            current = read.readLine();
                        }

                        firstInput = firstInput.toLowerCase();
                        secondInput = secondInput.toLowerCase();

                        //check that sequence is valid
                        if(Main.checkSeq(firstInput, secondInput)) {
                            //create the new grid
                            grid.resetGrid();
                            grid.setSequences(firstInput, secondInput);
                            grid.setNames(firstName, secondName);
                            seq1 = firstInput;
                            seq2 = secondInput;
                            System.out.println();
                            System.out.println("Sequences saved");
                        }
                        else{
                            System.out.println("Invalid sequence");
                        }
                        read.close();
                    }
                    catch(Exception e){
                        System.out.println("\n");
                        System.out.println("Error reading filepath");

                        seq1 = "";
                        seq2 = "";
                        grid.resetNames();
                        grid.resetGrid();
                    }
                }

                //create new matrix
                else if(inputNumber == 7){
                    boolean validMatrix = true;

                    //take ui
                    System.out.println("Enter name of matrix: ");
                    String name = input.nextLine();
                    System.out.println("Enter order of amino acids");
                    String acids = input.nextLine();
                    char[] chars = acids.toCharArray();
                    System.out.println("Enter scores of matrix: (Left to right, Top to Bottom)");
                    String scores = input.nextLine();

                    //store matrix scores in array of ints
                    String[] v = scores.trim().split("\\s+");
                    int[] scoresArray = new int[v.length];
                    for (int i = 0; i < v.length; i++) {
                        scoresArray[i] = Integer.parseInt(v[i]);
                    }

                    //error handling
                    //
                    //check that only amino acid chars are present
                    if(!acids.matches("[a-ik-np-tv-z]+")){
                        validMatrix = false;
                    }
                    //check for duplicate acids
                    HashSet set = new HashSet();
                    for (char c : chars) {
                        if (set.add(c) == false) {
                            System.out.println("Duplicate amino acid");
                            validMatrix = false;
                        }
                    }
                    //check for correct number of scores
                    if(scoresArray.length < chars.length * chars.length){
                        System.out.println("Not enough scores");
                        validMatrix = false;
                    }

                    //create new matrix if chars and scores are valid
                    if(validMatrix){
                        grid.setUpNewMatrix(name, chars, scoresArray);
                        System.out.println("Matrix created");
                    }
                    else{
                        System.out.println("Matrix not created");
                    }
                }
                //print matrices
                else if(inputNumber == 8){
                    grid.printMatrices();
                }
                //quit
                else if(inputNumber == 9){
                    seq1 = "";
                    seq2 = "";
                    break;
                }
                //user input error
                else {
                    System.out.println();
                    System.out.println("Invalid input.");
                }
            } catch (NumberFormatException e) {
                System.out.println();
                System.out.println("Invalid input. Please enter a number.");
            }
        }
        System.out.println("Goodbye");
    }
}