package xyz.strashi.PayMyBuddy.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import xyz.strashi.PayMyBuddy.model.Relationship;
import xyz.strashi.PayMyBuddy.model.Role;
import xyz.strashi.PayMyBuddy.model.User;
import xyz.strashi.PayMyBuddy.service.UserService;
import xyz.strashi.PayMyBuddy.service.impl.UserDetailService;
import xyz.strashi.PayMyBuddy.service.impl.Utility;

//@SpringBootTest
@WebMvcTest(controllers = HomeController.class)
//@ExtendWith(MockitoExtension.class)
//@ContextConfiguration
@AutoConfigureMockMvc(addFilters=false)
public class HomeControllerTests {
	
	@Autowired
	private MockMvc mockMvc;
	/*
	@Autowired
	private UserRepository userRepository;*/
	
	@Autowired
	private UserService userService ;

	@MockBean
	private UserDetailService userDetailService;
	
	@MockBean
	private Utility utility;
	
	
	@Test
	public void testHome() throws Exception{
		
		Principal principal = Mockito.mock(Principal.class);
		Mockito.when(principal.getName()).thenReturn("email1@xyz");
		RequestBuilder request = MockMvcRequestBuilders.get("/").principal(principal).accept(MediaType.APPLICATION_JSON);
		/*
		List<Relationship> relationships = new ArrayList<>();
		User user = new User(0L,Role.USER,"email1@xyz","password","firstName","lastName",50.0f, null, relationships);
		//when(principal.getName()).thenReturn(p.getName());
		//when(principal.getName()).thenReturn("email1@xyz");
		when(userService.findByEmail(any(String.class))).thenReturn(user);
		//when(userService.findByEmail(principal.getName())).thenReturn(user);
		*/
		mockMvc.perform(request).andExpect(status().isOk()).andDo(print());
		
	}
	/*
	@Test
	public void testDepositMoney() throws Exception{
		
		mockMvc.perform(post("/").param("principal","p").param("bankAccount","XXX").param("amount", "100"))
		.andExpect(status().isFound()).andDo(print())
		.andExpect(view().name("redirect:/"));
	}
	
	@Test
	public void testBankDeposit() throws Exception{
		mockMvc.perform(post("/bankDeposit").param("principal","p").param("bankAccount","XXX").param("amount", "100"))
		.andExpect(status().isFound()).andDo(print())
		.andExpect(view().name("redirect:/"));
	}
	
	@Test
	public void testContact() throws Exception{
		
		mockMvc.perform(get("/contact")).andExpect(status().isOk()).andDo(print());
		
	}
	
	@Test
	public void testGetCreateUser() throws Exception{
		
		mockMvc.perform(get("/createUser")).andExpect(status().isOk()).andDo(print());
		
	}
	
	@Test
	public void testPostCreateUser() throws Exception{
		
		List<Relationship> relationships = new ArrayList<>();
		User user = new User(0L,Role.USER,"email1@xyz","password","firstName","lastName",50.0f, null, relationships);
		when(userService.createUser(user)).thenReturn(user);
		
		mockMvc.perform(post("/createUser").param("user", "user"))
		.andExpect(status().isFound()).andDo(print())
		.andExpect(view().name("redirect:/login"));
	}

	@Test
	public void testGetAddBankAccount() throws Exception{
		
		mockMvc.perform(get("/addBankAccount")).andExpect(status().isOk()).andDo(print());
		
	}
	
	@Test
	public void testPostAddBankAccount() throws Exception{
				
		List<Relationship> relationships = new ArrayList<>();
		User user = new User(0L,Role.USER,"email1@xyz","password","firstName","lastName",50.0f, null, relationships);
		//when(principal.getName()).thenReturn(p.getName());
		//when(principal.getName()).thenReturn("email1@xyz");
		when(userService.findByEmail(any(String.class))).thenReturn(user);
		
		mockMvc.perform(post("/addBankAccount").param("principal", "p")
				.param("accountDescription", "compteTest","ibanNumber","XX123456"))
		.andExpect(status().isOk()).andDo(print());
		//.andExpect(view().name("redirect:/"));
	}
*/
	
}


