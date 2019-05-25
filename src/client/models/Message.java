package client.models;

import java.time.LocalDateTime;

public class Message {

	private Contact sender;
	private Contact forwarder;
	private Contact[] recievers;
	private Contact[] ccRecievers;
	private Contact[] bccRecievers;
	private LocalDateTime date;
	private String subject;
	private Attachment[] attachements;
	private boolean isHTML;
	private String body;
	
	public Message(Contact sender, Contact[] recievers, LocalDateTime date, String subject, boolean isHTML,
			String body) {
		super();
		this.sender = sender;
		this.recievers = recievers;
		this.date = date;
		this.subject = subject;
		this.isHTML = isHTML;
		this.body = body;
	}
	
	public void setForwarder(Contact forwarder) { this.forwarder = forwarder; }
	public void setCcRecievers(Contact[] ccRecievers) { this.ccRecievers = ccRecievers; }
	public void setBccRecievers(Contact[] bccRecievers) { this.bccRecievers = bccRecievers; }
	public void setAttachements(Attachment[] attachements) { this.attachements = attachements; }
	
	public Contact getSender() { return sender; }
	public Contact getForwarder() { return forwarder; }
	public Contact[] getRecievers() { return recievers; }
	public Contact[] getCcRecievers() { return ccRecievers; }
	public Contact[] getBccRecievers() { return bccRecievers; }
	public LocalDateTime getDate() { return date; }
	public String getSubject() { return subject; }
	public Attachment[] getAttachements() { return attachements; }
	public boolean isHTML() { return isHTML; }
	public String getBody() { return body; }

}
