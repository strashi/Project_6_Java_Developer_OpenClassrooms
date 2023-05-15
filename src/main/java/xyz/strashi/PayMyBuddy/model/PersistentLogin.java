package xyz.strashi.PayMyBuddy.model;

import java.time.Instant;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name="persistent_logins")
public class PersistentLogin {
	  @Column(name = "username", length = 64, nullable = false)
	    private String email;

	    @Id
	    @GeneratedValue
	    @Column(name="series", length = 64)
	    private String series;

	    @Column(name="token", length = 64, nullable = false)
	    private String token;

	    @Column(name="last_used", nullable = false)
	    private Instant lastUsed;
}
