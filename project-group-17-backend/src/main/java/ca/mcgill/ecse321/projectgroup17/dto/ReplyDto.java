package ca.mcgill.ecse321.projectgroup17.dto;

import java.sql.Date;
import java.util.Collections;
import java.util.List;
import java.util.Calendar;

import ca.mcgill.ecse321.projectgroup17.model.*;

public class ReplyDto {
	private String author;
	private String text;
	private long messageId;
	
	public ReplyDto() {
	}
	
	public ReplyDto(String author, String text, long messageId) {
		this.author = author;
		this.text = text;
		this.messageId = messageId;
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
	
	public long getMessageId() {
		return this.messageId;
	}
	
	public void setMessageId(long messageId) {
		this.messageId = messageId;
	}
	
	
}
