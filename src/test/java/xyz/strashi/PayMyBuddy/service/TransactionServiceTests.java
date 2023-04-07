package xyz.strashi.PayMyBuddy.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import xyz.strashi.PayMyBuddy.model.BankAccount;
import xyz.strashi.PayMyBuddy.model.Relationship;
import xyz.strashi.PayMyBuddy.model.Role;
import xyz.strashi.PayMyBuddy.model.Status;
import xyz.strashi.PayMyBuddy.model.User;
import xyz.strashi.PayMyBuddy.repository.UserRepository;

@SpringBootTest
public class TransactionServiceTests {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private TransactionService transactionService;
	
	@BeforeEach
	public void init() {
		userRepository.deleteAll();
		
	}
	
	@Test
	public void executeTransactionTest() {
		List<BankAccount> bankAccounts = null;
		List<Relationship> relationships = new ArrayList<>();
		User user1 = new User(0L,Role.USER,"email1@xyz","password","firstName","lastName",50.0f, null, relationships);
		User user2 = new User(0L,Role.USER,"email2@xyz","password","firstName2","lastName2",50.0f, null, relationships);
		User admin = new User(0L,Role.ADMIN,"admin@paymybuddy.com","password","firstName","lastName",50.0f, null, null);
		
		user1 = userRepository.save(user1);
		user2 = userRepository.save(user2);
		userRepository.save(admin);
		
		String status = transactionService.executeTransaction(user1, user2, 50, "resto",true);
		
		assertThat(status.equals(Status.ok.toString()));
	}
	/*
	@Test
	public void getTaxTest() {
		
		User user = new User(0L,Role.USER,"email1@xyz","password","firstName","lastName",50.0f, null, null);
		User admin = new User(0L,Role.ADMIN,"admin","password","firstName","lastName",50.0f, null, null);
		user = userRepository.save(user);
		userRepository.save(admin);
		User user1 = userRepository.findByEmail("email1@xyz").orElseThrow(() -> new UsernameNotFoundException("User not present"));
		float amount = 50f;
		String status = transactionService.getTax(user1, amount);
		
		assertThat(status.equals(Status.ok.toString()));
			
		
	}*/

}
