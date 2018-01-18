package dao;

import java.io.IOException;
import java.sql.Driver;
import java.util.Properties;

import org.springframework.jdbc.datasource.SimpleDriverDataSource;

import properties.DBInfoGetProperties;

public class InputPropertieSimpleDriverDataSource extends
		SimpleDriverDataSource {
	
	public InputPropertieSimpleDriverDataSource() {
	
	}
	
	public InputPropertieSimpleDriverDataSource(Driver driver, String url,
			DBInfoGetProperties dbInfoGetProperties){
		setDriver(driver);
		setUrl(url);
		Properties properties;
		try {
			properties = dbInfoGetProperties.defaultLoadPropFor();
			String username = properties.getProperty("db.id");
			String password =  properties.getProperty("db.password");
			setUsername(username);
			setPassword(password);
		} catch (IOException e) {
			System.out.println(e);
		}
		
		
	}

}
