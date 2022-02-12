package pet.service;

import java.util.List;

import pet.model.Post;
import pet.model.Profile;

/**
 * This interface outlines methods that must be implemented to fulfill the business logic of Post objects
 * @author Tyler
 */
public interface PostService {
	
	/**
	 * This is a method to create a new post in the Database. 
	 * @param myPost - Post to be created in the database.
	 * @return - Boolean value true if the post was created, false if it was not. 
	 */
	public boolean createPost(Post myPost);
	
	/**
	 * This is a method to select all posts inside the database.
	 * @return - List of posts within the database, or null if the operation fails.
	 */
	public List<Post> selectAllPost();
	
	/**
	 * This is a method to select all posts tied to a specific profile
	 * @param myProfile - profile to get posts tied to
	 * @return - List <Post> of posts tied to given profile
	 */
	public List<Post> selectUserPost(Profile myProfile);

	/**
	 * Method to update likes on a post
	 * @param np - Post to update
	 * @return - Post after update
	 */
	public Post updatePost(Post np);
	
	/**
	 * Method to select a post by it's id
	 * @param id - id to select post by
	 * @return - Post selected
	 */
	public Post selectPostById(int id);
}
