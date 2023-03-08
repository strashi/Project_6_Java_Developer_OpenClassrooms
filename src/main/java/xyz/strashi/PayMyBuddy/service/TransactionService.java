package xyz.strashi.PayMyBuddy.service;

import xyz.strashi.PayMyBuddy.model.Status;
import xyz.strashi.PayMyBuddy.model.User;

public interface TransactionService {
	
	public Status executeTransaction(User debitor, User creditor, float amount, String description);
	
	//public Status payMoneyToAFriend(User friend, float amount, String descriptif);
}
