import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Scanner;

public class Main {
    public static MatrixGrid grid = new MatrixGrid();

    public static Scanner input;
    public static String seq1 = "";
    public static String seq2 = "";


    public static boolean proteinMode;




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

        input = new Scanner(System.in);
        proteinMode = false;
        System.out.println("Pairwise Alignment Tool");

        while (true) {

            //print sequences
            System.out.println();
            if(seq1.equals("") || seq2.equals("")){
                System.out.println("Sequence 1:");
                System.out.println("(Enter sequence)");


                System.out.println("Sequence 2:");
                System.out.println("(Enter sequence)");
            }
            else if(grid.sequence1Name.equals("") || grid.sequence2Name.equals("")){
                System.out.println("Sequence 1:");
                System.out.println(seq1);

                System.out.println("Sequence 2:");
                System.out.println(seq2);
            }
            else{
                System.out.println(" " + grid.sequence1Name);
                System.out.println(seq1);

                System.out.println(" " + grid.sequence2Name);
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

            System.out.println("9: quit");

            String choice = input.nextLine();

            try {
                int inputNumber = Integer.parseInt(choice);

                //calc alignment
                //print optimal alignment
                //print max score
                if (inputNumber == 3) {
                    if(seq1.equals("") || seq2.equals("")){
                        System.out.println("Enter sequences first");
                    }
                    else {
                        if(proteinMode){
                            grid.useMatrix = true;
                            int m = 0;
                            int matrixNumber = 0;
                            System.out.println("Choose matrix: ");
                            for(int i = 0; i < grid.matrices.size(); i++){
                                System.out.println("" + i + ": " + grid.matrices.get(i).toString());
                            }




                            String s = input.nextLine();
                            m = Integer.parseInt(s);
                            //check that input is valid matrix
                            grid.setCurrentMatrix(m);
                            System.out.println("using matrix: " + grid.currentMatrix);

                        }
                        else{
                            grid.useMatrix = false;
                        }
                        grid.findAlignment();
                        grid.printAlignment();
                        System.out.println("\nMax score:");
                        System.out.println(grid.getMaxScore());
                    }
                }

                //print current grid
                else if(inputNumber == 4){
                    if(seq1.equals("") || seq2.equals("")){
                        System.out.println("Enter sequences first");
                    }
                    else {
                        grid.printGrid();
                    }
                }

                //print current arrow grid
                else if(inputNumber == 5) {
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
                    seq1 = "";
                    seq2 = "";
                    System.out.print("mode changed");
                }
                //ui
                else if (inputNumber == 1) {
                    //take UI
                    System.out.print("Enter the first sequence: ");
                    String firstInput = input.nextLine();
                    System.out.print("Enter the second sequence: ");
                    String secondInput = input.nextLine();

                    firstInput = firstInput.toLowerCase();
                    secondInput = secondInput.toLowerCase();


                    //check that sequence is valid
                    if(Main.checkSeq(firstInput, secondInput)) {
                        //create the new grid
                        seq1 = firstInput;
                        seq2 = secondInput;
                        char[] seq1Array = seq1.toCharArray();
                        char[] seq2Array = seq2.toCharArray();
                        grid = new MatrixGrid(seq1Array, seq2Array);

                        System.out.println();
                        System.out.println("Sequences saved");

                        //grid.printMatrices();

                    }
                    else{
                        System.out.println("Invalid sequence");
                    }
                }

                //file path input
                else if(inputNumber == 2){
                    System.out.println("Enter filepath: ");
                    String filepath = input.nextLine();
                    try(BufferedReader read = new BufferedReader(new FileReader(filepath))){
                        //read input from file
                        String seq1Name = read.readLine();

                        String firstInput = read.readLine();
                        String current = "";
                        while(!current.contains(">")){
                            firstInput = firstInput + current;
                            current = read.readLine();
                        }

                        String seq2Name = current;
                        String secondInput = read.readLine();
                        current = "";
                        while(current != null){
                            secondInput = secondInput + current;
                            current = read.readLine();
                        }

                        firstInput = firstInput.toLowerCase();
                        secondInput = secondInput.toLowerCase();

                        //check that sequence is valid
                        if(Main.checkSeq(firstInput, secondInput)) {
                            //create the new grid
                            seq1 = firstInput;
                            seq2 = secondInput;
                            char[] seq1Array = seq1.toCharArray();
                            char[] seq2Array = seq2.toCharArray();
                            grid = new MatrixGrid(seq1Array, seq2Array);

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

                    }
                }
                else if(inputNumber == 9){
                    seq1 = "";
                    seq2 = "";
                    break;
                }
                else {
                    System.out.println("\n");
                    System.out.println("Invalid input.");
                }
            } catch (NumberFormatException e) {
                System.out.println("\n");
                System.out.println("Invalid input. Please enter a number.");
            }
        }
        System.out.println("Goodbye");
    }



}