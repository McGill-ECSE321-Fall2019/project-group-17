package ca.mcgill.ecse321.projectgroup17.model;

import javax.persistence.*;
import java.util.Set;
import java.sql.Date;

@Entity(name="person")
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="personType", discriminatorType=DiscriminatorType.STRING)
@DiscriminatorValue("dummy")

@AttributeOverride(name ="personType", 
column = @Column(name="personType", nullable=false, length=8, 
    insertable = false, updatable = false))

public class Person{
	
	private String firstName;

	public void setFirstName(String value) {
		this.firstName = value;
	}
	public String getFirstName() {
		return this.firstName;
	}
	private String lastName;

	public void setLastName(String value) {
		this.lastName = value;
	}
	public String getLastName() {
		return this.lastName;
	}
	private String username;

	public void setUsername(String value) {
		this.username = value;
	}
	@Id
	public String getUsername() {
		return this.username;
	}
	private String password;

	public void setPassword(String value) {
		this.password = value;
	}
	public String getPassword() {
		return this.password;
	}
	private Set<Review> receivedReviews;

	@OneToMany(mappedBy="reviewee" )
	public Set<Review> getReceivedReviews() {
		return this.receivedReviews;
	}

	public void setReceivedReviews(Set<Review> receivedReviewss) {
		this.receivedReviews = receivedReviewss;
	}

	private Set<Review> givenReviews;

	@OneToMany(mappedBy="reviewer" )
	public Set<Review> getGivenReviews() {
		return this.givenReviews;
	}

	public void setGivenReviews(Set<Review> givenReviewss) {
		this.givenReviews = givenReviewss;
	}

	private String email;

	public void setEmail(String value) {
		this.email = value;
	}
	public String getEmail() {
		return this.email;
	}
	private Date created_date;

	public void setCreated_date(Date value) {
		this.created_date = value;
	}
	public Date getCreated_date() {
		return this.created_date;
	}
	private String sexe;

	public void setSexe(String value) {
		this.sexe = value;
	}
	public String getSexe() {
		return this.sexe;
	}
	private long age; 

	public void setAge(long age) {
		this.age = age;
	}
	public long getAge() {
		return this.age;
	}
	
	private String personType;
	
	public String getPersonType() {
		return this.personType;
	}
	public void setPersonType(String personType) {
		this.personType = personType;
	}



}
