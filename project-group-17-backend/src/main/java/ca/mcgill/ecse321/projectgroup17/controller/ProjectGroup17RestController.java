package ca.mcgill.ecse321.projectgroup17.controller;


import java.sql.Date;
import java.sql.Time;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ca.mcgill.ecse321.projectgroup17.dto.*;
import ca.mcgill.ecse321.projectgroup17.model.*;
import ca.mcgill.ecse321.projectgroup17.model.Appointment.AppointmentStatus;
import ca.mcgill.ecse321.projectgroup17.service.ProjectGroup17Service;

@CrossOrigin(origins = "*")
@RestController
public class ProjectGroup17RestController {
	
	@Autowired
	ProjectGroup17Service service;
	
	
	/*----------- PERSON ----------*/
	
	@PostMapping(value = { "/persons/createPerson", "/persons/createPerson/" })
	public PersonDto createPerson(@RequestParam("firstName") String firstName, @RequestParam("lastName") String lastName, 
			@RequestParam("username") String username, @RequestParam("personType") String personType, 
			@RequestParam("password") String password, @RequestParam("email") String email, @RequestParam("sexe") String sexe, 
			@RequestParam("age") long age) throws IllegalArgumentException {
		Person person = service.createPerson(personType, firstName, lastName, username, password, email, sexe, age);
		return convertToDto(person);
	}
	
	@GetMapping(value = { "/persons", "/persons/" })
	public List<PersonDto> getAllPersons() {
		List<PersonDto> personDtos = new ArrayList<>();
		for (Person person : service.getAllPersons()) {
			personDtos.add(convertToDto(person));
		}
		return personDtos;
	}
	
	@GetMapping(value = { "/persons/getByUsername", "/persons/getByUsername/" })
	public PersonDto getPersonByUsername(@RequestParam("username") String username) {
		Person p = service.getPersonByUsername(username);
		return convertToDto(p);
	}
	
	@GetMapping(value = { "/persons/getByEmail", "/persons/getByEmail/" })
	public PersonDto getPersonByEmail(@RequestParam("email") String email) {
		Person p = service.getPersonByEmail(email);
		return convertToDto(p);
	}
	
	@GetMapping(value = { "/persons/getByFirstNameAndLastName", "/persons/getByFirstNameAndLastName/" })
	public List<PersonDto> getPersonByFirstNameAndLastName(@RequestParam("firstName") String firstName, @RequestParam("lastName") String lastName) {
		List<PersonDto> result = new ArrayList<PersonDto>();
		List<Person> p = service.getPersonByFirstNameAndLastName(firstName, lastName);
		for(int i=0; i<p.size(); i++) {
			result.add(convertToDto(p.get(i)));
		}
		return result;
	}
	
	

	/*----------- APPOINTMENT --------------*/
	
	@GetMapping(value = { "/appointments/tutor", "/appointments/tutor/" })
	public List<AppointmentDto> getAppointmentsOfTutor(@RequestParam("username") String username) {
		Tutor t = (Tutor) service.getPersonByUsername(username);
		return createAppointmentDtosForTutor(t);
	}
	
	@PostMapping(value = { "/appointments/createAppointment", "/appointments/createAppointment/" })
	public AppointmentDto createAppointment(@RequestParam("date") long date, @RequestParam("startTime") long startTime, 
			@RequestParam("endTime") long endTime, @RequestParam("tutorUsername") String tutorUsername, 
			@RequestParam("roomId") long roomId, @RequestParam("status") String status) {
		
		Date realDate = new Date(date);
		Time realStartTime = new Time(startTime);
		Time realEndTime = new Time(endTime);
		Room room = service.getRoomByRoomID(roomId);
		Tutor tutor = (Tutor) service.getPersonByUsername(tutorUsername);
		Appointment appt = service.createAppointment(realDate, realEndTime, realStartTime, room, tutor, status);
		return convertToDto(appt);
	}
	
	private List<AppointmentDto> createAppointmentDtosForTutor(Tutor t) {
		List<Appointment> apptsForTutor = service.getAppointmentsByTutor(t);
		List<AppointmentDto> appts = new ArrayList<>();
		for (Appointment appt : apptsForTutor) {
			appts.add(convertToDto(appt));
		}
		return appts;
	}
	
	
	/*----------- COURSE ----------*/
	
	@PostMapping(value = { "/courses/createCourse", "/courses/createCourse/" })
	public CourseDto createCourse(@RequestParam("courseID") String courseID, @RequestParam("courseName") String courseName, 
			@RequestParam("level") String level, @RequestParam("subject") String subject) throws IllegalArgumentException {
		Course course = service.createCourse(courseID, courseName, level, subject);
		return convertToDto(course);
	}
	
