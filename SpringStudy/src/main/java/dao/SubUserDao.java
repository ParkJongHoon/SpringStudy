package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SubUserDao extends SuperUserDao {
	public SubUserDao() throws ClassNotFoundException, SQLException {
		super();
	}

	public Connection getConnection() throws ClassNotFoundException, SQLException{
		Class.forName("com.mysql.jdbc.Driver");
		Connection c = DriverManager.getConnection("jdbc:mysql://localhost/spring_study", "root", "");
		return c;
	}

}
