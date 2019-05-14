package com.dyerspot.ejb;

import javax.mail.Session;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.mail.Message;
import javax.mail.internet.*;
import javax.mail.Transport;

/**
 * Session Bean implementation class MailSenderBean
 */
//@Stateless
public class MailSenderBean {

    /**
     * Default constructor. 
     */
    public MailSenderBean() {
        // TODO Auto-generated constructor stub
    }
    
    public void sendEmail(String fromEmail, String username, String password,
    		String toEmail, String subject, String message) {
    	
    try {	
    	Properties props = System.getProperties();
    	props.put("mail.smtp.host", "smtp.gmail.com");
    	props.put("mail.smtp.auth", "true");
    	props.put("mail.smtp.port", "465");
    	props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
    	props.put("mail.smtp.socketFactory.port", "465");
    	props.put("mail.smtp.socketFactory.fallback", "false");
    	
    	Session mailSession = Session.getInstance(props, null);
    	mailSession.setDebug(true);
    	
    	Message mailMessage = new MimeMessage(mailSession);
    	
    	mailMessage.setFrom(new InternetAddress(fromEmail));
    	mailMessage.setRecipient(Message.RecipientType.TO, new InternetAddress(toEmail));
    	mailMessage.setContent(message, "text/html");
    	mailMessage.setSubject(subject);
    	
    	Transport transport = mailSession.getTransport("smtp");
    	transport.connect("stmp.gmail.com", username, password);
    	
    	transport.sendMessage(mailMessage, mailMessage.getAllRecipients());
    	
    	
    } catch (Exception e) {
    	Logger.getLogger(MailSenderBean.class.getName()).log(Level.SEVERE, null, "");
    }
    }

}
