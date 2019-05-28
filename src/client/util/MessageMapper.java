package client.util;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Address;
import javax.mail.Flags;
import javax.mail.Flags.Flag;
import javax.mail.Message;
import javax.mail.Message.RecipientType;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Part;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import client.models.Attachment;
import client.models.Contact;

public class MessageMapper {

	public static Message from(client.models.Message clientMsg) {
		Message msg = new MimeMessage(Session.getInstance(System.getProperties()));

		try {
			// Required
			msg.setFrom(ContactMapper.from(clientMsg.getSender()));
			msg.setSubject(clientMsg.getSubject());
			msg.setRecipients(RecipientType.TO,
					ContactMapper.fromMultipleContacts(clientMsg.getRecipients()).toArray(new InternetAddress[0]));
			msg.setSentDate(clientMsg.getDate());

			// Optional
			if (clientMsg.getCcRecipients().size() > 0)
				msg.setRecipients(RecipientType.CC,
						ContactMapper.fromMultipleContacts(clientMsg.getCcRecipients()).toArray(new InternetAddress[0]));
			if (clientMsg.getBccRecipients().size() > 0)
				msg.setRecipients(RecipientType.BCC,
						ContactMapper.fromMultipleContacts(clientMsg.getBccRecipients()).toArray(new InternetAddress[0]));
			if (clientMsg.isAnswered())
				msg.setFlag(Flag.ANSWERED, clientMsg.isAnswered());
			if (clientMsg.isDeleted())
				msg.setFlag(Flag.DELETED, clientMsg.isDeleted());
			if (clientMsg.isFlagged())
				msg.setFlag(Flag.FLAGGED, clientMsg.isFlagged());
			if (clientMsg.isDraft())
				msg.setFlag(Flag.DRAFT, clientMsg.isDraft());
			if (clientMsg.isSeen())
				msg.setFlag(Flag.SEEN, clientMsg.isSeen());

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

	public static client.models.Message from(Message serverMsg) {

		try {
			client.models.Message.Builder builder = new client.models.Message.Builder();
			builder.sender(ContactMapper.from(serverMsg.getFrom()[0]));
			builder.subject(serverMsg.getSubject());
			List<Contact> recipients = ContactMapper
					.fromMultipleAddresses(Arrays.asList(serverMsg.getRecipients(RecipientType.TO)));
			builder.addRecipients(recipients);
			builder.date(serverMsg.getSentDate());
			
			
			Multipart multipart = (Multipart) serverMsg.getContent();
			for (int i = 0; i < multipart.getCount(); i++) {
				Part part = multipart.getBodyPart(i);
				if (part.isMimeType("text/html")) {
					builder.body(part.getContent().toString(), true);
				} else if (part.isMimeType("text/plain")) {
					builder.body(part.getContent().toString(), false);
				} else {
					builder.addAttachment(new Attachment(part.getFileName(), part.getSize()));
				}
			}
			
			Address[] cc = serverMsg.getRecipients(RecipientType.CC);
			Address[] bcc = serverMsg.getRecipients(RecipientType.BCC);
			
			if (cc != null)
				builder.addCcRecievers(ContactMapper.fromMultipleAddresses(Arrays.asList(cc)));
			
			if (bcc != null)
				builder.addBccRecievers(ContactMapper.fromMultipleAddresses(Arrays.asList(bcc)));
			
			Flags flags = serverMsg.getFlags();
			
			builder.isAnswered(flags.contains(Flag.ANSWERED));
			builder.isDeleted(flags.contains(Flag.DELETED));
			builder.isFlagged(flags.contains(Flag.FLAGGED));
			builder.isDraft(flags.contains(Flag.DRAFT));
			builder.isSeen(flags.contains(Flag.SEEN));
			
			return builder.build();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
		
	}

}
