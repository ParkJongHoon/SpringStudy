package dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import properties.DBInfoGetProperties;

public class ConnectionMakerImpl implements ConnectionMaker {
	private boolean isProperties;
	private String propertiesPath;
	
	public ConnectionMakerImpl(boolean isProperties, String propertiesPath) {
		this.isProperties = isProperties;
		this.propertiesPath = propertiesPath;
	}

	public Connection makeConnection() throws ClassNotFoundException,
			SQLException {
		Class.forName("com.mysql.jdbc.Driver");
		Connection c;
		if(isProperties){
			c =  connectionPropertiesSetting(propertiesPath);
		}else{
			c = DriverManager.getConnection("jdbc:mysql://localhost/spring_study", "root", "");	
		}
		
		return c;
	}
	
	public Connection connectionPropertiesSetting(String propertiesPath) throws SQLException{
		DBInfoGetProperties dbIGP = new DBInfoGetProperties();
		Properties properties;
		String DBID = null;
		String DBPW = null;
		try {
			properties = dbIGP.loadPropForStatic(propertiesPath);
			DBID = properties.getProperty("db.id");
			DBPW = properties.getProperty("db.password");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		Connection c = DriverManager.getConnection("jdbc:mysql://localhost/spring_study", DBID, DBPW);	
		return c;
	}

}
