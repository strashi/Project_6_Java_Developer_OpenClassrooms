package xyz.strashi.PayMyBuddy.configuration;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import xyz.strashi.PayMyBuddy.service.impl.UserDetailService;
/**
 * Security configuration allowing connection with the app
 * 
 * @author steve
 *
 */
@Configuration
@EnableWebSecurity
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter{
	
//	@Autowired
//	private UserDetailService userDetailsService;
	
	@Bean
	public UserDetailsService userDetailsService() {
		return new UserDetailService();
	}
	
	
	@Autowired
	private DataSource dataSource;
		 		
	@Override
	public void configure(HttpSecurity http) throws Exception{
		http.authorizeRequests()
		.antMatchers("/").hasAnyAuthority("USER","ADMIN")
		.antMatchers("/admin").hasAuthority("ADMIN")
		.antMatchers("/createUser").permitAll()
		.anyRequest().authenticated()
		.and()
		.formLogin()
		  .loginPage("/login")
		  .permitAll() 
	     .and()
	      .rememberMe()
	      .tokenRepository(persistentTokenRepository())
	     .and()
	    .logout()
	    .logoutRequestMatcher(new AntPathRequestMatcher("/logout","GET"))
	    .logoutSuccessUrl("/login")
	    .permitAll();
	      
		
	}
	
    
	@Bean
	public PersistentTokenRepository persistentTokenRepository() {
		JdbcTokenRepositoryImpl tokenRepository = new JdbcTokenRepositoryImpl();
		tokenRepository.setDataSource(dataSource);
		return tokenRepository;
	}
	
	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	
//	@Bean
//	public AuthenticationManager authenticationManager() {
//		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
//		provider.setPasswordEncoder(passwordEncoder());
//		provider.setUserDetailsService(userDetailsService);
//		return new ProviderManager(provider);
//	}
	
//	@Bean
//	public DaoAuthenticationProvider authenticationProvider() {
//		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
//		authProvider.setUserDetailsService(userDetailsService());
//		authProvider.setPasswordEncoder(passwordEncoder());
//		return authProvider;
//	}.
	
	@Bean
	public AuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		authProvider.setUserDetailsService(userDetailsService());
		authProvider.setPasswordEncoder(passwordEncoder());
		return authProvider;
	}
	
//	 @Override
//	    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//	        auth.authenticationProvider(authenticationProvider());
//	    }
	
	
}

