package ca.mcgill.ecse321.projectgroup17;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.List;

import org.aspectj.lang.annotation.Before;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import ca.mcgill.ecse321.projectgroup17.dao.*;
import ca.mcgill.ecse321.projectgroup17.model.*;
import ca.mcgill.ecse321.projectgroup17.service.ProjectGroup17Service;


@RunWith(SpringRunner.class)
@SpringBootTest
public class TestProjectGroup17Service {

	@Autowired
	private ProjectGroup17Service service;

	@Autowired
	private PersonRepository personRepository;
	@Autowired
	private CourseRepository courseRepository;
	@Autowired
	private SpecificCourseRepository specificCourseRepository;
	@Autowired
	private AvailabilityRepository availabilityRepository;
	
<<<<<<< Updated upstream:project-group-17-backend/src/test/java/ca/mcgill/ecse321/projectgroup17/TestProjectGroup17Service.java
=======
	@Autowired
	private AppointmentRepository appointmentRepository;
	
>>>>>>> Stashed changes:project-group-17-backend/src/main/java/ca/mcgill/ecse321/projectgroup17/service/TestProjectGroup17Service.java
	

	public void clearDatabase() {
		appointmentRepository.deleteAll();
		availabilityRepository.deleteAll();
		personRepository.deleteAll();
	}
	
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
			service.createPerson(personType, firstName, lastName, username, password, email, null, 0L);
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
			service.createPerson(personType, firstName, lastName, username, password, email, null, 0L);
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
		
		String error = "";

		try {
			service.createPerson(personType, firstName, lastName, username, password, email, null, 0L);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}

		// check error
		assertEquals("Person type must be either 'Student' or 'Tutor'! First name cannot be empty! Last name cannot be empty! Username cannot be empty! Password cannot be empty! Email cannot be empty! ", error);

