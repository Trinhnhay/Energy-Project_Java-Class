import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;

public class SentEmail {
	
	public SentEmail() {
			
	}
	
	public void sentEmail (String receiver, String file) {
		String sender ="customercare@retailenergyprovider.com";
		String host = "127.0.0.1"; //this IP address is default for using computer, use for Fake STMP sever application
		
		 Properties properties = System.getProperties();
		 properties.setProperty("mail.smtp.host", host);
		 Session session = Session.getDefaultInstance(properties);
		 
	     try {
	         MimeMessage message = new MimeMessage(session);
	         message.setFrom(new InternetAddress(sender));
	         message.addRecipient(Message.RecipientType.TO, new InternetAddress(receiver));
	         message.setSubject("Green Energy Satement");
	         
	         //Add the content
	         Multipart multipart = new MimeMultipart();
	         
	         BodyPart messagePart = new MimeBodyPart();
	         messagePart.setText("This email is attached an invoice");
	         
	         //add the attachment
	         BodyPart attachment = new MimeBodyPart();
	         DataSource source = new FileDataSource("invoice/"+file);
	         attachment.setDataHandler(new DataHandler(source));
	         attachment.setFileName(file);
	         
	         multipart.addBodyPart(messagePart);
	         multipart.addBodyPart(attachment);
	         
	         message.setContent(multipart);
	         
	         Transport.send(message);
	         
	     }catch(MessagingException e) {
	         e.printStackTrace();
	         }
	}

}