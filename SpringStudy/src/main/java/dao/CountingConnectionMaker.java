package dao;

import java.sql.Connection;
import java.sql.SQLException;

public class CountingConnectionMaker implements ConnectionMaker {
	int counter = 0;
	private ConnectionMaker realConnectionMaker;
	
	public CountingConnectionMaker() {}
	
	public CountingConnectionMaker(ConnectionMaker realConnectionMaker) {
		this.realConnectionMaker = realConnectionMaker;
	}
	
	
	
	public Connection makeConnection() throws ClassNotFoundException,
			SQLException {
		this.counter++;
		return realConnectionMaker.makeConnection();
	}
	
	public int getCounter() {
		return counter;
	}

	public Connection connectionPropertiesSetting(String path)
			throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public void setProperties(boolean isProperties) {
		// TODO Auto-generated method stub

	}

	public void setPropertiesPath(String propertiesPath) {
		// TODO Auto-generated method stub

	}

	public void setRealConnectionMaker(ConnectionMaker realConnectionMaker) {
		this.realConnectionMaker = realConnectionMaker;
	}
	
	

}
