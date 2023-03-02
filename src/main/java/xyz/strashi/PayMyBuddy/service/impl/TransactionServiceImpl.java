package xyz.strashi.PayMyBuddy.service.impl;

import org.springframework.stereotype.Service;

import xyz.strashi.PayMyBuddy.model.User;
import xyz.strashi.PayMyBuddy.service.TransactionService;

@Service
public class TransactionServiceImpl implements TransactionService{

	@Override
	public void executeTransaction(User debtor, User creditor, float amount, String description) {
		// TODO Auto-generated method stub
		
	}

}
