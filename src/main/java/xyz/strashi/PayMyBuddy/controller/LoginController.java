package xyz.strashi.PayMyBuddy.controller;

import java.util.List;

import javax.annotation.security.RolesAllowed;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import xyz.strashi.PayMyBuddy.model.User;
import xyz.strashi.PayMyBuddy.service.UserService;

@Controller
public class LoginController {

	private UserService userService;
	
	public LoginController(UserService userService) {
		this.userService = userService;
	}
	
	@GetMapping("/")
	//@RolesAllowed("USER")
	public String home(Model model) {
		//List<User> users = userService.getUsers();
		//model.addAttribute("users", users);
		//model.addAttribute("user", new User());
		return "home";
	}

	@GetMapping("/addPerson")
	//@RolesAllowed("USER")
	public String addPerson(Model model) {
		model.addAttribute("user", new User());
		return "addPerson";
	}

	@PostMapping("/addPerson" )
	//@RolesAllowed("USER")
	public String savePerson(Model model, @ModelAttribute User user) {
		
		User test =	userService.createUser(user);
		
		return "redirect:/addPerson";
	}

}
