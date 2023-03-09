package xyz.strashi.PayMyBuddy.service;

import java.util.List;

import xyz.strashi.PayMyBuddy.model.BankAccount;
import xyz.strashi.PayMyBuddy.model.Relationship;
import xyz.strashi.PayMyBuddy.model.User;

public interface UserService {
	
	public User createUser(User user);

	public void loginUser(String email, String password);
	
	public User depositMoney(User user, float amount);
	
	public List<User> getUsers();
	
	public User addRelationship(User user1, User user2);
	
	public List<Relationship> getRelationships(User user);
	
	public User addBankAccount(User user,BankAccount bankAccount);
	
	public List<BankAccount> getBankAccounts(User user);
	
	
}
