package ca.mcgill.ecse321.projectgroup17;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatcher;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.internal.matchers.InstanceOf;
import org.mockito.invocation.InvocationOnMock;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;

import ca.mcgill.ecse321.projectgroup17.controller.ProjectGroup17RestController;
import ca.mcgill.ecse321.projectgroup17.dao.PersonRepository;
import ca.mcgill.ecse321.projectgroup17.model.*;
import ca.mcgill.ecse321.projectgroup17.model.Appointment.AppointmentStatus;
import ca.mcgill.ecse321.projectgroup17.model.Course.Level;
import ca.mcgill.ecse321.projectgroup17.service.ProjectGroup17Service;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.ArgumentMatchers.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.internal.progress.ThreadSafeMockingProgress.mockingProgress;

import java.sql.Date;
import java.sql.Time;
import java.util.Calendar;

@RunWith(SpringRunner.class)
@WebMvcTest(value = ProjectGroup17RestController.class, secure = false)
public class TestProjectGroup17RestController {


	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private ProjectGroup17Service service;

	@Mock
	private PersonRepository personDao;


	private static final String PERSON_KEY = "TestPerson";
	private static final String NONEXISTING_KEY = "NotAPerson";

	@Test
	public void testCreateCourse() throws Exception {


		String courseID = "TEST101";
		String name = "Intro to Testing Engineering";
		String level = "University";
		String subject = "Engineering";

		Level courseLevel = Level.valueOf(level.toUpperCase());

		when(service.createCourse(anyString(), anyString(), anyString(), anyString())).thenAnswer( (InvocationOnMock invocation) -> {
			if(invocation.getArgument(0).equals(courseID)) {
				Course course = new Course();
				course.setCourseID(courseID);
				course.setName(name);
				course.setLevel(courseLevel);
				course.setSubject(subject);
				return course;
			} else {
				return null;
			}
		});

		RequestBuilder requestBuilder = MockMvcRequestBuilders.post(
				"/courses/createCourse?courseID="+courseID+"&courseName="+name+"&level="+level+"&subject="+subject
				).accept(
						MediaType.APPLICATION_JSON);


		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		String expected = "{\"level\":\"UNIVERSITY\",\"courseName\":\"Intro to Testing Engineering\",\"specificCourses\":null,\"subject\":\"Engineering\",\"courseID\":\"TEST101\"}";
		assertEquals(expected, result.getResponse().getContentAsString());
	}


	@Test
	public void testCreateSpecificCourse() throws Exception {

		double hourlyRate = 16.50;
		String tutorUsername = "jimmyflimmy";
		String courseID = "TEST101";

		// convert to objects
			
		// Create a tutor object/row
		String personType = "Tutor";
		String firstName = "Jim";
		String lastName = "Flim";
		String username = "jimmyflimmy";
		String password = "pass123";
		String email = "jim@mail.ca";
		String sexe = "male";
		long age = 29L;
		
		Tutor t = (Tutor) service.createPerson(personType, firstName, lastName, username, password, email, sexe, age);
		
		// Create a course object/row
		String courseName = "Intro to Testing Engineering";
		String level = "UNIVERSITY";
		String subject = "Engineering";

		Course c = service.createCourse(courseID, courseName, level, subject);
		
		
		//Tutor t = (Tutor) service.getPersonByUsername(tutorUsername);
		//Course c = (Course) service.getCourseByID(courseID);
		

		when(service.createSpecificCourse(anyTutor(), anyCourse(), anyDouble())).thenAnswer( (InvocationOnMock invocation) -> {
			SpecificCourse sc = new SpecificCourse();
			sc.setHourlyRate(hourlyRate);
			sc.setCourse(c);
			sc.setTutor(t);
			return sc;
		});

		RequestBuilder requestBuilder = MockMvcRequestBuilders.post(
				"/specificCourses/create?hourlyRate="+hourlyRate+"&tutorUsername="+tutorUsername+"&courseID="+courseID)
				.accept(
						MediaType.APPLICATION_JSON);


		// We cannot assertEquals an autoGenrated id value
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		//String expected = "{\"hourlyRate\":16.50,\"tutorUsername\":\"jimmyflimmy\",\"courseID\":\"TEST101\"}";
		//assertEquals(true, result.getResponse().getContentAsString().contains("hourlyRate"));
		//assertEquals(true, result.getResponse().getContentAsString().contains("tutorUsername"));
		//assertEquals(true, result.getResponse().getContentAsString().contains("courseID"));
	}


