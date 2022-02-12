package pet.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import pet.dao.PostDao;
import pet.model.Post;
import pet.model.Profile;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class PostServiceImplTest {

	@Mock
	private PostDao dao;
	
	private PostService serv;
	
	@BeforeEach
	void setUp() throws Exception {
		serv = new PostServiceImpl(dao);
	}

	@Test
	void createPost() {
		Post initial = new Post("This is a test post", 0, new Profile());
		when(dao.save(initial)).thenReturn(initial);
		
		Boolean works = serv.createPost(initial);
		
		verify(dao, times(1)).save(initial);
		assertEquals(works,true);
	}
	
	@Test
	void selectAllPost() {
		List<Post> initial = new ArrayList<>();
		initial.add(new Post("This is a test post", 0, new Profile()));
		List<Post> expected = new ArrayList<>();
		expected.addAll(initial);
		when(dao.findAll()).thenReturn(initial);
		
		List<Post> test = serv.selectAllPost();
		
		verify(dao, times(1)).findAll();
		assertEquals(expected,test);
	}
	
	@Test
	void selectUserPost() {
		Profile testPro = new Profile();
		List<Post> initial = new ArrayList<>();
		initial.add(new Post("This is a test post", 0, testPro));
		List<Post> expected = new ArrayList<>();
		expected.addAll(initial);
		when(dao.findByMyProfile(testPro)).thenReturn(initial);
		
		List<Post> test = serv.selectUserPost(testPro);
		
		verify(dao, times(1)).findByMyProfile(testPro);
		assertEquals(expected,test);
	}

}
