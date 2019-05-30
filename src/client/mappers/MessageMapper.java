package client.mappers;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Address;
import javax.mail.BodyPart;
import javax.mail.Flags;
import javax.mail.Flags.Flag;
import javax.mail.Message.RecipientType;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Part;
import javax.mail.Session;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;

import client.models.Attachment;
import client.models.Contact;
import client.models.Message;
import client.models.Message.Builder;

public class MessageMapper {

	/**
	 * Client Message -> Java Mail Message
	 */
	public static javax.mail.Message map(Message clientMsg) throws MessagingException {
		javax.mail.Message msg = new MimeMessage(Session.getInstance(System.getProperties()));
		
		/*
		 * Required fields
		 */
		
		// Sender
		msg.setFrom(AddressMapper.map(clientMsg.getSender()));
		
		// Subject
		msg.setSubject(clientMsg.getSubject());
		
		// Recipients
		msg.setRecipients(RecipientType.TO, AddressMapper.mapAsArray(clientMsg.getRecipients()));
		
		// Date
		msg.setSentDate(clientMsg.getDate());

		/*
		 * Optional fields
		 */
		if (clientMsg.getCcRecipients().size() > 0)
			msg.setRecipients(RecipientType.CC, AddressMapper.mapAsArray(clientMsg.getCcRecipients()));
		if (clientMsg.getBccRecipients().size() > 0)
			msg.setRecipients(RecipientType.BCC, AddressMapper.mapAsArray(clientMsg.getBccRecipients()));
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
		for (Attachment clientAttachment : clientMsg.getAttachments()) {
			File file = clientAttachment.getFile();
			MimeBodyPart attachment = new MimeBodyPart();
			DataSource source = new FileDataSource(file);
			attachment.setDataHandler(new DataHandler(source));
			attachment.setFileName(file.getName());
			multipart.addBodyPart(attachment);
		}

		msg.setContent(multipart);
		return msg;
	}

	/**
	 * Java Mail Message -> Client Message
	 */
	public static Message map(javax.mail.Message serverMsg) throws MessagingException, IOException {
		Message.Builder builder = new Message.Builder();
		
		/*
		 * Required fields
		 */
		
		// Sender
		builder.sender(ContactMapper.map(serverMsg.getFrom()[0]));
		
		// Subject
		builder.subject(serverMsg.getSubject());
		
		// Recipients
		List<Contact> recipients = ContactMapper.map(serverMsg.getRecipients(RecipientType.TO));
		builder.addRecipients(recipients);
		
		// Date
		builder.date(serverMsg.getSentDate());
		
		// Body
		findContent(serverMsg, builder);
		
		/*
		 * Optional fields
		 */

		// CC Recipients
		Address[] cc = serverMsg.getRecipients(RecipientType.CC);
		if (cc != null) builder.addCcRecievers(ContactMapper.map(cc));
		
		// BCC Recipients
		Address[] bcc = serverMsg.getRecipients(RecipientType.BCC);
		if (bcc != null) builder.addBccRecievers(ContactMapper.map(bcc));
		
		// Flags
		Flags flags = serverMsg.getFlags();
		builder.isAnswered(flags.contains(Flag.ANSWERED));
		builder.isDeleted(flags.contains(Flag.DELETED));
		builder.isFlagged(flags.contains(Flag.FLAGGED));
		builder.isDraft(flags.contains(Flag.DRAFT));
		builder.isSeen(flags.contains(Flag.SEEN));
		
		// Build and return
		return builder.build();
	}

	private static void findContent(javax.mail.Message message, Builder builder) throws MessagingException, IOException {		
		if (message.isMimeType("multipart/*")) {
			handleMultipart((Multipart) message.getContent(), builder);
		} else {
			handleContent(message, builder);
		}
	}

	private static void handleMultipart(Multipart multipart, Builder builder) throws MessagingException, IOException {
		for (int i = 0; i < multipart.getCount(); i++) {
			BodyPart bodyPart = multipart.getBodyPart(i);
			if (bodyPart.isMimeType("multipart/*")) {
				handleMultipart((Multipart) bodyPart.getContent(), builder);
			} else {
				handleContent(bodyPart, builder);
			}
		}
	}
	
	private static void handleContent(Part part, Builder builder) throws MessagingException, IOException {
		if (part.isMimeType("text/*")) {
			String body;
			if (builder.hasBody()) {
				if (!builder.hasHTMLBody()) {
					if (part.isMimeType("text/html")) {
						body = MimeUtility.decodeText(part.getContent().toString());
					} else {
						body = builder.getBody() + "\n" + MimeUtility.decodeText(part.getContent().toString());
					}
				} else {
					return;
				}
			} else {
				body = MimeUtility.decodeText(part.getContent().toString());
			}

			builder.body(body, part.isMimeType("text/html"));
		} else {
			builder.addAttachment(new Attachment(part.getFileName(), part.getSize()));
		}
	}
	
}
