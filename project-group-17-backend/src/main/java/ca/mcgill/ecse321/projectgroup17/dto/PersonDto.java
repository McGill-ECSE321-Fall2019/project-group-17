package ca.mcgill.ecse321.projectgroup17.dto;

import java.util.ArrayList;

public class PersonDto {
	
	private String firstName;
	private String lastName;
	private String username;
	private String email;
	private String password;
	private String sexe;
	private long age;
	private String personType;
	private ArrayList<Long> receivedReviewsIds;
	private ArrayList<Long> givenReviewsIds;
	
		
	// Now, we define different constructors depending on the possibilities of inputs 
	// we can receive when asked to create a person.
	
	public PersonDto() {	
	}
	
	public PersonDto(String firstName, String lastName, String username, String personType, String email, String password) {
		this(firstName, lastName, username, personType, email, password, null, 0L);
	}
	
	public PersonDto(String firstName, String lastName, String username, String personType, String email, String password, String sexe, long age) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.username = username;
		this.personType = personType;
		this.email = email;
		this.password = password;
		this.age = age;
	}
	
	//Getters and Setters for DTO

	public String getFirstName() {
		return firstName;
	}
	
	public String getLastName() {
		return lastName;
	}
	
	public String getName() {
		return firstName+" "+lastName;
	}
	
	public String getUsername() {
		return username;
	}
	
	public String getEmail() {
		return email;
	}
	
	public String getPassword() {
		return password;
	}
	
	public String getSexe() {
		return sexe;
	}
	
	public long getAge() {
		return age;
	}
	
	public String getPersonType() {
		return personType;
	}
	
	public ArrayList<Long> getReceivedReviews() {
		return receivedReviewsIds;
	}
	
	public void setReceivedReviews(ArrayList<Long> receivedReviewsIds) {
		this.receivedReviewsIds = receivedReviewsIds;
	}
	
	public ArrayList<Long> getGivenReviews() {
		return givenReviewsIds;
	}
	
	public void setGivenReviews(ArrayList<Long> givenReviewsIds) {
		this.givenReviewsIds = givenReviewsIds;
	}
	

	
	

	

	
	
	

}
