import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Registration extends User {

    public Registration(String username, String password) {
        super(username, password);
    }

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("Would you like to create a new account? (y/n)");
            String userChoice = scanner.nextLine();

            if (userChoice.equalsIgnoreCase("y")) {
                registerUser(scanner);
            } else if (userChoice.equalsIgnoreCase("n")) {
                // Redirect to ExpenseTracker
                Scanner expenseTrackerScanner = new Scanner(System.in);
                System.out.println("Enter your username: ");
                String username = expenseTrackerScanner.next();
                System.out.println("Enter your password: ");
                String password = expenseTrackerScanner.next();

                ExpenseTracker expenseTracker = new ExpenseTracker(username, password);
                expenseTracker.registerWithInitialBalance(); // Automatically register with initial balance
                expenseTracker.runExpenseTracker(expenseTrackerScanner);
            } else {
                System.out.println("Invalid choice. Exiting Registration. Goodbye!");
            }
        }
    }

    private static void registerUser(Scanner scanner) {
        System.out.println("Enter your Name: ");
        String name = scanner.nextLine();

        System.out.println("Enter username: ");
        String username = scanner.nextLine();

        if (userExists(username)) {
            System.out.println("Username already exists. Please create another.");
            return;
        }

        System.out.println("Enter password: ");
        String password = scanner.nextLine();

        String confirmPassword;
        do {
            System.out.println("Confirm your password: ");
            confirmPassword = scanner.nextLine();

            if (!password.equals(confirmPassword)) {
                System.out.println("Password does not match. Try again.");
            } else {
                System.out.println("Password matched.");
            }
        } while (!password.equals(confirmPassword));

        // Use a constant for the file path
        String filePath = "C:\\Users\\jseab\\Expense Tracker\\user.txt";
        try (FileWriter writer = new FileWriter(filePath, true)) {
            writer.write(name + "," + username + "," + password + "\n");
            System.out.println("Successfully Registered!");

            // Automatically register with initial balance
            ExpenseTracker expenseTracker = new ExpenseTracker(username, password);
            expenseTracker.registerWithInitialBalance();

            System.out.println("You can now log in with your new credentials.");
        } catch (IOException e) {
            System.out.println("Error writing to file: " + e.getMessage());
        }
    }

    private static boolean userExists(String username) {
        // Use a constant for the file path
        String filePath = "C:\\Users\\jseab\\Expense Tracker\\user.txt";
        try (Scanner fileScanner = new Scanner(new File(filePath))) {
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();
                String[] parts = line.split(",");
                if (parts.length >= 2 && parts[1].equals(username)) {
                    return true;
                }
            }
        } catch (FileNotFoundException ex) {
            System.out.println("Error reading from file: " + ex.getMessage());
        }
        return false;
    }
}