	@GetMapping(value = { "/courses/courseID", "/courses/courseID/" })
	public List<CourseDto> getCourseByCourseID(@RequestParam("courseID") String courseID) {
		Course c = (Course) service.getCourseByID(courseID);
		List<CourseDto> coursesDto = new ArrayList<>();
		coursesDto.add(convertToDto(c));
		return coursesDto;
	}
	
	@GetMapping(value = { "/courses/subject", "/courses/subject/" })
	public List<CourseDto> getCoursesBySubject(@RequestParam("subject") String subject) {
		List<Course> courses = service.getCoursesBySubject(subject);
		List<CourseDto> coursesDto = new ArrayList<>();
		for(Course c : courses) {
			coursesDto.add(convertToDto(c));
		}
		return coursesDto;
	}
	
	/*
	@GetMapping(value = { "/courses/level", "/courses/level/" })
	public List<CourseDto> getCoursesByLevel(@RequestParam("level") String level) {
		List<Course> courses = service.getCoursesByLevel(level);
		List<CourseDto> coursesDto = new ArrayList<>();
		for(Course c : courses) {
			coursesDto.add(convertToDto(c));
		}
		
	 */
	
	@GetMapping(value = { "/courses", "/courses/" })
	public List<CourseDto> getAllCourses() {
		List<CourseDto> coursesDto = new ArrayList<>();
		for (Course course : service.getAllCourses()) {
			coursesDto.add(convertToDto(course));
		}
		return coursesDto;
	}
	
	
	/*----------- REVIEW ----------*/
	
	/**
	 * Creates a review object using the service method createReview.
	 * @param reviewText
	 * @param rating
	 * @param createdTime
	 * @param createdDate
	 * @param reviewee
	 * @param reviewer
	 * @param appointment
	 * @return	The review object as a ReviewDto.
	 * @throws IllegalArgumentException
	 */
	@PostMapping(value = { "/reviews/createReview", "/reviews/createReview/" })
	public ReviewDto createReview(@RequestParam("reviewText") String reviewText, @RequestParam("rating") int rating,
			@RequestParam("createdTime") Time createdTime, @RequestParam("createdDate") Date createdDate,
			@RequestParam("reviewee") Person reviewee, @RequestParam("reviewer") Person reviewer,
			@RequestParam("appointment") Appointment appointment) throws IllegalArgumentException {
		
		// formatter : on 
		Review review = service.createReview(reviewText, rating, createdTime, createdDate, reviewee, reviewer, appointment);
		return convertToDto(review);
		
	}
	
	/**
	 * Get all the reviews in database as transfer objects.
	 * 
	 * @return ArrayList of ReviewDto objects.
	 */
	@GetMapping(value = { "/reviews", "/reviews/"})
	public List<ReviewDto> getAllReviews(){
		List<Review> reviews = service.getAllReviews();
		List<ReviewDto> reviewsDto = new ArrayList<ReviewDto>();
		for(Review review : reviews) {
			reviewsDto.add(convertToDto(review));
		}
		return reviewsDto;
		
	}
	
	/**
	 * Get a review transfer object based on a reviewID.
	 * 
	 * @param id
	 * @return A ReviewDto object.
	 */
	@GetMapping(value = {"/reviews/reviewByID", "reviews/reviewByID/", "reviews/reviewById" ,"reviews/reviewById/"})
	public ReviewDto getReviewByReviewID(@RequestParam("id") long id) {
		Review review = service.getReviewByReviewID(id);
		return convertToDto(review);
	}
	
	/**
	 * Get a list of review transfer objects based on a specific reviewee, i.e. the object of a review.
	 * 
	 * @param reviewee
	 * @return An ArrayList of ReviewDto objects.
	 */
	@GetMapping(value = {"/reviews/reviewsByReviewee","/reviews/reviewsByReviewee/"})
	public List<ReviewDto> getReviewsByReviewee(@RequestParam("reviewee") Person reviewee){
		List<Review> reviews = service.getReviewsByReviewee(reviewee);
		List<ReviewDto> reviewsDto = new ArrayList<ReviewDto>();
		for (Review review : reviews) {
			reviewsDto.add(convertToDto(review));
		}
		return reviewsDto;	
	}
	
