package handler;

import model.Transaction;

public class TransactionOutputHandler {

    public void showTransactionAdded(Transaction transaction) {
        System.out.println("Transaction added successfully:");
        System.out.println(transaction);
    }

    public void showError(String message) {
        System.out.println("Error: " + message);
    }
}

