package pet.service;

import java.util.List;

import pet.model.User;

/**
 * This interface outlines the methods that must be implemented to fulfill the business logic tied to User objects
 * @author Tyler
 */
public interface UserService {
	/**
	 * Method to register a user in the database
	 * @param user - user to register
	 */
	public User register(User user);
	
	/**
	 * Method to login to the application
	 * @param username - username entered to login
	 * @param password - password entered to login
	 * @return - User object found matching credentials, or null if none exists
	 */
	public User login(String username, String password);
	
	/**
	 * 
	 * @param id
	 * @param newPassword
	 * @return
	 */
	public User changePassword(int id, String newPassword);
	
	/**
	 * Method to find a user by their email
	 * @param email - email address to search for
	 * @return - User object found matching email, or null if none exists
	 */
	public User findByEmail(String email);
	
	/**
	 * Method to find a user by their id
	 * @param id - id to find user by
	 * @return - User retrieved by given id
	 */
	public User findById(int id);
	
	/**
	 * Method to get all users from the database
	 * @return - List<User> of all users in the database
	 */
	public List<User> findAll();
}
