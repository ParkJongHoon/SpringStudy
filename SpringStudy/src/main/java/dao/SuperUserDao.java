package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import domain.User;

public abstract class SuperUserDao {
	private Connection c;
	public SuperUserDao() throws ClassNotFoundException, SQLException {
		c = getConnection();
	}
	
	public void add(User user) throws ClassNotFoundException, SQLException{
		isClosed();
		PreparedStatement ps = c.prepareStatement(
				"insert into users(id, name, password) values(?, ?, ?)");
		ps.setString(1, user.getId());
		ps.setString(2, user.getName());
		ps.setString(3, user.getPassword());
		
		ps.executeUpdate();
		
		ps.close();
		c.close();
	}
	
	public User get(String id) throws ClassNotFoundException, SQLException{
		isClosed();
		PreparedStatement ps = c.prepareStatement("select * from users where id=?");
		ps.setString(1, id);
		
		ResultSet rs = ps.executeQuery();
		rs.next();
		User user = new User();
		user.setId(rs.getString("id"));
		user.setName(rs.getString("name"));
		user.setPassword(rs.getString("password"));
		
		rs.close();
		ps.close();
		
		return user;
	}
	
	// 리팩토링한 결과
	public abstract Connection getConnection() throws ClassNotFoundException, SQLException;
	
	private void isClosed() throws ClassNotFoundException, SQLException{
		if(c.isClosed()){
			c = getConnection();
		}
	}

}
