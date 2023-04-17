package xyz.strashi.PayMyBuddy.service.impl;

import java.util.Formatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class Utility {
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	public String amountFormatter(double amount) {
		try (Formatter formatter = new Formatter()) {
			formatter.format("%.2f", amount);
			return formatter.toString();
		}
	}
	
	
	public String encoder(String password) {
		return bCryptPasswordEncoder.encode(password);
	}
}
