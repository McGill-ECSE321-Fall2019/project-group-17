package ca.mcgill.ecse321.projectgroup17.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.List;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import ca.mcgill.ecse321.projectgroup17.dao.*;
import ca.mcgill.ecse321.projectgroup17.model.*;


@RunWith(SpringRunner.class)
@SpringBootTest
public class TestProjectGroup17Service {

	@Autowired
	private ProjectGroup17Service service;

	@Autowired
	private PersonRepository personRepository;

	@Autowired
	private AvailabilityRepository availabilityRepository;

	@Test
	public void testCreateTutor() {

		assertEquals(0, service.getAllPersons().size());

		String personType = "Tutor";
		String firstName = "John";
		String lastName = "Smith";
		String username = "johnsmith123";
		String password = "pass123";
		String email = "john.smith@mail.ca";



		try {
			service.createPerson(personType, firstName, lastName, username, password, email);
		} catch (IllegalArgumentException e) {
			// Check that no error occurred
			fail();
		}

		List<Person> allPersons = service.getAllPersons();

		assertEquals(1, allPersons.size());
		assertEquals(personType, allPersons.get(0).getClass().toString());
		assertEquals(firstName, allPersons.get(0).getFirstName());
		assertEquals(lastName, allPersons.get(0).getLastName());
		assertEquals(username, allPersons.get(0).getUsername());
		assertEquals(password, allPersons.get(0).getPassword());
		assertEquals(email, allPersons.get(0).getEmail());
	}

	@Test
	public void testCreateStudent() {
		assertEquals(0, service.getAllPersons().size());

		String personType = "Student";
		String firstName = "Tim";
		String lastName = "Tom";
		String username = "timtom123";
		String password = "pass123";
		String email = "tim.tom@mail.ca";


		try {
			service.createPerson(personType, firstName, lastName, username, password, email);
		} catch (IllegalArgumentException e) {
			// Check that no error occurred
			fail();
		}

		List<Person> allPersons = service.getAllPersons();

		assertEquals(1, allPersons.size());
		assertEquals(personType, allPersons.get(0).getClass().toString());
		assertEquals(firstName, allPersons.get(0).getFirstName());
		assertEquals(lastName, allPersons.get(0).getLastName());
		assertEquals(username, allPersons.get(0).getUsername());
		assertEquals(password, allPersons.get(0).getPassword());
		assertEquals(email, allPersons.get(0).getEmail());
	}

	@Test
	public void testCreatePersonNull() {
		assertEquals(0, service.getAllPersons().size());

		String personType = null;
		String firstName = null;
		String lastName = null;
		String username = null;
		String password = null;
		String email = null;
		String error = null;

		try {
			service.createPerson(personType, firstName, lastName, username, password, email);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}

		// check error
		assertEquals("Person type must be either 'Student' or 'Tutor'! First name cannot be empty! Last name cannot be empty! Username cannot be empty! Password cannot be empty! Email cannot be empty! ", error);

		// check no change in memory
		assertEquals(0, service.getAllPersons().size());

	}

