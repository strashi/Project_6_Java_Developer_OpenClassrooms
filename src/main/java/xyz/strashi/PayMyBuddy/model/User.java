package xyz.strashi.PayMyBuddy.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name="user")
public class User  {
	
	public User(String firstName, String lastName, float balance) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.balance = balance;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="user_id", nullable = false, unique = true)
	private Long userId;
	
	@Enumerated(EnumType.STRING)
	private Role role = Role.USER;
	
	@Column(length = 50, nullable = false, unique = true)
	@NotBlank(message="email cannot be empty or null")
	@Email(message = " please enter a valid email address")
	private String email;
	
	@Column(length = 60)
	@NotBlank(message="password cannot be empty or null")
	private String password;
	
	@Column(name="first_name", length = 30)
	private String firstName;
	
	@Column(name="last_name", length = 30)
	private String lastName;
	
	@Column(nullable = false)
	private double balance;
	
	@LazyCollection(LazyCollectionOption.FALSE)
	@ManyToMany(
			cascade = { 
					CascadeType.PERSIST, 
					CascadeType.MERGE 
					}
			)
	List<BankAccount> bankAccounts = new ArrayList<>();
	
	@LazyCollection(LazyCollectionOption.FALSE)
	@OneToMany(cascade = CascadeType.ALL , orphanRemoval = true)
	@JoinColumn(name="user_id")
	List<Relationship> friends= new ArrayList<>();
		
	
}
