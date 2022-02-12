package pet.model;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.NoArgsConstructor;
/**
 * This class is a model for PasswordResetToken in the database
 * @author Henry
 *
 */
@Data
@NoArgsConstructor
@Entity
@Table(name="reset_token")
public class PasswordResetToken {

	private static final int EXPIRATION = 60 * 24; //minutes * hours in the day (= one full day)
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	private String token;
	
	@OneToOne(targetEntity = User.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "user_id")
	@JsonIgnore
    private User user;
 
    private Timestamp expiryDate;
    
    /**
     * Constructor that creates a PasswordResetToken using the String token and the User to tie it to
     * @param token - String token generated for reset
     * @param user - User to tie token to
     */
	public PasswordResetToken(String token, User user) {
		super();
		this.token = token;
		this.user = user;
		expiryDate = new Timestamp(System.currentTimeMillis()+(1000*60*EXPIRATION));
	}
}
