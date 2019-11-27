package ca.mcgill.ecse321.projectgroup17.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Id;
import java.sql.Date;
import java.sql.Time;
import java.util.List;

@Entity
public class Message {
	
	private long messageId;
	
	@Id @GeneratedValue
	public long getMessageId() {
		return this.messageId;
	}
	
	public void setMessageId(long messageId) {
		this.messageId = messageId;
	}
	
	private String author;
	
	public String getAuthor() {
		return this.author;
	}
	
	public void setAuthor(String author) {
		this.author = author;
	}
	
	private String text;
	
	public String getText() {
		return this.text;
	}
	
	public void setText(String text) {
		this.text = text;
	}
	
	private Date createdDate;

	public void setCreatedDate(Date value) {
		this.createdDate = value;
	}
	public Date getCreatedDate() {
		return this.createdDate;
	}
	
	private List<Reply> replies;
	
	@OneToMany(mappedBy="message")
	public List<Reply> getReplies() {
		return this.replies;
	}
	
	public void setReplies(List<Reply> replies) {
		this.replies = replies;
	}
	
	
	
	
}
