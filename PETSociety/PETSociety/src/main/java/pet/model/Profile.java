package pet.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Object for holding Profile information from the database
 * @author Tyler
 */
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name="profile")
public class Profile {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
//	@Column(name="profile_id")
	private int profileId;
	
//	@Column(name="first_name")
	private String firstName;
	
//	@Column(name="last_name")
	private String lastName;
	
//	@Column(name="description")
	private String description;
	
	private String profilePic; 
	
	@OneToMany(mappedBy = "myProfile", fetch=FetchType.EAGER)
	private List<Post> posts = new ArrayList<>();
	
	@OneToOne(mappedBy= "myProfile")
	@JsonIgnore
	private User myUser;
	
	/**
	 * Constructor to create a Profile object using firstName, lastName and Description
	 * @param firstName - the first name of the User tied to this profile
	 * @param lastName - the last name of the User tied to this profile
	 * @param description - A brief description of the User tied to this profile
	 */
	public Profile(String firstName, String lastName, String description) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.description = description;
	}
	
	@Override
	public String toString() {
		return "\nPROFILE: [ Id : " + this.profileId + "First Name : " + this.firstName + "Last Name : " + this.lastName + " Description : " + this.description + "]";
	}
}
