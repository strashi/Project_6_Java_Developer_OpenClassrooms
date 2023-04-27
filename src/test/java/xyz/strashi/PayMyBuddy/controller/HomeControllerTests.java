package xyz.strashi.PayMyBuddy.controller;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.google.gson.Gson;

import xyz.strashi.PayMyBuddy.model.BankAccount;
import xyz.strashi.PayMyBuddy.model.Role;
import xyz.strashi.PayMyBuddy.model.User;
import xyz.strashi.PayMyBuddy.repository.BankAccountRepository;
import xyz.strashi.PayMyBuddy.repository.TransactionRepository;
import xyz.strashi.PayMyBuddy.repository.UserRepository;
import xyz.strashi.PayMyBuddy.service.impl.Utility;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(Lifecycle.PER_CLASS)
public class HomeControllerTests {
	
	@Autowired
	private  UserRepository userRepository;
	
	@Autowired
	private  BankAccountRepository bankAccountRepository;
	
	@Autowired
	private  TransactionRepository transactionRepository;
	
	@Autowired
	private Utility utility;
	
	@Autowired
	private MockMvc mockMvc;
	
//	@BeforeEach
//	public void init() {
//		userRepository.deleteAll();
//		bankAccountRepository.deleteAll();
//		transactionRepository.deleteAll();
//		User user = new User(0L,Role.USER,"email1@xyz","password","firstName","lastName",50.0f, null, null);
//		userRepository.save(user);
//		User admin = new User(0L,Role.ADMIN,"admin@paymybuddy.com","admin","admin","system", 100000.0f ,null,null);
//		userRepository.save(admin);
//		BankAccount bankAccount = new BankAccount("compte test","123456");
//		bankAccountRepository.save(bankAccount);
//
//	}

	@BeforeAll
	public void init() {
		transactionRepository.deleteAll();
		userRepository.deleteAll();
		bankAccountRepository.deleteAll();
		
		User user = new User(0L,Role.USER,"email1@xyz","password","firstName","lastName",50.0f, null, null);
		userRepository.save(user);
		User admin = new User(0L,Role.ADMIN,"admin@paymybuddy.com","admin","admin","system", 100000.0f ,null,null);
		userRepository.save(admin);
		BankAccount bankAccount = new BankAccount("compte test","123456");
		bankAccountRepository.save(bankAccount);

	}
	
//	@AfterAll
//	public void clean() {
//		userRepository.deleteAll();
//		bankAccountRepository.deleteAll();
//		transactionRepository.deleteAll();
//	}
	
	
	@Test
	@WithUserDetails("email1@xyz")
	public void testHome() throws Exception{
		
		mockMvc.perform(get("/")).andExpect(status().isOk()).andDo(print());
		
	}
	
	@Test
	@WithUserDetails("email1@xyz")
	public void testDepositMoney() throws Exception{
		
		//A creer une fois pour les tests
		//BankAccount bankAccount = new BankAccount("compte test","123456");
		//bankAccountRepository.save(bankAccount);
		
		RequestBuilder request = MockMvcRequestBuilders.post("/")
				.param("ibanNumber","123456").param("amount", "100").accept(MediaType.APPLICATION_FORM_URLENCODED_VALUE).with(csrf());
						
		mockMvc.perform(request)
		.andExpect(status().isFound()).andDo(print())
		.andExpect(view().name("redirect:/"));
	
	}
	
	@Test
	@WithUserDetails("email1@xyz")
	public void testBankDeposit() throws Exception{
		
		RequestBuilder request = MockMvcRequestBuilders.post("/bankDeposit")
				.param("ibanNumber","123456").param("amount", "100").accept(MediaType.APPLICATION_FORM_URLENCODED_VALUE).with(csrf());
		
		mockMvc.perform(request)
		.andExpect(status().isFound()).andDo(print())
		.andExpect(view().name("redirect:/"));
	}
	
	@Test
	@WithUserDetails("email1@xyz")
	public void testContact() throws Exception{
		
		mockMvc.perform(get("/contact")).andExpect(status().isOk()).andDo(print());
		
	}
	
	@Test
	//@WithUserDetails("email1@xyz")
	public void testGetCreateUser() throws Exception{
		
		mockMvc.perform(get("/createUser")).andExpect(status().isOk()).andDo(print());
		
	}
	
	@Test
	//@WithUserDetails("email1@xyz")
	public void testPostCreateUser() throws Exception{
		
		String hashedPassword = utility.encoder("password");
		User user = new User(0L,Role.USER,"email1@xyz",hashedPassword,"firstName","lastName",50.0f, null, null);
				
		Gson gson = new Gson();
		String json = gson.toJson(user);
		System.out.println("json =" +json);
		
		mockMvc.perform(post("/createUser").contentType(MediaType.APPLICATION_JSON).content(json).with(csrf()))
		.andExpect(status().isOk()).andDo(print())
		.andExpect(view().name("createUser2"));
	}
	
	@Test
	@WithUserDetails("email1@xyz")
	public void testGetAddBankAccount() throws Exception{
		
		mockMvc.perform(get("/addBankAccount")).andExpect(status().isOk()).andDo(print());
		
	}
	
	@Test
	@WithUserDetails("email1@xyz")
	public void testPostAddBankAccount() throws Exception{
				
		RequestBuilder request = MockMvcRequestBuilders.post("/addBankAccount")
				.param("accountDescription", "nouveau compte")
				.param("ibanNumber","123456")
				.accept(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
				.with(csrf());
						
		mockMvc.perform(request)
		.andExpect(status().isFound()).andDo(print())
		.andExpect(view().name("redirect:/"));
		

	}

	
}


