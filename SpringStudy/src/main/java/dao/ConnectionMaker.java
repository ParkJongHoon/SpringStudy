package dao;

import java.sql.Connection;
import java.sql.SQLException;

public interface ConnectionMaker {
	public Connection makeConnection() throws ClassNotFoundException, SQLException;
	public Connection connectionPropertiesSetting(String path) throws SQLException;
	public void setProperties(boolean isProperties);
	public void setPropertiesPath(String propertiesPath);
}
