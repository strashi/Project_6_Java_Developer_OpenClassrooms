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
import xyz.strashi.PayMyBuddy.repository.BankAccountRepository;
import xyz.strashi.PayMyBuddy.repository.UserRepository;

@SpringBootTest
public class UserServiceTests {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private BankAccountRepository bankAccountRepository;
	
	@BeforeEach
	public void delete() {
		userRepository.deleteAll();
		bankAccountRepository.deleteAll();
		
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
		user1 = userRepository.save(user1);
		user2 = userRepository.save(user2);
		user3 = userRepository.save(user3);
	
		userService.addRelationship(user1.getEmail(), user2.getEmail());
	
		userService.addRelationship(user1.getEmail(), user3.getEmail());
		
		Optional<User> opt = userRepository.findById(user1.getUserId());
		User responseUser = opt.get();
		
		List<Relationship> relationsList = responseUser.getFriends();
		
		User responseUser1 = relationsList.get(0).getFriend();
		User responseUser2 = relationsList.get(1).getFriend();
				
		assertThat(responseUser1.equals(user2)) ;
		assertThat(responseUser2.equals(user3)) ;
		
	}
	
	@Test
	public void getRelationshipTest() {
		
		List<BankAccount> bankAccounts = null;
		List<Relationship> relationships = new ArrayList<>();
		User user1 = new User(0L,"email1@xyz","password","firstName","lastName",50.0f, null, relationships);
		User user2 = new User(0L,"email2@xyz","password","firstName2","lastName2",50.0f, null, relationships);
		User user3 = new User(0L,"email3@xyz","password","firstName3","lastName3",50.0f, null, relationships);
		user1 = userRepository.save(user1);
		user2 = userRepository.save(user2);
		user3 = userRepository.save(user3);
	
		userService.addRelationship(user1.getEmail(), user2.getEmail());
	
		userService.addRelationship(user1.getEmail(), user3.getEmail());
		
		List<Relationship> relationshipsList = userService.getRelationships(user1);
				
		
		User responseUser1 = relationshipsList.get(0).getFriend();
		User responseUser2 = relationshipsList.get(1).getFriend();
				
		assertThat(responseUser1.equals(user2)) ;
		assertThat(responseUser2.equals(user3)) ;

	}
	/*
	@Test
	public void addBankAccountTest() {
		List<BankAccount> bankAccounts = new ArrayList<>();
		List<Relationship> relationships = new ArrayList<>();
		User user = new User(0L,"email1@xyz","password","firstName","lastName",50.0f, bankAccounts, relationships);
		userRepository.save(user);
		BankAccount bankAccount = new BankAccount(0L,"compte courant","FR552545658552");
		
		User response = userService.addBankAccount(user,bankAccount);
		
		assertThat(bankAccount.getIbanNumber().equals(response.getBankAccounts().get(0).getIbanNumber()));
	}*/
	
	
	@Test
	public void getBankAccountsTest() {
		List<BankAccount> bankAccounts = new ArrayList<>();
		List<Relationship> relationships = new ArrayList<>();
		BankAccount bankAccount = new BankAccount(0L,"compte courant","FR552545658552");
		BankAccount bankAccount2 = new BankAccount(0L,"compte bancaire","FRXXXXXXXXXXX");

		bankAccounts.add(bankAccount);
		bankAccounts.add(bankAccount2);
		User user = new User(0L,"email1@xyz","password","firstName","lastName",50.0f, bankAccounts, relationships);
		user = userRepository.save(user);
		
		
		List<BankAccount> response = userService.getBankAccounts(user);
		//System.out.println(bankAccount.getIbanNumber());
		//System.out.println(bankAccount2.getIbanNumber());
		assertThat(response.size() == 2);
		assertThat(bankAccount.getIbanNumber().equals(response.get(0).getIbanNumber()));
		assertThat(bankAccount2.getIbanNumber().equals(response.get(1).getIbanNumber()));

	}
	/*
	@Test
	public void depositMoneyTest() {
		User user = new User(0L,"email1@xyz","password","firstName","lastName",50.0f, null, null);
		user = userRepository.save(user);
		user = userService.depositMoney(user, 100.0f);
		
		assertThat(user.getBalance() == 150);
	}
	*/
	
}
