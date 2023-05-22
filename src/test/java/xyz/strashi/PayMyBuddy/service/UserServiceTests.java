package xyz.strashi.PayMyBuddy.service;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import xyz.strashi.PayMyBuddy.model.BankAccount;
import xyz.strashi.PayMyBuddy.model.Relationship;
import xyz.strashi.PayMyBuddy.model.Role;
import xyz.strashi.PayMyBuddy.model.User;
import xyz.strashi.PayMyBuddy.repository.BankAccountRepository;
import xyz.strashi.PayMyBuddy.repository.TransactionRepository;
import xyz.strashi.PayMyBuddy.repository.UserRepository;
import xyz.strashi.PayMyBuddy.service.impl.TransactionServiceImpl;
import xyz.strashi.PayMyBuddy.tools.Utility;

@SpringBootTest
//@TestInstance(Lifecycle.PER_CLASS)
public class UserServiceTests {
	
	@Autowired
	private UserService userService;
	
	@MockBean
	private UserRepository userRepository;
	
	@MockBean
	private TransactionService transactionService;
	
	@MockBean
	private BankAccountRepository bankAccountRepository;
	
	@MockBean
	private TransactionRepository transactionRepository;
	
	@MockBean
	private Utility utility;
	
	@Value("${config.system.admin.email}")
	private String emailAdmin;

	@Value("${config.system.admin.firstName}")
	private String firstNameAdmin;

	@Value("${config.system.admin.lastName}")
	private String lastNameAdmin;

	@Value("${config.system.admin.balance}")
	private double balanceAdmin;

	@Value("${config.system.admin.password}")
	private String passwordAdmin;

	@Value("${config.system.admin.role}")
	private Role roleAdmin;
	
//	@BeforeEach
//	public void delete() {
//		userRepository.deleteAll();
//		bankAccountRepository.deleteAll();
//		
//	}
	
//	@BeforeAll
//	private void init() {
//		transactionRepository.deleteAll();
//		userRepository.deleteAll();
//		bankAccountRepository.deleteAll();
//		
//		List<BankAccount> bankAccounts = new ArrayList<>();
//		List<Relationship> relationships = new ArrayList<>();
//		User user1 = new User(0L, Role.USER,"email1@xyz","password","firstName","lastName",50.0f, bankAccounts, relationships);
//		User user2 = new User(0L, Role.USER,"email2@xyz","password","firstName2","lastName2",50.0f, bankAccounts, relationships);
//		User user3 = new User(0L,Role.USER,"email3@xyz","password","firstName3","lastName3",50.0f, bankAccounts, relationships);
//		user1 = userRepository.save(user1);
//		user2 = userRepository.save(user2);
//		user3 = userRepository.save(user3);
//	}
	
	@Test
	public void createUserTest() {
		List<BankAccount> bankAccounts = null;
		List<Relationship> relationships = null;
		User user = new User(0L, Role.USER,"email@xyz","password","firstName","lastName",50.0f, bankAccounts, relationships);
		
		Optional<User> opt = Optional.of(user);
		
		when(utility.encoder(any(String.class))).thenReturn("password");
		when(userRepository.save(user)).thenReturn(user);
	
		User responseUser = userService.createUser(user);
				
		assertTrue(responseUser.getEmail().equals(user.getEmail()));
	}
	
	
//	@Test
//	public void addRelationshipTest() {
//		
//		User user1 = new User(0L, Role.USER,"email1@xyz","password","firstName1","lastName1",50.0f, new ArrayList<>(), new ArrayList<>());
//		Optional<User> opt1 = Optional.of(user1);
//		User user2 = new User(0L, Role.USER,"email2@xyz","password","firstName2","lastName2",50.0f, new ArrayList<>(), new ArrayList<>());
//		Optional<User> opt2 = Optional.of(user2);
//		String emailUser = null;
//		String emailFriend = null;
//		
//		when(userRepository.findByEmail(emailUser)).thenReturn(opt1);
//		when(userRepository.findByEmail(emailFriend)).thenReturn(opt2);
//		
//		User response = userService.addRelationship(user1.getEmail(), user2.getEmail());
//	
//	//userService.addRelationship(user1.getEmail(), user3.getEmail());
//		
////		Optional<User> opt = userRepository.findById(user1.getUserId());
////		User responseUser = opt.get();
//		
//		List<Relationship> relationsList = response.getFriends();
//		
//		User responseUser1 = relationsList.get(0).getFriend();
//		//User responseUser2 = relationsList.get(1).getFriend();
//				
//		assertTrue(responseUser1.getEmail().equals(user2.getEmail())) ;
//		//assertTrue(responseUser2.equals(user3)) ;
//		
//	}
	
