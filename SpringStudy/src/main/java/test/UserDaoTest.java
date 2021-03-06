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
import dao.UserDao3;
import dao.UserDao4;
import dao.UserDao8;
import domain.User;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="/config/common/applicationContext.xml")
public class UserDaoTest {
	@Autowired
	private ApplicationContext context;
	
	UserDao8 dao;
	String setId;
	User user;
	User user2;
	
	@Before
	public void setUp(){
		
		// 2018-01-10 SpringDaoFactory의 UserDao3 안에 Connection을 주입함
		this.dao = this.context.getBean("userDao", UserDao8.class);
		// 2018-01-10 ConnectionMaker를 확장해서 connection을 할 때 마다 카운터 되는 기능 확장
		// ConnectionMaker type을 주입받아서 기능 동작함
		//CountingConnectionMaker cds = context.getBean("countingDataSource", CountingDataSource.class);
		setId = "whiteship";
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
	
	@Test
	public void count() throws SQLException, ClassNotFoundException{
		Assert.assertThat(dao.getCount(), CoreMatchers.is(1));
		
	}
	

}
