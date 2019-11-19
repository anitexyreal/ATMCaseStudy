package com.ATM.example;

public class ATM {

    private boolean userAuthenticated; // whether user is authenticated
    private int currentAccountNumber; // current user's account number
    private Screen screen; // ATM screen
    private Keypad keypad; // ATM keypad
    private CashDispenser cashDispenser; // ATM cash Dispenser
    private DepositSlot depositSlot; // ATM Deposit Slot
    private BankDatabase bankDatabase; //info from Bank account

    //constants corresponding to main menu options
    private final int BALANCE_INQUIRY = 1;
    private final int WITHDRAWAL = 2;
    private final int DEPOSIT = 3;
    private final int EXIT = 4;

    // No-argument ATM constructors initializes instance variables
    public ATM() {
        userAuthenticated = false; // user is not yet authenticated
        currentAccountNumber = 0; // no current account number
        screen = new Screen(); // create ATM screen
        keypad = new Keypad(); // create keypad
        cashDispenser = new CashDispenser(); // create ATM cash Dispenser
        depositSlot = new DepositSlot(); // create ATM Deposit Slot
        bankDatabase = new BankDatabase(); // create account info database

    }// end no-argument ATM constructor
    public void run(){
        while (true){
            while (!userAuthenticated){
                screen.displayMessageLine("\nWelcome!");
                authenticateUer();
            }
            performTransaction();
            userAuthenticated = false;
            currentAccountNumber = 0;
            screen.displayMessageLine("\nThankyou! Goodbye!");
        }

    }
    private void authenticateUer(){
        screen.displayMessage("\nEnter your Account Number:");
        int accountNumber = keypad.getInput();
        screen.displayMessage("\nPlease Enter Your PIN: ");
        int pin = keypad.getInput();

        // set userAuthenticted to boolean value return by bankDatabase
        userAuthenticated = bankDatabase.authenticateUser(accountNumber, pin);

        if (userAuthenticated){
            currentAccountNumber = accountNumber;
        }
        else
            screen.displayMessageLine("Invalid account number or PIN. Please try again.");
    }// end method authenticateUser
    private void performTransaction(){
        Transaction currentTransaction = null;
        boolean userExited = false;
        // loop while user has not yet chosen to exit
        while (!userExited){
            int mainMenuSelection = displayMainMenu();
            switch (mainMenuSelection){
                case BALANCE_INQUIRY:
                case WITHDRAWAL:
                case DEPOSIT:
                    currentTransaction = createTransaction(mainMenuSelection);
                    currentTransaction.execute();
                    break;
                case EXIT:
                    screen.displayMessageLine("\nExiting the System...");
                    userExited = true;
                    break;
                default:
                    screen.displayMessageLine("\nYou did not enter a valid selection. try again.");
                    break;
            }
        }
    }
    private int displayMainMenu(){
        screen.displayMessageLine("\nMain menu:");
        screen.displayMessageLine("1 - View my balance");
        screen.displayMessageLine("2 - Withdraw Cash");
        screen.displayMessageLine("3 - Deposit funds");
        screen.displayMessageLine("4 - Exit\n");
        screen.displayMessageLine("Enter a choice");
        return keypad.getInput();
    }
    private Transaction createTransaction(int type){
        Transaction temp = null;
        switch (type){
            case BALANCE_INQUIRY:
                temp = new BalanceInquiry(currentAccountNumber, screen, bankDatabase);
                break;
            case WITHDRAWAL:
                temp = new Withdrawal(currentAccountNumber, screen, bankDatabase, keypad, cashDispenser);
                break;
            case DEPOSIT:
                temp = new Deposit(currentAccountNumber, screen, bankDatabase, keypad, depositSlot);

        }
        return temp;
    }


}
