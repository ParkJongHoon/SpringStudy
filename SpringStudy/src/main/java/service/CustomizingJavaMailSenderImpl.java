package service;

import java.util.Properties;

import org.springframework.mail.javamail.JavaMailSenderImpl;

public class CustomizingJavaMailSenderImpl extends JavaMailSenderImpl {

	String customHost;
	int customPort;
	String customUserName;
	String customPassword;
	boolean starttlsEnable = false;
	boolean auth = false;
	

	public CustomizingJavaMailSenderImpl() {
		System.out.println("constructor 1");
	}

	

	public CustomizingJavaMailSenderImpl(String customHost, int customPort,
			String customUserName, String customPassword,
			boolean starttlsEnable, boolean auth) {
		System.out.println("constructor 2");
		this.customHost = customHost;
		this.customPort = customPort;
		this.customUserName = customUserName;
		this.customPassword = customPassword;
		this.starttlsEnable = starttlsEnable;
		this.auth = auth;
		setHost(customHost);
		setPort(customPort);
		setUsername(customUserName);
		setPassword(customPassword);
		setProperties(starttlsEnable, auth);
		
	}
	
	public void setProperties(boolean starttlsEnable, boolean auth){
		Properties props = new Properties();
		String starttlsEnableString = String.valueOf(starttlsEnable);
		String authString = String.valueOf(auth);
		props.put("mail.smtp.starttls.enable", starttlsEnableString);
		props.put("mail.smtp.auth", authString);
		this.setJavaMailProperties(props);
	}



	public String getCustomHost() {
		
		return customHost;
	}


	public void setCustomHost(String customHost) {
		setHost(customHost);
		this.customHost = customHost;
	}


	public int getCustomPort() {
		
		return customPort;
	}


	public void setCustomPort(int customPort) {
		setPort(customPort);
		this.customPort = customPort;
	}


	public String getCustomUserName() {
		return customUserName;
	}


	public void setCustomUserName(String customUserName) {
		setUsername(customUserName);
		this.customUserName = customUserName;
	}


	public String getCustomPassword() {
		return customPassword;
	}


	public void setCustomPassword(String customPassword) {
		setPassword(customPassword);
		this.customPassword = customPassword;
	}


	public boolean isStarttlsEnable() {
		return starttlsEnable;
	}


	public void setStarttlsEnable(boolean starttlsEnable) {
		this.starttlsEnable = starttlsEnable;
	}


	public boolean isAuth() {
		return auth;
	}


	public void setAuth(boolean auth) {
		setProperties(starttlsEnable, auth);
		this.auth = auth;
	}


	
	


	
	
}
