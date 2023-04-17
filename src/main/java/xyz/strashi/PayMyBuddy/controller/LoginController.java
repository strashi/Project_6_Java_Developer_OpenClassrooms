package xyz.strashi.PayMyBuddy.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class LoginController {
	
	private static final Logger logger = LoggerFactory.getLogger(LoginController.class);
	
	@GetMapping("/login")
	public String login() {
		logger.debug("GetMapping sollicité de LoginController");
		try {
			logger.info("GetMapping réussi de LoginController");
			return "login";
		}catch (Exception e) {
			logger.error("Erreur au LoginController", e);
			return null;
		}
	    
	  }
}
