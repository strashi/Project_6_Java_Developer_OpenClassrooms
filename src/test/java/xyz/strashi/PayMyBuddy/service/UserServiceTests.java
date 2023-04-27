package xyz.strashi.PayMyBuddy.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import xyz.strashi.PayMyBuddy.model.BankAccount;
import xyz.strashi.PayMyBuddy.model.Relationship;
import xyz.strashi.PayMyBuddy.model.Role;
import xyz.strashi.PayMyBuddy.model.User;
import xyz.strashi.PayMyBuddy.repository.BankAccountRepository;
import xyz.strashi.PayMyBuddy.repository.TransactionRepository;
import xyz.strashi.PayMyBuddy.repository.UserRepository;

@SpringBootTest
@TestInstance(Lifecycle.PER_CLASS)
public class UserServiceTests {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private BankAccountRepository bankAccountRepository;
	
	@Autowired
	private TransactionRepository transactionRepository;
	
//	@BeforeEach
//	public void delete() {
//		userRepository.deleteAll();
//		bankAccountRepository.deleteAll();
//		
//	}
	
	@BeforeAll
	private void init() {
		transactionRepository.deleteAll();
		userRepository.deleteAll();
		bankAccountRepository.deleteAll();
		
		List<BankAccount> bankAccounts = new ArrayList<>();
		List<Relationship> relationships = new ArrayList<>();
		User user1 = new User(0L, Role.USER,"email1@xyz","password","firstName","lastName",50.0f, bankAccounts, relationships);
		User user2 = new User(0L, Role.USER,"email2@xyz","password","firstName2","lastName2",50.0f, bankAccounts, relationships);
		User user3 = new User(0L,Role.USER,"email3@xyz","password","firstName3","lastName3",50.0f, bankAccounts, relationships);
		user1 = userRepository.save(user1);
		user2 = userRepository.save(user2);
		user3 = userRepository.save(user3);
	}
	
//	@Test
//	public void createUserTest() {
//		List<BankAccount> bankAccounts = null;
//		List<Relationship> relationships = null;
//		User user = new User(0L, Role.USER,"email@xyz","password","firstName","lastName",50.0f, bankAccounts, relationships);
//		
//		//User responseUser = userRepository.save(user);
//		User responseUser = userService.createUser(user);
//				
//		assertThat(responseUser.getEmail().equals(user.getEmail()));
//	}
	
	
	@Test
	public void addRelationshipTest() {
		User user1 =null;
		User user2 = null;
		User user3 = null;
//		List<BankAccount> bankAccounts = null;
//		List<Relationship> relationships = new ArrayList<>();
//		User user1 = new User(0L, Role.USER,"email1@xyz","password","firstName","lastName",50.0f, null, relationships);
//		User user2 = new User(0L, Role.USER,"email2@xyz","password","firstName2","lastName2",50.0f, null, relationships);
//		User user3 = new User(0L,Role.USER,"email3@xyz","password","firstName3","lastName3",50.0f, null, relationships);
//		user1 = userRepository.save(user1);
//		user2 = userRepository.save(user2);
//		user3 = userRepository.save(user3);
		Optional<User> optUser1 = userRepository.findByEmail("email1@xyz");
		if(!optUser1.isEmpty())
			user1 = optUser1.get();
		
		Optional<User> optUser2 = userRepository.findByEmail("email2@xyz");
		if(!optUser2.isEmpty())
			user2 = optUser2.get();
		
		Optional<User> optUser3 = userRepository.findByEmail("email3@xyz");
		if(!optUser3.isEmpty())
			user3 = optUser3.get();
		
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
		
//		List<BankAccount> bankAccounts = null;
//		List<Relationship> relationships = new ArrayList<>();
//		User user1 = new User(0L,Role.USER,"email1@xyz","password","firstName","lastName",50.0f, null, relationships);
//		User user2 = new User(0L,Role.USER,"email2@xyz","password","firstName2","lastName2",50.0f, null, relationships);
//		User user3 = new User(0L,Role.USER,"email3@xyz","password","firstName3","lastName3",50.0f, null, relationships);
//		user1 = userRepository.save(user1);
//		user2 = userRepository.save(user2);
//		user3 = userRepository.save(user3);
		User user1 =null;
		User user2 = null;
		User user3 = null;
		
		Optional<User> optUser1 = userRepository.findByEmail("email1@xyz");
		if(!optUser1.isEmpty())
			user1 = optUser1.get();
		
		Optional<User> optUser2 = userRepository.findByEmail("email2@xyz");
		if(!optUser2.isEmpty())
			user2 = optUser2.get();
		
		Optional<User> optUser3 = userRepository.findByEmail("email3@xyz");
		if(!optUser3.isEmpty())
			user3 = optUser3.get();
	
		userService.addRelationship(user1.getEmail(), user2.getEmail());
	
		userService.addRelationship(user1.getEmail(), user3.getEmail());
		
		List<Relationship> relationshipsList = userService.getRelationships(user1);
				
		
		User responseUser1 = relationshipsList.get(0).getFriend();
		User responseUser2 = relationshipsList.get(1).getFriend();
				
		assertThat(responseUser1.equals(user2)) ;
		assertThat(responseUser2.equals(user3)) ;

	}
	
