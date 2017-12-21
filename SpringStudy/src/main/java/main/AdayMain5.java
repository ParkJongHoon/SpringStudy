package main;

import java.sql.SQLException;

import dao.ConnectionMakerImpl;
import dao.SuperUserDao;
import dao.SubUserDao;
import dao.UserDao;
import dao.UserDao2;
import dao.UserDao3;
import dao.UserDaoFunction;
import domain.User;

public class AdayMain5 {

	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		
		/*
		 * 바뀌지 않는 정보를 상위 클래스에서 구현
		 * 바뀌는 정보를 하위 클래스에서 구현
		 * -> 템플릿 메소드 패턴(template method pattern)
		 */
		
		UserDao3 dao = new UserDao3(new ConnectionMakerImpl(false, null));
		
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
