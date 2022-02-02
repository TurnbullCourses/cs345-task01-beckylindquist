package edu.ithaca.dturnbull.bank;

import java.math.BigDecimal;

public class BankAccount {

    private String email;
    private double balance;

    /**
     * @throws IllegalArgumentException if email is invalid
     */
    public BankAccount(String email, double startingBalance){
        if (!isAmountValid(startingBalance)){
            throw new IllegalArgumentException("Invalid starting balance");
        } else if (!isEmailValid(email)){
            throw new IllegalArgumentException("Invalid email");
        } else {
            this.email = email;
            this.balance = startingBalance;
        }
    }

    public double getBalance(){
        return balance;
    }

    public String getEmail(){
        return email;
    }

    /**
     * @post reduces the balance by amount if amount is non-negative and smaller than balance
     */
    public void withdraw (double amount) throws InsufficientFundsException{
        if (!isAmountValid(amount)){
            throw new IllegalArgumentException("Withdraw amount invalid");
        } else if (amount > balance) {
            throw new InsufficientFundsException("Not enough money");
        } else {
            balance -= amount;
        }
    }

    public void deposit(double amount) throws IllegalArgumentException{
        if(isAmountValid(amount)){ //if amount is valid
            if(amount==0){
                throw new IllegalArgumentException("Cannot deposit zero");
            }
            balance += amount;
        }
        else {
            throw new IllegalArgumentException("Deposit amount invalid");
        }
    }

    public static boolean isEmailValid(String email){
        char[] emailChars = email.toCharArray();
        int atCount = 0;
        int atIndex = 0;
        int pCount = 0;
        //confirm that email contains one @ and stores its index
        for (int i = 0; i < emailChars.length; i++){
            if (emailChars[i] == '@'){
                atCount += 1;
                atIndex = i;    
                
            }
        }
        if (atCount != 1 || atIndex == 0){
            return false;
        }

        //checks prefix for concurrent .,_,- or .,_,- at the front or start and special characters(currently just #) 
        for (int j = 0; j < atIndex; j++){
            if ( (emailChars[j] == '_' || emailChars[j] == '-' || emailChars[j] == '.') && (emailChars[j+1] == '.' || emailChars[j+1] =='-' || emailChars[j+1] =='_' || emailChars[j+1] == '@')){
                return false;
            } else if ((j == 0 || (j+1) == atIndex) && (emailChars[j] == '_' || emailChars[j] == '-' || emailChars[j] == '.')){
                return false;
            } else if (emailChars[j] == '#'){ //ADD MORE
                return false;
            }
        }


        for (int n=atIndex; n < emailChars.length; n++){
            if (emailChars[n] == '.'){
                pCount += 1;
            } 
            if (emailChars[n] == '.' && ( ( (n+3) > emailChars.length ) || (n < (atIndex+2) ))){
                return false;
            }

            
        }
        if (pCount != 1){
            return false;
        }

        return true;
    }

    public static boolean isAmountValid(double amount){
        String amountString = Double.toString(amount);
        BigDecimal num = new BigDecimal(amountString);
        if (amount < 0){
            return false;
        } else if (num.scale() > 2){
            return false;
        } else {
            return true;
        }
    }


}
