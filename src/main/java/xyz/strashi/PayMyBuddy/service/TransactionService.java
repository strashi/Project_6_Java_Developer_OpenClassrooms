package xyz.strashi.PayMyBuddy.service;

import xyz.strashi.PayMyBuddy.model.User;

public interface TransactionService {
	
	public void executeTransaction(User debtor, User creditor, float amount, String description);
}
