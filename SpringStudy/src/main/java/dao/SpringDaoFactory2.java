package dao;

import java.io.IOException;
import java.util.Properties;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import javax.sql.DataSource;
import properties.DBInfoGetProperties;

// 애플리케이션 컨텍스트 또는 빈 팩토리가 사용할 설정정보라는 표시
@Configuration
public class SpringDaoFactory2 {
	
	// 오브젝트 생성을 담당하는 IoC용 메소드라는 표시
	@Bean
	public UserDao4 userDao(){
		return new UserDao4(countingDataSource());
	}
	
	@Bean 
	public DataSource dataSource(){
		SimpleDriverDataSource dataSource = new SimpleDriverDataSource();
		
		try {
			dataSource.setDriverClass(com.mysql.jdbc.Driver.class);
			dataSource.setUrl("jdbc:mysql://localhost/spring_study");
			Properties properties = dbGetProperties().defaultLoadPropFor();
			String userName = properties.getProperty("db.id");
			String password =  properties.getProperty("db.password");
			dataSource.setUsername(userName);
			dataSource.setPassword(password);
			
		} catch (IOException e) {
			System.out.println(e);
		}
		return dataSource;
	}
	@Bean
	public DataSource countingDataSource(){
		System.out.println("countingDataSource");
		return new CountingDataSource(dataSource());
	}
	
	
	@Bean
	public DBInfoGetProperties dbGetProperties(){
		return new DBInfoGetProperties();
	}

}
