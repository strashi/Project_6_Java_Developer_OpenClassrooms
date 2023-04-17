package xyz.strashi.PayMyBuddy.dto;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.ManyToMany;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
	
	private String email;
	
	@Column(name="first_name")
	private String firstName;
	
	@Column(name="last_name")
	private String lastName;
	
	private String balance;
	private String ibanNummer;
	
	@ManyToMany
	List<UserDTO> friendsDTO;
	
}
