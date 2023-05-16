package xyz.strashi.PayMyBuddy.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.google.gson.Gson;

import xyz.strashi.PayMyBuddy.model.Role;
import xyz.strashi.PayMyBuddy.model.User;
import xyz.strashi.PayMyBuddy.service.UserService;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(Lifecycle.PER_CLASS)
public class HomeControllerTests {
	
	@MockBean
	private UserService userService;
	
	@Autowired
	private MockMvc mockMvc;
	

	//@WithMockUser(username = "email1@xyz", password = "password", authorities= {"USER"})
	
	@Test
	@WithMockUser(username = "email1@xyz",password = "password",authorities= {"USER"})
	public void testHome() throws Exception{
		User user = new User(0L,Role.USER,"email1@xyz","password","firstName","lastName",50.0d, null, null);
		when(userService.findByEmail(any(String.class))).thenReturn(user);
		
		mockMvc.perform(get("/")).andExpect(status().isOk()).andDo(print());
		
	}
	
	@Test
	@WithMockUser(username = "email1@xyz", password = "password", authorities= {"USER"})
	public void testDepositMoney() throws Exception{
		
		RequestBuilder request = MockMvcRequestBuilders.post("/")
				.param("ibanNumber","123456").param("amount", "100").accept(MediaType.APPLICATION_FORM_URLENCODED_VALUE).with(csrf());
						
		mockMvc.perform(request)
		.andExpect(status().isFound()).andDo(print())
		.andExpect(view().name("redirect:/"));
	
	}
	
	@Test
	@WithMockUser(username = "email1@xyz", password = "password", authorities= {"USER"})
	public void testBankDeposit() throws Exception{
		
		RequestBuilder request = MockMvcRequestBuilders.post("/bankDeposit")
				.param("ibanNumber","123456").param("amount", "100").accept(MediaType.APPLICATION_FORM_URLENCODED_VALUE).with(csrf());
		
		mockMvc.perform(request)
		.andExpect(status().isFound()).andDo(print())
		.andExpect(view().name("redirect:/"));
	}
	
	@Test
	@WithMockUser(username = "email1@xyz", password = "password", authorities= {"USER"})
	public void testContact() throws Exception{
		
		mockMvc.perform(get("/contact")).andExpect(status().isOk()).andDo(print());
		
	}
	
	@Test
	public void testGetCreateUser() throws Exception{
		
		mockMvc.perform(get("/createUser")).andExpect(status().isOk()).andDo(print());
		
	}
	
	@Test
	@WithMockUser(username = "email1@xyz", password = "password", authorities= {"USER"})
	public void testPostCreateUser() throws Exception{
		
		User user = new User(0L,Role.USER,"email1@xyz","password","firstName","lastName",50.0d, null, null);
				
		Gson gson = new Gson();
		String json = gson.toJson(user);
		
		when(userService.createUser(any(User.class))).thenReturn(user);
		
		mockMvc.perform(post("/createUser").contentType(MediaType.APPLICATION_JSON).content(json).with(csrf()))
		.andExpect(status().isFound()).andDo(print())
		.andExpect(view().name("redirect:/login"));
	}
	
	@Test
	@WithMockUser(username = "email1@xyz", password = "password", authorities= {"USER"})
	public void testGetAddBankAccount() throws Exception{
		
		mockMvc.perform(get("/addBankAccount")).andExpect(status().isOk()).andDo(print());
		
	}
	
	@Test
	@WithMockUser(username = "email1@xyz", password = "password", authorities= {"USER"})
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


