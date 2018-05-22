package test;


import java.util.Arrays;
import java.util.List;



import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.mail.MailSender;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.hamcrest.Matchers.is;


import service.UserService;
import dao.UserDao;
import domain.Level;
import domain.User;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="/config/common/applicationContext.xml")
public class UserDaoTest5 {
	@Autowired
	private ApplicationContext context;
	
	@Autowired
	UserDao userDao;
	
	@Autowired
	UserService userService;
	
	
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
	
	@Test
	public void upgradeLevels(){
		userDao.deleteAll();
		for(User user: users){
			userDao.add(user);
		}
		
		userService.upgradeLevels();
		
		checkLevelUpgraded(users.get(0), false);
		checkLevelUpgraded(users.get(1), true);
		checkLevelUpgraded(users.get(2), false);
		checkLevelUpgraded(users.get(3), true);
		checkLevelUpgraded(users.get(4), false);
		
		
	}
	
	
	private void checkLevelUpgraded(User user, boolean upgraded){
		User userUpdate = userDao.get(user.getId());
		if(upgraded){
			
			assertThat(userUpdate.getLevel(), is(user.getLevel().nextLevel()));
		}else{
			assertThat(userUpdate.getLevel(), is(user.getLevel()));
		}
	}
	
	
	
	

}
