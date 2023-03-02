package xyz.strashi.PayMyBuddy.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import xyz.strashi.PayMyBuddy.model.User;
import xyz.strashi.PayMyBuddy.repository.UserRepository;
import xyz.strashi.PayMyBuddy.service.UserService;

@Service
public class UserServiceImpl implements UserService{
	
	@Autowired
	private UserRepository userRepository;
	
	@Override
	public User createUser(User user) {
		//User userSaved = userRepository.save(user);
		return userRepository.save(user);
	}

	@Override
	public void loginUser(String email, String password) {
		User user = userRepository.findByEmail(email);
		if(password.equals(user.getPassword())) {
			
		}
	}

	@Override
	public void depositMoney(float amount) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<User> getUsers() {
		// TODO Auto-generated method stub
		return userRepository.findAll();
	}

}
