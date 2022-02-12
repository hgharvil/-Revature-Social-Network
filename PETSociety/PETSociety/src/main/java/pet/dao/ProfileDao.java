package pet.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pet.model.Profile;

/**
 * This interface is extending JpaRepository to get Profile data from the database
 * @author Tyler
 */
@Repository
public interface ProfileDao extends JpaRepository<Profile, Integer>{
	
	/**
	 * Method to find profile by it's id
	 * @param id - id of profile
	 * @return - Profile retrieved by id
	 */
	public Profile findByProfileId(int id);
	
}
