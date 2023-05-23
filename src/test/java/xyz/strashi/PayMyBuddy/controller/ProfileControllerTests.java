package xyz.strashi.PayMyBuddy.controller;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import xyz.strashi.PayMyBuddy.model.Role;
import xyz.strashi.PayMyBuddy.model.User;

@SpringBootTest
@AutoConfigureMockMvc
public class ProfileControllerTests {
	@Autowired
	private MockMvc mockMvc;
	
	@Test
	@WithMockUser(username = "email1@xyz",password = "password",authorities= {"USER"})
	public void testProfileGet() throws Exception {
		
		mockMvc.perform(get("/profile")).andExpect(status().isOk()).andDo(print());
	}
	
	@Test
	@WithMockUser(username = "email1@xyz",password = "password",authorities= {"USER"})
	public void testUpdate() throws Exception {
		
		User user = new User(0L,Role.USER,"email1@xyz","password","firstName","lastName",50.0d, null, null);
		
		RequestBuilder request = MockMvcRequestBuilders.post("/profile")
				.param("user", user.toString()).param("principal","principal").param("redirAttrs", "redirAttrs").accept(MediaType.APPLICATION_FORM_URLENCODED_VALUE).with(csrf());
						
		mockMvc.perform(request)
		.andExpect(status().isFound()).andDo(print())
		.andExpect(view().name("redirect:/"));
		
	}
}
