package edu.ithaca.dturnbull.bank;

public class BankAccount {

    private String email;
    private double balance;

    /**
     * @throws IllegalArgumentException if email is invalid
     */
    public BankAccount(String email, double startingBalance){
        if (isEmailValid(email)){
            this.email = email;
            this.balance = startingBalance;
        }
        else {
            throw new IllegalArgumentException("Email address: " + email + " is invalid, cannot create account");
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
        if (amount <= balance){
            balance -= amount;
        }
        else {
            throw new InsufficientFundsException("Not enough money");
        }
    }


    public static boolean isEmailValid(String email){
        int atIndex = email.indexOf("@");
        int dotIndex = email.indexOf(".");
        int emailLength = email.length();
        String emailNoP = email.replace(".","");
        String emailNoAt = email.replace("@", "");

        if (atIndex == -1 || atIndex == 0 || atIndex == emailLength -1){
            return false;
        }
        else if (!(dotIndex > atIndex + 1)){
            return false;
        }
        else if (emailLength - emailNoP.length() != 1){
            return false;
        }
        else if (emailLength - emailNoAt.length() != 1){
            return false;
        } 
        else {
            return true;
        }
    }
}