	@Test
	public void addBankAccountTest() {
		//List<BankAccount> bankAccounts = new ArrayList<>();
	
		//BankAccount bankAccount = new BankAccount(0L,description,ibanNumber);
		//bankAccounts.add(bankAccount);
//		List<Relationship> relationships = new ArrayList<>();
//		User user = new User(0L,Role.USER,"email1@xyz","password","firstName","lastName",50.0f, bankAccounts, relationships);
//		user = userRepository.save(user);
		User user1 = null;
		
		Optional<User> optUser1 = userRepository.findByEmail("email1@xyz");
		if(!optUser1.isEmpty())
			user1 = optUser1.get();
		
		String description = "compte courant";
		String ibanNumber = "FR552545658552";
		
		User response = userService.addBankAccount(user1,description, ibanNumber);
		
		assertThat(ibanNumber.equals(response.getBankAccounts().get(0).getIbanNumber()));
	}
	
	
	@Test
	public void getBankAccountsTest() {
		List<BankAccount> bankAccounts = new ArrayList<>();
		//List<Relationship> relationships = new ArrayList<>();
		BankAccount bankAccount = new BankAccount(0L,"compte secret","FR555555555");
		BankAccount bankAccount2 = new BankAccount(0L,"compte bancaire","FRXXXXXXXXXXX");

		bankAccounts.add(bankAccount);
		bankAccounts.add(bankAccount2);
//		User user = new User(0L,Role.USER,"email1@xyz","password","firstName","lastName",50.0f, bankAccounts, relationships);
//		user = userRepository.save(user);
		
		User user1 = null;
		
		Optional<User> optUser1 = userRepository.findByEmail("email1@xyz");
		if(!optUser1.isEmpty())
			user1 = optUser1.get();
		user1.setBankAccounts(bankAccounts);
		userRepository.save(user1);
		
		List<BankAccount> response = userService.getBankAccounts(user1);
		//System.out.println(bankAccount.getIbanNumber());
		//System.out.println(bankAccount2.getIbanNumber());
		assertThat(response.size() == 2);
		assertThat(bankAccount.getIbanNumber().equals(response.get(0).getIbanNumber()));
		assertThat(bankAccount2.getIbanNumber().equals(response.get(1).getIbanNumber()));

	}
	
	
	@Test
	public void depositMoneyTest() {
//		User user = new User(0L,"email1@xyz","password","firstName","lastName",50.0f, null, null);
//		user = userRepository.save(user);
		
		User user1 = null;
		
		Optional<User> optUser1 = userRepository.findByEmail("email1@xyz");
		if(!optUser1.isEmpty())
			user1 = optUser1.get();
		userService.depositMoney(user1,"ibantest", 100.0f);
		
		assertThat(user1.getBalance() == 150);
	}
	/*
	@Test
	public void findByEmailTest() {
		List<BankAccount> bankAccounts = null;
		List<Relationship> relationships = null;
		User user = new User(0L, Role.USER,"email4@xyz","password","firstName","lastName",50.0f, bankAccounts, relationships);
		
		//User responseUser = userRepository.save(user);
		User responseUser = userService.createUser(user);
		
		User userToFind = userService.findByEmail(user.getEmail());
//		System.out.println(responseUser.getFirstName());
//		System.out.println(userToFind.getLastName());
//		System.out.println(userToFind.getUserId());


		assertThat(user.equals(userToFind));
		assertFalse(user.getFirstName() == userToFind.getLastName());
	}*/
	
}
