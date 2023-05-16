package xyz.strashi.PayMyBuddy.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import xyz.strashi.PayMyBuddy.model.BankAccount;
import xyz.strashi.PayMyBuddy.model.Relationship;
import xyz.strashi.PayMyBuddy.model.Role;
import xyz.strashi.PayMyBuddy.model.Status;
import xyz.strashi.PayMyBuddy.model.Transaction;
import xyz.strashi.PayMyBuddy.model.User;
import xyz.strashi.PayMyBuddy.repository.TransactionRepository;
import xyz.strashi.PayMyBuddy.repository.UserRepository;

@SpringBootTest
public class TransactionServiceTests {
	
	@Autowired
	private UserService userService;
	
	@MockBean
	private UserRepository userRepository;
	
	@MockBean
	private TransactionRepository transactionRepository;
		
	@Autowired
	private TransactionService transactionService;
	
//	@BeforeEach
//	public void init() {
//		userRepository.deleteAll();
//		
//	}
//	
	@Test
	public void executeTransactionTest() {
		List<BankAccount> bankAccounts = null;
		List<Relationship> relationships = new ArrayList<>();
		User creditor = new User(1L,Role.USER,"email1@xyz","password","firstName","lastName",50.0f, null, relationships);
		User debitor = new User(2L,Role.USER,"email2@xyz","password","firstName2","lastName2",50.0f, null, relationships);
		//User admin = new User(2L,Role.ADMIN,"admin@paymybuddy.com","password","firstName","lastName",50.0f, null, null);
		
		when(userRepository.save(creditor)).thenReturn(creditor);
		when(userRepository.save(debitor)).thenReturn(debitor);
		//userRepository.save(admin);
//		when(debitor.getBalance()).thenReturn(100d);
//		when(creditor.getBalance()).thenReturn(0d);
		//doNothing().when(transactionRepository.save(any(Transaction.class)));

		
		String status = transactionService.executeTransaction(creditor, debitor, 50, "resto",false);
		
		assertThat(status.equals(Status.ok.toString()));
	}
	
	@Test
	public void getTaxTest() {
		
		User debitor = new User(0L,Role.USER,"email1@xyz","password","firstName","lastName",50.0f, null, null);
		User admin = new User(0L,Role.ADMIN,"admin","password","firstName","lastName",50.0f, null, null);
		Optional<User> opt = Optional.of(admin);
		when(userRepository.findByEmail(any(String.class))).thenReturn(opt);
		//User user1 = userRepository.findByEmail("email1@xyz").orElseThrow(() -> new UsernameNotFoundException("User not present"));
		
		String status = transactionService.getTax(debitor, 50d);
		
		assertThat(status.equals(Status.ok.toString()));
			
		
	}

}
