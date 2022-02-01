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
        if(isAmountValid(amount)){ //if amount is valid
            if (amount <= balance){
                balance -= amount;
            }
            else {
                throw new InsufficientFundsException("Not enough money");
            }
        }
        else {
            throw new IllegalArgumentException("Withdraw amount invalid");
        }
    }

    public void deposit(double amount) throws IllegalArgumentException{
        if(isAmountValid(amount)){ //if amount is valid
            if(amount==0){
                throw new IllegalArgumentException("Cannot deposit zero");
            }
        }
        else {
            throw new IllegalArgumentException("Deposit amount invalid");
        }
    }

    
    public void transfer(String email, double amount) throws InsufficientFundsException{
        if(isAmountValid(amount)){ //if amount is valid
            if(isEmailValid(email)){
                if(amount<= balance){
                    balance-= amount;
                }
                throw new InsufficientFundsException("not enough money");
            }
            throw new IllegalArgumentException("Email invalid");
        }
        throw new IllegalArgumentException("Transfer amount invalid");
    }

    public static boolean isAmountValid(double amount){
        if(String.valueOf(amount) == (String.format("%.2f", amount))){ //if amount is two decimal places
            if(amount<0){ //if amount is negative
                return false;
            }
            return true;
        }
        return false;
    }


    /*
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
    */

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
        if (atCount != 1){
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
            if (emailChars[n] == '.' && ((n+3) > emailChars.length)){
                return false;
            }

            
        }
        if (pCount != 1){
            return false;
        }
        return true;
    }
}
