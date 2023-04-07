package xyz.strashi.PayMyBuddy.service;

import java.util.List;

import xyz.strashi.PayMyBuddy.model.Transaction;
import xyz.strashi.PayMyBuddy.model.User;

public interface TransactionService {
	
	public String executeTransaction(User debitor, User creditor, double amount, String description, boolean getTax);

	public List<Transaction> getTransactions(User user);
	
	public String getTax(User debitor, double amount);
}
