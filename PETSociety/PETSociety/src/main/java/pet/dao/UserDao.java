package pet.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pet.model.User;

/**
 * This interface is extending JpaRepository to get User data from the database
 * @author Tyler
 */
@Repository
public interface UserDao extends JpaRepository<User, Integer>{
	
	/**
	 * Method to find a user by their username and password
	 * @param petUsername - Username of User to find
	 * @param petPassword - Password of User to find
	 * @return - User found by username and password
	 */
	public User findByPetUsernameAndPetPassword(String petUsername, String petPassword);
	
	/**
	 * Method to find a user by their email
	 * @param petEmail - email of User to find
	 * @return - User found by given email
	 */
	public User findByPetEmail(String petEmail);
	
	/**
	 * Method to find a user by their id
	 * @param userId - id to find User tied to
	 * @return - User tied to given id
	 */
	public User findByUserId(int userId);
}
