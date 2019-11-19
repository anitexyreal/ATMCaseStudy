package com.ATM.example;

public class CashDispenser {
    private final static int INITIAL_COUNT = 500;
    private int count;
    public CashDispenser(){
        count = INITIAL_COUNT;
    }
    public void dispenseCash(int amount){
        int billsRequired = amount / 20;
        count -= billsRequired;
    }
    public boolean isSufficientCashAvailable(int amount){
        int billRequired = amount / 20;
        if (count >= billRequired)
            return true;
        else
            return false;
    }
}
