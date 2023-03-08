package xyz.strashi.PayMyBuddy.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import xyz.strashi.PayMyBuddy.model.Relationship;
import xyz.strashi.PayMyBuddy.model.User;
import xyz.strashi.PayMyBuddy.service.UserService;

@Controller
public class UserController {
	
	private UserService userService;
	
	public UserController(UserService userService) {
		this.userService = userService;
	}
	
	@GetMapping("/")
	public String home(Model model) {
		return "home";
	}
	
	@GetMapping("/createUser")
	public String createUser(Model model) {
		model.addAttribute("user", new User());
		return "createUser";
	}

	@PostMapping("/createUser" )
	public String createUser(Model model, @ModelAttribute User user) {
		
		userService.createUser(user);
		return "redirect:/createUser";
	}
	
	@GetMapping("/addRelationship")
	public String addRelationship(Model model) {
		model.addAttribute("relationship", new Relationship());
		return "addRelationship";
	}

	@PostMapping("/addRelationship" )
	public String addRelationship(Model model, @ModelAttribute User user1, @ModelAttribute User user2) {
		
		userService.addRelationship(user1,user2);
		return "redirect:/addRelationship";
	}
	
	@GetMapping("/getRelationships")
	public String getRelationships(Model model, User user) {
		userService.getRelationships(user);
		return "getRelationships";
	}
	
		
}
