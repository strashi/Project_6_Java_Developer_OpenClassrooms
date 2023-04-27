package xyz.strashi.PayMyBuddy.service.impl;

import java.util.Formatter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
/**
 * 2 utilities: to formate number with 2 decimal and to crypt the password
 * @author steve
 *
 */
@Service
public class Utility {
	
	private static final Logger logger = LoggerFactory.getLogger(Utility.class);
	
	@Autowired
	private PasswordEncoder passwordEncoder;
			
	public String amountFormatter(double amount) {
		logger.debug("amountFormatter sollicité de Utility");
		try {
			try (Formatter formatter = new Formatter()) {
				formatter.format("%.2f", amount);
				logger.info("amountFormatter effectuée de Utility");
				return formatter.toString();
			}
		}catch (Exception e) {
			logger.error("Erreur au amountFormatter de Utility", e);
			return null;
		}
	}
	
	
	public String encoder(String password) {
		logger.debug("encoder sollicité de Utility");
		try {
			logger.info("encoder effectuée de Utility");
			return passwordEncoder.encode(password);
		}catch (Exception e) {
			logger.error("Erreur au encoder de Utility", e);
			return null;
		}
	}
}
