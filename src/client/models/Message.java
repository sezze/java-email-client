package client.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;

import client.Main;

public class Message implements Serializable {

	// Properties
	private Contact sender;
	private List<Contact> recipients = new ArrayList<Contact>();
	private List<Contact> ccRecipients = new ArrayList<Contact>();
	private List<Contact> bccRecipients = new ArrayList<Contact>();
	private String subject;
	private Date date;
	private String body;
	private List<Attachment> attachments = new ArrayList<Attachment>();
	
	private boolean isAnswered;
	private boolean isDeleted;
	private boolean isFlagged;
	private boolean isDraft;
	private boolean isSeen;
	
	private boolean isHTML;
	
	// Serialization ID
	private static final long serialVersionUID = 5557600110663396148L;
	
	private Message() {}

	/*
	 * Property getters and setters
	 */
	
	// Sender
	public Contact getSender() {
		return sender;
	}

	public void setSender(Contact sender) {
		this.sender = sender;
	}

	// Recipients
	public List<Contact> getRecipients() {
		return Collections.unmodifiableList(recipients);
	}

	public void addRecipients(Collection<? extends Contact> recipients) {
		this.recipients.addAll(recipients);
	}
	
	// CC Recipients
	public List<Contact> getCcRecipients() {
		return Collections.unmodifiableList(ccRecipients);
	}

	public void addCcRecipients(Collection<? extends Contact> ccRecipients) {
		this.ccRecipients.addAll(ccRecipients);
	}

	// BCC Recipients
	public List<Contact> getBccRecipients() {
		return Collections.unmodifiableList(bccRecipients);
	}

	public void addBccRecipients(Collection<? extends Contact> bccRecipients) {
		this.bccRecipients.addAll(bccRecipients);
	}
	
	// Subject
	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}
	
	// Date
	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
	
	// Body
	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}
	
	// Attachments
	public List<Attachment> getAttachments() {
		return Collections.unmodifiableList(attachments);
	}

	public void addAttachments(Collection<? extends Attachment> attachements) {
		this.attachments.addAll(attachements);
	}
	
	// Is answered
	public boolean isAnswered() {
		return isAnswered;
	}
	
	public void setAnswered(boolean isAnswered) {
		this.isAnswered = isAnswered;
	}

	// Is deleted
	public boolean isDeleted() {
		return isDeleted;
	}

	public void setDeleted(boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

	// Is flagged
	public boolean isFlagged() {
		return isFlagged;
	}

	public void setFlagged(boolean isFlagged) {
		this.isFlagged = isFlagged;
	}

	// Is draft
	public boolean isDraft() {
		return isDraft;
	}

	public void setDraft(boolean isDraft) {
		this.isDraft = isDraft;
	}

	// Is seen
	public boolean isSeen() {
		return isSeen;
	}

	public void setSeen(boolean isSeen) {
		this.isSeen = isSeen;
	}

	// Is HTML (body)
	public boolean isHTML() {
		return isHTML;
	}

	public void setHTML(boolean isHTML) {
		this.isHTML = isHTML;
	}
	
	/*
	 * Builder
	 */
	public static class Builder {
		
		// Properties
		private Contact sender;
		private List<Contact> recipients = new ArrayList<Contact>();
		private List<Contact> ccRecipients = new ArrayList<Contact>();
		private List<Contact> bccRecipients = new ArrayList<Contact>();
		private String subject;
		private Date date;
		private String body;
		private List<Attachment> attachments = new ArrayList<Attachment>();
		
		private boolean isAnswered;
		private boolean isDeleted;
		private boolean isFlagged;
		private boolean isDraft;
		private boolean isSeen;
		
		private boolean isHTML;
		
		public Builder() {}
		
		public Message build() {
			Message msg = new Message();
			
			if (sender == null || subject == null || recipients.size() == 0 || body == null) {
				Main.LOGGER.log(Level.SEVERE, "Missing required builder field. Required: Sender, recipients, subject, body", new IllegalStateException());
				System.exit(1);
			}
			
			// Required
			msg.setSender(sender);
			msg.setSubject(subject);
			msg.addRecipients(recipients);
			msg.setDate(date != null ? date : new Date()); // If not specified, use current date
			msg.setBody(body);
			msg.setHTML(isHTML);
				
			// Optional
			if (ccRecipients != null) msg.addCcRecipients(ccRecipients);
			if (bccRecipients != null) msg.addBccRecipients(bccRecipients);
			if (attachments != null) msg.addAttachments(attachments);
			msg.setAnswered(isAnswered);
			msg.setDeleted(isDeleted);
			msg.setFlagged(isFlagged);
			msg.setDraft(isDraft);
			msg.setSeen(isSeen);
			
			return msg;
		}
		
		/*
		 * Builder setters
		 */
		
		public Builder sender(Contact sender) {
			this.sender = sender;
			return this;
		}
		
		public Builder addRecipients(Collection<? extends Contact> recipients) {
			this.recipients.addAll(recipients);
			return this;
		}
		
		public Builder addCcRecievers(Collection<? extends Contact> ccRecipients) {
			this.ccRecipients.addAll(ccRecipients);
			return this;
		}
		
		public Builder addBccRecievers(Collection<? extends Contact> bccRecipients) {
			this.bccRecipients.addAll(bccRecipients);
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
		
		public Builder addAttachments(Collection<? extends Attachment> attachments) {
			this.attachments.addAll(attachments);
			return this;
		}
		
		public Builder addAttachment(Attachment attachment) {
			this.attachments.add(attachment);
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
	
}
