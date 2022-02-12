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

import pet.dao.ProfileDao;
import pet.model.Profile;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class ProfileServiceImplTest {

	@Mock
	private ProfileDao dao;
	
	private ProfileService serv;
	
	@BeforeEach
	void setUp() throws Exception {
		serv = new ProfileServiceImpl(dao);
	}
	
	@Test
	void selectAllProfile() {
		List<Profile> initial = new ArrayList<>();
		initial.add(new Profile("Testie","Tester","This is a test"));
		List<Profile> expected = new ArrayList<>();
		expected.addAll(initial);
		when(dao.findAll()).thenReturn(initial);
		
		List<Profile> test = serv.getAll();
		
		verify(dao, times(1)).findAll();
		assertEquals(expected,test);
	}
}
