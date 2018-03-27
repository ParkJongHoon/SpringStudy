package test;

import java.sql.SQLException;

import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.JUnitCore;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import dao.CountingConnectionMaker;
import dao.CountingDataSource;
import dao.UserDao;
import dao.UserDao3;
import dao.UserDao4;
import dao.UserDao8;
import dao.UserDao9;
import domain.Level;
import domain.User;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="/config/common/applicationContext.xml")
public class UserDaoTest2 {
	@Autowired
	private ApplicationContext context;
	
	@Autowired
	UserDao dao;
	
	String setId;
	User user1;
	User user2;
	User user3;
	@Before
	public void setUp(){
		
		// 2018-01-10 SpringDaoFactory의 UserDao3 안에 Connection을 주입함

		// 2018-01-10 ConnectionMaker를 확장해서 connection을 할 때 마다 카운터 되는 기능 확장
		// ConnectionMaker type을 주입받아서 기능 동작함
		//CountingConnectionMaker cds = context.getBean("countingDataSource", CountingDataSource.class);
		//this.dao = this.context.getBean("userDao", UserDao9.class);
		setId = "whiteship";
		this.user1 = new User("m05214", "박종훈", "test", Level.BASIC, 1, 0);
		this.user2 = new User("hyejoony", "전혜준", "test", Level.SILVER, 55, 10);
		this.user3 = new User("IU", "이지은", "test", Level.GOLD, 100, 40);
	}
	
	@Test
	public void deleteAll() throws SQLException, ClassNotFoundException{
		dao.deleteAll();
	}
	
	@Test
	public void delete() throws SQLException, ClassNotFoundException{
		dao.delete(setId);
	}
	
	@Test
	public void addAndGet(){
		dao.deleteAll();
		dao.add(user1);
		dao.add(user2);
		dao.add(user3);
		
		User userGet1 = dao.get(user1.getId());
		checkSameUser(userGet1, user1);
		
		User userGet2 = dao.get(user2.getId());
		checkSameUser(userGet2, user2);
	}
	/*
	@Test
	public void add() throws SQLException, ClassNotFoundException{
		user = new User();
		user.setId(setId);
		user.setName("박종훈");
		user.setPassword("test");
		dao.add(user);	
	}
	
	@Test
	public void get() throws SQLException, ClassNotFoundException{
		user = new User();
		user.setId(setId);
		user.setName("박종훈");
		user.setPassword("test");
		user2 = dao.get(user.getId());
		Assert.assertThat(user2.getName(), CoreMatchers.is(user.getName()));
		Assert.assertThat(user2.getPassword(), CoreMatchers.is(user.getPassword()));
	}
	*/
	
	@Test
	public void update(){
		dao.deleteAll();
		dao.add(user1);
		
		user1.setName("강다니엘");
		user1.setPassword("password");
		user1.setLevel(Level.GOLD);
		user1.setLogin(1000);
		user1.setRecommend(9999);
		dao.update(user1);
		
		User user1Update = dao.get(user1.getId());
		checkSameUser(user1Update, user1);
	}
	
	@Test
	public void count() throws SQLException, ClassNotFoundException{
		dao.add(user1);
		dao.add(user2);
		dao.add(user3);
		Assert.assertThat(dao.getCount(), CoreMatchers.is(3));
		
	}
	
	private void checkSameUser(User user1, User user2){
		Assert.assertThat(user1.getId(), CoreMatchers.is(user2.getId()));
		Assert.assertThat(user1.getName(), CoreMatchers.is(user2.getName()));
		Assert.assertThat(user1.getPassword(), CoreMatchers.is(user2.getPassword()));
		Assert.assertThat(user1.getLevel(), CoreMatchers.is(user2.getLevel()));
		Assert.assertThat(user1.getLogin(), CoreMatchers.is(user2.getLogin()));
		Assert.assertThat(user1.getRecommend(), CoreMatchers.is(user2.getRecommend()));
	}
	

}
