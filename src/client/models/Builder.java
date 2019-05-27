package client.models;

import java.io.File;
import java.util.Date;

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

public class Builder {
	
	private Contact sender;
	private Contact[] recievers;
	private Contact[] ccRecievers;
	private Contact[] bccRecievers;
	private String subject;
	private LocalDateTime date;
	private String body;
	private Attachment[] attachements;
	
	private boolean isAnswered;
	private boolean isDeleted;
	private boolean isFlagged;
	private boolean isDraft;
	private boolean isSeen;
	
	private boolean isHTML;
	
	public Builder() {}
	
	public Message build() {
		Message msg = new MimeMessage(Session.getInstance(System.getProperties()));
		
		try {
			// Required
			msg.setFrom(sender);
			msg.setSubject(subject);
			msg.setRecipients(RecipientType.TO, recievers);
			msg.setSentDate(date != null ? date : new Date()); // If not specified, use current date
			
			// Optional
			if (ccRecievers != null) msg.setRecipients(RecipientType.CC, ccRecievers);
			if (bccRecievers != null) msg.setRecipients(RecipientType.BCC, bccRecievers);
			if (isAnswered)	msg.setFlag(Flag.ANSWERED, isAnswered);
			if (isDeleted)	msg.setFlag(Flag.DELETED, isDeleted);
			if (isFlagged)	msg.setFlag(Flag.FLAGGED, isFlagged);
			if (isDraft)	msg.setFlag(Flag.DRAFT, isDraft);
			if (isSeen)		msg.setFlag(Flag.SEEN, isSeen);
			
			// Body
			MimeBodyPart content = new MimeBodyPart();
			content.setText(body, "utf8", isHTML ? "html" : "plain");
			
			Multipart multipart = new MimeMultipart();
			multipart.addBodyPart(content);
			
			// Attachments
			if (attachements != null) {
				for (File file : attachements) {
					MimeBodyPart attachment = new MimeBodyPart();
					DataSource source = new FileDataSource(file);
					attachment.setDataHandler(new DataHandler(source));
					attachment.setFileName(file.getName());
					multipart.addBodyPart(attachment);
				}
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
	
	public Builder sender(InternetAddress sender) {
		this.sender = sender;
		return this;
	}
	
	public Builder recievers(InternetAddress[] recievers) {
		this.recievers = recievers;
		return this;
	}
	
	public Builder ccRecievers(InternetAddress[] ccRecievers) {
		this.ccRecievers = ccRecievers;
		return this;
	}
	
	public Builder bccRecievers(InternetAddress[] bccRecievers) {
		this.bccRecievers = bccRecievers;
		return this;
	}
	
	public Builder date(Date date) {
		this.date = date;
		return this;
	}
	
	public Builder subject(String subject) {
		this.subject = subject;
		return this;
	}
	
	public Builder attachements(File[] attachements) {
		this.attachements = attachements;
		return this;
	}
	
	public Builder body(String body, boolean isHTML) {
		this.body = body;
		this.isHTML = isHTML;
		return this;
	}
	
	public Builder isAnswered(boolean isAnswered) {
		this.isAnswered = isAnswered;
		return this;
	}
	
	public Builder isDeleted(boolean isDeleted) {
		this.isDeleted = isDeleted;
		return this;
	}
	
	public Builder isDraft(boolean isDraft) {
		this.isDraft = isDraft;
		return this;
	}
	
	public Builder isFlagged(boolean isFlagged) {
		this.isFlagged = isFlagged;
		return this;
	}
	
	public Builder isSeen(boolean isSeen) {
		this.isSeen = isSeen;
		return this;
	}
	
}
