package xyz.strashi.PayMyBuddy.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name="users")
public class User {
	
	public User(String firstName, String lastName, float balance) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.balance = balance;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long userId;
	
	private String email;
	private String password;
	
	@Column(name="first_name")
	private String firstName;
	
	@Column(name="last_name")
	private String lastName;
	
	private float balance;
	private String ibanNummer;
	
	@ManyToMany
	List<User> friends;
	

}
