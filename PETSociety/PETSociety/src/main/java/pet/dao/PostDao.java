package pet.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pet.model.Post;
import pet.model.Profile;

/**
 * This interface is extending JpaRepository to get Post data from the database
 * @author Tyler
 */
@Repository
public interface PostDao extends JpaRepository<Post, Integer>{
	
	/**
	 * Method to find all posts tied to a specific Profile
	 * @param myProfile - Profile to find posts from
	 * @return - List<Post> that contains all posts from that profile
	 */
	public List<Post> findByMyProfile(Profile myProfile);
	
	/**
	 * Method to find all posts by id
	 * @param id - id to find posts 
	 * @return - List<Post> posts tied to given id
	 */
	public List<Post> findAllByPostId(int id);
}
