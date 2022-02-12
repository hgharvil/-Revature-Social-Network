package pet.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pet.dao.ProfileDao;
import pet.model.Profile;

/**
 * This class implements all methods in the ProfileService Interface, which works on handling any business logic for Profile objects.
 * @author Tyler
 */
@Service
public class ProfileServiceImpl implements ProfileService{
	ProfileDao dao;
	
	@Autowired
	public ProfileServiceImpl(ProfileDao dao) {
		this.dao=dao;
	}
	
	@Override
	public List<Profile> getAll(){
		return dao.findAll();
	}

	@Override
	public Profile getById(int id) {
		return dao.findByProfileId(id);
	}

	@Override
	public Profile updateProfile(Profile pro) {
		return dao.save(pro);
	}
}
