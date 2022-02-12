package pet.service;

import java.util.List;

import pet.model.Profile;

/**
 * This interface is a blueprint for the methods required to be a service class handling profiles
 * @author Tyler
 */
public interface ProfileService {
	
	/**
	 * This method calls the dao to get all profiles in the database
	 * @return - List<Profile> of all profiles in the database
	 */
	public List<Profile> getAll();
	
	/**
	 * Method to get a profile by it's id
	 * @param id - id to get profile by
	 * @return - Profile retrieved with given id
	 */
	public Profile getById(int id);
	
	/**
	 * Method to update a profile
	 * @param pro - profile to updated
	 * @return - Profile in database after update
	 */
	public Profile updateProfile(Profile pro);
}
