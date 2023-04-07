package xyz.strashi.PayMyBuddy.service;

import java.util.List;

import xyz.strashi.PayMyBuddy.model.BankAccount;
import xyz.strashi.PayMyBuddy.model.Relationship;
import xyz.strashi.PayMyBuddy.model.User;

public interface UserService {
	
	public User createUser(User user);

	public void depositMoney(User user, String ibanNumber, double amount);
	
	public User addRelationship(String emailUser, String emailFriend);
	
	public List<Relationship> getRelationships(User user);
	
	public List<User> getRelationshipsUser(User user);
	
	public User addBankAccount(User user,String accountDescription, String ibanNumber);
	
	public List<BankAccount> getBankAccounts(User user);

	public User findByEmail(String email);

	public void bankDeposit(User user, String bankAccount, double amount);
	
	
}
