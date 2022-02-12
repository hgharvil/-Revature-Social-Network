package pet.service;



import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pet.dao.UserDao;
import pet.model.User;

@Service
public class UserServiceImpl implements UserService {

	UserDao dao;
	
	@Autowired
	public UserServiceImpl(UserDao dao) {
		this.dao = dao;
	}

	@Override
	public User register(User user) {
		return dao.save(user);
	}

	@Override
	public User login(String username, String password) {
		return dao.findByPetUsernameAndPetPassword(username, password);
	}
	
	@Override
	public User changePassword(int id, String newPassword) {
		User myUser = dao.findByUserId(id);
		myUser.setPetPassword(newPassword);
		return dao.save(myUser);
	}

	@Override
	public User findByEmail(String email) {
		return dao.findByPetEmail(email);
	}

	@Override
	public User findById(int id) {
		return dao.findByUserId(id);
	}

	@Override
	public List<User> findAll() {
		return dao.findAll();
	}


	

}
