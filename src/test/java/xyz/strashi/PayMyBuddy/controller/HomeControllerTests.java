package xyz.strashi.PayMyBuddy.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

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
	
	@MockBean
	private UserService userService ;
	
	@MockBean
	private UserDetailService userDetailService;
	
	@MockBean
	private Utility utility;
	
	/*
	@Test
	public void testHome() throws Exception{
		
		List<Relationship> relationships = new ArrayList<>();
		User user = new User(0L,Role.USER,"email1@xyz","password","firstName","lastName",50.0f, null, relationships);
		//when(principal.getName()).thenReturn(p.getName());
		//when(principal.getName()).thenReturn("email1@xyz");
		when(userService.findByEmail(any(String.class))).thenReturn(user);
		//when(userService.findByEmail(principal.getName())).thenReturn(user);
		
		mockMvc.perform(get("/")).andExpect(status().isOk()).andDo(print());
		
	}*/
	
	@Test
	//@DisplayName("testDepositMoney")
	public void testDepositMoney() throws Exception{
		
		mockMvc.perform(post("/").param("principal","p").param("bankAccount","XXX").param("amount", "100")).andExpect(status().isFound()).andDo(print());
	}
}


