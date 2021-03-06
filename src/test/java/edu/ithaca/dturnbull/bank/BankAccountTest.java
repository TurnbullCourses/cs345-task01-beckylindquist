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
      
        assertThrows(IllegalArgumentException.class, ()->  bankAccount.deposit(100.001));  //should remain unchanged/not work

        assertThrows(IllegalArgumentException.class, ()->  bankAccount.deposit(0));
    }
    
    @Test
    void transferTest() throws InsufficientFundsException{
        BankAccount bankAccount1 = new BankAccount("a@b.com", 200);
        BankAccount bankAccount2 = new BankAccount("b@c.com", 0);

        bankAccount1.transfer(bankAccount2, 100); //transfers 100 from a@b.com to b@c.com
        assertEquals(100, bankAccount1.getBalance());
        assertEquals(100, bankAccount2.getBalance());

        assertThrows(InsufficientFundsException.class, ()-> bankAccount1.transfer(bankAccount2, 300)); //transfers more than is in a@b.com account

        assertThrows(IllegalArgumentException.class, ()-> bankAccount1.transfer(bankAccount2, -1)); //transfers invalid amount

        assertThrows(IllegalArgumentException.class, ()-> bankAccount1.transfer(bankAccount2, 0)); //transfers zero amount
    }
    
    @Test
    void withdrawTest() throws InsufficientFundsException{
        BankAccount bankAccount = new BankAccount("a@b.com", 200); 
        
        bankAccount.withdraw(100);//withdraw some money
        assertEquals(100, bankAccount.getBalance());

        assertThrows(IllegalArgumentException.class, ()->  bankAccount.withdraw(100.001)); //should remain unchanged/not work

        bankAccount.withdraw(0); //withdraw zero money
        assertEquals(100, bankAccount.getBalance());

        bankAccount.withdraw(100); //withdraw all funds
        assertEquals(0, bankAccount.getBalance(), 0.001);

        assertThrows(InsufficientFundsException.class, () -> bankAccount.withdraw(300)); //withdraw more than is in account
        
        assertThrows(IllegalArgumentException.class, () -> bankAccount.withdraw(-1)); //withdraw negative money
    }
    
    

    @Test
    void isAmountValidTest(){
        assertEquals(true, BankAccount.isAmountValid(100.25)); //normal amount
        assertEquals(false, BankAccount.isAmountValid(100.001)); //>2 decimal places... needs to not just be zeros cuz 1.000 is just 1 in all math so java doesnt see the zeros as deicamals
        assertEquals(false, BankAccount.isAmountValid(-1)); //negative number
    }
    
    @Test
    void isEmailValidTest(){
        //Middle Tests
        assertTrue(BankAccount.isEmailValid( "a@b.com")); //valid email
        assertFalse(BankAccount.isEmailValid("")); //no email

        //Prefix Tests
        assertTrue(BankAccount.isEmailValid("a.b@c.com")); //two nonconjunctive periods allowed
        assertFalse(BankAccount.isEmailValid(".a@b.com")); //no prefix startiing with . why does this work with a peroid but not a _??
        assertFalse(BankAccount.isEmailValid("-a@b.com"));
        assertFalse(BankAccount.isEmailValid("abc.@b.com")); //no prefix ending with underscore
        assertFalse(BankAccount.isEmailValid("a#b@c.com")); //no special chars
        assertFalse(BankAccount.isEmailValid("@b.com")); //no prefix
        
        //Domain Tests
        assertFalse(BankAccount.isEmailValid("a@b")); //no domain end
        assertFalse(BankAccount.isEmailValid("abc@zyx.c")); //no domain with only 1 character
        assertFalse(BankAccount.isEmailValid("a@@b.com")); //no two @s
        assertFalse(BankAccount.isEmailValid("a@b.c.om")); //cannot have more than one peroid in email (according to wikipedia)
        assertTrue(BankAccount.isEmailValid("a@b.c#om")); //no special chars
        assertFalse(BankAccount.isEmailValid("abc@.com")); //need domain name
        

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