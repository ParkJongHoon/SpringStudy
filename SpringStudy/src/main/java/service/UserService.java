package service;

import java.util.List;

import dao.UserDao9;
import domain.Level;
import domain.User;

public class UserService {
	UserDao9 userDao;

	public void setUserDao(UserDao9 userDao) {
		this.userDao = userDao;
	}
	
	public void upgradeLevels(){
		List<User> users =userDao.getAll();
		for(User user: users){
			if(canUpgradeLevel(user)){
				upgradeLevel(user);
			}
		}
	}
	
	public void add(User user){
		if(user.getLevel() == null) user.setLevel(Level.BASIC);
		userDao.add(user);
	}
	
	private boolean canUpgradeLevel(User user){
		Level currentLevel = user.getLevel();
		switch(currentLevel){
		case BASIC: return (user.getLogin() > 50);
		case SILVER: return (user.getRecommend() >= 30);
		case GOLD: return false;
		default: throw new IllegalArgumentException("Unknown Level: " + currentLevel);
		}
	}
	
	private void upgradeLevel(User user){
		user.upgradeLevel();
		userDao.update(user);
	}
}
