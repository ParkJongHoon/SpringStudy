package test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.sql.DataSource;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.mockito.Mockito.*;

import org.mockito.ArgumentCaptor;

import service.UserService;
import service.UserServiceImpl;
import service.MockMailSender;
import service.UserServiceTx;
import dao.UserDao;
import domain.Level;
import domain.User;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="/config/common/applicationContext.xml")
public class UserServiceTest {

	@Autowired
	private ApplicationContext context;
	
	@Autowired
	UserDao userDao;
	
	@Autowired
	DataSourceTransactionManager transactionManager;
	
	@Autowired
	UserService userService;
	
	@Autowired
	UserServiceImpl userServiceImpl;
	
	@Autowired
	MailSender mailSender;
	
	User user;
	List<User> users; // 테스트 픽스처
	
	
	
	@Before
	public void setUp(){
		users = Arrays.asList(
				new User("bumjin", "박범진", "p1", Level.BASIC, 49, 0, "m05214@naver.com"),
				new User("joytouch", "강명성", "p2", Level.BASIC, 50, 0, "m05214@naver.com"),
				new User("erwins", "신승한", "p3", Level.SILVER, 60, 29, "m05214@naver.com"),
				new User("madnite1", "이상호", "p4", Level.SILVER, 60, 30, "m05214@naver.com"),
				new User("green", "오민규", "p5", Level.GOLD, 100, 100, "m05214@naver.com")
		);
	}
	/*
	@Test
	@DirtiesContext
	public void upgradeLevels() throws Exception{
		userDao.deleteAll();
		for(User user : users) userDao.add(user);
		MockMailSender mockMailSender = new MockMailSender();
		userServiceImpl.setMailSender(mockMailSender);
		
		
		UserServiceTx txUserService = (UserServiceTx) userService;
		txUserService.setTransactionManager(transactionManager);
		txUserService.setUserService(userServiceImpl);
		
		txUserService.upgradeLevels();
		
		checkLevelUpgraded(users.get(0), false);
		checkLevelUpgraded(users.get(1), true);
		checkLevelUpgraded(users.get(2), false);
		checkLevelUpgraded(users.get(3), true);
		checkLevelUpgraded(users.get(4), false);
		
		List<String> request = mockMailSender.getRequests();
		assertThat(request.size(), is(2));
		assertThat(request.get(0), is(users.get(1).getEmail()));
		assertThat(request.get(1), is(users.get(3).getEmail()));
		
		
		
	}*/
	
	/*
	 * 작성일: 2018-05-26
	 * 작성자: 박종훈
	 * 작성내용: 선언적 트랜잭션을 Mock객체를 이용해서 처리 테스트
	 * -> Mock객체를 일일히 만들어야 하는 번거로움에 대한 문제가 존재
	 */
	
	@Test
	public void upgradeLevels() throws Exception{
		UserServiceImpl userServiceImpl = new UserServiceImpl();
		
		MockUserDao mockUserDao = new MockUserDao(this.users);
		userServiceImpl.setUserDao(mockUserDao);
		
		MockMailSender mockMailSender = new MockMailSender();
		userServiceImpl.setMailSender(mockMailSender);
		
		userServiceImpl.upgradeLevels();
		
		List<User> updated = mockUserDao.getUpdated();
		assertThat(updated.size(), is(2));
		checkLevelUpgraded(updated.get(0), "joytouch", Level.SILVER );
		checkLevelUpgraded(updated.get(1), "madnite1", Level.GOLD );
		
		List<String> request = mockMailSender.getRequests();
		assertThat(request.size(), is(2));
		assertThat(request.get(0), is(users.get(1).getEmail()));
		assertThat(request.get(1), is(users.get(3).getEmail()));
	}
	
	@Test
	public void mockUpgradeLevels() throws Exception{
		UserServiceImpl userServiceImpl = new UserServiceImpl();
		
		UserDao mockUserDao = mock(UserDao.class);
		when(mockUserDao.getAll()).thenReturn(this.users);
		userServiceImpl.setUserDao(mockUserDao);
		
		MailSender mockMailSender = mock(MailSender.class);
		userServiceImpl.setMailSender(mockMailSender);
		
		userServiceImpl.upgradeLevels();
		
		verify(mockUserDao, times(2)).update(any(User.class));
		verify(mockUserDao, times(2)).update(any(User.class));
		verify(mockUserDao).update(users.get(1));
		assertThat(users.get(1).getLevel(), is(Level.SILVER));
		verify(mockUserDao).update(users.get(3));
		assertThat(users.get(3).getLevel(), is(Level.GOLD));
		
		ArgumentCaptor<SimpleMailMessage> mailMessageArg =
				ArgumentCaptor.forClass(SimpleMailMessage.class);
		verify(mockMailSender, times(2)).send(mailMessageArg.capture());
		List<SimpleMailMessage> mailMessages = mailMessageArg.getAllValues();
		assertThat(mailMessages.get(0).getTo()[0], is(users.get(1).getEmail()));
		assertThat(mailMessages.get(1).getTo()[0], is(users.get(3).getEmail()));
	}
	
	@Test
	public void upgradeAllOrNothing() throws Exception{
		
		UserServiceTx txUserService = (UserServiceTx) userService;
		txUserService.setTransactionManager(transactionManager);
		txUserService.setUserService(userServiceImpl);
		
		userDao.deleteAll();
		for(User user : users) userDao.add(user);
		
		try{
		txUserService.upgradeLevels();
		}catch(Exception e){
			
		}
		
		
	}
	
	private void checkLevelUpgraded(User user, boolean upgraded){
		User userUpdate = userDao.get(user.getId());
		if(upgraded){
			
			assertThat(userUpdate.getLevel(), is(user.getLevel().nextLevel()));
		}else{
			assertThat(userUpdate.getLevel(), is(user.getLevel()));
		}
	}
	
	private void checkLevelUpgraded(User updated, String expectedId, Level expectedLevel){
		assertThat(updated.getId(), is(expectedId));
		assertThat(updated.getLevel(), is(expectedLevel));
	}
	
	static class MockUserDao implements UserDao{
		private List<User> users;
		private List<User> updated = new ArrayList<User>();
		
		private MockUserDao(List<User> users){
			this.users = users;
		}

		public List<User> getUpdated() {
			return updated;
		}

		public void setDataSource(DataSource dataSource) {
			// TODO Auto-generated method stub
			
		}

		public void deleteAll() {
			// TODO Auto-generated method stub
			
		}

		public void delete(String id) {
			// TODO Auto-generated method stub
			
		}

		public int getCount() throws SQLException {
			// TODO Auto-generated method stub
			return 0;
		}

		public User get(String id) {
			// TODO Auto-generated method stub
			return null;
		}

		public List<User> getAll() {
			return this.users;
		}

		public void add(User user) {
			// TODO Auto-generated method stub
			
		}

		public void update(User user) {
			updated.add(user);
		}
		
	}
	
}
