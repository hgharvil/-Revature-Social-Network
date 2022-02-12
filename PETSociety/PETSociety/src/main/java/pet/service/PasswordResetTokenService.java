package pet.service;

import pet.model.PasswordResetToken;
import pet.model.User;

public interface PasswordResetTokenService {
	/**
	 * Method to create reset token for a given user
	 * @param user - User to tie token to
	 * @param token - Token generated for reset
	 */
	public void createPasswordResetTokenForUser(User user, String token);
	
	/**
	 * Method to verify a token
	 * @param code - String entered to verify matches token
	 * @return - PasswordResetToken object matched
	 */
	public PasswordResetToken verifyToken(String code);
	
	/**
	 * Method to delete a token that has been generated
	 * @param forgotPasswordToken - PasswordResetToken to delete
	 */
	public void deleteToken(PasswordResetToken forgotPasswordToken);
}
