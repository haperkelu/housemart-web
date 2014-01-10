package com.pieli.web.test;

import javax.mail.Message;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * 
 * @author pai.li
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:/beans/spring*.xml" })
public class EmailTester {
	
	private static final String ENCODING = "UTF-8";

	@Autowired
	private JavaMailSender mailSender;

	@Autowired
	private SimpleMailMessage mailMessage;
	
	String to = "pai.li@dianping.com";

	@Test
	public void sendEmail() {
		
		Assert.assertNotNull(mailSender);
		Assert.assertNotNull(mailMessage);
		
		MimeMessagePreparator preparator = new MimeMessagePreparator() {
			
			public void prepare(MimeMessage mimeMessage) throws Exception {
				mimeMessage.setRecipient(Message.RecipientType.TO,
						new InternetAddress(to));
				mimeMessage.setFrom(new InternetAddress(mailMessage.getFrom()));
				mimeMessage.setSubject(mailMessage.getSubject(), ENCODING);
				mimeMessage.setText(mailMessage.getText(), ENCODING);
			}
			
		};
		mailSender.send(preparator);
		
	}
	
	public void testMeta(){
		
		javax.net.ssl.SSLSocketFactory factory;
		factory = null;
		Assert.assertNull(factory);
		
	}

}
