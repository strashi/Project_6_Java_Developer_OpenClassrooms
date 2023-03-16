package xyz.strashi.PayMyBuddy.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import xyz.strashi.PayMyBuddy.model.BankAccount;
import xyz.strashi.PayMyBuddy.model.Relationship;
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
		User user1 = new User(0L,"email1@xyz","password","firstName","lastName",50.0f, null, relationships);
		User user2 = new User(0L,"email2@xyz","password","firstName2","lastName2",50.0f, null, relationships);
		
		user1 = userRepository.save(user1);
		user2 = userRepository.save(user2);
		
		Status status = transactionService.executeTransaction(user1, user2, 50, "resto");
		
		assertThat(status.equals(Status.ok));
	}

}
