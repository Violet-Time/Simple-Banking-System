package banking;

import java.util.Scanner;

public class View {

    Bank bank;

    Scanner scanner = new Scanner(System.in);

    boolean exit = true;

    public View(Bank bank) {
        this.bank = bank;
        mainMenu();
    }

    public void printCreateCard(String cardNumber, String password) {
        System.out.printf(
                "Your card has been created\n" +
                "Your card number:\n%s" + "\n" +
                "Your card PIN:\n%s" + "\n", cardNumber, password);
    }

    public void mainMenu() {
        User user;
        while (exit) {
            System.out.println(
                    "1. Create an account\n" +
                            "2. Log into account\n" +
                            "0. Exit\n");
            switch (scanner.nextInt()) {
                case 1 :
                    user = bank.createUser();
                    printCreateCard(user.getCardNumber(), user.getPin());
                    break;
                case 2 :
                    System.out.println("Enter your card number:");
                    scanner.nextLine();
                    String cardNumber = scanner.nextLine();
                    System.out.println("Enter your PIN:");
                    String password = scanner.nextLine();
                    user = bank.getUser(cardNumber, password);
                    if (user == null) {
                        System.out.println("\nWrong card number or PIN!\n");
                        break;
                    } else {
                        System.out.println("You have successfully logged in!\n");
                        loggedMenu(user);
                    }
                    break;
                case 0 :
                    exit = false;
                    break;
                default:
                    break;
            }
        }
    }

    public void loggedMenu(User user) {
        while (exit) {
            if (user == null) {
                break;
            }
            System.out.println(
                    "1. Balance\n" +
                    "2. Add income\n" +
                    "3. Do transfer\n" +
                    "4. Close account\n" +
                    "5. Log out\n" +
                    "0. Exit\n");
            switch (scanner.nextInt()) {
                case 1 :
                    System.out.println("Balance: " + user.getBalance());
                    break;
                case 2 :
                    System.out.println("Enter income:");
                    user = bank.addIncome(user,scanner.nextInt());
                    System.out.println("Income was added!");
                    break;
                case 3 :
                    System.out.println("Enter card number:");
                    scanner.nextLine();
                    String number = scanner.nextLine();
                    if (user.getCardNumber().equals(number)) {
                        System.out.println("You can't transfer money to the same account!");
                        break;
                    }
                    if (!bank.checkBankNumber(number)) {
                        System.out.println("Probably you made mistake in the card number. Please try again!");
                        break;
                    }
                    if (!bank.search(number)) {
                        System.out.println("Such a card does not exist.");
                        break;
                    }
                    System.out.println("Enter how much money you want to transfer:");
                    int money = scanner.nextInt();
                    if (money > user.getBalance()) {
                        System.out.println("Not enough money!");
                        break;
                    }
                    user = bank.transfer(user, number, money);
                    break;
                case 4 :
                    user = bank.deleteUser(user);
                    System.out.println("The account has been closed!");
                    break;
                case 5 :
                    user = null;
                    break;
                case 0 :
                    exit = false;
                    break;
                default:
                    break;
            }
        }
    }


}
