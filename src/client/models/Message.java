package client.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

public class Message implements Serializable{

	private Contact sender;
	private List<Contact> recipients;
	private List<Contact> ccRecipients;
	private List<Contact> bccRecipients;
	private String subject;
	private Date date;
	private String body;
	private List<Attachment> attachments;
	
	private boolean isAnswered;
	private boolean isDeleted;
	private boolean isFlagged;
	private boolean isDraft;
	private boolean isSeen;
	
	private boolean isHTML;
	private static final long serialVersionUID = 5557600110663396148L;
	
	private Message() {
		recipients = new ArrayList<Contact>();
		ccRecipients = new ArrayList<Contact>();
		bccRecipients = new ArrayList<Contact>();
		attachments = new ArrayList<Attachment>();
	}
	
	public static class Builder {
		
		private Contact sender;
		private List<Contact> recipients;
		private List<Contact> ccRecipients;
		private List<Contact> bccRecipients;
		private String subject;
		private Date date;
		private String body;
		private List<Attachment> attachments;
		
		private boolean isAnswered;
		private boolean isDeleted;
		private boolean isFlagged;
		private boolean isDraft;
		private boolean isSeen;
		
		private boolean isHTML;
		
		public Builder() {
			recipients = new ArrayList<Contact>();
			ccRecipients = new ArrayList<Contact>();
			bccRecipients = new ArrayList<Contact>();
			attachments = new ArrayList<Attachment>();
		}
		
		public Message build() {
			Message msg = new Message();
			
			try {
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
				
			} catch (NullPointerException e) {
				System.out.println("Tried to create a message without required fields:");
				System.out.println("Sender, recipients, subject, body");
				e.printStackTrace();
			}
			
			return msg;
		}
		
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

	public Contact getSender() {
		return sender;
	}

	public void setSender(Contact sender) {
		this.sender = sender;
	}

	public List<Contact> getRecipients() {
		return recipients;
	}

	public void addRecipients(Collection<? extends Contact> recipients) {
		this.recipients.addAll(recipients);
	}

	public List<Contact> getCcRecipients() {
		return ccRecipients;
	}

	public void addCcRecipients(Collection<? extends Contact> ccRecipients) {
		this.ccRecipients.addAll(ccRecipients);
	}

	public List<Contact> getBccRecipients() {
		return bccRecipients;
	}

	public void addBccRecipients(Collection<? extends Contact> bccRecipients) {
		this.bccRecipients.addAll(bccRecipients);
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public List<Attachment> getAttachements() {
		return attachments;
	}

	public void addAttachments(Collection<? extends Attachment> attachements) {
		this.attachments.addAll(attachements);
	}

	public boolean isAnswered() {
		return isAnswered;
	}

	public void setAnswered(boolean isAnswered) {
		this.isAnswered = isAnswered;
	}

	public boolean isDeleted() {
		return isDeleted;
	}

	public void setDeleted(boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

	public boolean isFlagged() {
		return isFlagged;
	}

	public void setFlagged(boolean isFlagged) {
		this.isFlagged = isFlagged;
	}

	public boolean isDraft() {
		return isDraft;
	}

	public void setDraft(boolean isDraft) {
		this.isDraft = isDraft;
	}

	public boolean isSeen() {
		return isSeen;
	}

	public void setSeen(boolean isSeen) {
		this.isSeen = isSeen;
	}

	public boolean isHTML() {
		return isHTML;
	}

	public void setHTML(boolean isHTML) {
		this.isHTML = isHTML;
	}
	
}
