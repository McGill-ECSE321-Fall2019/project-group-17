package ca.mcgill.ecse321.projectgroup17.dto;

import java.sql.Date;
import java.util.Collections;
import java.util.List;
import java.util.Calendar;

import ca.mcgill.ecse321.projectgroup17.model.*;

public class MessageDto {
	private String author;
	private String text;
	private Date createdDate;
	private List<ReplyDto> replies;
	private long messageId;
	
	public MessageDto() {
	}
	
	public MessageDto(String author, String text, Date createdDate) {
		this(author, text, Collections.emptyList(), createdDate);
	}
	
	public MessageDto(String author, String text, List<ReplyDto> replies, Date createdDate) {
		this.author = author;
		this.text = text;
		this.replies = replies;
		this.createdDate = createdDate;
	}
	
	public String getAuthor() {
		return this.author;
	}
	
	public void setAuthor(String author) {
		this.author = author;
	}
	
	public String getText() {
		return this.text;
	}
	
	public void setText(String text) {
		this.text = text;
	}
	
	public void setReplies(List<ReplyDto> replies) {
		this.replies = replies;
	}
	
	public List<ReplyDto> getReplies() {
		return this.replies;
	}
	
	public long getMessageId() {
		return this.messageId;
	}
	
	public void setMessageId(long messageId) {
		this.messageId = messageId;
	}
	
	public Date getCreatedDate() {
		return this.createdDate;
	}
	
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	
	
	
}
