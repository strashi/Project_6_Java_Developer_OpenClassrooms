package xyz.strashi.PayMyBuddy.service;

import java.util.List;

import xyz.strashi.PayMyBuddy.model.BankAccount;
import xyz.strashi.PayMyBuddy.model.Relationship;
import xyz.strashi.PayMyBuddy.model.User;

public interface UserService {
	
	public User createUser(User user);

	public void loginUser(String email, String password);
	
	public void depositMoney(float amount);
	
	public List<User> getUsers();
	
	public User addRelationship(User user1, User user2);
	
	public List<Relationship> getRelationships(User user);
	
	public BankAccount addBankAccount();
	
	public List<BankAccount> getBankAccounts();
	
	
}
