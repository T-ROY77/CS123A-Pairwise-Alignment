import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {
        Grid grid = new Grid();
        Scanner input = new Scanner(System.in);
        String seq1 = "";
        String seq2 = "";

        while (true) {

            System.out.println("\n");
            System.out.println("1: Calculate an Optimal Alignment");
            System.out.println("2: Print grid");
            System.out.println("3: Print arrow grid");
            System.out.println("4: Enter new query sequences");
            System.out.println("5: Enter new query sequences with filepath");
            System.out.println("6: quit");
            System.out.println("Sequence 1: " + seq1);
            System.out.println("Sequence 2: " + seq2);
            String choice = input.nextLine();

            try {
                int inputNumber = Integer.parseInt(choice);
                if (inputNumber == 1) {
                    grid.findAlignment();
                    grid.printAlignment();
                    System.out.println("\nMax score:");
                    System.out.println(grid.getMaxScore());
                }
                else if(inputNumber == 2){
                    grid.printGrid();
                }
                else if(inputNumber == 3) {
                    grid.printArrowGrid();

                }
                else if (inputNumber == 4) {
                    //take UI
                    System.out.print("Enter the first sequence: ");
                    String firstInput = input.nextLine();
                    seq1 = firstInput;
                    System.out.print("Enter the second sequence: ");
                    String secondInput = input.nextLine();
                    seq2 = secondInput;

                    //create the new grid
                    char[] seq1Array = seq1.toCharArray();
                    char[] seq2Array = seq2.toCharArray();
                    grid = new Grid(seq1Array, seq2Array);
                    System.out.println("\n");
                }
                else if(inputNumber == 5){

                    System.out.println("Enter filepath: ");
                    String filepath = input.nextLine();
                    try(BufferedReader read = new BufferedReader(new FileReader(filepath))){
                        String firstInput = read.readLine();
                        seq1 = firstInput;
                        String secondInput = read.readLine();
                        seq2 = secondInput;

                        //create the new grid
                        char[] seq1Array = seq1.toCharArray();
                        char[] seq2Array = seq2.toCharArray();
                        grid = new Grid(seq1Array, seq2Array);
                        System.out.println("\n");
                    }
                    catch(Exception e){
                        System.out.println("Error reading filepath");

                    }
                }
                else if(inputNumber == 6){
                    break;
                }
                else {
                    System.out.println("Invalid input.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
            }
        }
        System.out.println("Goodbye");
    }
}