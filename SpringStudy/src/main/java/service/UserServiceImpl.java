package service;

import java.util.List;

import javax.mail.PasswordAuthentication;

import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;

import dao.UserDao;
import domain.Level;
import domain.User;

public class UserServiceImpl implements UserService {
	UserDao userDao;
	MailSender mailSender;
	
	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	public void setMailSender(MailSender mailSender) {
		this.mailSender = mailSender;
	}

	public void add(User user) {
		if(user.getLevel() == null) user.setLevel(Level.BASIC);
		userDao.add(user);
	}
	
	/*
	 * 작성일: 2018-05-26
	 * 작성자: 박종훈
	 * 작성내용: UserService에 대한 구현체
	 * 
	 * upgradeLevels()의 비즈니스 로직 구현
	 * 
	 */

	public void upgradeLevels() {
		List<User> users =userDao.getAll();
		for(User user: users){
			if(canUpgradeLevel(user)){
				upgradeLevel(user);
			}
		}	

	}
	
	private boolean canUpgradeLevel(User user){
		Level currentLevel = user.getLevel();
		switch(currentLevel){
		case BASIC: return (user.getLogin() >= 50);
		case SILVER: return (user.getRecommend() >= 30);
		case GOLD: return false;
		default: throw new IllegalArgumentException("Unknown Level: " + currentLevel);
		}
	}
	
	private void upgradeLevel(User user){		
		user.upgradeLevel();
		userDao.update(user);
		sendUpgradeEMail(user);
	}
	
	private void sendUpgradeEMail(User user){
		/*
		Properties props = new Properties();
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "211.49.99.14");
		props.put("mail.smtp.port", "25");
		 props.put("mail.smtp.auth", "true");
		
		Authenticator auth = new SMTPAuthenticator();
		Session s = Session.getInstance(props, auth);
		
		MimeMessage message = new MimeMessage(s);
		try{
			message.setHeader("Content-Type", "text/plain; charset=UTF-8");
			message.setFrom(new InternetAddress("springstudy@roseSystems.kr"));
			message.addRecipient(Message.RecipientType.TO, new InternetAddress("m05214@naver.com"));
			message.setSubject("Upgrade 안내", "utf-8");
			message.setText("사용자님의 등급이 " + user.getLevel().name() +
					"로 업그레이드 되었습니다");
			
			Transport.send(message);
		}catch(AddressException e){
			throw new RuntimeException(e);
		}catch(MessagingException e){
			throw new RuntimeException(e);
		}
		*/
		
		SimpleMailMessage mailMessage = new SimpleMailMessage();
		mailMessage.setTo(user.getEmail());
		mailMessage.setFrom("useradmin@ksug.org");
		mailMessage.setSubject("Upgrade 안내");
		mailMessage.setText("사용자님의 등급이 " + user.getLevel().name());
		mailSender.send(mailMessage);
		
	}
	

	private class SMTPAuthenticator extends javax.mail.Authenticator {
		  public PasswordAuthentication getPasswordAuthentication() {
		   return new PasswordAuthentication("emailuser", "emailuser"); // Google id, pwd, 주의) @gmail.com 은 제외하세요
		  }
	}

}