	@Test
	public void testGetPersonByUsername() throws Exception {

		when(service.getPersonByUsername(anyString())).thenAnswer( (InvocationOnMock invocation) -> {
			if(invocation.getArgument(0).equals(PERSON_KEY)) {
				Person person = new Person();
				person.setUsername(PERSON_KEY);
				return person;
			} else {
				return null;
			}
		});

		RequestBuilder requestBuilder = MockMvcRequestBuilders.get(
				"/persons/getByUsername?username="+PERSON_KEY).accept(
						MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		String expected = "{\"firstName\":null,\"lastName\":null,\"username\":\"TestPerson\",\"email\":null,\"password\":null,\"sexe\":null,\"age\":0,\"personType\":null,\"name\":\"null null\"}";

		assertEquals(expected, result.getResponse().getContentAsString());

	}

	@Test
	public void testCreatePerson() throws Exception {

		String personType = "Tutor";
		String firstName = "John";
		String lastName = "Smith";
		String username = "johnsmith123";
		String password = "pass123";
		String email = "john.smith@mail.ca";
		String sexe = "male";
		long age = 29L;

		when(service.createPerson(anyString(), anyString(), anyString(), anyString(), anyString(), anyString(), anyString(), anyLong())).thenAnswer( (InvocationOnMock invocation) -> {
			if(invocation.getArgument(3).equals(username)) {
				Person person = new Person();
				person.setFirstName(firstName);
				person.setLastName(lastName);
				person.setUsername(username);
				person.setEmail(email);
				person.setPassword(password);
				person.setSexe(sexe);
				person.setAge(age);
				return person;
			} else {
				return null;
			}
		});

		RequestBuilder requestBuilder = MockMvcRequestBuilders.post(
				"/persons/createPerson?firstName="+firstName+"&lastName="+lastName+"&username="+username+"&personType="+personType
				+"&password="+password+"&email="+email+"&sexe="+sexe+"&age="+age).accept(
						MediaType.APPLICATION_JSON);


		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		String expected = "{\"firstName\":\"John\",\"lastName\":\"Smith\",\"username\":\"johnsmith123\","
				+ "\"email\":\"john.smith@mail.ca\",\"password\":\"pass123\",\"sexe\":null,\"age\":29,"
				+ "\"personType\":null,\"name\":\"John Smith\"}";
		assertEquals(expected, result.getResponse().getContentAsString());
	}

	/*
	@Test
	public void testCreateAppointment() throws Exception {

		String personType = "Tutor";
		String firstName = "John";
		String lastName = "Smith";
		String username = "johnsmith123";
		String password = "pass123";
		String email = "john.smith@mail.ca";
		String sexe = "male";
		long age = 29L;

		Tutor person = new Tutor();
		person.setFirstName(firstName);
		person.setLastName(lastName);
		person.setUsername(username);
		person.setEmail(email);
		person.setPassword(password);
		person.setSexe(sexe);
		person.setAge(age);

		Date date = new Date(Calendar.getInstance().getTime().getTime());
		Time endTime = new Time(9, 0, 0);
		Time startTime = new Time(10, 0, 0);
		long roomId = 1060L;
		boolean isBig = false;
		String status = "Requested";
		Room room = service.createRoom(roomId, isBig);
		//System.out.println(room.getRoomID());

		when(service.createAppointment(anyDate(), anyTime(), anyTime(), anyRoom(), anyTutor(), anyString())).thenAnswer( (InvocationOnMock invocation) -> {
			if(invocation.getArgument(3).equals(username)) {
				Appointment appt = new Appointment();
				appt.setDate(date);
				appt.setStartTime(startTime);
				appt.setEndTime(endTime);
				appt.setRoom(room);
				appt.setTutor(person);
				appt.setCreatedDate(new Date(Calendar.getInstance().getTime().getTime()));
				AppointmentStatus apptStatus = AppointmentStatus.valueOf(status.toUpperCase());
				appt.setStatus(apptStatus);
				return appt;
			} else {
				return null;
			}
		});

		RequestBuilder requestBuilder = MockMvcRequestBuilders.post(
				"/appointments/createAppointment?date="+Calendar.getInstance().getTime().getTime()+"&startTime="+Calendar.getInstance().getTime().getTime()+"&endTime="+Calendar.getInstance().getTime().getTime()+"&tutorUsername=jimmyflimmy"
						+"&roomId="+roomId+"&status="+status).accept(
								MediaType.APPLICATION_JSON);



		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		String expected = "{\"firstName\":\"John\",\"lastName\":\"Smith\",\"username\":\"johnsmith123\","
				+ "\"email\":\"john.smith@mail.ca\",\"password\":\"pass123\",\"sexe\":null,\"age\":29,"
				+ "\"personType\":null,\"name\":\"John Smith\"}";
		System.out.println("HFIWEJFOEFEOFEFW");
		System.out.println(result.getResponse().getStatus());
		assertEquals(expected, result.getResponse().getContentAsString());
	}
	 */


	@Test
	public void testCreateRoom() throws Exception {

		long roomID = 1010L;
		boolean isBig = true;

		when(service.createRoom(anyLong(), anyBoolean())).thenAnswer( (InvocationOnMock invocation) -> {
			Room room = new Room();
			room.setRoomID(roomID);
			room.setBig(isBig);
			return room;
		});

		RequestBuilder requestBuilder = MockMvcRequestBuilders.post(
				"/rooms/createRoom?roomID="+roomID+"&isBig="+isBig).accept(
						MediaType.APPLICATION_JSON);


		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		String expected = "{\"roomId\":1010,\"isBig\":true}";
		assertEquals(expected, result.getResponse().getContentAsString());
	}

	public static Date anyDate() {
		reportMatcher(new InstanceOf(Date.class, "<any date>"));
		return new Date(Calendar.getInstance().getTimeInMillis());
	}

	public static Time anyTime() {
		reportMatcher(new InstanceOf(Time.class, "<any time>"));
		return new Time(Calendar.getInstance().getTimeInMillis());
	}

	public static Room anyRoom() {
		reportMatcher(new InstanceOf(Room.class, "<any room>"));
		return new Room();
	}

	public static Tutor anyTutor() {
		reportMatcher(new InstanceOf(Tutor.class, "<any tutor>"));
		return new Tutor();
	}

	public static Course anyCourse() {
		reportMatcher(new InstanceOf(Course.class, "<any course>"));
		return new Course();
	}

	private static void reportMatcher(ArgumentMatcher<?> matcher) {
		mockingProgress().getArgumentMatcherStorage().reportMatcher(matcher);
	}

}