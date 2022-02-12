package pet.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import pet.dao.UserDao;
import pet.model.User;

public class UserServiceImplTest {
	
	@Mock
	private UserDao dao;
	
	private UserService serv;
	
	@BeforeEach
	void setUp() throws Exception {
		serv = new UserServiceImpl(dao);
	}
	
//	@Test
//	void register() {
//		User initial = new User("test","1234", "test@test.test");
//		User expected = new User("test","1234", "test@test.test");
//		when(dao.save(initial)).thenReturn(initial);
//		
//		User works = serv.register(initial);
//		
//		verify(dao, times(1)).save(initial);
//		assertEquals(works,expected);
//	}
	
//	@Test
//	void login() {
//		User initial = new User("test","1234", null);
//		User expected = new User("test","1234", null);
//		when(dao.findByPetUsernameAndPetPassword("test", "1234")).thenReturn(initial);
//		
//		User works = serv.login("test","1234");
//		
//		verify(dao, times(1)).findByPetUsernameAndPetPassword("test", "1234");
//		assertEquals(works,expected);
//	}

}
