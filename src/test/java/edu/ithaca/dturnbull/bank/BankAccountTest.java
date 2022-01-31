package edu.ithaca.dturnbull.bank;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


class BankAccountTest {

    @Test
    void getBalanceTest() {
        BankAccount bankAccount = new BankAccount("a@b.com", 200);
        assertEquals(200, bankAccount.getBalance(), 0.001);
    }

    @Test
    void depositTest()  throws InsufficientFundsException{
        BankAccount bankAccount = new BankAccount("a@b.com", 200);
        
        bankAccount.deposit(100); //deposit some money
        assertEquals(300, bankAccount.getBalance());

        bankAccount.deposit(100.001); //deposit 3 decimals
        assertThrows(IllegalArgumentException.class, ()->  bankAccount.deposit(100.001));  //should remain unchanged/not work

        bankAccount.deposit(0); //deposit zero
        assertThrows(IllegalArgumentException.class, ()->  bankAccount.deposit(0));
    }
    @Test
    void withdrawTest() throws InsufficientFundsException{
        BankAccount bankAccount = new BankAccount("a@b.com", 200); 
        
        bankAccount.withdraw(100);//withdraw some money
        assertEquals(100, bankAccount.getBalance());

        bankAccount.withdraw(100.001); //withdraw 3 decimals
        assertThrows(IllegalArgumentException.class, ()->  bankAccount.withdraw(100.001)); //should remain unchanged/not work

        bankAccount.withdraw(0); //withdraw zero money
        assertEquals(100, bankAccount.getBalance());

        bankAccount.withdraw(200); //withdraw all funds
        assertEquals(0, bankAccount.getBalance(), 0.001);

        assertEquals(100, bankAccount.getBalance(), 0.001); //withdraw more than is in account
        assertThrows(InsufficientFundsException.class, () -> bankAccount.withdraw(300));
        
        assertEquals(-1, bankAccount.getBalance(), 0.001); //withdraw negative money
        assertThrows(NegativeWithdrawException.class, () -> bankAccount.withdraw(-1)); 
    }

    @Test
    void isEmailValidTest(){
        assertTrue(BankAccount.isEmailValid( "a@b.com")); //valid email
        assertFalse(BankAccount.isEmailValid("")); //no email

        //Prefix Tests
        assertTrue(BankAccount.isEmailValid("a.b@c.com")); //two nonconjunctive periods allowed
        assertFalse(BankAccount.isEmailValid(".a@b.com")); //no prefix startiing with . why does this work with a peroid but not a _??
        assertFalse(BankAccount.isEmailValid("-a@b.com"));
        assertFalse(BankAccount.isEmailValid("abc.@b.com")); //no prefix ending with underscore
        assertFalse(BankAccount.isEmailValid("a#b@c.com")); //no special chars
        
        //Domain Tests
        assertFalse(BankAccount.isEmailValid("@b.com")); //no prefix
        assertFalse(BankAccount.isEmailValid("a@b")); //no domain end
        assertFalse(BankAccount.isEmailValid("abc@zyx.c")); //no domain with only 1 character
        assertFalse(BankAccount.isEmailValid("a@@b.com")); //no two @s
        assertFalse(BankAccount.isEmailValid("a@b.c.om")); //cannot have more than one peroid in email (according to wikipedia)
        assertTrue(BankAccount.isEmailValid("a@b.c#om")); //no special chars
    }

    @Test
    void constructorTest() {
        BankAccount bankAccount = new BankAccount("a@b.com", 200);

        assertEquals("a@b.com", bankAccount.getEmail());
        assertEquals(200, bankAccount.getBalance(), 0.001);
        //check for exception thrown correctly
        assertThrows(IllegalArgumentException.class, ()-> new BankAccount("", 100));
    }

}