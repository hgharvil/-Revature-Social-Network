package pet.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pet.model.PasswordResetToken;
import pet.model.User;


@Repository
public interface PasswordResetTokenDao extends JpaRepository<PasswordResetToken, Integer> {
	/**
	 * Method to find a passwordResetToken by the String token itself
	 * @param token - Token to retrieve
	 * @return - PasswordResetToken tied to the token String
	 */
	public PasswordResetToken findByToken(String token);
	
	/**
	 * Method to find a PasswordResetToken by the user it's tied to
	 * @param user - User tied to token
	 * @return - PasswordResetToken retrieved
	 */
	public PasswordResetToken findByUser(User user);
}
