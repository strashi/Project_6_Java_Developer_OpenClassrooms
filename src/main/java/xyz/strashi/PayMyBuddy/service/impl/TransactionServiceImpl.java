package xyz.strashi.PayMyBuddy.service.impl;

import org.springframework.stereotype.Service;

import xyz.strashi.PayMyBuddy.model.Status;
import xyz.strashi.PayMyBuddy.model.User;
import xyz.strashi.PayMyBuddy.service.TransactionService;

@Service
public class TransactionServiceImpl implements TransactionService{

	@Override
	public Status executeTransaction(User debitor, User creditor, float amount, String description) {
		
		return Status.ok;
		
	}

}