	/**
	 * Get a list of review transfer objects based on a specific reviewer, i.e. the author of a review.
	 * 
	 * @param reviewer
	 * @return An ArrayList of ReviewDto objects.
	 */
	@GetMapping(value = {"/reviews/reviewsByReviewer","/reviews/reviewsByReviewer/"})
	public List<ReviewDto> getReviewsByReviewer(@RequestParam("reviewer") Person reviewer){
		List<Review> reviews = service.getReviewsByReviewer(reviewer);
		List<ReviewDto> reviewsDto = new ArrayList<ReviewDto>();
		for (Review review : reviews) {
			reviewsDto.add(convertToDto(review));
		}
		return reviewsDto;	
	}
	
	/**
	 * Get a list of review transfer objects based on a specific appointment.
	 * 
	 * @param appointment
	 * @return An ArrayList of ReviewDto objects.
	 */
	@GetMapping(value = {"/reviews/reviewsByAppointment","/reviews/reviewsByAppointment/"})
	public List<ReviewDto> getReviewsByAppointment(@RequestParam("appointment") Appointment appointment){
		List<Review> reviews = service.getReviewsByAppointment(appointment);
		List<ReviewDto> reviewsDto = new ArrayList<ReviewDto>();
		for (Review review : reviews) {
			reviewsDto.add(convertToDto(review));
		}
		return reviewsDto;	
	}
	
	
	
	/*----------- AVAILABILITY ----------*/
	
	/**
	 * Creates an Availability
	 * @param tutorUsername
	 * @param date
	 * @param createdDate
	 * @param startTime
	 * @param endTime
	 * @return AvailabilityDto object
	 * @throws IllegalArgumentException
	 */
	@PostMapping(value = { "/availabilities/createAvailability", "/availabilities/createAvailability/" })
	public AvailabilityDto createAvailability(@RequestParam("tutorUsername")String tutorUsername, @RequestParam("date")long date, 
			@RequestParam("createdDate")long createdDate, @RequestParam("startTime")long startTime, @RequestParam("endTime")long endTime) throws IllegalArgumentException {
		// @formatter:on
		Date realDate = new Date(date);
		Date realCreatedDate = new Date(createdDate);
		Time realStartTime = new Time(startTime);
		Time realEndTime = new Time(endTime);
		Tutor tutor = (Tutor) service.getPersonByUsername(tutorUsername);
		Availability availability = service.createAvailability(tutor,realDate,realCreatedDate,realStartTime,realEndTime);
		return convertToDto(availability);
	}
	
	/**
	 * Get all the availabilities in the database.
	 * 
	 * @return ArrayList of AvailabilityDto objects
	 */
	@GetMapping(value = { "/availabilities", "/availabilities/" })
	public List<AvailabilityDto> getAllAvailabilities() {
		List<AvailabilityDto> availabilityDtos = new ArrayList<>();
		for (Availability availability : service.getAllAvailabilities()) {
			availabilityDtos.add(convertToDto(availability));
		}
		return availabilityDtos;
	}
	
	/**
	 * Get all the availabilities in the database for a specific tutor.
	 * 
	 * @param tutorUsername
	 * @return ArrayList of AvailabilityDto objects
	 */
	@GetMapping(value = { "/availabilities/getByTutor", "availabilities/getByTutor/" })
	public List<AvailabilityDto> getAllAvailabilitiesByTutor(@RequestParam("tutorUsername")String tutorUsername) {
		List<AvailabilityDto> availabilityDtos = new ArrayList<>();
		for (Availability availability : service.getAvailabilityByTutorUsername(tutorUsername)) {
			availabilityDtos.add(convertToDto(availability));
		}
		return availabilityDtos;
	}
	
	/**
	 * Get all the availabilities in the database for a specific date.
	 * 
	 * @param tutorUsername
	 * @return ArrayList of AvailabilityDto objects
	 */
	@GetMapping(value = { "/availabilities/getByDate", "/availabilities/getByDate/" })
	public List<AvailabilityDto> getAllAvailabilitiesByDate(@RequestParam("date")long date) {
		Date realDate = new Date(date);
		List<AvailabilityDto> availabilityDtos = new ArrayList<>();
		for (Availability availability : service.getAvailabilityByDate(realDate)) {
			availabilityDtos.add(convertToDto(availability));
		}
		return availabilityDtos;
	}
	


	
	/*----------- ROOM ----------*/
	
	
	
	
	
	
	
	
	/*----------- SPECIFIC COURSE ----------*/
	
	
	@PostMapping(value = { "/specificCourses/create", "/specificCourses/create/" })
	public SpecificCourseDto createSpecificCourse(@RequestParam("hourlyRate") String hourlyRate, @RequestParam("tutorUsername") String tutorUsername, 
			@RequestParam("courseID") String courseID) throws IllegalArgumentException {
		Tutor t = (Tutor) service.getPersonByUsername(tutorUsername);
		Course c = (Course) service.getCourseByID(courseID);
		double rate = Double.parseDouble(hourlyRate);
		SpecificCourse sc = service.createSpecificCourse(t, c, rate);
		return convertToDto(sc);
	}
	
