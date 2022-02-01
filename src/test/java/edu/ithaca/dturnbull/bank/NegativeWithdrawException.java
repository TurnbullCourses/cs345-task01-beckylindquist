package edu.ithaca.dturnbull.bank;

public class NegativeWithdrawException extends Exception {
    private static final long serialVersionUID = 1L;

    public NegativeWithdrawException(String s) {
        super(s);
    }

}

