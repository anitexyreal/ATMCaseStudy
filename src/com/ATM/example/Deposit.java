package com.ATM.example;

public class Deposit extends Transaction {
    private double amount;
    private Keypad keypad;
    private DepositSlot depositSlot;
    private final static int CANCELED = 0;
    public Deposit(int userAccountNumber, Screen atmScreen, BankDatabase atmBankDatabase, Keypad atmKeypad, DepositSlot atmDepositSlot) {
        super(userAccountNumber, atmScreen, atmBankDatabase);
        keypad = atmKeypad;
        depositSlot = atmDepositSlot;
    }
    @Override
    public void execute(){
        BankDatabase bankDatabase = getBankDatabase();
        Screen screen = getScreen();
        amount = promptForDepositAmount();
        if (amount != CANCELED){
            screen.displayMessage("\nPlease insert a deposit envelope containing fund.");
            screen.displayDollarAmount(amount);
            screen.displayMessageLine(".");
            boolean envelopeReceived = depositSlot.isEnvelopeReceived();
            if (envelopeReceived){
                screen.displayMessageLine("\nYour envelope has been received" + "received.\nNote: The money will not " + "be available until we verify the amount of any" + "enclosed envelope and your checks clear.");

                bankDatabase.credit(getAccountNumber(), amount);
            }
            else {
                screen.displayMessageLine("\nYou did not insert an " + "Envelope, so the ATM has cancelled your transaction.");
            }
        }
        else {
            screen.displayMessageLine("\nCanceling transaction...");
        }

    }
    private double promptForDepositAmount(){
        Screen screen = getScreen();
        screen.displayMessage("\nPlease Enter a deposit amount in " + "CENTS (or 0 to cancel):");

        int input = keypad.getInput();
        if (input == CANCELED)
            return CANCELED;
        else {
            return (double) input / 100;
        }


    }
}
