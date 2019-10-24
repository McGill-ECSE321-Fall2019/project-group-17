package ca.mcgill.ecse321.projectgroup17;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.junit.Before;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import ca.mcgill.ecse321.projectgroup17.controller.ProjectGroup17RestController;
import ca.mcgill.ecse321.projectgroup17.dao.*;
import ca.mcgill.ecse321.projectgroup17.model.Appointment;
import ca.mcgill.ecse321.projectgroup17.model.Availability;
import ca.mcgill.ecse321.projectgroup17.model.Course;
import ca.mcgill.ecse321.projectgroup17.model.Person;
import ca.mcgill.ecse321.projectgroup17.model.Review;
import ca.mcgill.ecse321.projectgroup17.model.SpecificCourse;
import ca.mcgill.ecse321.projectgroup17.model.Tutor;
import ca.mcgill.ecse321.projectgroup17.model.Student;
import ca.mcgill.ecse321.projectgroup17.model.Room;
import ca.mcgill.ecse321.projectgroup17.service.ProjectGroup17Service;
import ca.mcgill.ecse321.projectgroup17.model.Appointment.AppointmentStatus;
import ca.mcgill.ecse321.projectgroup17.model.Course.Level;


@RunWith(SpringRunner.class)
@SpringBootTest

// the line below is apparently what we should use instead of the two above...
//@RunWith(MockitoJUnitRunner.class) 

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
	@Autowired
	private ReviewRepository reviewRepository;
	@Autowired
	private TutorRepository tutorRepository;
	@Autowired
	private StudentRepository studentRepository;
	@Autowired
	private PersonAvailabilitiesRepository personAvailabilitiesRepository;
	

	/*------------------------------------------*/


	//@Before // or @After ?? --> does not seem to clear DB before each tests...
	
	@Before
	public void clearDatabase() {
		// First, we clear registrations to avoid exceptions due to inconsistencies
		
		availabilityRepository.deleteAll();
		reviewRepository.deleteAll();
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
			System.out.println(e.getMessage());
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
		assertEquals("Course ID must be specified (ie: ECSE321)!Course name must be specified!Course level must be specified!Invalid course level specified (Highschool, Cegep, University)!The course name must be specified!", 
				error);

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


		List<Course> course = service.getCoursesBySubject(subject);
		List<Course> course2 = service.getCoursesBySubject(subject2);
		List<Course> course3 = service.getCoursesBySubject(subject3);

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
		
		String tutorUsername = "johnsmith123";

		Date date = new Date(Calendar.getInstance().getTime().getTime());
		Time endTime = new Time(9, 0, 0);
		Time startTime = new Time(10, 0, 0);
		long roomId = 1000L;
		String status = "Requested";

		try {
			Room room = service.createRoom(roomId, false);
			service.createAppointment(date, endTime, startTime, room, tutorUsername, status);
		} catch (IllegalArgumentException e) {
			fail();
		}

		List<Appointment> allAppointments = service.getAllAppointments();

		assertEquals(1, allAppointments.size());
		assertEquals(date, allAppointments.get(0).getDate());
		assertEquals(endTime, allAppointments.get(0).getEndTime());
		assertEquals(startTime, allAppointments.get(0).getStartTime());
		assertEquals(roomId, allAppointments.get(0).getRoom().getRoomID());
		assertEquals(tutorUsername, allAppointments.get(0).getTutor().getUsername());
		assertEquals(status, allAppointments.get(0).getStatus());

	}

	@Test
	public void testCreateAppointmentNull() {
		assertEquals(0, service.getAllAppointments().size());
		
		String error = "";
		String tutorUsername = null;
		Date date = null;
		Time endTime = null;
		Time startTime = null;
		long roomId = 1000L;
		String status = null;

		try {
			Room room = service.createRoom(roomId, true);
			service.createAppointment(date, startTime, endTime, room, tutorUsername, status);
		} catch (IllegalArgumentException e) {
			// Check that no error occurred
			error = e.getMessage();
		}	

		// make sure no appointment were created
		assertEquals(error, "Appointment date cannot be empty! Appointment start time cannot be empty! Appointment end time cannot be empty! Appointment tutor cannot be null! Appointment status cannot be empty and must be 'Requested'! Appointment room cannot be null! ");
		assertEquals(0, service.getAllAppointments().size());


	}

	@Test
	public void testCreateAppointmentEndTimeBeforeStartTime() {
		assertEquals(0, service.getAllAppointments().size());

		String error = "";
		
		String personType = "Tutor";
		String firstName = "John";
		String lastName = "Smith";
		String username = "johnsmith123";
		String password = "pass123";
		String email = "john.smith@mail.ca";
		String sex = "male";
		long age = 20;
		boolean big = false;
		String status = "Requested";
		long roomId = 1000L;

		java.sql.Date date = java.sql.Date.valueOf( "2019-10-31" );
		java.sql.Time startTime = java.sql.Time.valueOf( "18:05:00" );
		java.sql.Time endTime = java.sql.Time.valueOf( "19:05:00" );

		try {
			Room room = service.createRoom(roomId, big);
			Tutor tutor = (Tutor) service.createPerson(personType, firstName, lastName, username, password, email, sex, age);
			service.createAppointment(date, startTime, endTime, room, username, status);
		} catch (IllegalArgumentException e) {
			// Check that no error occurred
			error = e.getMessage();
			//System.out.println(error);
		}
		
		assertEquals(error, "Appointment end time cannot be before appointment start time! ");
		assertEquals(0, service.getAllAppointments().size());
		
	}

	@Test
	public void testGetAppointmentByDate() {

		List<Appointment> appointments;

		String personType = "Tutor";
		String firstName = "John";
		String lastName = "Smith";
		String username = "johnsmith123";
		String password = "pass123";
		String email = "john.smith@mail.ca";
		long roomId = 1000L;

		
		Date date = new Date(Calendar.getInstance().getTime().getTime());
		Time endTime = new Time(9, 0, 0);
		Time startTime = new Time(10, 0, 0);
		
		String status = "Requested";

		try {
			Room room = service.createRoom(roomId, false);
			Tutor tutor = (Tutor) service.createPerson(personType, firstName, lastName, username, password, email, null, 0L);
			service.createAppointment(date, startTime, endTime, room, username, status);
		} catch (IllegalArgumentException e) {
			// Check that no error occurred
			//System.out.println(e);
			fail();
		}

		appointments = appointmentRepository.findByDate(date);

		for(int i=0; i<appointments.size(); i++) {
			assertEquals(date, appointments.get(i).getDate());
		}

	

	}
	
	@Test
	public void testGetAppointmentByStartTimeAndEndTime() {
		
		List<Appointment> appointments;
		
		String personType = "Tutor";
		String firstName = "John";
		String lastName = "Smith";
		String username = "johnsmith123";
		String password = "pass123";
		String email = "john.smith@mail.ca";
		
		long roomId = 1000L;

		Tutor tutor = (Tutor) service.createPerson(personType, firstName, lastName, username, password, email, null, 0L);

		Date date = new Date(Calendar.getInstance().getTime().getTime());
		Time endTime = new Time(9, 0, 0);
		Time startTime = new Time(10, 0, 0);
		Room room = service.createRoom(roomId, false);
		String status = "Requested";
		
		try {
			service.createAppointment(date, startTime, endTime, room, username, status);
		} catch (IllegalArgumentException e) {
			// Check that no error occurred
			fail();
		}
		
		appointments = appointmentRepository.findByStartTimeAndEndTime(startTime, endTime);
		
		for(int i=0; i<appointments.size(); i++) {
			assertEquals(startTime, appointments.get(i).getStartTime());
			assertEquals(endTime, appointments.get(i).getEndTime());
		}
	}
	

	/*------------------------------------------*/

	@Test
	public void testCreateAvailabilityNull() {
		assertEquals(0, service.getAllAvailabilities().size());

		Date date = null;
		Date createdDate = null;
		Time startTime = null;
		Time endTime = null;
		Tutor tutor = null;
		String error = null;

		try {
			service.createAvailability(tutor, date, createdDate, startTime, endTime);
		} catch (IllegalArgumentException e) {
			error = e.getMessage().toString();
			System.out.println(error);
		}
		assertEquals(error, "Must specify a tutor! Date cannot be empty! Created date cannot be empty! Start time cannot be empty! End time cannot be empty!");

		//make sure an availability was not created
		assertEquals(0, service.getAllAvailabilities().size());
	}	

	@Test
	public void testCreateAvailability() {
		assertEquals(0, service.getAllAvailabilities().size());

		java.sql.Date date = java.sql.Date.valueOf( "2019-10-03" );
		java.sql.Date createdDate = java.sql.Date.valueOf( "2019-10-03" );
		java.sql.Time startTime = java.sql.Time.valueOf( "17:05:00" );
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
			service.createAvailability(tutor, date, createdDate, startTime, endTime);
		} catch (IllegalArgumentException e) {
			System.out.println(e.getMessage());
			fail();
		}
		
		List<Availability> av = service.getAllAvailabilities();
		//make sure an availability was created
		assertEquals(1, service.getAllAvailabilities().size());
		assertEquals(av.get(0).getTutor().getUsername(), username);
		assertEquals(av.get(0).getDate(), date);
		assertEquals(av.get(0).getCreatedDate(), createdDate);
		assertEquals(av.get(0).getStartTime(), startTime);
		assertEquals(av.get(0).getEndTime(), endTime);
		
		
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
		java.sql.Date createdDate = java.sql.Date.valueOf( "2019-10-03" );
		java.sql.Time startTime = java.sql.Time.valueOf( "19:05:00" );
		java.sql.Time endTime = java.sql.Time.valueOf( "18:05:00" );
		try {
			service.createAvailability(tutor,date,createdDate,startTime,endTime);
		} catch (IllegalArgumentException e) {
			error = e.getMessage().toString();
		}
		assertEquals(error, "End time cannot be before startTime!");

		//make sure an availability was not created
		assertEquals(0, service.getAllAvailabilities().size());
	}

	@Test
	public void testGetAvailabilityByDate() {
		assertEquals(0, service.getAllAvailabilities().size());
		
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
		java.sql.Date createdDate = java.sql.Date.valueOf( "2019-10-01" );
		java.sql.Time startTime = java.sql.Time.valueOf( "18:05:00" );
		java.sql.Time endTime = java.sql.Time.valueOf( "19:05:00" );

		service.createAvailability(tutor,date,createdDate,startTime,endTime);

		//Create 2nd availability
		java.sql.Date date2 = java.sql.Date.valueOf( "2019-10-02" );
		java.sql.Date createdDate2 = java.sql.Date.valueOf( "2019-10-02" );
		java.sql.Time startTime2 = java.sql.Time.valueOf( "17:05:00" );
		java.sql.Time endTime2 = java.sql.Time.valueOf( "18:05:00" );

		service.createAvailability(tutor,date2,createdDate2,startTime2,endTime2);

		try {
			service.getAvailabilityByDate(date);
		} catch (IllegalArgumentException e) {
			fail();
		}
		
		assertEquals(service.getAvailabilityByDate(date).size(), 1);
		assertEquals(service.getAvailabilityByDate(date).get(0).getDate(),date);
		assertEquals(service.getAvailabilityByDate(date).get(0).getCreatedDate(),createdDate);
		assertEquals(service.getAvailabilityByDate(date).get(0).getTutor().getUsername(),username);
		assertEquals(service.getAvailabilityByDate(date).get(0).getStartTime(),startTime);
		assertEquals(service.getAvailabilityByDate(date).get(0).getEndTime(),endTime);
		
		
		

	}

	@Test
	public void testGetAvailabilityByTutorUsername() {
		assertEquals(0, service.getAllAvailabilities().size());
		
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
		java.sql.Date createdDate = java.sql.Date.valueOf( "2019-10-03" );
		java.sql.Time startTime = java.sql.Time.valueOf( "18:05:00" );
		java.sql.Time endTime = java.sql.Time.valueOf( "19:05:00" );

		service.createAvailability(tutor,date,createdDate,startTime,endTime);

		//Create 2nd availability
		java.sql.Date date2 = java.sql.Date.valueOf( "2019-10-04" );
		java.sql.Date createdDate2 = java.sql.Date.valueOf( "2019-10-04" );
		java.sql.Time startTime2 = java.sql.Time.valueOf( "17:05:00" );
		java.sql.Time endTime2 = java.sql.Time.valueOf( "18:05:00" );

		service.createAvailability(tutor,date2,createdDate2,startTime2,endTime2);

		try {
			service.getAvailabilityByTutorUsername(username);
		} catch (IllegalArgumentException e) {
			fail();
		}
		
		assertEquals(service.getAvailabilityByTutorUsername(username).size(), 2);
		assertEquals(service.getAvailabilityByTutorUsername(username).get(0).getDate(),date);
		assertEquals(service.getAvailabilityByTutorUsername(username).get(0).getCreatedDate(),createdDate);
		assertEquals(service.getAvailabilityByTutorUsername(username).get(0).getTutor().getUsername(),username);
		assertEquals(service.getAvailabilityByTutorUsername(username).get(0).getStartTime(),startTime);
		assertEquals(service.getAvailabilityByTutorUsername(username).get(0).getEndTime(),endTime);
		assertEquals(service.getAvailabilityByTutorUsername(username).get(1).getDate(),date2);
		assertEquals(service.getAvailabilityByTutorUsername(username).get(1).getCreatedDate(),createdDate2);
		assertEquals(service.getAvailabilityByTutorUsername(username).get(1).getTutor().getUsername(),username);
		assertEquals(service.getAvailabilityByTutorUsername(username).get(1).getStartTime(),startTime2);
		assertEquals(service.getAvailabilityByTutorUsername(username).get(1).getEndTime(),endTime2);
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
		String error = "";

		try {
			service.createSpecificCourse(tutor, course, hourlyRate);
		} catch (IllegalArgumentException e) {
			System.out.println(e.getMessage());
			error = e.getMessage();
		}

		assertEquals(error, "Tutor cannot be null! Course cannot be null! HourlyRate must be above minimum wage!");

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


	// -----------------------------------------------------------
	//CHARLES BOURBEAU
	//REVIEW REPOSITORY TESTS
	// -----------------------------------------------------------

	@Test
	public void testCreateReview() {					

		specificCourseRepository.deleteAll();
		courseRepository.deleteAll();;
		availabilityRepository.deleteAll();
		reviewRepository.deleteAll();
		appointmentRepository.deleteAll();
		roomRepository.deleteAll();
		personRepository.deleteAll();
		


		assertEquals(0, service.getAllReviews().size());

		String reviewText = "This is text concerning the review. ";
		int rating = 5;
		Time createdTime = Time.valueOf("10:00:00");
		Date createdDate = Date.valueOf("2019-10-10");

		// creating a first person to be the reviewee

		String firstName = "John";
		String lastName = "Smith";
		String username = "johnsmith123";
		String password = "pass123";
		String email = "john.smith@mail.ca";

		Person reviewee = new Person();
		reviewee.setFirstName(firstName);
		reviewee.setLastName(lastName);
		reviewee.setUsername(username);
		reviewee.setPassword(password);
		reviewee.setEmail(email);

		// creating a second person to be the reviewer

		String firstName3 = "Alex";
		String lastName3 = "Jones";
		String username3 = "alexjones123";
		String password3 = "pass123";
		String email3 = "alex.jones@mail.ca";

		Person reviewer = new Person();
		reviewer.setFirstName(firstName3);
		reviewer.setLastName(lastName3);
		reviewer.setUsername(username3);
		reviewer.setPassword(password3);
		reviewer.setEmail(email3);

		// creating the Appointment tied to the review 

		Time startTime = Time.valueOf("10:00:00");
		Time endTime = Time.valueOf("11:00:00");
		Date appointmentDate = Date.valueOf("2019-10-10");

		Room room = new Room();


		Appointment appointment = new Appointment();
		appointment.setStatus(Appointment.AppointmentStatus.ACCEPTED);
		appointment.setRoom(room);

		//			appointment.setTutor(reviewee);
		//			Set<Student> students = new HashSet<Student>();
		//			students.add(reviewer);
		//			appointment.setStudent(students);

		//saving what was created

		personRepository.save(reviewer);
		personRepository.save(reviewee);
		roomRepository.save(room);
		appointmentRepository.save(appointment);


		try {
			service.createReview(reviewText, rating, createdTime, createdDate, reviewee, reviewer, appointment);
		} catch (IllegalArgumentException e) {
			fail();
		}

		List<Review> reviews = service.getAllReviews();
		Review review = reviews.get(0);

		assertEquals(1, reviews.size());
		assertEquals("This is text concerning the review. ", review.getReviewText());
		assertEquals(5, review.getRating());
		assertEquals(reviewee, review.getReviewee());
		assertEquals(reviewer, review.getReviewer());
		assertEquals(appointment, review.getAppointment());


	}

	@Test
	public void testReviewNoText() {

		assertEquals(0, service.getAllReviews().size());

		String reviewText = null;
		int rating = 5;
		Time createdTime = Time.valueOf("10:00:00");
		Date createdDate = Date.valueOf("2019-10-10");

		// creating a first person to be the reviewee

		String firstName = "John";
		String lastName = "Smith";
		String username = "johnsmith123";
		String password = "pass123";
		String email = "john.smith@mail.ca";

		Person reviewee = new Person();
		reviewee.setFirstName(firstName);
		reviewee.setLastName(lastName);
		reviewee.setUsername(username);
		reviewee.setPassword(password);
		reviewee.setEmail(email);

		// creating a second person to be the reviewer

		String firstName3 = "Alex";
		String lastName3 = "Jones";
		String username3 = "alexjones123";
		String password3 = "pass123";
		String email3 = "alex.jones@mail.ca";

		Person reviewer = new Person();
		reviewer.setFirstName(firstName3);
		reviewer.setLastName(lastName3);
		reviewer.setUsername(username3);
		reviewer.setPassword(password3);
		reviewer.setEmail(email3);

		// creating the Appointment tied to the review 

		Time startTime = Time.valueOf("10:00:00");
		Time endTime = Time.valueOf("11:00:00");
		Date appointmentDate = Date.valueOf("2019-10-10");

		Room room = new Room();


		Appointment appointment = new Appointment();
		appointment.setStatus(Appointment.AppointmentStatus.ACCEPTED);
		appointment.setRoom(room);

		//			appointment.setTutor(reviewee);
		//			Set<Student> students = new HashSet<Student>();
		//			students.add(reviewer);
		//			appointment.setStudent(students);

		//saving what was created

		personRepository.save(reviewer);
		personRepository.save(reviewee);
		roomRepository.save(room);
		appointmentRepository.save(appointment);

		String error = null;
		try {
			service.createReview(reviewText, rating, createdTime, createdDate, reviewee, reviewer, appointment);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}

		assertEquals(0, service.getAllReviews().size());
		assertEquals(error, "A review must containt text. ");

	}

	@Test
	public void testReviewNoRating() {

		assertEquals(0, service.getAllReviews().size());

		String reviewText = "This is review text. ";
		Integer rating = null;
		Time createdTime = Time.valueOf("10:00:00");
		Date createdDate = Date.valueOf("2019-10-10");

		// creating a first person to be the reviewee

		String firstName = "John";
		String lastName = "Smith";
		String username = "johnsmith123";
		String password = "pass123";
		String email = "john.smith@mail.ca";

		Person reviewee = new Person();
		reviewee.setFirstName(firstName);
		reviewee.setLastName(lastName);
		reviewee.setUsername(username);
		reviewee.setPassword(password);
		reviewee.setEmail(email);

		// creating a second person to be the reviewer

		String firstName3 = "Alex";
		String lastName3 = "Jones";
		String username3 = "alexjones123";
		String password3 = "pass123";
		String email3 = "alex.jones@mail.ca";

		Person reviewer = new Person();
		reviewer.setFirstName(firstName3);
		reviewer.setLastName(lastName3);
		reviewer.setUsername(username3);
		reviewer.setPassword(password3);
		reviewer.setEmail(email3);

		// creating the Appointment tied to the review 

		Time startTime = Time.valueOf("10:00:00");
		Time endTime = Time.valueOf("11:00:00");
		Date appointmentDate = Date.valueOf("2019-10-10");

		Room room = new Room();


		Appointment appointment = new Appointment();
		appointment.setStatus(Appointment.AppointmentStatus.ACCEPTED);
		appointment.setRoom(room);

		//			appointment.setTutor(reviewee);
		//			Set<Student> students = new HashSet<Student>();
		//			students.add(reviewer);
		//			appointment.setStudent(students);

		//saving what was created

		personRepository.save(reviewer);
		personRepository.save(reviewee);
		roomRepository.save(room);
		appointmentRepository.save(appointment);

		String error = null;
		try {
			service.createReview(reviewText, rating, createdTime, createdDate, reviewee, reviewer, appointment);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}

		assertEquals(0, service.getAllReviews().size());
		assertEquals(error, "A rating must be a number between 0 and 5. ");

	}

	@Test
	public void testReviewWithInvalidRating() {

		assertEquals(0, service.getAllReviews().size());

		String reviewText = "This is review text. ";

		// first test rating with a value too high
		Integer rating = 6;

		Time createdTime = Time.valueOf("10:00:00");
		Date createdDate = Date.valueOf("2019-10-10");

		// creating a first person to be the reviewee

		String firstName = "John";
		String lastName = "Smith";
		String username = "johnsmith123";
		String password = "pass123";
		String email = "john.smith@mail.ca";

		Person reviewee = new Person();
		reviewee.setFirstName(firstName);
		reviewee.setLastName(lastName);
		reviewee.setUsername(username);
		reviewee.setPassword(password);
		reviewee.setEmail(email);

		// creating a second person to be the reviewer

		String firstName3 = "Alex";
		String lastName3 = "Jones";
		String username3 = "alexjones123";
		String password3 = "pass123";
		String email3 = "alex.jones@mail.ca";

		Person reviewer = new Person();
		reviewer.setFirstName(firstName3);
		reviewer.setLastName(lastName3);
		reviewer.setUsername(username3);
		reviewer.setPassword(password3);
		reviewer.setEmail(email3);

		// creating the Appointment tied to the review 

		Time startTime = Time.valueOf("10:00:00");
		Time endTime = Time.valueOf("11:00:00");
		Date appointmentDate = Date.valueOf("2019-10-10");

		Room room = new Room();


		Appointment appointment = new Appointment();
		appointment.setStatus(Appointment.AppointmentStatus.ACCEPTED);
		appointment.setRoom(room);

		//			appointment.setTutor(reviewee);
		//			Set<Student> students = new HashSet<Student>();
		//			students.add(reviewer);
		//			appointment.setStudent(students);

		//saving what was created

		personRepository.save(reviewer);
		personRepository.save(reviewee);
		roomRepository.save(room);
		appointmentRepository.save(appointment);

		String error = null;
		try {
			service.createReview(reviewText, rating, createdTime, createdDate, reviewee, reviewer, appointment);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}

		assertEquals(0, service.getAllReviews().size());
		assertEquals(error, "A rating must be a number between 0 and 5. ");

		//now set the rating too low
		rating = -5;

		error = null;
		try {
			service.createReview(reviewText, rating, createdTime, createdDate, reviewee, reviewer, appointment);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}

		assertEquals(0, service.getAllReviews().size());
		assertEquals(error, "A rating must be a number between 0 and 5. ");
	}

	@Test 
	public void testReviewNoCreatedTime() {

		assertEquals(0, service.getAllReviews().size());

		String reviewText = "This is review text. ";
		Integer rating = 5;
		Time createdTime = null;
		Date createdDate = Date.valueOf("2019-10-10");

		// creating a first person to be the reviewee

		String firstName = "John";
		String lastName = "Smith";
		String username = "johnsmith123";
		String password = "pass123";
		String email = "john.smith@mail.ca";

		Person reviewee = new Person();
		reviewee.setFirstName(firstName);
		reviewee.setLastName(lastName);
		reviewee.setUsername(username);
		reviewee.setPassword(password);
		reviewee.setEmail(email);

		// creating a second person to be the reviewer

		String firstName3 = "Alex";
		String lastName3 = "Jones";
		String username3 = "alexjones123";
		String password3 = "pass123";
		String email3 = "alex.jones@mail.ca";

		Person reviewer = new Person();
		reviewer.setFirstName(firstName3);
		reviewer.setLastName(lastName3);
		reviewer.setUsername(username3);
		reviewer.setPassword(password3);
		reviewer.setEmail(email3);

		// creating the Appointment tied to the review 

		Time startTime = Time.valueOf("10:00:00");
		Time endTime = Time.valueOf("11:00:00");
		Date appointmentDate = Date.valueOf("2019-10-10");

		Room room = new Room();


		Appointment appointment = new Appointment();
		appointment.setStatus(Appointment.AppointmentStatus.ACCEPTED);
		appointment.setRoom(room);

		//			appointment.setTutor(reviewee);
		//			Set<Student> students = new HashSet<Student>();
		//			students.add(reviewer);
		//			appointment.setStudent(students);

		//saving what was created

		personRepository.save(reviewer);
		personRepository.save(reviewee);
		roomRepository.save(room);
		appointmentRepository.save(appointment);

		String error = null;
		try {
			service.createReview(reviewText, rating, createdTime, createdDate, reviewee, reviewer, appointment);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}

		assertEquals(0, service.getAllReviews().size());
		assertEquals(error, "The review must have a time of creation. ");
	}

	@Test
	public void testReviewNoCreatedDate() {

		assertEquals(0, service.getAllReviews().size());

		String reviewText = "This is review text. ";
		Integer rating = 5;
		Time createdTime = Time.valueOf("10:00:00");
		Date createdDate = null;

		// creating a first person to be the reviewee

		String firstName = "John";
		String lastName = "Smith";
		String username = "johnsmith123";
		String password = "pass123";
		String email = "john.smith@mail.ca";

		Person reviewee = new Person();
		reviewee.setFirstName(firstName);
		reviewee.setLastName(lastName);
		reviewee.setUsername(username);
		reviewee.setPassword(password);
		reviewee.setEmail(email);

		// creating a second person to be the reviewer

		String firstName3 = "Alex";
		String lastName3 = "Jones";
		String username3 = "alexjones123";
		String password3 = "pass123";
		String email3 = "alex.jones@mail.ca";

		Person reviewer = new Person();
		reviewer.setFirstName(firstName3);
		reviewer.setLastName(lastName3);
		reviewer.setUsername(username3);
		reviewer.setPassword(password3);
		reviewer.setEmail(email3);

		// creating the Appointment tied to the review 

		Time startTime = Time.valueOf("10:00:00");
		Time endTime = Time.valueOf("11:00:00");
		Date appointmentDate = Date.valueOf("2019-10-10");

		Room room = new Room();


		Appointment appointment = new Appointment();
		appointment.setStatus(Appointment.AppointmentStatus.ACCEPTED);
		appointment.setRoom(room);

		//			appointment.setTutor(reviewee);
		//			Set<Student> students = new HashSet<Student>();
		//			students.add(reviewer);
		//			appointment.setStudent(students);

		//saving what was created

		personRepository.save(reviewer);
		personRepository.save(reviewee);
		roomRepository.save(room);
		appointmentRepository.save(appointment);

		String error = null;
		try {
			service.createReview(reviewText, rating, createdTime, createdDate, reviewee, reviewer, appointment);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}

		assertEquals(0, service.getAllReviews().size());
		assertEquals(error, "The review must have a date of creation. ");
	}

	@Test 
	public void testCreateReviewNoReviewee() {

		assertEquals(0, service.getAllReviews().size());

		String reviewText = "This is review text. ";
		Integer rating = 5;
		Time createdTime = Time.valueOf("10:00:00");
		Date createdDate = Date.valueOf("2019-10-10");

		// creating a first person to be the reviewee. it is null

		Person reviewee = null;


		// creating a second person to be the reviewer

		String firstName3 = "Alex";
		String lastName3 = "Jones";
		String username3 = "alexjones123";
		String password3 = "pass123";
		String email3 = "alex.jones@mail.ca";

		Person reviewer = new Person();
		reviewer.setFirstName(firstName3);
		reviewer.setLastName(lastName3);
		reviewer.setUsername(username3);
		reviewer.setPassword(password3);
		reviewer.setEmail(email3);

		// creating the Appointment tied to the review 

		Time startTime = Time.valueOf("10:00:00");
		Time endTime = Time.valueOf("11:00:00");
		Date appointmentDate = Date.valueOf("2019-10-10");

		Room room = new Room();


		Appointment appointment = new Appointment();
		appointment.setStatus(Appointment.AppointmentStatus.ACCEPTED);
		appointment.setRoom(room);

		//			appointment.setTutor(reviewee);
		//			Set<Student> students = new HashSet<Student>();
		//			students.add(reviewer);
		//			appointment.setStudent(students);

		//saving what was created

		personRepository.save(reviewer);
		personRepository.save(reviewee);
		roomRepository.save(room);
		appointmentRepository.save(appointment);

		String error = null;
		try {
			service.createReview(reviewText, rating, createdTime, createdDate, reviewee, reviewer, appointment);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}

		assertEquals(0, service.getAllReviews().size());
		assertEquals(error, "The review must have a reviewee. ");

	}

	@Test 
	public void testCreateReviewNoReviewer() {

		assertEquals(0, service.getAllReviews().size());

		String reviewText = "This is review text. ";
		Integer rating = 5;
		Time createdTime = Time.valueOf("10:00:00");
		Date createdDate = Date.valueOf("2019-10-10");

		// creating a first person to be the reviewer. it is null

		Person reviewer = null;


		// creating a second person to be the reviewee

		String firstName3 = "Alex";
		String lastName3 = "Jones";
		String username3 = "alexjones123";
		String password3 = "pass123";
		String email3 = "alex.jones@mail.ca";

		Person reviewee = new Person();
		reviewee.setFirstName(firstName3);
		reviewee.setLastName(lastName3);
		reviewee.setUsername(username3);
		reviewee.setPassword(password3);
		reviewee.setEmail(email3);

		// creating the Appointment tied to the review 

		Time startTime = Time.valueOf("10:00:00");
		Time endTime = Time.valueOf("11:00:00");
		Date appointmentDate = Date.valueOf("2019-10-10");

		Room room = new Room();


		Appointment appointment = new Appointment();
		appointment.setStatus(Appointment.AppointmentStatus.ACCEPTED);
		appointment.setRoom(room);

		//			appointment.setTutor(reviewee);
		//			Set<Student> students = new HashSet<Student>();
		//			students.add(reviewer);
		//			appointment.setStudent(students);

		//saving what was created

		personRepository.save(reviewer);
		personRepository.save(reviewee);
		roomRepository.save(room);
		appointmentRepository.save(appointment);

		String error = null;
		try {
			service.createReview(reviewText, rating, createdTime, createdDate, reviewee, reviewer, appointment);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}

		assertEquals(0, service.getAllReviews().size());
		assertEquals(error, "The review must have a reviewer. ");

	}

	@Test
	public void testReviewNoAppointment() {
		
		assertEquals(0, service.getAllReviews().size());

		String reviewText = "This is review text. ";
		int rating = 5;
		Time createdTime = Time.valueOf("10:00:00");
		Date createdDate = Date.valueOf("2019-10-10");

		// creating a first person to be the reviewee

		String firstName = "John";
		String lastName = "Smith";
		String username = "johnsmith123";
		String password = "pass123";
		String email = "john.smith@mail.ca";

		Person reviewee = new Person();
		reviewee.setFirstName(firstName);
		reviewee.setLastName(lastName);
		reviewee.setUsername(username);
		reviewee.setPassword(password);
		reviewee.setEmail(email);

		// creating a second person to be the reviewer

		String firstName3 = "Alex";
		String lastName3 = "Jones";
		String username3 = "alexjones123";
		String password3 = "pass123";
		String email3 = "alex.jones@mail.ca";

		Person reviewer = new Person();
		reviewer.setFirstName(firstName3);
		reviewer.setLastName(lastName3);
		reviewer.setUsername(username3);
		reviewer.setPassword(password3);
		reviewer.setEmail(email3);

		// creating the Appointment tied to the review. it is null

		Appointment appointment = null;

		//			appointment.setTutor(reviewee);
		//			Set<Student> students = new HashSet<Student>();
		//			students.add(reviewer);
		//			appointment.setStudent(students);

		//saving what was created

		personRepository.save(reviewer);
		personRepository.save(reviewee);

		String error = null;
		try {
			service.createReview(reviewText, rating, createdTime, createdDate, reviewee, reviewer, appointment);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}

		assertEquals(0, service.getAllReviews().size());
		assertEquals(error, "The review must have an appointment. ");
	}

	@Test
	public void testDeleteNonExistentReview() {
		
		assertEquals(0, service.getAllReviews().size());

		// there is no review
		long invalid_id = -1;
		
		String error = null;
		try {
			service.deleteReview(invalid_id);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		
		assertEquals(error, "This review does not exist. ");

		
	}

	@Test
	public void testGetReviewByReviewID() {
		//create a new review, the id should be generated automatically

		Review review = new Review();
		reviewRepository.save(review);

		long id = review.getReviewID();

		assertEquals(service.getReviewByReviewID(id), review);

	}

	@Test
	public void testGetReviewsByReviewee(){

		//create the review

		//create the reviewee
		String firstName = "John";
		String lastName = "Smith";
		String username = "johnsmith123";
		String password = "pass123";
		String email = "john.smith@mail.ca";
		
		String personType3 = "Tutor";
		String firstName3 = "Alex";
		String lastName3 = "Jones";
		String username3 = "alexjones123";
		String password3 = "pass123";
		String email3 = "alex.jones@mail.ca";
		
		Tutor reviewee = (Tutor) service.createPerson(personType3, firstName3, lastName3, username3, password3, email3, null, 1000L);
		Student reviewer = (Student) service.createPerson("Student", firstName, lastName, username, password, email, null, 10L);
		Room room = service.createRoom(123L, false);
		
		Appointment appointment = service.createAppointment(new Date(Calendar.getInstance().getTime().getTime()), new Time(Calendar.getInstance().getTime().getTime()), new Time(Calendar.getInstance().getTime().getTime()), room, "alexjones123", "Requested");
		
		Review review1 = service.createReview("Hello", new Integer(4), new Time(Calendar.getInstance().getTime().getTime()),new Date(Calendar.getInstance().getTime().getTime()), reviewee, reviewer, appointment);
		

		assertEquals(service.getReviewsByReviewee(reviewee).get(0).getReviewID(), review1.getReviewID());
	}

	@Test
	public void testGetReviewsByReviewer(){

		//create the review
		Review review = new Review();
		reviewRepository.save(review);

		//create the reviewer
		String firstName = "John";
		String lastName = "Smith";
		String username = "johnsmith123";
		String password = "pass123";
		String email = "john.smith@mail.ca";

		Person reviewer = new Person();
		reviewer.setFirstName(firstName);
		reviewer.setLastName(lastName);
		reviewer.setUsername(username);
		reviewer.setPassword(password);
		reviewer.setEmail(email);

		assertEquals(service.getReviewsByReviewer(reviewer), review);

	}
	
	@Test
	public void testGetReviewsByAppointment(){

		//create the review
		Review review = new Review();
		reviewRepository.save(review);

		//creating the appointment
		Room room = new Room();					
		Appointment appointment = new Appointment();
		appointment.setStatus(Appointment.AppointmentStatus.ACCEPTED);
		appointment.setRoom(room);

		appointmentRepository.save(appointment);
		roomRepository.save(room);

		assertEquals(service.getReviewsByAppointment(appointment), review);
	}

	@Test
	public void testGetAllReviews(){

		// create 3 reviews

		Review review1 = new Review();
		reviewRepository.save(review1);

		Review review2 = new Review();
		reviewRepository.save(review2);

		Review review3 = new Review();
		reviewRepository.save(review3);


		ArrayList<Review> reviews = new ArrayList<Review>();
		reviews.add(review1);
		reviews.add(review2);
		reviews.add(review3);

		ArrayList<Review> reviewsToTest = (ArrayList<Review>) service.getAllReviews();

		for(int i = 0; i < 2; i++) {
			assertEquals(reviewsToTest.get(i), reviews.get(i));
		}

	}


	@Test
	public void testDeleteReview() {

		assertEquals(0, service.getAllReviews().size());

		// create a review
		Review review = new Review();
		reviewRepository.save(review);

		//confirm the review is really there
		assertEquals(1, service.getAllReviews().size());

		//delete the review
		service.deleteReview(review.getReviewID());

		//confirm it was deleted
		assertEquals(0, service.getAllReviews().size());

	}

	@Test
	public void testDeleteAllReviews(){

		assertEquals(0, service.getAllReviews().size());

		//create 3 reviews			
		Review review1 = new Review();
		reviewRepository.save(review1);

		Review review2 = new Review();
		reviewRepository.save(review2);

		Review review3 = new Review();
		reviewRepository.save(review3);

		assertEquals(3, service.getAllReviews().size());

		//delete the reviews			
		service.deleteAllReviews();

		//confirm they were deleted
		assertEquals(0, service.getAllReviews().size());

	}


}

