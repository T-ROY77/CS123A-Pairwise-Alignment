import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {
        MatrixGrid grid = new MatrixGrid();

        Scanner input = new Scanner(System.in);
        String seq1 = "";
        String seq2 = "";


        String mode = "nucleotide";

        while (true) {

            System.out.println("\n");
            System.out.println("1: Enter new query sequences");
            System.out.println("2: Enter new query sequences with filepath");
            System.out.println("3: Calculate an Optimal Alignment");
            System.out.println("4: Print grid");
            System.out.println("5: Print arrow grid");
            System.out.println("6: Change mode (" + mode + " mode)");
            System.out.println("9: quit");
            if(seq1.equals("") || seq2.equals("")){
                System.out.println("Sequence 1: (Enter sequence)");
                System.out.println("Sequence 2: (Enter sequence)");
            }
            else if(grid.sequence1Name.equals("") || grid.sequence2Name.equals("")){
                System.out.println("Sequence 1: " + seq1);
                System.out.println("Sequence 2: " + seq2);
            }
            else{
                System.out.println(" " + grid.sequence1Name + " " + seq1);
                System.out.println(" " + grid.sequence2Name + " " + seq2);
            }
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
                        if(mode.equalsIgnoreCase("nucleotide")){
                            grid.useMatrix = false;
                        }
                        else{
                            grid.useMatrix = true;
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
                    if(mode.equalsIgnoreCase("nucleotide")){
                        mode = "protein";
                    }
                    else{
                        mode = "nucleotide";
                    }
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

                    //check that sequence is only letters excluding o u and j (no amino acid)
                    if(!firstInput.matches("[a-ik-np-tv-z]+") || !secondInput.matches("[a-ik-np-tv-z]+") ){
                        System.out.println("Invalid sequence");
                    }
                    else {
                        //create the new grid
                        seq1 = firstInput.toLowerCase();
                        seq2 = secondInput.toLowerCase();
                        char[] seq1Array = seq1.toCharArray();
                        char[] seq2Array = seq2.toCharArray();
                        grid = new MatrixGrid(seq1Array, seq2Array);

                        System.out.println();
                        System.out.println("Sequences saved");
                    }
                }

                //file path input
                else if(inputNumber == 2){
                    System.out.println("Enter filepath: ");
                    String filepath = input.nextLine();
                    try(BufferedReader read = new BufferedReader(new FileReader(filepath))){
                        //read input from file
                        //**************************
                        String seq1Name = read.readLine();
                        String firstInput = read.readLine();
                        String seq2Name = read.readLine();
                        String secondInput = read.readLine();
                        firstInput = firstInput.toLowerCase();
                        secondInput = secondInput.toLowerCase();

                        //check that sequence is only letters excluding o u and j (no amino acid)
                        if(!firstInput.matches("[a-ik-np-tv-z]+") || !secondInput.matches("[a-ik-np-tv-z]+") ){
                            System.out.println("Invalid sequence");
                        }
                        else {
                            //create the new grid
                            seq1 = firstInput;
                            seq2 = secondInput;
                            char[] seq1Array = seq1.toCharArray();
                            char[] seq2Array = seq2.toCharArray();
                            grid = new MatrixGrid(seq1Array, seq2Array);
                            grid.setNames(seq1Name, seq2Name);

                            System.out.println("\n");
                            System.out.println("Sequences saved");
                        }
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