		// check no change in memory
		assertEquals(0, service.getAllPersons().size());

	}
	
	@Test
	public void testCreatePersonEmpty() {
		assertEquals(0, service.getAllPersons().size());

		String personType = "";
		String firstName = "";
		String lastName = "";
		String username = "";
		String password = "";
		String email = "";
		
		String error = "";

		try {
			service.createPerson(personType, firstName, lastName, username, password, email, null, 0L);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}

		// check error
		assertEquals("Person type must be either 'Student' or 'Tutor'! First name cannot be empty! Last name cannot be empty! Username cannot be empty! Password cannot be empty! Email cannot be empty! ", error);

		// check no change in memory
		assertEquals(0, service.getAllPersons().size());

	}
	
	@Test
	public void testCreatePersonSpaces() {
		assertEquals(0, service.getAllPersons().size());

		String personType = " ";
		String firstName = " ";
		String lastName = " ";
		String username = " ";
		String password = " ";
		String email = " ";
		String error = "";

		try {
			service.createPerson(personType, firstName, lastName, username, password, email, null, 0L);
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
			service.createPerson(personType, firstName, lastName, username, password, email, null, 0L);
			service.createPerson(personType2, firstName2, lastName2, username2, password2, email2, null, 0L);
			service.createPerson(personType3, firstName3, lastName3, username3, password3, email3, null, 0L);
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
			service.createPerson(personType, firstName, lastName, username, password, email, null, 0L);
			service.createPerson(personType2, firstName2, lastName2, username2, password2, email2, null, 0L);
			service.createPerson(personType3, firstName3, lastName3, username3, password3, email3, null, 0L);
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
			service.createPerson(personType, firstName, lastName, username, password, email, null,0L);
			service.createPerson(personType2, firstName2, lastName2, username2, password2, email2, null, 0L);
			service.createPerson(personType3, firstName3, lastName3, username3, password3, email3, null, 0L);
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
			service.createPerson(personType, firstName, lastName, username, password, email, null, 0L);
			service.createPerson(personType2, firstName2, lastName2, username2, password2, email2, null, 0L);
			service.createPerson(personType3, firstName3, lastName3, username3, password3, email3, null, 0L);
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
			service.createPerson(personType, firstName, lastName, username, password, email, null, 0L);
			service.createPerson(personType2, firstName2, lastName2, username2, password2, email2, null, 0L);
			service.createPerson(personType3, firstName3, lastName3, username3, password3, email3, null, 0L);
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
	
	public void testGetPersonByUsernameDoesNotExist() {
		
		
		
	}
	
	public void testCreateAppointment() {
		
		assertEquals(0, service.getAllAppointments().size());
		
		String personType = "Tutor";
		String firstName = "John";
		String lastName = "Smith";
		String username = "johnsmith123";
		String password = "pass123";
		String email = "john.smith@mail.ca";
		
		Tutor tutor = new Tutor();
		tutor.setFirstName(firstName);
		tutor.setLastName(lastName);
		tutor.setUsername(username);
		tutor.setPassword(password);
		tutor.setEmail(email);
		
		Date date = new Date(Calendar.getInstance().getTime().getTime());
		Time endTime = new Time(9, 0, 0);
		Time startTime = new Time(10, 0, 0);
		Room room = new Room();
		room.setRoomID(1L);
		String status = "Requested";
		
		try {
			service.createAppointment(date, endTime, startTime, room, tutor, status);
		} catch (IllegalArgumentException e) {
			fail();
		}
		
		List<Appointment> allAppointments = service.getAllAppointments();
		
		assertEquals(1, allAppointments.size());
		assertEquals(date, allAppointments.get(0).getDate());
		assertEquals(endTime, allAppointments.get(0).getEndTime());
		assertEquals(startTime, allAppointments.get(0).getStartTime());
		assertEquals(room.getRoomID(), allAppointments.get(0).getRoom().getRoomID());
		assertEquals(status, allAppointments.get(0).getStatus());
		
	}
	
	@Test
	public void testCreteAppointmentNull() {
		assertEquals(0, service.getAllAppointments().size());
		
		Tutor tutor = null;
		Date date = null;
		Time endTime = null;
		Time startTime = null;
		Room room = null;
		String status = null;
		
		String error = "";

<<<<<<< Updated upstream:project-group-17-backend/src/test/java/ca/mcgill/ecse321/projectgroup17/TestProjectGroup17Service.java
	@Before
	public void clearDatabase() {
		// First, we clear registrations to avoid exceptions due to inconsistencies
		availabilityRepository.deleteAll();
		specificCourseRepository.deleteAll();
		courseRepository.deleteAll();
		personRepository.deleteAll();
	}
	
	//Availability Tests
	@Test
	public void testCreateAvailability() {
		assertEquals(0, service.getAllPersons().size());
		
=======
		try {
			service.createAppointment(date, endTime, startTime, room, tutor, status);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}

		// check error
		assertEquals("Appointment date cannot be empty! Appointment start time cannot be empty! Appointment end time cannot be empty! Appointment tutor cannot be empty! Appointment status cannot be empty and must be either 'Requested' or 'Accepted' or 'Refused' or 'Paid' or 'Cancelled'! ", error);

		// check no change in memory
		assertEquals(0, service.getAllAppointments().size());

	}
	
	@Test
	public void testCreateAppointmentEndTimeBeforeStartTime() {
		assertEquals(0, service.getAllAppointments().size());
>>>>>>> Stashed changes:project-group-17-backend/src/main/java/ca/mcgill/ecse321/projectgroup17/service/TestProjectGroup17Service.java
		
		String personType = "Tutor";
		String firstName = "John";
		String lastName = "Smith";
		String username = "johnsmith123";
		String password = "pass123";
		String email = "john.smith@mail.ca";
<<<<<<< Updated upstream:project-group-17-backend/src/test/java/ca/mcgill/ecse321/projectgroup17/TestProjectGroup17Service.java
		Tutor tutor = (Tutor) service.createPerson(personType, firstName, lastName, username, password, email);
		
		java.sql.Date date = java.sql.Date.valueOf( "2019-10-31" );
		java.sql.Time startTime = java.sql.Time.valueOf( "18:05:00" );
		java.sql.Time endTime = java.sql.Time.valueOf( "19:05:00" );
		
		try {
			service.createAvailability(tutor,date,startTime,endTime);
		} catch (IllegalArgumentException e) {
			// Check that no error occurred
			fail();
		}
	}
	
	@Test
	public void testCreateAvailabilityNull() {
		assertEquals(0, service.getAllAvailabilities().size());
		
		Date date = null;
		Time startTime = null;
		Time endTime = null;
		Tutor tutor = null;
		String error = null;
		
		try {
			service.createAvailability(tutor,date,startTime,endTime);
		} catch (IllegalArgumentException e) {
			error = e.getMessage().toString();
		}
		assertEquals(error, "Must specify a tutor! Date cannot be empty! Start time cannot be empty! End time cannot be empty! ");
		
		//make sure an availability was not created
		assertEquals(0, service.getAllAvailabilities().size());
	}
	
	@Test
	public void testCreateAvailabilityTime() {
		assertEquals(0, service.getAllAvailabilities().size());
		
		String error = null;
		//Make a tutor
		String personType = "Tutor";
		String firstName = "John";
		String lastName = "Smith";
		String username = "johnsmith123";
		String password = "pass123";
		String email = "john.smith@mail.ca";
		Tutor tutor = (Tutor) service.createPerson(personType, firstName, lastName, username, password, email);

		//Create 1st availability
		java.sql.Date date = java.sql.Date.valueOf( "2019-10-03" );
		java.sql.Time startTime = java.sql.Time.valueOf( "19:05:00" );
		java.sql.Time endTime = java.sql.Time.valueOf( "18:05:00" );
		try {
			service.createAvailability(tutor,date,startTime,endTime);
		} catch (IllegalArgumentException e) {
			error = e.getMessage().toString();
		}
		assertEquals(error, "End time cannot be before startTime! ");
		
		//make sure an availability was not created
		assertEquals(0, service.getAllAvailabilities().size());
	}
	
	@Test
	public void testGetAvailabilityByDate() {
		
		//Make a tutor
		String personType = "Tutor";
		String firstName = "John";
		String lastName = "Smith";
		String username = "johnsmith123";
		String password = "pass123";
		String email = "john.smith@mail.ca";
		Tutor tutor = (Tutor) service.createPerson(personType, firstName, lastName, username, password, email);

		//Create 1st availability
		java.sql.Date date = java.sql.Date.valueOf( "2019-10-01" );
		java.sql.Time startTime = java.sql.Time.valueOf( "18:05:00" );
		java.sql.Time endTime = java.sql.Time.valueOf( "19:05:00" );

		service.createAvailability(tutor,date,startTime,endTime);

		//Create 2nd availability
		java.sql.Date date2 = java.sql.Date.valueOf( "2019-10-02" );
		java.sql.Time startTime2 = java.sql.Time.valueOf( "17:05:00" );
		java.sql.Time endTime2 = java.sql.Time.valueOf( "18:05:00" );

		service.createAvailability(tutor,date2,startTime2,endTime2);

		try {
			service.getAvailabilityByDate(date);
		} catch (IllegalArgumentException e) {
			fail();
		}
	
	}
	
	@Test
	public void testGetAvailabilityByTutorUsername() {
		
		//Make a tutor
		String personType = "Tutor";
		String firstName = "John";
		String lastName = "Smith";
		String username = "johnsmith123";
		String password = "pass123";
		String email = "john.smith@mail.ca";
		Tutor tutor = (Tutor) service.createPerson(personType, firstName, lastName, username, password, email);
		
		//Create 1st availability
		java.sql.Date date = java.sql.Date.valueOf( "2019-10-03" );
		java.sql.Time startTime = java.sql.Time.valueOf( "18:05:00" );
		java.sql.Time endTime = java.sql.Time.valueOf( "19:05:00" );
		
		service.createAvailability(tutor,date,startTime,endTime);
		
		//Create 2nd availability
		java.sql.Date date2 = java.sql.Date.valueOf( "2019-10-04" );
		java.sql.Time startTime2 = java.sql.Time.valueOf( "17:05:00" );
		java.sql.Time endTime2 = java.sql.Time.valueOf( "18:05:00" );
		
		service.createAvailability(tutor,date2,startTime2,endTime2);
		
		try {
			service.getAvailabilityByTutorUsername(username);
		} catch (IllegalArgumentException e) {
			fail();
		}
		
	}	
	
	
	//SpecificCourse Tests
	@Test
	public void testCreateSpecificCourse() {
		//Make a tutor
		String personType = "Tutor";
		String firstName = "John";
		String lastName = "Smith";
		String username = "johnsmith123";
		String password = "pass123";
		String email = "john.smith@mail.ca";
		Tutor tutor = (Tutor) service.createPerson(personType, firstName, lastName, username, password, email);

		//Make course
		String name = "DiscreteStructures";
		String level = "University";
		String subject = "Math";
		Double hourlyRate = 13.0;
		Course course = service.createCourse(name, level, subject);
		
		try {
			service.createSpecificCourse(tutor, course, hourlyRate);
		} catch (IllegalArgumentException e) {
			fail();
		}
	}
	@Test
	public void testCreateSpecificCourseNull() {
		assertEquals(0, service.getAllSpecificCourses().size());
		
		Tutor tutor = null;
		Double hourlyRate = null;
		Course course = null;
		String error = null;
		
		try {
			service.createSpecificCourse(tutor, course, hourlyRate);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		
		assertEquals(error, "Tutor cannot be null! Course cannot be null! HourlyRate must be above minimum wage! ");
		
		assertEquals(0, service.getAllAvailabilities().size());
	}
	@Test
	public void testGetSpecificCourseByCourse() {
		//Make a tutor
		String personType = "Tutor";
		String firstName = "John";
		String lastName = "Smith";
		String username = "johnsmith123";
		String password = "pass123";
		String email = "john.smith@mail.ca";
		Tutor tutor = (Tutor) service.createPerson(personType, firstName, lastName, username, password, email);

		//Make course
		String name = "DiscreteStructures";
		String level = "University";
		String subject = "Math";
		Double hourlyRate = 13.0;
		Course course = service.createCourse(name, level, subject);
=======
		
		Tutor tutor = new Tutor();
		tutor.setFirstName(firstName);
		tutor.setLastName(lastName);
		tutor.setUsername(username);
		tutor.setPassword(password);
		tutor.setEmail(email);
		
		Date date = new Date(Calendar.getInstance().getTime().getTime());
		Time endTime = new Time(10, 0, 0);
		Time startTime = new Time(9, 0, 0);
		Room room = new Room();
		room.setRoomID(1L);
		String status = "Requested";
		
		String error = "";

		try {
			service.createAppointment(date, endTime, startTime, room, tutor, status);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}

		// check error
		assertEquals("Appointment end time cannot be before appointment start time! ", error);

		// check no change in memory
		assertEquals(0, service.getAllAppointments().size());
		
	}
	
//	public void testAddStudentToAppointment() {
//		
//		String personType = "Tutor";
//		String firstName = "John";
//		String lastName = "Smith";
//		String username = "johnsmith123";
//		String password = "pass123";
//		String email = "john.smith@mail.ca";
//		
//		Tutor tutor = new Tutor();
//		tutor.setFirstName(firstName);
//		tutor.setLastName(lastName);
//		tutor.setUsername(username);
//		tutor.setPassword(password);
//		tutor.setEmail(email);
//		
//		String personType2 = "Student";
//		String firstName2 = "Tim";
//		String lastName2 = "Tom";
//		String username2 = "timtom123";
//		String password2 = "pass123";
//		String email2 = "tim.tom@mail.ca";
//		
//		Student student = new Student();
//		student.setFirstName(firstName2);
//		student.setLastName(lastName2);
//		student.setUsername(username2);
//		student.setPassword(password2);
//		student.setEmail(email2);
//		
//		Date date = new Date(Calendar.getInstance().getTime().getTime());
//		Time endTime = new Time(10, 0, 0);
//		Time startTime = new Time(9, 0, 0);
//		Room room = new Room();
//		room.setRoomID(1L);
//		String status = "Requested";
//		
//		String error = "";
//
//		try {
//			service.createAppointment(date, endTime, startTime, room, tutor, status);
//		} catch (IllegalArgumentException e) {
//			error = e.getMessage();
//		}
//		
//	}
>>>>>>> Stashed changes:project-group-17-backend/src/main/java/ca/mcgill/ecse321/projectgroup17/service/TestProjectGroup17Service.java

		service.createSpecificCourse(tutor, course, hourlyRate);
		try {
			service.getSpecificCourseByCourse(name);
		} catch (IllegalArgumentException e) {
			fail();
		}
	}

	@Test
	public void testGetSpecificCourseByTutorUsername() {
		//Make a tutor
		String personType = "Tutor";
		String firstName = "John";
		String lastName = "Smith";
		String username = "johnsmith123";
		String password = "pass123";
		String email = "john.smith@mail.ca";
		Tutor tutor = (Tutor) service.createPerson(personType, firstName, lastName, username, password, email);

		//Make course
		String name = "DiscreteStructures";
		String level = "University";
		String subject = "Math";
		Double hourlyRate = 13.0;
		Course course = service.createCourse(name, level, subject);

		service.createSpecificCourse(tutor, course, hourlyRate);
		try {
			service.getSpecificCourseByTutor(username);
		} catch (IllegalArgumentException e) {
			fail();
		}
	}
	@Test
	public void testGetSpecificCourseByID() {
		//Make a tutor
		String personType = "Tutor";
		String firstName = "John";
		String lastName = "Smith";
		String username = "johnsmith123";
		String password = "pass123";
		String email = "john.smith@mail.ca";
		Tutor tutor = (Tutor) service.createPerson(personType, firstName, lastName, username, password, email);

		//Make course
		String name = "DiscreteStructures";
		String level = "University";
		String subject = "Math";
		Double hourlyRate = 13.0;
		Course course = service.createCourse(name, level, subject);

		SpecificCourse specificCourse = service.createSpecificCourse(tutor, course, hourlyRate);
		try {
			service.getSpecificCourseByID(specificCourse.getID());
		} catch (IllegalArgumentException e) {
			fail();
		}
	}
	
}

