package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;



import javax.sql.DataSource;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import domain.User;

public class UserDao6 {
	/*
	private JdbcContext jdbcContext;
	
	
	public void setJdbcContext(JdbcContext jdbcContext) {
		this.jdbcContext = jdbcContext;
	}

	public void add(User user) throws ClassNotFoundException, SQLException{
		this.jdbcContext.workWithStatementStrategy(new StatementStrategy() {
			
			public PreparedStatement makePreparedStatement(Connection c)
					throws SQLException {
			
				return c.prepareStatement("insert into users(id, name, password) values(?, ?, ?)");
			}
		});
				
		
	}
	
	public User get(String id) throws ClassNotFoundException, SQLException{
		Connection c = dataSource.getConnection();
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
	
	public void delete(String id) throws ClassNotFoundException, SQLException{
		Connection c = dataSource.getConnection();
		PreparedStatement ps = c.prepareStatement(
				"delete from users where id=?");
		ps.setString(1, id);
		ps.executeUpdate();
		ps.close();
		c.close();
	}
	
	public void deleteAll() throws ClassNotFoundException, SQLException{
		executeSql("delete from users");
	}
	
	public int getCount() throws SQLException{
		/*
		Connection c = dataSource.getConnection();
		PreparedStatement ps = c.prepareStatement(
				"select count(*) from users");
		ResultSet rs = ps.executeQuery();
		rs.next();
		int count = rs.getInt(1);
		
		rs.close();
		ps.close();
		c.close();
		return count;
		
		return this.jdbc
	}
	
	private void executeSql(final String query) throws SQLException{
		this.jdbcContext.workWithStatementStrategy(
				new StatementStrategy() {
					public PreparedStatement makePreparedStatement(Connection c)
							throws SQLException {
						return c.prepareStatement(query);
					}
				});
	}

	
	*/
}
