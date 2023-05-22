package xyz.strashi.PayMyBuddy.service.impl;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.apache.commons.math3.util.Precision;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import xyz.strashi.PayMyBuddy.model.Status;
import xyz.strashi.PayMyBuddy.model.Transaction;
import xyz.strashi.PayMyBuddy.model.User;
import xyz.strashi.PayMyBuddy.repository.TransactionRepository;
import xyz.strashi.PayMyBuddy.repository.UserRepository;
import xyz.strashi.PayMyBuddy.service.TransactionService;
/**
 * Manage all money transfers : transactions and taxes between PayMyBuddy accounts
 * @author steve
 *
 */
@Service
public class TransactionServiceImpl implements TransactionService {

	private static final Logger logger = LoggerFactory.getLogger(TransactionServiceImpl.class);

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private TransactionRepository transactionRepository;

	
	 @Value("${config.system.admin.email}")
	 private String emailAdmin;
	

	@Transactional
	@Override
	public String executeTransaction(User debitor, User creditor, double amount, String description, boolean getTax) {
		logger.debug("executeTransaction sollicité de TransactionServiceImpl");
					
			try {
				Transaction transaction = null;
				try {
					amount = Precision.round(amount, 2);
					double balanceDebitorBefore = Precision.round(debitor.getBalance(), 2);
					double balanceCreditorBefore = Precision.round(creditor.getBalance(), 2);
					double balanceDebitorAfter = Precision.round(balanceDebitorBefore - amount, 2);
					double balanceCreditorAfter = Precision.round(balanceCreditorBefore + amount, 2);
					Date transactionDate = new Date();
					transaction = new Transaction(0L, debitor, creditor, amount, description, transactionDate,
							Status.failed.toString(), balanceDebitorBefore, balanceDebitorAfter, balanceCreditorBefore,
							balanceCreditorAfter);
		
					debitor.setBalance(balanceDebitorAfter);
					creditor.setBalance(balanceCreditorAfter);
					userRepository.save(debitor);
					userRepository.save(creditor);
					if (getTax)
						getTax(debitor, amount);
					transaction.setStatus(Status.ok.toString());
					transactionRepository.save(transaction);
					logger.info("executeTransaction ok de TransactionServiceImpl");
					return Status.ok.toString();
				} catch (Exception e) {
					transaction.setStatus(Status.failed.toString());
					transactionRepository.save(transaction);
					logger.info("executeTransaction failed de TransactionServiceImpl");
					return Status.failed.toString();
				}
			}catch (Exception e) {
				logger.error("Erreur au executeTransaction de TransactionServiceImpl", e);
				return null;
			}
					
	}

	@Override
	public List<Transaction> getTransactions(User user) {
		logger.debug("getTransactions sollicité de TransactionServiceImpl");
		try {
			List<Transaction> transactionsList = transactionRepository.getAllTransactions(user);
			logger.info("getTransactions effectuée de TransactionServiceImpl");
			return transactionsList;
		}catch (Exception e) {
			logger.error("Erreur au getTransactions de TransactionServiceImpl", e);
			return null;
		}
		
	}
	@Transactional
	@Override
	public String getTax(User debitor, double amount) {
		logger.debug("getTax sollicité de TransactionServiceImpl");
		try {
			User admin = userRepository.findByEmail(emailAdmin)
					.orElseThrow(() -> new UsernameNotFoundException("User not present"));
			double fee = Precision.round(amount * 0.5f / 100, 2);
			Transaction transaction = null;
			try {
				double balanceDebitorBefore = Precision.round(debitor.getBalance(), 2);
				double balanceCreditorBefore = Precision.round(admin.getBalance(), 2);
				double balanceDebitorAfter = Precision.round(balanceDebitorBefore - fee, 2);
				double balanceCreditorAfter = Precision.round(balanceCreditorBefore + fee, 2);
	
				Date transactionDate = new Date();
				transaction = new Transaction(0L, debitor, admin, fee, "Monetize Application", transactionDate,
						Status.failed.toString(), balanceDebitorBefore, balanceDebitorAfter, balanceCreditorBefore,
						balanceCreditorAfter);
	
				debitor.setBalance(balanceDebitorAfter);
				admin.setBalance(balanceCreditorAfter);
				userRepository.save(debitor);
				userRepository.save(admin);
				transaction.setStatus(Status.ok.toString());
				transactionRepository.save(transaction);
				logger.info("getTax ok de TransactionServiceImpl");
				return Status.ok.toString();
				
			} catch (Exception e) {
				transactionRepository.save(transaction);
				logger.info("getTax failed de TransactionServiceImpl");
				return Status.failed.toString();
			}
		}catch (Exception e) {
			logger.error("Erreur au getTax de TransactionServiceImpl", e);
			return null;
		}
		
	}
}
