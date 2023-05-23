package xyz.strashi.PayMyBuddy.testIT;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import xyz.strashi.PayMyBuddy.model.BankAccount;
import xyz.strashi.PayMyBuddy.model.Relationship;
import xyz.strashi.PayMyBuddy.model.Role;
import xyz.strashi.PayMyBuddy.model.User;
import xyz.strashi.PayMyBuddy.repository.BankAccountRepository;
import xyz.strashi.PayMyBuddy.repository.TransactionRepository;
import xyz.strashi.PayMyBuddy.repository.UserRepository;
import xyz.strashi.PayMyBuddy.service.UserService;
import xyz.strashi.PayMyBuddy.tools.Utility;

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
	
	@Autowired
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
		
		
		String hashedPassword = utility.encoder(passwordAdmin);
		User adminSystem = new User();
		adminSystem.setEmail(emailAdmin);
		adminSystem.setFirstName(firstNameAdmin);
		adminSystem.setLastName(lastNameAdmin);
		adminSystem.setRole(roleAdmin);
		adminSystem.setBalance(balanceAdmin);
		adminSystem.setPassword(hashedPassword);
		if (!userRepository.findByEmail(emailAdmin).isPresent()) {
			userRepository.save(adminSystem);
		}
		
	}
	
	@Test
	public void createUserTest() {
		List<BankAccount> bankAccounts = null;
		List<Relationship> relationships = null;
		User user = new User(0L, Role.USER,"email@xyz","password","firstName","lastName",50.0f, bankAccounts, relationships);
		
		User responseUser = userService.createUser(user);
				
		assertEquals(user.getEmail(),responseUser.getEmail());
	}
	
	
	@Test
	public void addRelationshipTest() {
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
		
		Optional<User> opt = userRepository.findById(user1.getUserId());
		User responseUser = opt.get();
		
		List<Relationship> relationsList = responseUser.getFriends();
		
		User responseUser1 = relationsList.get(0).getFriend();
		User responseUser2 = relationsList.get(1).getFriend();
				
		assertEquals(responseUser1.getEmail(),user2.getEmail()) ;
		assertEquals(responseUser2.getEmail(),user3.getEmail()) ;
		
	}
	
	@Test
	public void getRelationshipTest() {
		
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
				
		assertEquals(responseUser1.getEmail(),user2.getEmail()) ;
		assertEquals(responseUser2.getEmail(),user3.getEmail()) ;

	}
	
	@Test
	public void addBankAccountTest() {
	
		User user1 = null;
		
		Optional<User> optUser1 = userRepository.findByEmail("email1@xyz");
		if(!optUser1.isEmpty())
			user1 = optUser1.get();
		
		String description = "compte courant";
		String ibanNumber = "FR552545658552";
		
		User response = userService.addBankAccount(user1,description, ibanNumber);
		
		assertEquals(ibanNumber,response.getBankAccounts().get(0).getIbanNumber());
	}
	
	
	@Test
	public void getBankAccountsTest() {
		List<BankAccount> bankAccounts = new ArrayList<>();
		
		BankAccount bankAccount = new BankAccount(0L,"compte secret","FR555555555");
		BankAccount bankAccount2 = new BankAccount(0L,"compte bancaire","FRXXXXXXXXXXX");

		bankAccounts.add(bankAccount);
		bankAccounts.add(bankAccount2);
		
		User user1 = null;
		
		Optional<User> optUser1 = userRepository.findByEmail("email2@xyz");
		if(!optUser1.isEmpty())
			user1 = optUser1.get();
		user1.setBankAccounts(bankAccounts);
		userRepository.save(user1);
		
		List<BankAccount> response = userService.getBankAccounts(user1);
	
		assertEquals(response.size(), 2);
		assertEquals(bankAccount.getIbanNumber(),response.get(0).getIbanNumber());
		assertEquals(bankAccount2.getIbanNumber(),response.get(1).getIbanNumber());
	
	}
	
	
	@Test
	public void depositMoneyTest() {
		
		User user1 = null;
		Optional<User> optUser1 = userRepository.findByEmail("email1@xyz");
		if(!optUser1.isEmpty())
			user1 = optUser1.get();
		
		String description = "compte courant";
		String ibanNumber = "FR552545658552";
		
		userService.addBankAccount(user1,description, ibanNumber);
		
		userService.depositMoney(user1,"FR552545658552", 100.0d);
				
		assertEquals(150, user1.getBalance());

	}
	
	@Test
	public void findByEmailTest() {
		List<BankAccount> bankAccounts = null;
		List<Relationship> relationships = null;
		User user = new User(0L, Role.USER,"email4@xyz","password","firstName","lastName",50.0f, bankAccounts, relationships);
							
		userService.createUser(user);
		
		User userToFind = userService.findByEmail(user.getEmail());

		assertTrue(user.getLastName().equals(userToFind.getLastName()) );
		assertEquals(userToFind.getFirstName(),user.getFirstName());
		
	}
}
