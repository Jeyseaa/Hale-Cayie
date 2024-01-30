import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class User {
    private String username;
    private String password;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public static boolean userLogin(Scanner scanner, String enteredUsername, String enteredPassword) {
        try (Scanner fileScanner = new Scanner(new File("C:\\Users\\jseab\\Expense Tracker\\user.txt"))) {
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();
                String[] parts = line.split(",");
                if (parts.length == 3 && parts[1].equals(enteredUsername) && parts[2].equals(enteredPassword)) {
                    System.out.println("Successfully logged in.");
                    return true;
                }
            }
        } catch (FileNotFoundException ex) {
            System.out.println("Error reading from file: " + ex.getMessage());
        }
        System.out.println("Login failed. Please try again.");
        return false;
    }
}
