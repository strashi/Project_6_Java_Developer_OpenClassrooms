package xyz.strashi.PayMyBuddy.service.impl;

import org.slf4j.Logger;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import xyz.strashi.PayMyBuddy.model.User;
import xyz.strashi.PayMyBuddy.repository.UserRepository;
/**
 * Class used for the authentication
 * @author steve
 *
 */
@Service
public class UserDetailService implements UserDetailsService{
	
	private static final Logger logger = LoggerFactory.getLogger(UserDetailService.class);
	
	@Autowired
	private UserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		logger.debug("loadUserByUsername sollicité de UserDetailService");
		try {
			User user = userRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("User not present"));
			logger.info("loadUserByUsername effectuée de UserDetailService");
			return new org.springframework.security.core.userdetails.User(username, user.getPassword(), true, true, true, true, AuthorityUtils.createAuthorityList(user.getRole().toString()));
			
		}catch (Exception e) {
			logger.error("Erreur au loadUserByUsername de UserDetailService", e);
			return null;
		}
	}

}
