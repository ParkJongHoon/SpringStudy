package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;











import java.util.List;

import javax.sql.DataSource;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;

import domain.User;

public class UserDao7 {
	
	private JdbcTemplate jdbcTemplate;
	private DataSource dataSource;

	public void setJdbcTemplate(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
		this.dataSource = dataSource;
	}
	
	public void delete(String id){
		this.jdbcTemplate.update("delete from users where id=?", id);	
	}
	
	public void deleteAll(){
	this.jdbcTemplate.update("delete from users");
	}
	
	public int getCount() throws SQLException{
		return (int)this.jdbcTemplate.queryForObject("select count(*) from users", Integer.class);
	}
	
	public User get(String id){
		return this.jdbcTemplate.queryForObject("select * from users where id=?", new Object[]{id},
				new RowMapper<User>(){
					public User mapRow(ResultSet rs, int rowNum) throws SQLException{
						User user = new User();
						user.setId(rs.getString("id"));
						user.setName(rs.getString("name"));
						user.setPassword(rs.getString("password"));
						return user;
					}
		});
	}
	
	public List<User> getAll(){
		return this.jdbcTemplate.query("select * from user order by by id",
				new RowMapper<User>(){

					public User mapRow(ResultSet rs, int rowNum)
							throws SQLException {
						User user = new User();
						user.setId(rs.getString("id"));
						user.setName(rs.getString("name"));
						user.setPassword(rs.getString("password"));
						return user;
					}
			
		});
	}
	
	public void add(final User user){
		this.jdbcTemplate.update("insert into users(id, name, password) values(?, ?, ?)",
				user.getId(), user.getName(), user.getPassword());
	}
	
	
	
	
	}
