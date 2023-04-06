import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {

//        Scanner input = new Scanner(System.in);
//
//        System.out.println("Input sequence 1: ");
//        String seq1 = input.nextLine();
//        System.out.println("Input sequence 2: ");
//        String seq2 = input.nextLine();
//
//        System.out.println(seq1);
        //take ui
        //

        String sequence1 = "hytdcdcdchtttk";
        String sequence2 = "ioacddtfdsfsttp";

        char[] seq1Array = sequence1.toCharArray();
        char[] seq2Array = sequence2.toCharArray();

        Grid grid = new Grid(seq1Array, seq2Array);
        System.out.println("\n");


        grid.findAlignment();

        //print statements
        grid.printGrid();
        System.out.println("\n");
        grid.printArrowGrid();
        System.out.println("\n");
        grid.printAlignment();
        System.out.println("\nMax score:");
        System.out.println(grid.getMaxScore());

    }
}