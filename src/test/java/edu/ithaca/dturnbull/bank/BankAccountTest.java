package edu.ithaca.dturnbull.bank;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


class BankAccountTest {

    @Test
    void getBalanceTest() {
        BankAccount bankAccount = new BankAccount("a@b.com", 200);
        assertEquals(200, bankAccount.getBalance(), 0.001);

        BankAccount bankAccountZero = new BankAccount("b@c.com", 0);
        assertEquals (0, bankAccountZero.getBalance(), 0.001);
    }

    @Test
    void withdrawTest() throws InsufficientFundsException{
        BankAccount bankAccount = new BankAccount("a@b.com", 200); //withdraw some money
        bankAccount.withdraw(100);

        assertEquals(100, bankAccount.getBalance(), 0.001);
        assertThrows(InsufficientFundsException.class, () -> bankAccount.withdraw(300)); //withdrawing more than is in account

        bankAccount.withdraw(100); //withdraw all funds
        assertEquals(0, bankAccount.getBalance(), 0.001);

        bankAccount.withdraw(0); //withdraw zero funds
        assertEquals(0, bankAccount.getBalance(), 0.001);

        bankAccount.withdraw(-1); //withdraw negative funds
        assertThrows(WithdrawNegativeFundsException.class, () -> bankAccount.withdraw(-1)); //throws new exception

    }

    @Test
    void isEmailValidTest(){
        assertTrue(BankAccount.isEmailValid( "a@b.com")); //valid email
        assertFalse(BankAccount.isEmailValid("")); //no email

        //Prefix Tests
        assertTrue(BankAccount.isEmailValid("a.b@c.com")); //two nonconjunctive periods allowed
        assertFalse(BankAccount.isEmailValid(".a@b.com")); //no prefix startiing with .
        assertFalse(BankAccount.isEmailValid("abc_@b.com")); //no prefix ending with underscore
        assertFalse(BankAccount.isEmailValid("a#b@c.com")); //no special chars

        //Domain Tests
        assertFalse(BankAccount.isEmailValid("b.com")); //just domain
        assertFalse(BankAccount.isEmailValid("a@b")); //no domain end
        assertFalse(BankAccount.isEmailValid("a@b.c")); //no domain with only 1 character
        assertFalse(BankAccount.isEmailValid("a@@b.com")); //no two @s
        assertFalse(BankAccount.isEmailValid("a@b..com")); //no two conjunctive periods
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