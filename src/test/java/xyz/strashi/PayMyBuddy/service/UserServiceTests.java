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
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
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
					
		assertTrue(relationshipsList.get(0).getFriend().getEmail().equals(user2.getEmail())) ;
		
	}
	
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
	
		when(userRepository.findById(user.getUserId())).thenReturn(opt);
				
		List<BankAccount> response = userService.getBankAccounts(user);
	
		assertTrue(response.size() == 2);
		assertTrue(bankAccount.getIbanNumber().equals(response.get(0).getIbanNumber()));
		assertTrue(bankAccount2.getIbanNumber().equals(response.get(1).getIbanNumber()));

	}
	
	
	@Test
	public void depositMoneyTest() {
		BankAccount bankAccount = new BankAccount(0L,"compte secret","ibantest");
		List<BankAccount> bankAccounts = new ArrayList<>();
		bankAccounts.add(bankAccount);
		User user = new User(1L,Role.USER,"email1@xyz","password","firstName","lastName",50.0f, bankAccounts, null);

		User adminSystem = new User();
		adminSystem.setEmail(emailAdmin);
		adminSystem.setFirstName(firstNameAdmin);
		adminSystem.setLastName(lastNameAdmin);
		adminSystem.setRole(roleAdmin);
		adminSystem.setBalance(balanceAdmin);
		adminSystem.setPassword(passwordAdmin);
		Optional<User> optAdmin = Optional.of(adminSystem);
		
		String description = "Reload from compte secret";
		double amount = 100d;

		when(bankAccountRepository.findByIbanNumber(any(String.class))).thenReturn(bankAccount);
		when(userRepository.findByEmail("admin@paymybuddy.com")).thenReturn(optAdmin);
		when(transactionService.executeTransaction(adminSystem, user, amount, description, false)).thenReturn("ok");
		
		userService.depositMoney(user,"ibantest", 100.0d);
		
		verify(transactionService,times(1)).executeTransaction(adminSystem, user, amount, description, false);
		
	}

}
