package main;

import java.sql.SQLException;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import dao.ConnectionMaker;
import dao.ConnectionMakerImpl;
import dao.DaoFactory;
import dao.SpringDaoFactory;
import dao.SuperUserDao;
import dao.SubUserDao;
import dao.UserDao;
import dao.UserDao2;
import dao.UserDao3;
import dao.UserDaoFunction;
import domain.User;

public class AdayMain7 {

	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		
		/*
		 * 바뀌지 않는 정보를 상위 클래스에서 구현
		 * 바뀌는 정보를 하위 클래스에서 구현
		 * -> 템플릿 메소드 패턴(template method pattern)
		 */
		
		// spring 애플리케이션 컨텍스트를 이용해서 UserDao3 객체를 생성 및 관계생성을 함
		// @Configuration이 붙은 자바코드를 설정정보로 사용하려면
		// AnnotationConfigApplication를 이용하면 된다.
		ApplicationContext context = new AnnotationConfigApplicationContext(SpringDaoFactory.class);
		UserDao3 dao = context.getBean("userDao", UserDao3.class);
		dao.setConnectionMaker(context.getBean("connectionMaker", ConnectionMaker.class));
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

	}

}
