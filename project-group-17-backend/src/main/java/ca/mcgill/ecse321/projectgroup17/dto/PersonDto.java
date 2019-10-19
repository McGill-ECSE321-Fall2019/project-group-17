package ca.mcgill.ecse321.projectgroup17.dto;

public class PersonDto {
	
	private String firstName;
	private String lastName;
	private String username;
	private String email;
	private String password;
	private String sexe;
	private long age;
	private String personType;
		
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
	
	//

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
	
	
	// define other getters and setters for everything we can reach from a Person object
	// (look at Domain Model)
	

	

	
	
	

}
