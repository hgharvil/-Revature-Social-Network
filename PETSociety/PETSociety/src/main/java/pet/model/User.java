package pet.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Object for holding User information from the database
 * @author Tyler
 */
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name="pet_user")
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int userId;
	
	@Column(unique=true)
	private String petUsername;
	
//	@Column(name="pet_password")
	private String petPassword;
	
	@Column(unique=true)
	private String petEmail;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "profile_id_fk"/* , referencedColumnName="profile_id" */)
	private Profile myProfile;
	
	/**
	 * Constructor for creating a User that requires the username, password, and email
	 * @param username - Username of the User account to be created
	 * @param password - Password of the User account to be created
	 * @param email - Email of the User account to be created
	 */
	public User(String username, String password, String email) {
		this.petUsername=username;
		this.petPassword=password;
		this.petEmail=email;
	}
	
	/**
	 * Constructor for creating a User that simply knows it's username and the profile tied to it (Useful for sending to JS)
	 * @param username - Username of this User
	 * @param profile - Profile of this User
	 */
	public User(String username, Profile profile) {
		this.petUsername=username;
		this.myProfile = profile;
	}
	
	/**
	 * Constructor for creating a User that assigns the id, username and profile (for JS) ONLY USE AFTER RETRIEVING
	 * @param id - Id of this User
	 * @param username - Username of this User
	 * @param profile - Profile of this User
	 */
	public User(int id, String username, Profile profile) {
		this.userId = id;
		this.petUsername=username;
		this.myProfile = profile;
	}
	
	@Override
	public String toString() {
		return "\nUSER : [ Id : " + this.userId + " Username : " + this.petUsername + "]";
	}
	
}
