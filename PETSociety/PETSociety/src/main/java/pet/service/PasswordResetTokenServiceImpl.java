package pet.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pet.dao.PasswordResetTokenDao;
import pet.model.PasswordResetToken;
import pet.model.User;

@Service
public class PasswordResetTokenServiceImpl implements PasswordResetTokenService {

	PasswordResetTokenDao dao;
	
	@Autowired
	public PasswordResetTokenServiceImpl(PasswordResetTokenDao dao) {
		this.dao = dao;
	}
	
	@Override
	public void createPasswordResetTokenForUser(User user, String token) {
		PasswordResetToken myToken = new PasswordResetToken(token,user);
		PasswordResetToken beforeToken = dao.findByUser(user);
		if(beforeToken != null) dao.delete(beforeToken);
		dao.save(myToken);
	}
	
	@Override
	public PasswordResetToken verifyToken(String code) {
		return dao.findByToken(code);
	}
	
	@Override
	public void deleteToken(PasswordResetToken forgotPasswordToken) {
		dao.delete(forgotPasswordToken);
	}

}
