package xyz.strashi.PayMyBuddy.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import xyz.strashi.PayMyBuddy.model.BankAccount;
import xyz.strashi.PayMyBuddy.model.Relationship;
import xyz.strashi.PayMyBuddy.model.User;
import xyz.strashi.PayMyBuddy.repository.UserRepository;

@SpringBootTest
public class UserServiceTests {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private UserRepository userRepository;
	
	@BeforeEach
	public void delete() {
		userRepository.deleteAll();
		
	}
	
	@Test
	public void createUserTest() {
		List<BankAccount> bankAccounts = null;
		List<Relationship> relationships = null;
		User user = new User(0L,"email@xyz","password","firstName","lastName",50.0f, bankAccounts, relationships);
		
		//User responseUser = userRepository.save(user);
		User responseUser = userService.createUser(user);
				
		assertThat(responseUser.getEmail().equals(user.getEmail()));
	}
	
	@Test
	public void addRelationshipTest() {
		List<BankAccount> bankAccounts = null;
		List<Relationship> relationships = new ArrayList<>();
		User user1 = new User(0L,"email1@xyz","password","firstName","lastName",50.0f, null, relationships);
		User user2 = new User(0L,"email2@xyz","password","firstName2","lastName2",50.0f, null, relationships);
		User user3 = new User(0L,"email3@xyz","password","firstName3","lastName3",50.0f, null, relationships);
		user1 = userService.createUser(user1);
		user2 = userService.createUser(user2);
		user3 = userService.createUser(user3);
	
		userService.addRelationship(user1, user2);
	
		userService.addRelationship(user1, user3);
		
		Optional<User> opt = userRepository.findById(user1.getUserId());
		User responseUser = opt.get();
		
		List<Relationship> relationsList = responseUser.getFriends();
		
		Long long1 = relationsList.get(0).getUserIdFriend();
		Long long2 = relationsList.get(1).getUserIdFriend();
				
		assertThat(long1 == user2.getUserId());
		assertThat(long2 == user3.getUserId());
		
	}
	
	@Test
	public void getRelationshipTest() {
		
		List<BankAccount> bankAccounts = null;
		List<Relationship> relationships = new ArrayList<>();
		User user1 = new User(0L,"email1@xyz","password","firstName","lastName",50.0f, null, relationships);
		User user2 = new User(0L,"email2@xyz","password","firstName2","lastName2",50.0f, null, relationships);
		User user3 = new User(0L,"email3@xyz","password","firstName3","lastName3",50.0f, null, relationships);
		user1 = userService.createUser(user1);
		user2 = userService.createUser(user2);
		user3 = userService.createUser(user3);
	
		userService.addRelationship(user1, user2);
	
		userService.addRelationship(user1, user3);
		
		List<Relationship> relationshipsList = userService.getRelationships(user1);
		
		Long long2 = relationshipsList.get(0).getUserIdFriend();
		Long long3 = relationshipsList.get(1).getUserIdFriend();
		
		assertThat(long2 == user2.getUserId());
		assertThat(long3 == user3.getUserId());

	}
	
	@Test
	public void addBankAccountTest() {
		List<BankAccount> bankAccounts = new ArrayList<>();
		List<Relationship> relationships = new ArrayList<>();
		User user = new User(0L,"email1@xyz","password","firstName","lastName",50.0f, bankAccounts, relationships);
		userRepository.save(user);
		BankAccount bankAccount = new BankAccount(0L,"FR552545658552","compte courant",100.0f);
		
		User response = userService.addBankAccount(user,bankAccount);
		
		assertThat(bankAccount.getIbanNumber().equals(response.getBankAccounts().get(0).getIbanNumber()));
	}
	
	
	@Test
	public void getBankAccountsTest() {
		List<BankAccount> bankAccounts = new ArrayList<>();
		List<Relationship> relationships = new ArrayList<>();
		BankAccount bankAccount = new BankAccount(0L,"FR552545658552","compte courant",100.0f);
		BankAccount bankAccount2 = new BankAccount(0L,"FRxxxxxxxxx","compte courant",100.0f);

		bankAccounts.add(bankAccount);
		bankAccounts.add(bankAccount2);
		User user = new User(0L,"email1@xyz","password","firstName","lastName",50.0f, bankAccounts, relationships);
		user = userService.createUser(user);
		
		
		List<BankAccount> response = userService.getBankAccounts(user);
		System.out.println(bankAccount.getIbanNumber());
		System.out.println(bankAccount2.getIbanNumber());
		assertThat(bankAccount.getIbanNumber().equals(response.get(0).getIbanNumber()));
		assertThat(bankAccount2.getIbanNumber().equals(response.get(1).getIbanNumber()));

	}
	
	@Test
	public void depositMoneyTest() {
		User user = new User(0L,"email1@xyz","password","firstName","lastName",50.0f, null, null);
		user = userService.createUser(user);
		user = userService.depositMoney(user, 100.0f);
		
		assertThat(user.getBalance() == 150);
	}
	
	
}
