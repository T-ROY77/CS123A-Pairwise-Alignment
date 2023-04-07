import java.util.Scanner;

public class userLoopExample {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.print("Enter a number (1 or 2, or type 'quit' to exit): ");
            String input = scanner.nextLine();

            if (input.equalsIgnoreCase("quit")) {
                break;
            }

            try {
                int inputNumber = Integer.parseInt(input);
                if (inputNumber == 2) {
                    while (true) {
                        System.out.print("Enter the first value (or type 'quit' to exit): ");
                        String firstInput = scanner.nextLine();

                        if (firstInput.equalsIgnoreCase("quit")) {
                            break;
                        }

                        System.out.print("Enter the second value: ");
                        String secondInput = scanner.nextLine();

                        System.out.println("First input: " + firstInput);
                        System.out.println("Second input: " + secondInput);
                    }
                } else if (inputNumber == 1) {
                    // Do nothing
                } else {
                    System.out.println("Invalid input. Please enter 1 or 2.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number or 'quit'.");
            }
        }

        System.out.println("Program stopped by user.");
    }

}