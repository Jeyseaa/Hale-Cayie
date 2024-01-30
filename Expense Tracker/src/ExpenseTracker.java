import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class ExpenseTracker extends User {
    private List<Transaction> history = new ArrayList<>();
    private double balance = 0;
    private double totalAmount = 0;

    public ExpenseTracker(String username, String password) {
        super(username, password);
    }

    public void runExpenseTracker(Scanner scanner) {
        boolean validLogin = User.userLogin(scanner, getUsername(), getPassword());
        if (validLogin) {
            registerWithInitialBalance(); // Ensure initial balance for existing users
            displayMenu(scanner);
        }
    }

    private void displayMenu(Scanner scanner) {
        int choice;
        do {
            System.out.println("\n** Expense Tracker Menu **");
            System.out.println("1. Add Transaction");
            System.out.println("2. View History");
            System.out.println("3. Exit");
            System.out.println("Enter your choice: ");
            choice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character

            switch (choice) {
                case 1:
                    addTransaction(scanner);
                    break;
                case 2:
                    showHistory();
                    break;
                case 3:
                    System.out.println("Exiting Expense Tracker. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 3);
    }

    private void addTransaction(Scanner scanner) {
        System.out.println("\n** Add Transaction **");
        Date date = new Date(); // Automatic date

        System.out.println("1. Expense");
        System.out.println("2. Income");
        System.out.println("Select transaction type (1/2): ");
        int transactionType = scanner.nextInt();
        scanner.nextLine();

        String category = selectCategory(scanner, transactionType);

        System.out.println("Enter amount: ");
        String amount = scanner.nextLine();

        double amountValue = Double.parseDouble(amount);

        if (transactionType == 1) {
            // Expense
            balance -= amountValue;
        } else {
            // Income
            balance += amountValue;
        }

        Transaction transaction = new Transaction(date, category, amount, transactionType, balance);
        history.add(transaction);

        System.out.println("Transaction added successfully.");
    }

    private String selectCategory(Scanner scanner, int transactionType) {
        String categoryType = (transactionType == 1) ? "Expense" : "Income";
        System.out.println("\n** Select " + categoryType + " Category **");
        System.out.println("1. Food");
        System.out.println("2. Travel");
        System.out.println("3. Entertainment");
        System.out.println("4. Groceries");
        System.out.println("5. Others");
        System.out.println("Enter category number (1-5): ");
        int categoryNumber = scanner.nextInt();
        scanner.nextLine();

        switch (categoryNumber) {
            case 1:
                return "Food";
            case 2:
                return "Travel";
            case 3:
                return "Entertainment";
            case 4:
                return "Groceries";
            case 5:
                return "Others";
            default:
                System.out.println("Invalid category. Defaulting to 'Others'.");
                return "Others";
        }
    }

    private void showHistory() {
        System.out.println("\n** Transaction History **");
        if (history.isEmpty()) {
            System.out.println("No transactions available.");
        } else {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            System.out.println("Date\t\tType\tCategory\tAmount\tBalance\tTotal Amount");

            double totalAmount = balance; // Initialize totalAmount with the initial balance

            for (Transaction transaction : history) {
                if (transaction.getTransactionType() == 1) {
                    // Expense
                    totalAmount -= transaction.getAmountValue(); // Subtract expense amount from totalAmount
                } else {
                    // Income
                    totalAmount += transaction.getAmountValue(); // Add income amount to totalAmount
                }

                System.out.println(
                        dateFormat.format(transaction.getDate()) + "\t" +
                                (transaction.getTransactionType() == 1 ? "Expense" : "Income") + "\t" + "\t" +
                        transaction.getCategory() + "\t" +
                                String.format("%.2f", Double.parseDouble(transaction.getAmount())) + "\t" +
                                String.format("%.2f", transaction.getBalance()) + "\t" +
                                String.format("%.2f", totalAmount)
                );
            }
        }
    }

    private static class Transaction {
        private Date date;
        private String category;
        private String amount;
        private int transactionType;
        private double balance;

        public Transaction(Date date, String category, String amount, int transactionType, double balance) {
            this.date = date;
            this.category = category;
            this.amount = amount;
            this.transactionType = transactionType;
            this.balance = balance;
        }

        public Date getDate() {
            return date;
        }

        public String getCategory() {
            return category;
        }

        public String getAmount() {
            return amount;
        }

        public int getTransactionType() {
            return transactionType;
        }

        public double getBalance() {
            return balance;
        }

        public double getAmountValue() {
            return Double.parseDouble(amount);
        }
    }

    void registerWithInitialBalance() {
        // Set initial balance to 500 for new users
        if (balance == 0) {
            this.balance = 500;
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter your username: ");
        String username = scanner.next();
        System.out.println("Enter your password: ");
        String password = scanner.next();

        ExpenseTracker expenseTracker = new ExpenseTracker(username, password);
        expenseTracker.runExpenseTracker(scanner);
    }
}
