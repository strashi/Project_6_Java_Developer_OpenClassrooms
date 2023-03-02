package xyz.strashi.PayMyBuddy.service;

import java.util.List;

import xyz.strashi.PayMyBuddy.model.User;

public interface UserService {
	
	public User createUser(User user);

	public void loginUser(String email, String password);
	
	public void depositMoney(float amount);

	public List<User> getUsers();
}
