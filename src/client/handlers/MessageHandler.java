package client.handlers;

import java.io.File;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Flags.Flag;
import javax.mail.Message;
import javax.mail.Message.RecipientType;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import client.models.Attachment;
import client.models.Contact;

public class MessageHandler {
	
	private Message messageConverter(client.models.Message clientMsg) {
		Message msg = new MimeMessage(Session.getInstance(System.getProperties()));
		
		try {
			// Required
			msg.setFrom(clientMsg.getSender().asAddress());
			msg.setSubject(clientMsg.getSubject());
			msg.setRecipients(RecipientType.TO, Contact.asAddresses(clientMsg.getRecipients()).toArray(new InternetAddress[0]));
			msg.setSentDate(clientMsg.getDate());
			
			// Optional
			if (clientMsg.getCcRecipients().size() > 0)
				msg.setRecipients(RecipientType.CC, Contact.asAddresses(clientMsg.getCcRecipients()).toArray(new InternetAddress[0]));
			if (clientMsg.getBccRecipients().size() > 0)
				msg.setRecipients(RecipientType.BCC, Contact.asAddresses(clientMsg.getBccRecipients()).toArray(new InternetAddress[0]));
			if (clientMsg.isAnswered())	msg.setFlag(Flag.ANSWERED, clientMsg.isAnswered());
			if (clientMsg.isDeleted())	msg.setFlag(Flag.DELETED, clientMsg.isDeleted());
			if (clientMsg.isFlagged())	msg.setFlag(Flag.FLAGGED, clientMsg.isFlagged());
			if (clientMsg.isDraft())	msg.setFlag(Flag.DRAFT, clientMsg.isDraft());
			if (clientMsg.isSeen())		msg.setFlag(Flag.SEEN, clientMsg.isSeen());
			
			// Body
			MimeBodyPart content = new MimeBodyPart();
			content.setText(clientMsg.getBody(), "utf8", clientMsg.isHTML() ? "html" : "plain");
			
			Multipart multipart = new MimeMultipart();
			multipart.addBodyPart(content);
			
			// Attachments
			for (Attachment clientAttachment : clientMsg.getAttachements()) {
				File file = clientAttachment.getFile();
				MimeBodyPart attachment = new MimeBodyPart();
				DataSource source = new FileDataSource(file);
				attachment.setDataHandler(new DataHandler(source));
				attachment.setFileName(file.getName());
				multipart.addBodyPart(attachment);
			}
			
			msg.setContent(multipart);
			return msg;
			
		} catch (MessagingException e) {
			e.printStackTrace();
		} catch (NullPointerException e) {
			System.out.println("Tried to create a message without required fields:");
			System.out.println("Sender, recipients, subject, body");
			e.printStackTrace();
		}
		
		return msg;
	}
}
