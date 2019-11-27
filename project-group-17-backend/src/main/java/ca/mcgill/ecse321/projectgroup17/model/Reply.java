package ca.mcgill.ecse321.projectgroup17.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.ManyToOne;
import javax.persistence.Id;
import java.sql.Date;

@Entity
public class Reply {
	
	private long replyId;
	
	@Id @GeneratedValue
	public long getReplyId() {
		return this.replyId;
	}
	
	public void setReplyId(long replyId) {
		this.replyId = replyId;
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
	
	private Message message;
	
	@ManyToOne
	public Message getMessage() {
		return this.message;
	}
	
	public void setMessage(Message message) {
		this.message = message;
	}
}
