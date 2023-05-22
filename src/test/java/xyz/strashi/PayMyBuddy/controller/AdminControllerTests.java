package xyz.strashi.PayMyBuddy.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
public class AdminControllerTests {
	
	@Autowired
	private MockMvc mockMvc;
	
	@Test
	@WithMockUser(username = "email1@xyz",password = "password",authorities= {"ADMIN"})
	public void testAdmin() throws Exception {
		
		mockMvc.perform(get("/admin")).andExpect(status().isOk()).andDo(print());
	}
}
