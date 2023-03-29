package xyz.strashi.PayMyBuddy.service;

import java.util.List;

import xyz.strashi.PayMyBuddy.model.Status;
import xyz.strashi.PayMyBuddy.model.Transaction;
import xyz.strashi.PayMyBuddy.model.User;

public interface TransactionService {
	
	public Status executeTransaction(User debitor, User creditor, float amount, String description, boolean getTax);

	public List<Transaction> getTransactions(User user);
	
			
}
