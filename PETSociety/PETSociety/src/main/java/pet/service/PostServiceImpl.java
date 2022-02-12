package pet.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pet.dao.PostDao;
import pet.model.Post;
import pet.model.Profile;

/**
 * This class implements PostService as well as being a Spring Bean
 * @author Tyler
 */
@Service
public class PostServiceImpl implements PostService {

	PostDao dao;
	
	@Autowired
	public PostServiceImpl(PostDao dao) {
		this.dao=dao;
	}
	
	@Override
	public boolean createPost(Post myPost) {
		
		if(!myPost.equals(dao.save(myPost))) {
			return false;
		}
		return true;
	}

	@Override
	public List<Post> selectAllPost() {
		return dao.findAll();
	}

	@Override
	public List<Post> selectUserPost(Profile myProfile) {
		return dao.findByMyProfile(myProfile);
	}

	@Override
	public Post updatePost(Post np) {
		return dao.save(np);
	}

	@Override
	public Post selectPostById(int id) {
		return dao.getById(id);
	}

	
	

	

}
