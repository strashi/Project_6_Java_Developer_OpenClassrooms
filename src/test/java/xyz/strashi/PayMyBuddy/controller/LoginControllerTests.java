package xyz.strashi.PayMyBuddy.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import xyz.strashi.PayMyBuddy.model.Relationship;
import xyz.strashi.PayMyBuddy.model.Role;
import xyz.strashi.PayMyBuddy.model.User;
import xyz.strashi.PayMyBuddy.service.UserService;
import xyz.strashi.PayMyBuddy.service.impl.UserDetailService;
import xyz.strashi.PayMyBuddy.service.impl.Utility;

@WebMvcTest(controllers = LoginController.class)
@AutoConfigureMockMvc(addFilters=false)
public class LoginControllerTests {

	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private UserService userService ;
	
	@MockBean
	private UserDetailService userDetailService;
	
	@MockBean
	private Utility utility;
	
	@Test
	public void testLogin() throws Exception{
		
		mockMvc.perform(get("/login")).andExpect(status().isOk()).andDo(print());
		
	}
	
}
