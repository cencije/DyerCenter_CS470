package com.msn.gabrielle.backend;

import java.util.Properties;
import java.util.ArrayList;
import java.util.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

import javax.mail.*;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class EmailSender {
	
	public static void main(String[] args) {
		EmailSender es = new EmailSender();
		es.sendEmail();
    }
	
	/**
	 * Sends a generic email, useful for testing.
	 */
	public void sendEmail() {
		final String username = "ignitemDyer@gmail.com";
        final String password = "ignitem470";

        Properties prop = new Properties();
		prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.port", "587");
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.starttls.enable", "true"); //TLS
        
        Session session = Session.getInstance(prop,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("ignitemDyer@gmail.com"));
            message.setRecipients(
                    Message.RecipientType.TO,
                    InternetAddress.parse("dummydyer@gmail.com")
            );
            message.setSubject("Testing Gmail TLS");
            message.setText("Dear Dyer Center Employee,"
                    + "\n\n Here's an email with tons of useful information!");

            Transport.send(message);

            System.out.println("Done");

        } catch (MessagingException e) {
            e.printStackTrace();
        }
	}

	/**
	 * Sends an email with a structured message including specific information 
	 * about a new project when it is submitted.
	 * 
	 * @param sub A variable of type String.
	 * @param title A variable of type String.
	 * @param description A variable of type String.
	 * @param timeframe A variable of type String.
	 * @param location A variable of type String.
	 * @param paid A variable of type String.
	 * @param senderType A variable of type int.
	 * @param name A variable of type String.
	 * @param skillsRequired A variable of type String.
	 */
	public void sendSpecificEmail(String sub, String title, String description,
			String timeframe, String location, String paid, int senderType, String name, 
			String skillsRequired) {
		Date d = new Date();
		Timestamp current = new java.sql.Timestamp(d.getTime());
		String timeStamp = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss").format(current);
		
		final String username = "ignitemDyer@gmail.com";
        final String password = "ignitem470";

        Properties prop = new Properties();
		prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.port", "587");
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.starttls.enable", "true"); //TLS
        
        Session session = Session.getInstance(prop,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("ignitemDyer@gmail.com"));
            message.setRecipients(
                    Message.RecipientType.TO,
                    InternetAddress.parse("dummydyer@gmail.com")
            );
            message.setSubject(sub);
            if(senderType ==1) {
            	message.setText("A student named " + name
            		+ " has submitted a project with the following information: \n(Submitted on " 
            		+ timeStamp + ")"
            		+ "\n\nPROJECT TITLE: \n" + title
            		+ "\n\nDESCRIPTION: \n" + description 
            		+ "\n\nSKILLS REQUIRED: \n" + skillsRequired
            		+ "\n\nDETAILS:\n" + timeframe + "\n" + location + "\n" + paid);
            }
            if(senderType ==2) {
            	message.setText("An alumnus named " + name
            		+ " has submitted a project proposal with the following information: "
            		+ "\n\nPROJECT TITLE: \n" + title
            		+ "\n\nDESCRIPTION: \n" + description
            		+ "\n\nSKILLS REQUIRED: \n" + skillsRequired
            		+ "\n\nDETAILS:\n" + timeframe + "\n" + location + "\n" + paid);
            }

            Transport.send(message);

            System.out.println("Done");

        } catch (MessagingException e) {
            e.printStackTrace();
        }
	}
}
