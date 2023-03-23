package xyz.strashi.PayMyBuddy.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.hibernate.annotations.DynamicUpdate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import xyz.strashi.PayMyBuddy.model.BankAccount;
import xyz.strashi.PayMyBuddy.model.Relationship;
import xyz.strashi.PayMyBuddy.model.User;
import xyz.strashi.PayMyBuddy.repository.UserRepository;
import xyz.strashi.PayMyBuddy.service.UserService;

@Service
@DynamicUpdate
public class UserServiceImpl implements UserService{
	
	@Autowired
	private UserRepository userRepository;
	
	@Override
	public User createUser(User user) {
		//User userSaved = userRepository.save(user);
		return userRepository.save(user);
	}

	@Override
	public void loginUser(String email, String password) {
		User user = userRepository.findByEmail(email);
		if(password.equals(user.getPassword())) {
			
		}
	}

	@Override
	public User depositMoney(User user, float amount) {
		//user = userRepository.findByEmail(user.getEmail());
		float newAmount = user.getBalance() + amount;
		user.setBalance(newAmount);
		return userRepository.save(user);
		
	}

	@Override
	public List<User> getUsers() {
		// TODO Auto-generated method stub
		return userRepository.findAll();
	}
	
	@Override
	public User addRelationship(String emailUser, String emailFriend) {
		User user = userRepository.findByEmail(emailUser);
		User friend = userRepository.findByEmail(emailFriend);
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
		return relationshipsList ;
	}
	
	@Override
	public List<String> getRelationshipsFirstName(User user) {
		List<Relationship>friendsList = this.getRelationships(user);
		List<String> RelationshipFirstNameList = new ArrayList<>();
		for(Relationship relationship : friendsList) {
			RelationshipFirstNameList.add(relationship.getFriend().getFirstName());
		}
		return RelationshipFirstNameList;
	}
	
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
		//user = userRepository.findByEmail(user.getEmail());
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

	@Override
	public User getUser() {
		return userRepository.findByEmail("aaa");
	
	}

	public User findByEmail(String email) {
		return userRepository.findByEmail(email);
	}

	

	

}
