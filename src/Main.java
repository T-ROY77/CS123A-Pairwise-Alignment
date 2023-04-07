import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Map;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {
        //BLOSUMGrid proteinGrid = new BLOSUMGrid();
        Grid grid = new Grid();

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
            else{
                System.out.println("Sequence 1: " + seq1);
                System.out.println("Sequence 2: " + seq2);
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
                else if(inputNumber == 5) {
                    if(seq1.equals("") || seq2.equals("")){
                        System.out.println("Enter sequences first");
                    }
                    else {
                        grid.printArrowGrid();
                    }

                }
                else if(inputNumber == 6){
                    if(mode.equalsIgnoreCase("nucleotide")){
                        mode = "protein";
                    }
                    else{
                        mode = "nucleotide";
                    }
                    System.out.println("mode changed");
                    seq1 = "";
                    seq2 = "";
                }
                else if (inputNumber == 1) {
                    //take UI
                    System.out.print("Enter the first sequence: ");
                    String firstInput = input.nextLine();
                    System.out.print("Enter the second sequence: ");
                    String secondInput = input.nextLine();

                    //check that sequence is only letters
                    if(!firstInput.matches("[a-zA-Z]+") || !secondInput.matches("[a-zA-Z]+")){
                        System.out.println("Invalid sequence");
                    }
                    else {
                        //create the new grid
                        seq1 = firstInput.toLowerCase();
                        seq2 = secondInput.toLowerCase();
                        char[] seq1Array = seq1.toCharArray();
                        char[] seq2Array = seq2.toCharArray();

                        if(mode.equalsIgnoreCase("nucleotide")){
                            grid = new Grid(seq1Array, seq2Array);
                        }
                        else{
                            grid = new BLOSUMGrid(seq1Array, seq2Array);
                        }
                        System.out.println("\n");
                        System.out.println("Sequences saved");
                        System.out.println("\n");
                    }
                }
                else if(inputNumber == 2){

                    System.out.println("Enter filepath: ");
                    String filepath = input.nextLine();
                    try(BufferedReader read = new BufferedReader(new FileReader(filepath))){
                        String firstInput = read.readLine();
                        String secondInput = read.readLine();
                        if(!firstInput.matches("[a-zA-Z]+") || !secondInput.matches("[a-zA-Z]+")){
                            System.out.println("\n");
                            System.out.println("Invalid sequence");
                            System.out.println("\n");
                        }
                        else {
                            //create the new grid
                            seq1 = firstInput;
                            seq2 = secondInput;
                            char[] seq1Array = seq1.toCharArray();
                            char[] seq2Array = seq2.toCharArray();
                            if(mode.equalsIgnoreCase("nucleotide")){
                                grid = new Grid(seq1Array, seq2Array);
                            }
                            else{
                                grid = new BLOSUMGrid(seq1Array, seq2Array);
                            }
                            System.out.println("\n");
                            System.out.println("Sequences saved");
                            System.out.println("\n");
                        }
                    }
                    catch(Exception e){
                        System.out.println("\n");
                        System.out.println("Error reading filepath");

                    }
                }
                else if(inputNumber == 9){
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