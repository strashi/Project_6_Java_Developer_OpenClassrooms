package xyz.strashi.PayMyBuddy.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

//@WebMvcTest(controllers = LoginController.class)
@SpringBootTest
@AutoConfigureMockMvc//(addFilters=false)
public class LoginControllerTests {

	@Autowired
	private MockMvc mockMvc;
	/*
	@MockBean
	private UserService userService ;
	
	@MockBean
	private UserDetailService userDetailService;
	
	@MockBean
	private Utility utility;
	*/
	@Test
	public void testLogin() throws Exception{
		
		mockMvc.perform(get("/login")).andExpect(status().isOk()).andDo(print());
		
	}
	
}