	@Test
	public void testGetPersonByUsername() {

		assertEquals(0, service.getAllPersons().size());

		String personType = "Tutor";
		String firstName = "John";
		String lastName = "Smith";
		String username = "johnsmith123";
		String password = "pass123";
		String email = "john.smith@mail.ca";

		String personType2 = "Student";
		String firstName2 = "Tim";
		String lastName2 = "Tom";
		String username2 = "timtom123";
		String password2 = "pass123";
		String email2 = "tim.tom@mail.ca";

		String personType3 = "Tutor";
		String firstName3 = "Alex";
		String lastName3 = "Jones";
		String username3 = "alexjones123";
		String password3 = "pass123";
		String email3 = "alex.jones@mail.ca";

		try {
			service.createPerson(personType, firstName, lastName, username, password, email);
			service.createPerson(personType2, firstName2, lastName2, username2, password2, email2);
			service.createPerson(personType3, firstName3, lastName3, username3, password3, email3);
		} catch (IllegalArgumentException e) {
			// Check that no error occurred
			fail();
		}



		Person person = service.getPersonByUsername(username);
		Person person2 = service.getPersonByUsername(username2);
		Person person3 = service.getPersonByUsername(username3);

		assertEquals(username, person.getUsername());
		assertEquals(firstName, person.getFirstName());
		assertEquals(lastName, person.getLastName());
		assertEquals(password, person.getPassword());
		assertEquals(email, person.getEmail());

		assertEquals(username2, person2.getUsername());
		assertEquals(firstName2, person2.getFirstName());
		assertEquals(lastName2, person2.getLastName());
		assertEquals(password2, person2.getPassword());
		assertEquals(email2, person2.getEmail());

		assertEquals(username3, person3.getUsername());
		assertEquals(firstName3, person3.getFirstName());
		assertEquals(lastName3, person3.getLastName());
		assertEquals(password3, person3.getPassword());
		assertEquals(email3, person3.getEmail());
	}

	@Test
	public void testGetPersonByFirstName() {

		assertEquals(0, service.getAllPersons().size());

		String personType = "Tutor";
		String firstName = "John";
		String lastName = "Smith";
		String username = "johnsmith123";
		String password = "pass123";
		String email = "john.smith@mail.ca";

		String personType2 = "Student";
		String firstName2 = "Tim";
		String lastName2 = "Tom";
		String username2 = "timtom123";
		String password2 = "pass123";
		String email2 = "tim.tom@mail.ca";

		String personType3 = "Tutor";
		String firstName3 = "Alex";
		String lastName3 = "Jones";
		String username3 = "alexjones123";
		String password3 = "pass123";
		String email3 = "alex.jones@mail.ca";

		try {
			service.createPerson(personType, firstName, lastName, username, password, email);
			service.createPerson(personType2, firstName2, lastName2, username2, password2, email2);
			service.createPerson(personType3, firstName3, lastName3, username3, password3, email3);
		} catch (IllegalArgumentException e) {
			// Check that no error occurred
			fail();
		}


		List<Person> person = service.getPersonByFirstName(firstName);
		List<Person> person2 = service.getPersonByFirstName(firstName2);
		List<Person> person3 = service.getPersonByFirstName(firstName3);

		for(int i=0; i<person.size(); i++) assertEquals(firstName, person.get(i).getFirstName());
		for(int i=0; i<person2.size(); i++) assertEquals(firstName2, person2.get(i).getFirstName());
		for(int i=0; i<person3.size(); i++) assertEquals(firstName3, person3.get(i).getFirstName());
	}

	@Test
	public void testGetPersonByLastName() {

		assertEquals(0, service.getAllPersons().size());

		String personType = "Tutor";
		String firstName = "John";
		String lastName = "Smith";
		String username = "johnsmith123";
		String password = "pass123";
		String email = "john.smith@mail.ca";

		String personType2 = "Student";
		String firstName2 = "Tim";
		String lastName2 = "Tom";
		String username2 = "timtom123";
		String password2 = "pass123";
		String email2 = "tim.tom@mail.ca";

		String personType3 = "Tutor";
		String firstName3 = "Alex";
		String lastName3 = "Jones";
		String username3 = "alexjones123";
		String password3 = "pass123";
		String email3 = "alex.jones@mail.ca";

		try {
			service.createPerson(personType, firstName, lastName, username, password, email);
			service.createPerson(personType2, firstName2, lastName2, username2, password2, email2);
			service.createPerson(personType3, firstName3, lastName3, username3, password3, email3);
		} catch (IllegalArgumentException e) {
			// Check that no error occurred
			fail();
		}


		List<Person> person = service.getPersonByLastName(lastName);
		List<Person> person2 = service.getPersonByLastName(lastName2);
		List<Person> person3 = service.getPersonByLastName(lastName3);

		for(int i=0; i<person.size(); i++) assertEquals(lastName, person.get(i).getLastName());
		for(int i=0; i<person2.size(); i++) assertEquals(lastName2, person2.get(i).getLastName());
		for(int i=0; i<person3.size(); i++) assertEquals(lastName3, person3.get(i).getLastName());
	}

