package xyz.strashi.PayMyBuddy.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

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
		user = userRepository.findByEmail(user.getEmail());
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
	public User addRelationship(User user1, User user2) {
		user1 = userRepository.findByEmail(user1.getEmail());
		user2 = userRepository.findByEmail(user2.getEmail());
		LocalDateTime creationDate = LocalDateTime.now();
		Relationship rel = new Relationship(user2.getUserId(),creationDate);
		
		user1.getFriends().add(rel);
		
		return userRepository.save(user1);
	}

	@Override
	public List<Relationship> getRelationships(User user) {

		Optional<User> opt = userRepository.findById(user.getUserId());
		User responseUser = opt.get();
		
		List<Relationship> relationshipsList = responseUser.getFriends();
		return relationshipsList ;
	}

	@Override
	public User addBankAccount(User user,BankAccount bankAccount) {
		user = userRepository.findByEmail(user.getEmail());
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

}
