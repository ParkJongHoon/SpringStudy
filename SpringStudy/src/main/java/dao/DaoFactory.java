package dao;

public class DaoFactory {
	public UserDao3 userDao(boolean isProperties, String propertiesPath){
		ConnectionMaker connectionMaker = new ConnectionMakerImpl(isProperties, propertiesPath);
		UserDao3 userDao = new UserDao3(connectionMaker);
		return userDao;
	}

}
