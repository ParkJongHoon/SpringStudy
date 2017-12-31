package main;

import java.sql.SQLException;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

import dao.ConnectionMaker;
import dao.ConnectionMakerImpl;
import dao.CountingConnectionMaker;
import dao.DaoFactory;
import dao.SpringDaoFactory;
import dao.SuperUserDao;
import dao.SubUserDao;
import dao.UserDao;
import dao.UserDao2;
import dao.UserDao3;
import dao.UserDaoFunction;
import domain.User;

public class AdayMain9 {

	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		
		/*
		 * 바뀌지 않는 정보를 상위 클래스에서 구현
		 * 바뀌는 정보를 하위 클래스에서 구현
		 * -> 템플릿 메소드 패턴(template method pattern)
		 */
		

		/*
		 * xml에 bean 정보를 가지고 Spring bean 객체를 생성한다.
		 */
		ApplicationContext context = 
				new GenericXmlApplicationContext("/config/common/ApplicationContext.xml");
		// 2018-01-10 SpringDaoFactory의 UserDao3 안에 Connection을 주입함
		UserDao3 dao = context.getBean("userDao", UserDao3.class);
		// 2018-01-10 ConnectionMaker를 확장해서 connection을 할 때 마다 카운터 되는 기능 확장
		// ConnectionMaker type을 주입받아서 기능 동작함
		CountingConnectionMaker ccm = context.getBean("connectionMaker", CountingConnectionMaker.class);
		User user = new User();
		user.setId("whiteship");
		user.setName("박종훈");
		user.setPassword("test");
		dao.add(user);
		System.out.println(user.getId() + " 등록 성공");
		
		User user2 = dao.get(user.getId());
		System.out.println(user2.getName());
		
		System.out.println(user2.getPassword());
		
		System.out.println(user2.getId() + " 조회 성공");
		// connection counter를 찍어준다.
		System.out.println("Connection counter: " +ccm.getCounter());

	}

}
