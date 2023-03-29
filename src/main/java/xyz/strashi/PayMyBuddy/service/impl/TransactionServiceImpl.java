package xyz.strashi.PayMyBuddy.service.impl;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import xyz.strashi.PayMyBuddy.model.Status;
import xyz.strashi.PayMyBuddy.model.Transaction;
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
	
	@Transactional
	@Override
	public Status executeTransaction(User debitor, User creditor, float amount, String description, boolean getTax) {
		Transaction transaction = null;
		try {
			
			float balanceDebitorBefore = debitor.getBalance();
			float balanceCreditorBefore = creditor.getBalance();
			float balanceDebitorAfter = balanceDebitorBefore - amount;
			float balanceCreditorAfter = balanceCreditorBefore + amount;
			Date transactionDate = new Date();
			transaction = new Transaction(0L,debitor,creditor, amount, description,transactionDate,Status.failed, balanceDebitorBefore,
					balanceDebitorAfter,balanceCreditorBefore,balanceCreditorAfter);
		
			debitor.setBalance(balanceDebitorAfter);
			creditor.setBalance(balanceCreditorAfter);
			userRepository.save(debitor);
			userRepository.save(creditor);
			transaction.setStatus(Status.ok);
			transactionRepository.save(transaction);
			if(getTax)
				getTax(debitor,amount);
			return Status.ok;
		}catch(Exception e) {
			transactionRepository.save(transaction);
			return Status.failed;
		}
		
	}

	@Override
	public List<Transaction> getTransactions(User user) {
		
		List<Transaction> transactionsList = transactionRepository.getAllTransactions(user);
		return transactionsList;
	}
	
	public Status getTax(User debitor, float amount) {
		User admin = userRepository.findByEmail("admin").orElseThrow(() -> new UsernameNotFoundException("User not present"));
		float fee = amount * 0.5f/100;
		Transaction transaction = null;
	try {
			
			float balanceDebitorBefore = debitor.getBalance();
			float balanceCreditorBefore = admin.getBalance();
			float balanceDebitorAfter = balanceDebitorBefore - fee;
			float balanceCreditorAfter = balanceCreditorBefore + fee;
			Date transactionDate = new Date();
			transaction = new Transaction(0L,debitor, admin, fee, "Monetize Application",transactionDate,Status.failed, balanceDebitorBefore,
					balanceDebitorAfter,balanceCreditorBefore,balanceCreditorAfter);
		
			debitor.setBalance(balanceDebitorAfter);
			admin.setBalance(balanceCreditorAfter);
			userRepository.save(debitor);
			userRepository.save(admin);
			transaction.setStatus(Status.ok);
			transactionRepository.save(transaction);
		
			return Status.ok;
		}catch(Exception e) {
			transactionRepository.save(transaction);
			return Status.failed;
		}
	}
}
