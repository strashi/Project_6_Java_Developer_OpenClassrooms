package xyz.strashi.PayMyBuddy.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import xyz.strashi.PayMyBuddy.model.Status;
import xyz.strashi.PayMyBuddy.model.User;
import xyz.strashi.PayMyBuddy.repository.TransactionRepository;
import xyz.strashi.PayMyBuddy.repository.UserRepository;
import xyz.strashi.PayMyBuddy.service.TransactionService;

@Service
public class TransactionServiceImpl implements TransactionService{
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private TransactionRepository transactionRepository;

	@Override
	public Status executeTransaction(User debitor, User creditor, float amount, String description) {
		// TODO Auto-generated method stub
		return null;
	}
	
	/*
	@Override
	public Status executeTransaction(User debitor, User creditor, float amount, String description) {
		debitor = userRepository.findByEmail(debitor.getEmail());
		creditor = userRepository.findByEmail(creditor.getEmail());
		//debitor.setBalance(debitor.getBalance() - amount);
		//creditor.setBalance(creditor.getBalance() + amount);
		userRepository.executeTransaction(debitor.getEmail(),creditor.getEmail(),amount);
		
			return Status.ok;
		
	}*/

}
