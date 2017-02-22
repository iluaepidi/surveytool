package ilu.surveytool.emailsender;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class EmailSender {

	public EmailSender()
	{
		super();
	}
	
	public boolean send(String email, String subject, String content)
	{
		boolean sent = false;
		
		//final String username = "soporte@surveytool.es";
		final String username = "soporte@apsis4all.eu";
		//final String username = "jagdkw@gmail.com";
		
		final String password = "Tec872es";
		//final String password = "74843275";
 
		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		//props.put("mail.smtp.starttls.enable", "true");
		
		
		//props.put("mail.smtp.host", "smtp.gmail.com");
		//props.put("mail.smtp.host", "173.194.78.108");
				
		//***********
		props.put("mail.smtp.host", "10.128.0.20");
		props.put("mail.smtp.port", "25");
		//******************
				
		//props.put("mail.smtp.port", "587");
 
		Session session = Session.getInstance(props,
		  new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		  });
		//Session session = Session.getDefaultInstance(props);
 
		try {
 
			MimeMessage message = new MimeMessage(session);
			//message.setFrom(new InternetAddress("soporte.apsis4all.ts@gmail.com"));
			//message.setFrom(new InternetAddress("soporte@apsis4all.eu"));
			message.setFrom(new InternetAddress("soporte@surveytool.es"));
			message.setRecipients(Message.RecipientType.TO,
				InternetAddress.parse(email));
			message.setSubject(subject);
			message.setText(content, "UTF-8", "html");
 
			Transport.send(message);

			sent = true; 
			//System.out.println("Done");
 
		} catch (MessagingException e) {
			//throw new RuntimeException(e);
		}
		
		return sent;
	}
	
}