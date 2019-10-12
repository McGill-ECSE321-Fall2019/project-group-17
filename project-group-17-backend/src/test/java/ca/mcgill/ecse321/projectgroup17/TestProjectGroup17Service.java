package ca.mcgill.ecse321.projectgroup17;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.junit.Before;
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
	@Autowired
	private RoomRepository roomRepository;
	@Autowired
	private AppointmentRepository appointmentRepository;

	/*------------------------------------------*/


	//@Before // or @After ?? --> does not seem to clear DB before each tests...
	public void clearDatabase() {
		// First, we clear registrations to avoid exceptions due to inconsistencies
		availabilityRepository.deleteAll();
		appointmentRepository.deleteAll();
		specificCourseRepository.deleteAll();
		courseRepository.deleteAll();
		roomRepository.deleteAll();
		personRepository.deleteAll();
		
		
		
	}

	/*-----------------------------------------*/

	@Test
	public void testCreateCourse() {
		assertEquals(0, service.getAllCourses().size());

		String courseID = "ECSE321";
		String subject = "Science";
		String level = "University";
		String name = "Intro to the Software Engineering Profession";

		try {
			service.createCourse(courseID, name, level, subject);
		} catch(IllegalArgumentException e) {
			fail();
		}

		List<Course> allCourses = service.getAllCourses();

		assertEquals(1, allCourses.size());
		assertEquals(courseID, allCourses.get(0).getCourseID());
		assertEquals(subject, allCourses.get(0).getSubject());
		assertEquals(level, allCourses.get(0).getLevel());
		assertEquals(name, allCourses.get(0).getName());

	}

	@Test
	public void testCreateCourseNullOrEmpty() {
		assertEquals(0, service.getAllCourses().size());

		String courseID = null;
		String subject = null;
		String level = null;
		String name = null;

		String error = "";

		try {
			service.createCourse(courseID, name, level, subject);
		} catch(IllegalArgumentException e) {
			error = e.getMessage();
		}

		// check error
		assertEquals(0, error.length());

		// check no change in memory
		assertEquals(0, service.getAllCourses().size());

	}

	@Test
	public void testGetCourseByCourseID() {

		assertEquals(0, service.getAllCourses().size());

		String courseID = "ECSE321";
		String subject = "Science";
		String level = "University";
		String name = "Intro to the Software Engineering Profession";

		String courseID2 = "ECSE321";
		String subject2 = "Mathematic";
		String level2 = "University";
		String name2 = "Probability";

		String courseID3 = "ECSE202";
		String subject3 = "Programming";
		String level3 = "University";
		String name3 = "Intro to Software Development";

		try {
			service.createCourse(courseID, name, level, subject);
			service.createCourse(courseID2, name2, level2, subject2);
			service.createCourse(courseID3, name3, level3, subject3);

		} catch (IllegalArgumentException e) {
			// Check that no error occurred
			fail();
		}


		Course course = service.getCourseByID(subject);
		Course course2 = service.getCourseByID(subject2);
		Course course3 = service.getCourseByID(subject3);

		assertEquals(courseID, course.getCourseID());
		assertEquals(subject, course.getSubject());
		assertEquals(level, course.getLevel());
		assertEquals(name, course.getName());

		assertEquals(courseID2, course2.getCourseID());
		assertEquals(subject2, course2.getSubject());
		assertEquals(level2, course2.getLevel());
		assertEquals(name2, course2.getName());

		assertEquals(courseID3, course3.getCourseID());
		assertEquals(subject3, course3.getSubject());
		assertEquals(level3, course3.getLevel());
		assertEquals(name3, course3.getName());
	}

	@Test
	public void testDeleteCourseByCourseID() {

		assertEquals(0, service.getAllCourses().size());

		String courseID = "ECSE321";
		String subject = "Science";
		String level = "University";
		String name = "Intro to the Software Engineering Profession";

		service.createCourse(courseID, name, level, subject);

		try {
			service.deleteCourseByCourseID(courseID);
		} catch(IllegalArgumentException e) {
			fail();
		}

		assertEquals(0, service.getAllCourses().size());

	}


	@Test
	public void testGetCourseBySubject() {

		assertEquals(0, service.getAllCourses().size());

		String courseID = "ECSE321";
		String subject = "Science";
		String level = "University";
		String name = "Intro to the Software Engineering Profession";

		String courseID2 = "ECSE321";
		String subject2 = "Mathematic";
		String level2 = "University";
		String name2 = "Probability";

		String courseID3 = "ECSE202";
		String subject3 = "Programming";
		String level3 = "University";
		String name3 = "Intro to Software Development";

		try {
			service.createCourse(courseID, name, level, subject);
			service.createCourse(courseID2, name2, level2, subject2);
			service.createCourse(courseID3, name3, level3, subject3);

		} catch (IllegalArgumentException e) {
			// Check that no error occurred
			fail();
		}


		List<Course> course = service.getCourseBySubject(subject);
		List<Course> course2 = service.getCourseBySubject(subject2);
		List<Course> course3 = service.getCourseBySubject(subject3);

		assertEquals(courseID, course.get(0).getCourseID());
		assertEquals(subject, course.get(0).getSubject());
		assertEquals(level, course.get(0).getLevel());
		assertEquals(name, course.get(0).getName());

		assertEquals(courseID2, course2.get(0).getCourseID());
		assertEquals(subject2, course2.get(0).getSubject());
		assertEquals(level2, course2.get(0).getLevel());
		assertEquals(name2, course2.get(0).getName());

		assertEquals(courseID3, course3.get(0).getCourseID());
		assertEquals(subject3, course3.get(0).getSubject());
		assertEquals(level3, course3.get(0).getLevel());
		assertEquals(name3, course3.get(0).getName());
	}


	/*-----------------------------------------*/


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
		assertEquals(personType, allPersons.get(0).getClass().getSimpleName());
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
		assertEquals(personType, allPersons.get(0).getClass().getSimpleName());
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
		String firstName3 = "Tim";
		String lastName3 = "Tom";
		String username3 = "timothytom";
		String password3 = "pass123";
		String email3 = "timothy.tom@mail.ca";

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

		for(int i=0; i<person.size(); i++) assertEquals(lastName, person.get(i).getLastName());
		for(int i=0; i<person2.size(); i++) assertEquals(lastName2, person2.get(i).getLastName());
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
		String firstName3 = "Tim";
		String lastName3 = "Tom";
		String username3 = "timothytom";
		String password3 = "pass123";
		String email3 = "timothy.tom@mail.ca";

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

		for(int i=0; i<person.size(); i++) {
			assertEquals(firstName, person.get(i).getFirstName());
			assertEquals(lastName, person.get(i).getLastName());
		}
		for(int i=0; i<person2.size(); i++) {
			assertEquals(firstName2, person2.get(i).getFirstName());
			assertEquals(lastName2, person2.get(i).getLastName());
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

		
	@Test
	public void testGetPersonByUsernameDoesNotExist() {
		
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
			
		Person person = service.getPersonByUsername("wrongusername");
			
		// make sure the returned person object is actually null since it should not have found any users with that username
		assertEquals(null, person);

	}

	@Test
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
	public void testCreateAppointmentNull() {
		assertEquals(0, service.getAllAppointments().size());

		Tutor tutor = null;
		Date date = null;
		Time endTime = null;
		Time startTime = null;
		Room room = null;
		String status = null;

		try {
			service.createAppointment(date, startTime, endTime, room, tutor, status);
		} catch (IllegalArgumentException e) {
			// Check that no error occurred
			fail();
		}	

		// make sure no appointment were created
		assertEquals(0, service.getAllAppointments().size());


	}

	@Test
	public void testCreateAppointmentEndTimeBeforeStartTime() {
		assertEquals(0, service.getAllAppointments().size());

		String personType = "Tutor";
		String firstName = "John";
		String lastName = "Smith";
		String username = "johnsmith123";
		String password = "pass123";
		String email = "john.smith@mail.ca";
		String sex = "male";
		long age = 20;
		Tutor tutor = (Tutor) service.createPerson(personType, firstName, lastName, username, password, email, sex, age);
		long roomID = (long) 1234;
		boolean big = false;
		Room room = service.createRoom(roomID, big);
		String status = "Requested";

		java.sql.Date date = java.sql.Date.valueOf( "2019-10-31" );
		java.sql.Time startTime = java.sql.Time.valueOf( "18:05:00" );
		java.sql.Time endTime = java.sql.Time.valueOf( "19:05:00" );

		try {
			service.createAppointment(date, startTime, endTime, room, tutor, status);
		} catch (IllegalArgumentException e) {
			// Check that no error occurred
			fail();
		}
	}
	
	@Test
	public void testGetAppointmentByDate() {
		
		List<Appointment> appointments = ;
		
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
			service.createAppointment(date, startTime, endTime, room, tutor, status);
		} catch (IllegalArgumentException e) {
			// Check that no error occurred
			fail();
		}
		
		appointments = appointmentRepository.findByDate(date);
		
		for(int i=0; i<appointments.size(); i++) {
			assertEquals(date, appointments.get(i).getDate());
		}
		
		
		
	}

	/*------------------------------------------*/

	@Test
	public void testCreateAvailabilityNull() {
		assertEquals(0, service.getAllAvailabilities().size());

		Date date = null;
		Time startTime = null;
		Time endTime = null;
		Tutor tutor = null;
		String error = null;

		try {
			service.createAvailability(tutor, date, startTime, endTime);
		} catch (IllegalArgumentException e) {
			error = e.getMessage().toString();
		}
		assertEquals(error, "Must specify a tutor! Date cannot be empty! Start time cannot be empty! End time cannot be empty! ");

		//make sure an availability was not created
		assertEquals(0, service.getAllAvailabilities().size());
	}	

	@Test
	public void testCreateAvailability() {
		assertEquals(0, service.getAllAvailabilities().size());

		java.sql.Date date = java.sql.Date.valueOf( "2019-10-03" );
		java.sql.Time startTime = java.sql.Time.valueOf( "19:05:00" );
		java.sql.Time endTime = java.sql.Time.valueOf( "18:05:00" );
		//Make a tutor
		String personType = "Tutor";
		String firstName = "John";
		String lastName = "Smith";
		String username = "johnsmith123";
		String password = "pass123";
		String email = "john.smith@mail.ca";
		String sex = "male";
		long age = 20;
		Tutor tutor = (Tutor) service.createPerson(personType, firstName, lastName, username, password, email, sex, age);

		try {
			service.createAvailability(tutor, date, startTime, endTime);
		} catch (IllegalArgumentException e) {
			fail();
		}

		//make sure an availability was created
		assertEquals(1, service.getAllAvailabilities().size());
	}

	@Test
	public void testCreateAvailabilityEndTimeBeforeStartTime() {
		assertEquals(0, service.getAllAvailabilities().size());

		String error = null;
		//Make a tutor
		String personType = "Tutor";
		String firstName = "John";
		String lastName = "Smith";
		String username = "johnsmith123";
		String password = "pass123";
		String email = "john.smith@mail.ca";
		String sex = "male";
		long age = 20;
		Tutor tutor = (Tutor) service.createPerson(personType, firstName, lastName, username, password, email, sex, age);
		

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
		String sex = "male";
		long age = 20;
		Tutor tutor = (Tutor) service.createPerson(personType, firstName, lastName, username, password, email, sex, age);

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
		String sex = "male";
		long age = 20;
		Tutor tutor = (Tutor) service.createPerson(personType, firstName, lastName, username, password, email, sex, age);

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

	/*------------------------------------------*/

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
		String sex = "male";
		long age = 20;
		Tutor tutor = (Tutor) service.createPerson(personType, firstName, lastName, username, password, email, sex, age);

		//Make course
		String courseID = "MATH240";
		String name = "DiscreteStructures";
		String level = "University";
		String subject = "Math";
		Double hourlyRate = 13.0;
		Course course = service.createCourse(courseID, name, level, subject);

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
	public void testGetSpecificCourseByCourseID() {
		//Make a tutor
		String personType = "Tutor";
		String firstName = "John";
		String lastName = "Smith";
		String username = "johnsmith123";
		String password = "pass123";
		String email = "john.smith@mail.ca";
		String sex = "male";
		long age = 20;
		Tutor tutor = (Tutor) service.createPerson(personType, firstName, lastName, username, password, email, sex, age);
		//Make a tutor
		String personType2 = "Tutor";
		String firstName2 = "John";
		String lastName2 = "Smith";
		String username2 = "johnsmith123";
		String password2 = "pass123";
		String email2 = "john.smith@mail.ca";
		String sex2 = "male";
		long age2 = 20;
		Tutor tutor2 = (Tutor) service.createPerson(personType2, firstName2, lastName2, username2, password2, email2, sex2, age2);

		//Make course
		String courseID = "MATH240";
		String name = "DiscreteStructures";
		String level = "University";
		String subject = "Math";
		Double hourlyRate = 13.0;
		Double hourlyRate2 = 14.0;
		Course course = service.createCourse(courseID, name, level, subject);
		
		service.createSpecificCourse(tutor, course, hourlyRate);
		
		service.createSpecificCourse(tutor2, course, hourlyRate2);
		
		try {
			service.getSpecificCourseByCourse(courseID);
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
		String sex = "male";
		long age = 20;
		Tutor tutor = (Tutor) service.createPerson(personType, firstName, lastName, username, password, email, sex, age);

		//Make course
		String courseID = "MATH240";
		String name = "DiscreteStructures";
		String level = "University";
		String subject = "Math";
		Double hourlyRate = 13.0;
		Course course = service.createCourse(courseID, name, level, subject);

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
		String sex = "male";
		long age = 20;
		Tutor tutor = (Tutor) service.createPerson(personType, firstName, lastName, username, password, email, sex, age);

		//Make course
		String courseID = "MATH240";
		String name = "DiscreteStructures";
		String level = "University";
		String subject = "Math";
		Double hourlyRate = 13.0;
		Course course = service.createCourse(courseID, name, level, subject);

		SpecificCourse specificCourse = service.createSpecificCourse(tutor, course, hourlyRate);
		try {
			service.getSpecificCourseByID(specificCourse.getSpecificCourseID());
		} catch (IllegalArgumentException e) {
			fail();
		}
	}


	/*------------------------------------------*/








	/*------------------------------------------*/

}

