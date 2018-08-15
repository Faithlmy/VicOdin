
package com.vic.rest.util;


import java.util.Properties;

import javax.mail.Session;
import javax.mail.Transport;


import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import com.vic.rest.vo.EmailMessage;


public class SendEmailUtil {
private static JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
	
	//常量
	public static String MAIL_SMTP_PROTOCOL = "smtps";
	public static String MAIL_SMTP_STARTTLS_ENABLE = "true";
	public static String MAIL_SMTP_LOCALHOST = "ismetoad";
	public static String MAIL_HOST = "***";
	public static Integer MAIL_PORT = 587;
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	/**
	 * @function 验证邮箱是否可连接
	 * @param mailHost
	 * @param port
	 * @param userName
	 * @param password
	 * @return
	 */
	public static String validEmailFrom(String userName, String password){
		String valid = "success";
		Transport transport;
		try {
			Session session = Session.getInstance(mailSender.getJavaMailProperties());
			transport = session.getTransport(JavaMailSenderImpl.DEFAULT_PROTOCOL);
			transport.connect(MAIL_HOST, MAIL_PORT, userName, password);
		}
		catch (Exception ex) {
			return "error1";
		}
		return valid;
	}
	
	/**
	 * 发送普通文本邮件，使用指定的服务器
	 * @param host
	 * @param mailMessage
	 */
	public static String sendMail(EmailMessage emailMessage){
		String result = "success";
		try{
			Properties props = new Properties();
			props.put("mail.smtp.protocol", MAIL_SMTP_PROTOCOL);
			props.put("mail.smtp.starttls.enable", MAIL_SMTP_STARTTLS_ENABLE);
			props.put("mail.smtp.ssl.trust", MAIL_HOST);
			props.put("mail.smtp.localhost", MAIL_SMTP_LOCALHOST);
			mailSender.setJavaMailProperties(props);
			
			mailSender.setHost(MAIL_HOST); 
			mailSender.setPort(MAIL_PORT);
			mailSender.send(createMailMessage(emailMessage));
			
		}catch(Exception e){
			e.printStackTrace();
			result = e.getMessage();
			return result;
		}
		return result;
	}
	
	/**
	 * 创建普通文本邮件
	 * @param emailMessage
	 * @return
	 */
	private static SimpleMailMessage createMailMessage(EmailMessage emailMessage){
		SimpleMailMessage sm = new SimpleMailMessage();
		if(emailMessage.getMailBccs()!=null)sm.setBcc(emailMessage.getMailBccs());
		if(emailMessage.getMailTCcs()!=null)sm.setCc(emailMessage.getMailTCcs());
		if(emailMessage.getMailFrom()!=null)sm.setFrom(emailMessage.getMailFrom());
		if(emailMessage.getReplyTo()!=null)sm.setReplyTo(emailMessage.getReplyTo());
		if(emailMessage.getMailDate()!=null)sm.setSentDate(emailMessage.getMailDate());
		if(emailMessage.getMailSubject()!=null)sm.setSubject(emailMessage.getMailSubject());
		if(emailMessage.getMailText()!=null)sm.setText(emailMessage.getMailText());
		if(emailMessage.getMailTos()!=null)sm.setTo(emailMessage.getMailTos());
		return sm;
	}
	
	public static void main(String[] args) {
		EmailMessage emailMessage = new EmailMessage();
		emailMessage.setMailFrom("ODIN_SYSTEM");
//		emailMessage.setMailFromName("ODIN SYSTEM");
		emailMessage.setMailSubject("subject");
		emailMessage.setMailText("text");
		emailMessage.setMailTos(new String[]{"abc@mail.foxconn.com"});
		String result = SendEmailUtil.sendMail(emailMessage);
		System.out.println(result);
	}
}
