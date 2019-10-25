package ca.mcgill.ecse321.projectgroup17;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.sql.Date;
import java.sql.Time;
import java.util.Calendar;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import ca.mcgill.ecse321.projectgroup17.dao.AppointmentRepository;
import ca.mcgill.ecse321.projectgroup17.dao.AvailabilityRepository;
import ca.mcgill.ecse321.projectgroup17.dao.CourseRepository;
import ca.mcgill.ecse321.projectgroup17.dao.PersonRepository;
import ca.mcgill.ecse321.projectgroup17.dao.ReviewRepository;
import ca.mcgill.ecse321.projectgroup17.dao.RoomRepository;
import ca.mcgill.ecse321.projectgroup17.dao.SpecificCourseRepository;
import ca.mcgill.ecse321.projectgroup17.dao.StudentRepository;
import ca.mcgill.ecse321.projectgroup17.dao.TutorRepository;
import ca.mcgill.ecse321.projectgroup17.model.Appointment.AppointmentStatus;
import ca.mcgill.ecse321.projectgroup17.model.Availability;
import ca.mcgill.ecse321.projectgroup17.model.Course;
import ca.mcgill.ecse321.projectgroup17.model.*;
import ca.mcgill.ecse321.projectgroup17.model.Tutor;
import ca.mcgill.ecse321.projectgroup17.service.ProjectGroup17Service;

@RunWith(SpringRunner.class)
@SpringBootTest

public class DeleteDatabaseTest {

	
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
	public void test() { 
		/* Below was used for testing purposes only.
		Tutor t = (Tutor) service.createPerson("Tutor", "charles", "poulin", "cb", "h", "4@mail.ca", "undecided", 69);
		Student s = (Student) service.createPerson("Student", "ty", "poulin", "ty", "h", "4@mail.ca", "undecided", 69);
		Availability a = service.createAvailability(t, new Date(Calendar.getInstance().getTime().getTime()), new Date(Calendar.getInstance().getTime().getTime())
				, new Time(Calendar.getInstance().getTime().getTime()), new Time(Calendar.getInstance().getTime().getTime() + 3600000));
		Room r = service.createRoom(1000L, false);
		Appointment ap = service.createAppointment(new Date(Calendar.getInstance().getTime().getTime()), new Time(Calendar.getInstance().getTime().getTime())
				, new Time(Calendar.getInstance().getTime().getTime()), r, t, AppointmentStatus.REQUESTED.toString());
		Review rev = service.createReview("h", 4, new Time(Calendar.getInstance().getTime().getTime()), new Date(Calendar.getInstance().getTime().getTime()), t, s, ap);
		Course course = service.createCourse("ECSE123", "HELLO", "University", "Engineering");
		SpecificCourse sc = service.createSpecificCourse(t, course, 5D);
		
		*/
	}
}
