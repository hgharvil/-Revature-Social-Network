package pet.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Object for holding Post information from the database
 * @author Tyler
 */
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name="post")
public class Post {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
//	@Column(name="post_id")
	private int postId;
	
//	@Column(name="text")
	private String text;
	
//	@Column(name="likes")
	private int likes;
	
	private String img;
	
	
	@ManyToOne(cascade = CascadeType.MERGE, fetch=FetchType.LAZY)
	@JoinColumn(name = "profile_id_fk")
	@JsonIgnore
	private Profile myProfile;

	/**
	 * Constructor for Creating a post with the text, likes, and Profile known
	 * @param text - Text in the body of the Post
	 * @param likes - Likes on the Post
	 * @param myProfile - Profile that created the post
	 */
	public Post(String text, int likes, Profile myProfile) {
		super();
		this.text = text;
		this.likes = likes;
		this.myProfile = myProfile;
	}
	
	@Override
	public String toString() {
		return  "\nPOST [Id: " + this.postId + "Text : " + this.text + "Likes : " +  this.likes + "]";
	}
	
}
