package xyz.strashi.PayMyBuddy.controller;

import java.security.Principal;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import xyz.strashi.PayMyBuddy.model.User;
import xyz.strashi.PayMyBuddy.service.UserService;
/**
 * The update page.
 * @author steve
 *
 */
@Controller
public class ProfileController {
	
	private static final Logger logger = LoggerFactory.getLogger(ProfileController.class);

	
	private UserService userService;
	
	public ProfileController(UserService userService) {
		this.userService = userService;
	}
	
	@GetMapping("/profile")
	public String profile(Model model, Principal principal) {
		logger.debug("GetMapping /profile sollicité de ProfileController");
		try {
			User user = userService.findByEmail(principal.getName());
			model.addAttribute("user", user);
			logger.info("GetMapping /profile réussi de ProfileController");
			return "profile";
		}catch (Exception e) {
			logger.error("Erreur au GetMapping /profile du ProfileController", e);
			return null;
		}
		
	}
	
	@PostMapping("/profile")
	public String update(User user, Principal principal) {
		logger.debug("PostMapping /profile sollicité de ProfileController");
		try {
		
			User userTest = userService.updateUser(user, principal);
			
			if(userTest == null) {
				logger.info("PostMapping /profile user null réussi de ProfileController");
				return "profile2";
			}else {
				logger.info("PostMapping /profile réussi de ProfileController");
				return "redirect:/";
			}
				
			
		}catch (Exception e) {
			logger.error("Erreur au PostMapping /profile du ProfileController", e);
			return null;
		}
	}
}
