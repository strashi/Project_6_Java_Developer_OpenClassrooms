package xyz.strashi.PayMyBuddy.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.hibernate.annotations.DynamicUpdate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import xyz.strashi.PayMyBuddy.model.BankAccount;
import xyz.strashi.PayMyBuddy.model.Relationship;
import xyz.strashi.PayMyBuddy.model.User;
import xyz.strashi.PayMyBuddy.repository.BankAccountRepository;
import xyz.strashi.PayMyBuddy.repository.UserRepository;
import xyz.strashi.PayMyBuddy.service.TransactionService;
import xyz.strashi.PayMyBuddy.service.UserService;

@Service
@DynamicUpdate
public class UserServiceImpl implements UserService{
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private BankAccountRepository bankAccountRepository;
	
	@Autowired
	private TransactionService transactionService;
	
	
	@Override
	public User createUser(User user) {
		 	BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		 	String hashedPassword = passwordEncoder.encode(user.getPassword());
	        user.setPassword(hashedPassword);
		
		return userRepository.save(user);
	}
	/*
	@Override
	public void loginUser(String email, String password) {
		User user = userRepository.findByEmail(email);
		if(password.equals(user.getPassword())) {
			
		}
	}*/

	@Override
	public void depositMoney(User user, String ibanNumber, float amount) {
		
		//float newAmount = user.getBalance() + amount;
		//user.setBalance(newAmount);
		//System.out.println("numero iban "+ibanNumber);
		//Creation transaction
		BankAccount bankAccount = bankAccountRepository.findByIbanNumber(ibanNumber);
		String description = "Reload from "+bankAccount.getAccountDescription();
		//System.out.println("description compte "+bankAccount.getAccountDescription());
		User debitor = new User();
		transactionService.executeTransaction(debitor, user, amount, description, false);
			
	
		
	}
	/*
	@Override
	public List<User> getUsers() {
		// TODO Auto-generated method stub
		return userRepository.findAll();
	}*/
	
	@Override
	public User addRelationship(String emailUser, String emailFriend) {
		User user = userRepository.findByEmail(emailUser).orElseThrow(() -> new UsernameNotFoundException("User not present"));
		User friend = userRepository.findByEmail(emailFriend).orElseThrow(() -> new UsernameNotFoundException("User not present"));
		System.out.println("email user" +emailUser);
		Date creationDate = new Date();
		Relationship rel = new Relationship(friend,creationDate);
		
		user.getFriends().add(rel);
		
		return userRepository.save(user);
	}

	
	@Override
	public List<Relationship> getRelationships(User user) {

		Optional<User> opt = userRepository.findById(user.getUserId());
		User responseUser = opt.get();
		
		List<Relationship> relationshipsList = responseUser.getFriends();
		return relationshipsList;
	}
	/*
	@Override
	public List<String> getRelationshipsFirstName(User user) {
		List<Relationship>friendsList = this.getRelationships(user);
		List<String> RelationshipFirstNameList = new ArrayList<>();
		for(Relationship relationship : friendsList) {
			RelationshipFirstNameList.add(relationship.getFriend().getFirstName());
		}
		return RelationshipFirstNameList;
	}*/
	
	public List<User> getRelationshipsUser(User user) {
		List<Relationship>friendsList = this.getRelationships(user);
		List<User> RelationshipUserList = new ArrayList<>();
		for(Relationship relationship : friendsList) {
			RelationshipUserList.add(relationship.getFriend());
		}
		return RelationshipUserList;
	}
	
	@Override
	public User addBankAccount(User user,String accountDescription, String ibanNumber) {
		BankAccount bankAccount = new BankAccount(accountDescription, ibanNumber);
		user.getBankAccounts().add(bankAccount);
		return userRepository.save(user);
	}
	
	@Override
	public List<BankAccount> getBankAccounts(User user) {
		Optional<User> opt = userRepository.findById(user.getUserId());
		User responseUser = opt.get();
		
		 List<BankAccount> bankAccountsList = responseUser.getBankAccounts();
		return bankAccountsList;
	}
	/*
	@Override
	public User getUser() {
		return userRepository.findByEmail("aaa");
	
	}*/

	public User findByEmail(String email) {
		return userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User not present"));
	}

	@Override
	public void bankDeposit(User user, String ibanNumber, float amount) {
		BankAccount bankAccount = bankAccountRepository.findByIbanNumber(ibanNumber);
		String description = "Deposit to "+bankAccount.getAccountDescription();
		//System.out.println("description compte "+bankAccount.getAccountDescription());
		User creditor = new User();
		transactionService.executeTransaction(user, creditor, amount, description, false);
		
	}

	

	

}
