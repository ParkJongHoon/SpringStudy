package dao;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import properties.DBInfoGetProperties;

// 애플리케이션 컨텍스트 또는 빈 팩토리가 사용할 설정정보라는 표시
@Configuration
public class SpringDaoFactory {
	
	// 오브젝트 생성을 담당하는 IoC용 메소드라는 표시
	@Bean
	public UserDao3 userDao(){
		return new UserDao3(connectionMaker());
	}
	
	@Bean
	public ConnectionMaker connectionMaker(){
		return new CountingConnectionMaker(realConnectionMaker());
	}
	
	@Bean
	public ConnectionMaker realConnectionMaker(){
		return new ConnectionMakerImpl(true, "E:\\ProjectStpring\\DBInfo\\db.properties");
	}
	

}
