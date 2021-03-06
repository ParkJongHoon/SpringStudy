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

import domain.Level;
import domain.User;

public class UserDao9 implements UserDao{
	
	private JdbcTemplate jdbcTemplate;
	private DataSource dataSource;

	
	
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
		this.dataSource = dataSource;
	}

	
	private RowMapper<User> userMapper =
			new RowMapper<User>() {
				public User mapRow(ResultSet rs, int rowNum) throws SQLException{
					User user = new User();
					user.setId(rs.getString("id"));
					user.setName(rs.getString("name"));
					user.setPassword(rs.getString("password"));
					user.setLevel(Level.valueOf(rs.getInt("level")));
					user.setLogin(rs.getInt("login"));
					user.setRecommend(rs.getInt("recommend"));
					user.setEmail(rs.getString("email"));
					return user;
				}
		
			};
	
	public void deleteAll(){
	this.jdbcTemplate.update("delete from users");
	}
	
	public void delete(String id){
		this.jdbcTemplate.update("delete from users where id=?", id);	
	}
	
	public int getCount() throws SQLException{
		return (int)this.jdbcTemplate.queryForObject("select count(*) from users", Integer.class);
	}
	
	public User get(String id){
		return this.jdbcTemplate.queryForObject("select * from users where id=?", new Object[]{id},
				this.userMapper);
	}
	
	public List<User> getAll(){
		return this.jdbcTemplate.query("select * from users order by id",
				this.userMapper);
	}
	
	public void add(final User user){
		this.jdbcTemplate.update("insert into users(id, name, password, level, login, recommend, email) values(?, ?, ?, ?, ?, ?, ?)",
				user.getId(), user.getName(), user.getPassword(),
				user.getLevel().intValue(), user.getLogin(), user.getRecommend(), user.getEmail());
	}

	public void update(User user) {
		this.jdbcTemplate.update("update users set name=?, password=?, level =?, login=?, recommend=? where id =?",
				user.getName(), user.getPassword(), user.getLevel().intValue(), user.getLogin(), user.getRecommend(), user.getId());
		
	}
	
	
	
	
	}