	@Test
	public void testGetPersonByFirstNameAndLastName() {

		assertEquals(0, service.getAllPersons().size());

		String personType = "Tutor";
		String firstName = "John";
		String lastName = "Smith";
		String username = "johnsmith123";
		String password = "pass123";
		String email = "john.smith@mail.ca";

		String personType2 = "Student";
		String firstName2 = "Tim";
		String lastName2 = "Tom";
		String username2 = "timtom123";
		String password2 = "pass123";
		String email2 = "tim.tom@mail.ca";

		String personType3 = "Tutor";
		String firstName3 = "Alex";
		String lastName3 = "Jones";
		String username3 = "alexjones123";
		String password3 = "pass123";
		String email3 = "alex.jones@mail.ca";

		try {
			service.createPerson(personType, firstName, lastName, username, password, email);
			service.createPerson(personType2, firstName2, lastName2, username2, password2, email2);
			service.createPerson(personType3, firstName3, lastName3, username3, password3, email3);
		} catch (IllegalArgumentException e) {
			// Check that no error occurred
			fail();
		}


		List<Person> person = service.getPersonByFirstNameAndLastName(firstName, lastName);
		List<Person> person2 = service.getPersonByFirstNameAndLastName(firstName2, lastName2);
		List<Person> person3 = service.getPersonByFirstNameAndLastName(firstName3, lastName3);

		for(int i=0; i<person.size(); i++) {
			assertEquals(firstName, person.get(i).getFirstName());
			assertEquals(lastName, person.get(i).getLastName());
		}
		for(int i=0; i<person2.size(); i++) {
			assertEquals(firstName2, person.get(i).getFirstName());
			assertEquals(lastName2, person2.get(i).getLastName());
		}
		for(int i=0; i<person3.size(); i++) {
			assertEquals(firstName3, person.get(i).getFirstName());
			assertEquals(lastName3, person3.get(i).getLastName());
		}
	}

	@Test
	public void testGetPersonByEmail() {

		assertEquals(0, service.getAllPersons().size());

		String personType = "Tutor";
		String firstName = "John";
		String lastName = "Smith";
		String username = "johnsmith123";
		String password = "pass123";
		String email = "john.smith@mail.ca";

		String personType2 = "Student";
		String firstName2 = "Tim";
		String lastName2 = "Tom";
		String username2 = "timtom123";
		String password2 = "pass123";
		String email2 = "tim.tom@mail.ca";

		String personType3 = "Tutor";
		String firstName3 = "Alex";
		String lastName3 = "Jones";
		String username3 = "alexjones123";
		String password3 = "pass123";
		String email3 = "alex.jones@mail.ca";

		try {
			service.createPerson(personType, firstName, lastName, username, password, email);
			service.createPerson(personType2, firstName2, lastName2, username2, password2, email2);
			service.createPerson(personType3, firstName3, lastName3, username3, password3, email3);
		} catch (IllegalArgumentException e) {
			// Check that no error occurred
			fail();
		}



		Person person = service.getPersonByEmail(email);
		Person person2 = service.getPersonByEmail(email2);
		Person person3 = service.getPersonByEmail(email3);

		assertEquals(email, person.getEmail());
		assertEquals(email2, person2.getEmail());
		assertEquals(email3, person3.getEmail());
	}



	public void clearDatabase() {
		// Fisrt, we clear registrations to avoid exceptions due to inconsistencies
		availabilityRepository.deleteAll();

		personRepository.deleteAll();
	}

}