	@GetMapping(value = { "/specificCourses/tutor", "/specificCourses/tutor/" })
	public List<SpecificCourseDto> getSpecificCoursesOfTutor(@RequestParam("username") String tutorUsername) {
		List<SpecificCourse> scourses = service.getSpecificCourseByTutor(tutorUsername);
		List<SpecificCourseDto> scDto = new ArrayList<>();
		for (SpecificCourse sc : scourses) {
			scDto.add(convertToDto(sc));
		}
		return scDto;
	}

	
	@GetMapping(value = { "/specificCourses", "/specificCourses/" })
	public List<SpecificCourseDto> getAllSpecificCourses() {
		List<SpecificCourseDto> scDto = new ArrayList<>();
		for (SpecificCourse sc : service.getAllSpecificCourses()) {
			scDto.add(convertToDto(sc));
		}
		return scDto;
	}
	
	
	/*--------------------------------------*/
	
	
	//CONVERT TO DOMAIN OBJECT METHODS
	
	private Person convertToDomainObject(PersonDto pDto) {
		List<Person> allPersons = service.getAllPersons();
		for (Person person : allPersons) {
			if (person.getUsername().equals(pDto.getUsername())) {
				return person;
			}
		}
		return null;
	}
	
	
	//CONVERT TO DTO METHODS
	
	private ReviewDto convertToDto(Review rev) {
		if(rev == null) {
			throw new IllegalArgumentException("There is no such review");
		}
		ReviewDto reviewDto;
		if(rev.getReviewee() == null || rev.getReviewer() == null || rev.getAppointment() == null) {
			reviewDto = new ReviewDto(rev.getReviewText(), rev.getRating());
		}else if(rev.getCreatedDate() == null || rev.getCreatedTime() == null) {
			reviewDto = new ReviewDto(rev.getReviewText(), rev.getRating(), rev.getReviewee(), rev.getReviewer(), rev.getAppointment());
		}else {
			reviewDto = new ReviewDto(rev.getReviewText(), rev.getRating(), rev.getReviewee(), rev.getReviewer(), rev.getAppointment(),
					rev.getCreatedDate(), rev.getCreatedTime());
		}
		return reviewDto;
	}
	
	private AppointmentDto convertToDto(Appointment appt) {
		if (appt == null) {
				throw new IllegalArgumentException("There is no such Appointment!");
		}
		PersonDto tutor = convertToDto(appt.getTutor());
		AppointmentDto apptDto = new AppointmentDto(appt.getDate(), appt.getStartTime(), appt.getEndTime(), appt.getStatus(), tutor);
		return apptDto;
		
	}
	
	private PersonDto convertToDto(Person p) {
		if(p == null) {
			throw new IllegalArgumentException("There is no such Person!");
		}
		PersonDto personDto = new PersonDto(p.getFirstName(), p.getLastName(), p.getUsername(), p.getPersonType(), p.getEmail(), p.getPassword(), p.getSexe(), p.getAge());
		return personDto;
	}
	

	private AvailabilityDto convertToDto(Availability a) {
		if(a == null) {
			throw new IllegalArgumentException("There is no such Person!");
		}
		AvailabilityDto availabilityDto = new AvailabilityDto(a.getTutor(),a.getDate(),a.getCreatedDate(),a.getStartTime(),a.getEndTime());
		return availabilityDto;
	}
	private CourseDto convertToDto(Course c) {
		if(c == null) {
			throw new IllegalArgumentException("There is no such Course!");
		}
		CourseDto courseDto = new CourseDto(c.getCourseID(), c.getName(), c.getLevel(), c.getSubject());
		return courseDto;
	}
	
	private SpecificCourseDto convertToDto(SpecificCourse sc) {
		if(sc == null) {
			throw new IllegalArgumentException("There is no such Specific Course!");
		}
		SpecificCourseDto specificCourseDto = new SpecificCourseDto(sc.getHourlyRate(), sc.getTutor().getUsername(), sc.getCourse().getCourseID(), sc.getSpecificCourseID());
		return specificCourseDto;
	}
	
}