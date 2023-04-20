package xyz.strashi.PayMyBuddy.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;

//@ExtendWith(SpringExtension.class)
@SpringBootTest
//@WebMvcTest(controllers = HomeController.class)
//@ContextConfiguration
@AutoConfigureMockMvc
public class HomeControllerTests {
	
	@Autowired
	private MockMvc mockMvc;
	
	@Test
	@WithUserDetails("email1@xyz")
	public void testHome() throws Exception{
		
		//RequestBuilder request = MockMvcRequestBuilders.get("/").accept(MediaType.APPLICATION_JSON);
	
		//mockMvc.perform(request).andExpect(status().isOk()).andDo(print());
		
		mockMvc.perform(get("/")).andExpect(status().isOk()).andDo(print());
		
	}
	/*
	@Test
	@WithUserDetails("email1@xyz")
	public void testDepositMoney() throws Exception{
		
		RequestBuilder request = MockMvcRequestBuilders.post("/")
				.param("bankAccount","XXX").param("amount", "100").accept(MediaType.APPLICATION_JSON);
		
		//.andExpect(view().name("redirect:/"));
		
		mockMvc.perform(request).andExpect(status().isOk()).andDo(print());
		
	}*/
	/*
	@Test
	@WithUserDetails("email1@xyz")
	public void testBankDeposit() throws Exception{
		mockMvc.perform(post("/bankDeposit").param("principal","p").param("bankAccount","XXX").param("amount", "100"))
		.andExpect(status().isFound()).andDo(print())
		.andExpect(view().name("redirect:/"));
	}
	*/
	@Test
	@WithUserDetails("email1@xyz")
	public void testContact() throws Exception{
		
		mockMvc.perform(get("/contact")).andExpect(status().isOk()).andDo(print());
		
	}
	
	@Test
	@WithUserDetails("email1@xyz")
	public void testGetCreateUser() throws Exception{
		
		mockMvc.perform(get("/createUser")).andExpect(status().isOk()).andDo(print());
		
	}
	/*
	@Test
	@WithUserDetails("email1@xyz")
	public void testPostCreateUser() throws Exception{
		
		List<Relationship> relationships = new ArrayList<>();
		User user = new User(0L,Role.USER,"email1@xyz","password","firstName","lastName",50.0f, null, relationships);
		when(userService.createUser(user)).thenReturn(user);
		
		mockMvc.perform(post("/createUser").param("user", "user"))
		.andExpect(status().isFound()).andDo(print())
		.andExpect(view().name("redirect:/login"));
	}
*/
	@Test
	@WithUserDetails("email1@xyz")
	public void testGetAddBankAccount() throws Exception{
		
		mockMvc.perform(get("/addBankAccount")).andExpect(status().isOk()).andDo(print());
		
	}
	/*
	@Test
	@WithUserDetails("email1@xyz")
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


