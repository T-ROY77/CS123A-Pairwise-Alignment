import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {
        Grid grid = new Grid();
        Scanner input = new Scanner(System.in);
        String seq1 = "";
        String seq2 = "";
        boolean stop = false;



        while (true) {
            char[] seq1Array = seq1.toCharArray();
            char[] seq2Array = seq2.toCharArray();
            grid = new Grid(seq1Array, seq2Array);
            System.out.println("\n");


            System.out.println("Sequence 1: " + seq1);
            System.out.println("Sequence 2: " + seq2);
            System.out.println("1: Calculate an Optimal Alignment");
            System.out.println("2: Print grid");
            System.out.println("3: Print arrow grid");
            System.out.println("4: Enter new query sequences");
            System.out.println("5: quit");
            String choice = input.nextLine();

            try {
                int inputNumber = Integer.parseInt(choice);
                if (inputNumber == 1) {
                    grid.findAlignment();
                    grid.printAlignment();
                    System.out.println("\nMax score:");
                    System.out.println(grid.getMaxScore());
                    inputNumber = 0;

                }
                else if(inputNumber == 2){
                    grid.printGrid();
                    inputNumber = 0;
                }
                else if(inputNumber == 3) {
                    grid.printArrowGrid();
                    inputNumber = 0;

                }
                else if (inputNumber == 4) {

                        System.out.print("Enter the first value: ");
                        String firstInput = input.nextLine();
                        seq1 = firstInput;
                        System.out.print("Enter the second value: ");
                        String secondInput = input.nextLine();
                        seq2 = secondInput;

                }
                else if(inputNumber == 5){
                    break;
                }
                else {
                    System.out.println("Invalid input.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
            }
        }
            System.out.println("Program stopped by user.");
    }


}