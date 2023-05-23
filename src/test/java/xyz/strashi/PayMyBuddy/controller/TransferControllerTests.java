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

@SpringBootTest
@AutoConfigureMockMvc
public class TransferControllerTests {

	@Autowired
	private MockMvc mockMvc;
	
	@Test
	@WithMockUser(username = "email1@xyz",password = "password",authorities= {"USER"})
	public void testTransfertGet() throws Exception {
		
		mockMvc.perform(get("/transfer")).andExpect(status().isOk()).andDo(print());
	}
	
	@Test
	@WithMockUser(username = "email1@xyz",password = "password",authorities= {"USER"})
	public void testTransfertPost() throws Exception {
		
		RequestBuilder request = MockMvcRequestBuilders.post("/transfer")
				.param("principal","principal").param("emailCreditor", "email@creditor").param("amount", "100").param("description", "description").accept(MediaType.APPLICATION_FORM_URLENCODED_VALUE).with(csrf());
						
		mockMvc.perform(request)
		.andExpect(status().isFound()).andDo(print())
		.andExpect(view().name("redirect:/transfer"));
		
	}
	
	@Test
	@WithMockUser(username = "email1@xyz",password = "password",authorities= {"USER"})
	public void testAddRelationshipGet() throws Exception {
		
		mockMvc.perform(get("/addRelationship")).andExpect(status().isOk()).andDo(print());
	}
	
	@Test
	@WithMockUser(username = "email1@xyz",password = "password",authorities= {"USER"})
	public void testAddRelationshipPost() throws Exception {
		
		RequestBuilder request = MockMvcRequestBuilders.post("/addRelationship")
				.param("principal","principal").param("emailFriend", "email@friend").accept(MediaType.APPLICATION_FORM_URLENCODED_VALUE).with(csrf());
						
		mockMvc.perform(request)
		.andExpect(status().isFound()).andDo(print())
		.andExpect(view().name("redirect:/transfer"));
		
	}
}
