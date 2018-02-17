package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import domain.Level;
import domain.User;

public interface UserDao {
	
	public void setDataSource(DataSource dataSource);
	public void deleteAll();
	public void delete(String id);	
	public int getCount() throws SQLException;
	public User get(String id);
	public List<User> getAll();	
	public void add(final User user);
	public void update(User user);
	
	
	
	

}
