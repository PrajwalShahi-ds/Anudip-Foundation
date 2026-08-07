package com.bank;

import com.bank.entity.Account;
import com.bank.entity.AccountType;
import com.bank.entity.Transaction;
import com.bank.entity.User;
import com.bank.service.BankingService;
import com.bank.service.UserService;
import com.bank.util.JPAUtil;

import java.math.BigDecimal;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

public class Main {

    private static final UserService userService = new UserService();
    private static final BankingService bankingService = new BankingService();
    private static final Scanner scanner = new Scanner(System.in);
    private static User currentUser = null;

    public static void main(String[] args) {
        System.out.println("=================================================");
        System.out.println("       WELCOME TO ONLINE BANKING SYSTEM          ");
        System.out.println("   (Powered by Java, JPA / Hibernate & MySQL)    ");
        System.out.println("=================================================");

        boolean running = true;
        while (running) {
            if (currentUser == null) {
                running = showMainMenu();
            } else {
                running = showUserDashboard();
            }
        }

        JPAUtil.close();
        System.out.println("\nThank you for using Online Banking System. Goodbye!");
    }

    private static boolean showMainMenu() {
        System.out.println("\n-------------------------------------------------");
        System.out.println("MAIN MENU:");
        System.out.println("1. User Registration");
        System.out.println("2. Login");
        System.out.println("3. Exit");
        System.out.print("Choose an option (1-3): ");

        String choice = scanner.nextLine().trim();
        switch (choice) {
            case "1":
                handleRegistration();
                break;
            case "2":
                handleLogin();
                break;
            case "3":
                return false;
            default:
                System.out.println("❌ Invalid option. Please enter 1, 2, or 3.");
        }
        return true;
    }

    private static boolean showUserDashboard() {
        System.out.println("\n=================================================");
        System.out.println(" USER DASHBOARD - Welcome, " + currentUser.getFullName() + " (@" + currentUser.getUsername() + ")");
        System.out.println("=================================================");
        System.out.println("1. View My Accounts & Balances");
        System.out.println("2. Open New Bank Account");
        System.out.println("3. Deposit Funds");
        System.out.println("4. Withdraw Funds");
        System.out.println("5. Transfer Money");
        System.out.println("6. Transaction History");
        System.out.println("7. Logout");
        System.out.print("Choose an option (1-7): ");

        String choice = scanner.nextLine().trim();
        switch (choice) {
            case "1":
                displayUserAccounts();
                break;
            case "2":
                handleCreateAccount();
                break;
            case "3":
                handleDeposit();
                break;
            case "4":
                handleWithdrawal();
                break;
            case "5":
                handleTransfer();
                break;
            case "6":
                handleTransactionHistory();
                break;
            case "7":
                System.out.println("Logged out successfully.");
                currentUser = null;
                break;
            default:
                System.out.println("❌ Invalid option. Please enter 1-7.");
        }
        return true;
    }

    private static void handleRegistration() {
        System.out.println("\n--- USER REGISTRATION ---");
        System.out.print("Enter Username: ");
        String username = scanner.nextLine().trim();

        System.out.print("Enter Password: ");
        String password = scanner.nextLine().trim();

        System.out.print("Enter Full Name: ");
        String fullName = scanner.nextLine().trim();

        System.out.print("Enter Email Address: ");
        String email = scanner.nextLine().trim();

        if (username.isEmpty() || password.isEmpty() || fullName.isEmpty() || email.isEmpty()) {
            System.out.println("❌ All fields are required.");
            return;
        }

        try {
            User user = userService.registerUser(username, password, fullName, email);
            System.out.println("✅ User registered successfully! User ID: " + user.getId());
            System.out.println("You can now login with your credentials.");
        } catch (Exception e) {
            System.out.println("❌ Registration failed: " + e.getMessage());
        }
    }

    private static void handleLogin() {
        System.out.println("\n--- USER LOGIN ---");
        System.out.print("Enter Username: ");
        String username = scanner.nextLine().trim();

        System.out.print("Enter Password: ");
        String password = scanner.nextLine().trim();

        try {
            currentUser = userService.loginUser(username, password);
            System.out.println("✅ Login successful! Welcome back, " + currentUser.getFullName());
        } catch (Exception e) {
            System.out.println("❌ Login failed: " + e.getMessage());
        }
    }

    private static void displayUserAccounts() {
        System.out.println("\n--- MY ACCOUNTS ---");
        try {
            List<Account> accounts = bankingService.getUserAccounts(currentUser.getId());
            if (accounts.isEmpty()) {
                System.out.println("ℹ️ You don't have any open bank accounts yet. Choose option 2 to open one.");
                return;
            }

            System.out.printf("%-18s %-12s %-15s %-20s\n", "Account Number", "Type", "Balance ($)", "Created At");
            System.out.println("------------------------------------------------------------------");
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            for (Account acc : accounts) {
                System.out.printf("%-18s %-12s %-15.2f %-20s\n",
                        acc.getAccountNumber(),
                        acc.getAccountType(),
                        acc.getBalance(),
                        acc.getCreatedAt().format(formatter));
            }
        } catch (Exception e) {
            System.out.println("❌ Error fetching accounts: " + e.getMessage());
        }
    }