	@Test
	public void getRelationshipTest() {
		
		List<BankAccount> bankAccounts = null;
		List<Relationship> relationships = new ArrayList<>();
		
		User user2 = new User(0L,Role.USER,"email2@xyz","password","firstName2","lastName2",50.0f, null, relationships);
		User user3 = new User(0L,Role.USER,"email3@xyz","password","firstName3","lastName3",50.0f, null, relationships);
		Date date = new Date();
		Relationship relationship = new Relationship(0L,user2,date);
		relationships.add(relationship);
		User user1 = new User(0L,Role.USER,"email1@xyz","password","firstName","lastName",50.0f, null, relationships);
		Optional<User> opt1 = Optional.of(user1);
		when(userRepository.findByEmail(any(String.class))).thenReturn(opt1);
		
		List<Relationship> relationshipsList = userService.getRelationships(user1);
				
		
		
		//User responseUser2 = relationshipsList.get(1).getFriend();
				
		assertTrue(relationshipsList.get(0).getFriend().getEmail().equals(user2.getEmail())) ;
		//assertThat(responseUser2.equals(user3)) ;

	}
	
//	@Test
//	public void addBankAccountTest() {
//		//List<BankAccount> bankAccounts = new ArrayList<>();
//	
//		//BankAccount bankAccount = new BankAccount(0L,description,ibanNumber);
//		//bankAccounts.add(bankAccount);
////		List<Relationship> relationships = new ArrayList<>();
////		User user = new User(0L,Role.USER,"email1@xyz","password","firstName","lastName",50.0f, bankAccounts, relationships);
////		user = userRepository.save(user);
//		User user1 = null;
//		
//		Optional<User> optUser1 = userRepository.findByEmail("email1@xyz");
//		if(!optUser1.isEmpty())
//			user1 = optUser1.get();
//		
//		String description = "compte courant";
//		String ibanNumber = "FR552545658552";
//		
//		User response = userService.addBankAccount(user1,description, ibanNumber);
//		
//		assertThat(ibanNumber.equals(response.getBankAccounts().get(0).getIbanNumber()));
//	}
	
	
	@Test
	public void getBankAccountsTest() {
		List<BankAccount> bankAccounts = new ArrayList<>();
		//List<Relationship> relationships = new ArrayList<>();
		BankAccount bankAccount = new BankAccount(0L,"compte secret","FR555555555");
		BankAccount bankAccount2 = new BankAccount(0L,"compte bancaire","FRXXXXXXXXXXX");

		bankAccounts.add(bankAccount);
		bankAccounts.add(bankAccount2);
		User user = new User(0L,Role.USER,"email1@xyz","password","firstName","lastName",50.0f, bankAccounts, null);
		Optional<User> opt = Optional.of(user);
		//user = userRepository.save(user);
		when(userRepository.findById(user.getUserId())).thenReturn(opt);
		//User user1 = null;
		
//		Optional<User> optUser1 = userRepository.findByEmail("email1@xyz");
//		if(!optUser1.isEmpty())
//			user1 = optUser1.get();
//		user1.setBankAccounts(bankAccounts);
//		userRepository.save(user1);
		
		List<BankAccount> response = userService.getBankAccounts(user);
		//System.out.println(bankAccount.getIbanNumber());
		//System.out.println(bankAccount2.getIbanNumber());
		assertTrue(response.size() == 2);
		assertTrue(bankAccount.getIbanNumber().equals(response.get(0).getIbanNumber()));
		assertTrue(bankAccount2.getIbanNumber().equals(response.get(1).getIbanNumber()));

	}
	
	
	@Test
	public void depositMoneyTest() {
		BankAccount bankAccount = new BankAccount(0L,"compte secret","ibantest");
		List<BankAccount> bankAccounts = new ArrayList<>();
		bankAccounts.add(bankAccount);
		User user = new User(0L,Role.USER,"email1@xyz","password","firstName","lastName",50.0f, bankAccounts, null);
//		user = userRepository.save(user);
		User adminSystem = new User();
		adminSystem.setEmail(emailAdmin);
		adminSystem.setFirstName(firstNameAdmin);
		adminSystem.setLastName(lastNameAdmin);
		adminSystem.setRole(roleAdmin);
		adminSystem.setBalance(balanceAdmin);
		adminSystem.setPassword(passwordAdmin);
		Optional<User> optAdmin = Optional.of(adminSystem);
		
//		User user1 = null;
		String ibanNumber = null;
		String description = null;
		double amount = 0;
		
	
		
//		Optional<User> optUser1 = userRepository.findByEmail("email1@xyz");
//		if(!optUser1.isEmpty())
//			user1 = optUser1.get();
		
		when(bankAccountRepository.findByIbanNumber(ibanNumber)).thenReturn(bankAccount);
		when(userRepository.findByEmail("admin@paymybuddy.com")).thenReturn(optAdmin);
		when(transactionService.executeTransaction(adminSystem, user, amount, description, false)).thenReturn("ok");
		
		userService.depositMoney(user,"ibantest", 100.0f);
		
		verify(transactionService,times(1)).executeTransaction(adminSystem, user, amount, description, false);
		//assertThat(user1.getBalance() == 150);
	}
	
//	@Test
//	public void findByEmailTest() {
//		List<BankAccount> bankAccounts = null;
//		List<Relationship> relationships = null;
//		User user = new User(0L, Role.USER,"email4@xyz","password","firstName","lastName",50.0f, bankAccounts, relationships);
//		
//		//User responseUser = userRepository.save(user);
//		User responseUser = userService.createUser(user);
//		
//		User userToFind = userService.findByEmail(user.getEmail());
////		System.out.println(responseUser.getFirstName());
////		System.out.println(userToFind.getLastName());
////		System.out.println(userToFind.getUserId());
//
//
//		assertThat(user.equals(userToFind));
//		assertFalse(user.getFirstName() == userToFind.getLastName());
//	}
	/*
	@Test
	@WithMockUser(username = "email1@xyz",password = "password",authorities= {"USER"})
	public void updateUserTest() {
		
		Principal principal = (Principal)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		User user = new User(0L, Role.USER,"email4@xyz","password","firstName2","lastName",50.0f, null, null);
		
		User response = userService.updateUser(user,principal);
		
		assertEquals("firstName2",response.getFirstName());
	}
	*/
}
