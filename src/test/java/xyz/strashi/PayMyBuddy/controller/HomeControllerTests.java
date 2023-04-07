package xyz.strashi.PayMyBuddy.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import xyz.strashi.PayMyBuddy.service.UserService;

@WebMvcTest(controllers = HomeController.class)
public class HomeControllerTests {
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private UserService userService;
	/*
	@WithMockUser(username = "email1@xyz", password = "password", roles = "USER")
	@Test
	public void testHome() throws Exception{
		
		mockMvc.perform(get("/")).andExpect(status().isOk()).andDo(print());
		
	}*/
	/*
	@Test
	public void testDepositMoney() throws Exception{
		
		mockMvc.perform(post("/")).andExpect(status().isOk()).andDo(print());
	}*/
}