    private static void handleCreateAccount() {
        System.out.println("\n--- OPEN NEW BANK ACCOUNT ---");
        System.out.println("Select Account Type:");
        System.out.println("1. SAVINGS");
        System.out.println("2. CHECKING");
        System.out.print("Choice (1-2): ");
        String typeChoice = scanner.nextLine().trim();

        AccountType type;
        if ("1".equals(typeChoice)) {
            type = AccountType.SAVINGS;
        } else if ("2".equals(typeChoice)) {
            type = AccountType.CHECKING;
        } else {
            System.out.println("❌ Invalid account type selection.");
            return;
        }

        System.out.print("Enter Initial Deposit Amount ($): ");
        String amountStr = scanner.nextLine().trim();
        try {
            BigDecimal initialDeposit = new BigDecimal(amountStr);
            if (initialDeposit.compareTo(BigDecimal.ZERO) < 0) {
                System.out.println("❌ Initial deposit cannot be negative.");
                return;
            }

            Account newAcc = bankingService.createAccount(currentUser.getId(), type, initialDeposit);
            System.out.println("✅ Bank Account Created Successfully!");
            System.out.println("   Account Number: " + newAcc.getAccountNumber());
            System.out.println("   Account Type:   " + newAcc.getAccountType());
            System.out.println("   Balance:        $" + newAcc.getBalance());
        } catch (NumberFormatException e) {
            System.out.println("❌ Invalid amount format.");
        } catch (Exception e) {
            System.out.println("❌ Account creation failed: " + e.getMessage());
        }
    }

    private static void handleDeposit() {
        System.out.println("\n--- DEPOSIT FUNDS ---");
        System.out.print("Enter Account Number: ");
        String accNum = scanner.nextLine().trim();

        System.out.print("Enter Deposit Amount ($): ");
        String amountStr = scanner.nextLine().trim();

        try {
            BigDecimal amount = new BigDecimal(amountStr);
            bankingService.deposit(accNum, amount);
            System.out.println("✅ Successfully deposited $" + amount + " into " + accNum);
        } catch (NumberFormatException e) {
            System.out.println("❌ Invalid amount format.");
        } catch (Exception e) {
            System.out.println("❌ Deposit failed: " + e.getMessage());
        }
    }

    private static void handleWithdrawal() {
        System.out.println("\n--- WITHDRAW FUNDS ---");
        System.out.print("Enter Account Number: ");
        String accNum = scanner.nextLine().trim();

        System.out.print("Enter Withdrawal Amount ($): ");
        String amountStr = scanner.nextLine().trim();

        try {
            BigDecimal amount = new BigDecimal(amountStr);
            bankingService.withdraw(accNum, amount);
            System.out.println("✅ Successfully withdrew $" + amount + " from " + accNum);
        } catch (NumberFormatException e) {
            System.out.println("❌ Invalid amount format.");
        } catch (Exception e) {
            System.out.println("❌ Withdrawal failed: " + e.getMessage());
        }
    }

    private static void handleTransfer() {
        System.out.println("\n--- TRANSFER MONEY ---");
        System.out.print("Enter Your (Sender) Account Number: ");
        String senderAcc = scanner.nextLine().trim();

        System.out.print("Enter Recipient Account Number: ");
        String recipientAcc = scanner.nextLine().trim();

        System.out.print("Enter Transfer Amount ($): ");
        String amountStr = scanner.nextLine().trim();

        try {
            BigDecimal amount = new BigDecimal(amountStr);
            bankingService.transferMoney(senderAcc, recipientAcc, amount);
            System.out.println("✅ Successfully transferred $" + amount + " from " + senderAcc + " to " + recipientAcc);
        } catch (NumberFormatException e) {
            System.out.println("❌ Invalid amount format.");
        } catch (Exception e) {
            System.out.println("❌ Transfer failed: " + e.getMessage());
        }
    }

    private static void handleTransactionHistory() {
        System.out.println("\n--- TRANSACTION HISTORY ---");
        System.out.print("Enter Account Number: ");
        String accNum = scanner.nextLine().trim();

        try {
            List<Transaction> transactions = bankingService.getAccountTransactions(accNum);
            if (transactions.isEmpty()) {
                System.out.println("ℹ️ No transactions found for account " + accNum);
                return;
            }

            System.out.printf("%-6s %-15s %-12s %-25s %-20s %-20s\n", "ID", "Type", "Amount ($)", "Timestamp", "Related Account", "Description");
            System.out.println("------------------------------------------------------------------------------------------------------");
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            for (Transaction tx : transactions) {
                System.out.printf("%-6d %-15s %-12.2f %-25s %-20s %-20s\n",
                        tx.getId(),
                        tx.getTransactionType(),
                        tx.getAmount(),
                        tx.getTimestamp().format(formatter),
                        tx.getRelatedAccountNumber() != null ? tx.getRelatedAccountNumber() : "-",
                        tx.getDescription());
            }
        } catch (Exception e) {
            System.out.println("❌ Error fetching transaction history: " + e.getMessage());
        }
    }
}
