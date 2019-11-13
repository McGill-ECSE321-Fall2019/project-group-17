package ca.mcgill.ecse321.projectgroup17.controller;


import java.sql.Date;
import java.sql.Time;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
	/**
	 * Creates Person using service method createPerson with fields provided by request
	 * @param firstName
	 * @param lastName
	 * @param username
	 * @param personType
	 * @param password
	 * @param email
	 * @param sexe
	 * @param age
	 * @return PersonDto object
	 * @throws IllegalArgumentException
	 */
	@PostMapping(value = { "/persons/createPerson", "/persons/createPerson/" })
	public PersonDto createPerson(@RequestParam("firstName") String firstName, @RequestParam("lastName") String lastName, 
			@RequestParam("username") String username, @RequestParam("personType") String personType, 
			@RequestParam("password") String password, @RequestParam("email") String email, @RequestParam(value="sexe", required=false, 
			defaultValue="none") String sexe, @RequestParam(value="age", required=false, defaultValue="18") long age) throws IllegalArgumentException {
		Person person = service.createPerson(personType, firstName, lastName, username, password, email, sexe, age);	//Create person using provided parameters
		return convertToDto(person);
	}
	/**
	 * Get list of PersonDtos
	 * @return list of PersonDto objects
	 */
	@GetMapping(value = { "/persons", "/persons/" })
	public List<PersonDto> getAllPersons() {
		List<PersonDto> personDtos = new ArrayList<>();
		for (Person person : service.getAllPersons()) {
			personDtos.add(convertToDto(person));
		}
		return personDtos;
	}
	/**
	 * Get Person by username and convert to PersonDto
	 * @param username
	 * @return PersonDto object
	 */
	@GetMapping(value = { "/persons/getByUsername", "/persons/getByUsername/" })
	public PersonDto getPersonByUsername(@RequestParam("username") String username) {
		Person p = service.getPersonByUsername(username);
		return convertToDto(p);
	}
	
	/**
	 * Get Person by email and convert to PersonDto
	 * @param email
	 * @return PersonDto object
	 */
	@GetMapping(value = { "/persons/getByEmail", "/persons/getByEmail/" })
	public PersonDto getPersonByEmail(@RequestParam("email") String email) {
		Person p = service.getPersonByEmail(email);
		return convertToDto(p);
	}
	
	/**
	 * Get Person by first and last name and convert to PersonDto
	 * @param firstName
	 * @param lastName
	 * @return PersonDto objects
	 */
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
	
	/**
	 * Get Appointment of a particular tutor
	 * @param username
	 * @return AppointmentDtos
	 */
	@GetMapping(value = { "/appointments/tutor", "/appointments/tutor/" })
	public List<AppointmentDto> getAppointmentsOfTutor(@RequestParam("username") String username) {
		Tutor t = (Tutor) service.getPersonByUsername(username);
		return createAppointmentDtosForTutor(t);
	}
	
	@GetMapping(value = { "/appointments/status", "/appointments/status/" })
	public List<AppointmentDto> getAppointmentsOfTutorByStatus(@RequestParam("username") String username, @RequestParam("status") String status) {
		Tutor t = (Tutor) service.getPersonByUsername(username);
		return createAppointmentDtosForTutorByStatus(t, status);
	}
	
	@PostMapping(value = { "/appointments/changeStatus", "/appointments/changeStatus/" })
	public AppointmentDto changeAppointmentStatus(@RequestParam("n") long appointmentId, @RequestParam("newStatus") String newStatus) {
		Appointment appt = service.getAppointmentById(appointmentId);
		appt = service.changeAppointmentStatus(appt, newStatus);
		return convertToDto(appt);
	}
	/**
	 * Create Appointment using service method and convert to AppointmentDto
	 * @param date
	 * @param startTime
	 * @param endTime
	 * @param tutorUsername
	 * @param roomId
	 * @param status
	 * @param students
	 * @return AppointmentDto object
	 * @throws IllegalArgumentException
	 */
	@PostMapping(value = { "/appointments/createAppointment", "/appointments/createAppointment/" })
	public AppointmentDto createAppointment(@RequestParam("date") long date, @RequestParam("startTime") long startTime, 
			@RequestParam("endTime") long endTime, @RequestParam("tutorUsername") String tutorUsername, 
			@RequestParam("roomId") long roomId, @RequestParam("status") String status, @RequestParam("students") List<String> students) throws IllegalArgumentException {
		
		Date realDate = new Date(date);					//Convert long param to date
		Time realStartTime = new Time(startTime);		//Convert long param to time
		Time realEndTime = new Time(endTime);			//Convert long param to time
		Room room = service.getRoomByRoomID(roomId);	//Get Room using provided param roomId
		Tutor tutor = (Tutor) service.getPersonByUsername(tutorUsername);	//Get Tutor using provided param tutorUsername
		Set<Student> student = new HashSet<Student>();						
		for(int i=0; i < students.size(); i++) {							//Create set of students using given student usernames
			Student s = (Student) service.getPersonByUsername(students.get(i));
			student.add(s);
		}
		Appointment appt = service.createAppointment(realDate, realEndTime, realStartTime, room, tutor, status, student); //Create Appontment using service method
		return convertToDto(appt);
	}
	/**
	 * Creates appointment data transfer objects for a tutor
	 * @param t
	 * @return
	 */
	private List<AppointmentDto> createAppointmentDtosForTutor(Tutor t) {
		List<Appointment> apptsForTutor = service.getAppointmentsByTutor(t);
		List<AppointmentDto> appts = new ArrayList<>();
		for (Appointment appt : apptsForTutor) {
			appts.add(convertToDto(appt));
		}
		return appts;
	}
	private List<AppointmentDto> createAppointmentDtosForTutorByStatus(Tutor t, String status) {
		List<Appointment> apptsForTutor = service.getAppointmentsByTutor(t);
		List<AppointmentDto> appts = new ArrayList<>();
		for (Appointment appt : apptsForTutor) {
			if(appt.getStatus().toString().equals(status)) {
				appts.add(convertToDto(appt));
			}
		}
		return appts;
	}
	
	
	/*----------- COURSE ----------*/
	/**
	 * Create Course using service method and convert to CourseDto
	 * @param courseID
	 * @param courseName
	 * @param level
	 * @param subject
	 * @return CourseDto object
	 * @throws IllegalArgumentException
	 */
	@PostMapping(value = { "/courses/createCourse", "/courses/createCourse/" })
	public CourseDto createCourse(@RequestParam("courseID") String courseID, @RequestParam("courseName") String courseName, 
			@RequestParam("level") String level, @RequestParam("subject") String subject) throws IllegalArgumentException {
		Course course = service.createCourse(courseID, courseName, level, subject);
		return convertToDto(course);
	}
	
	/**
	 * Get Course by course ID and convert to DTO
	 * @param courseID
	 * @return CourseDto object
	 */
	@GetMapping(value = { "/courses/courseID", "/courses/courseID/" })
	public List<CourseDto> getCourseByCourseID(@RequestParam("courseID") String courseID) {
		Course c = (Course) service.getCourseByID(courseID);
		List<CourseDto> coursesDto = new ArrayList<>();
		coursesDto.add(convertToDto(c));
		return coursesDto;
	}
	/**
	 * Get Courses by subject
	 * @param subject
	 * @return List of CourseDto objects
	 */
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
	/**
	 * Get all courses
	 * @return List of CourseDto objects
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
	 * @param name_reviewee
	 * @param name_reviewer
	 * @param appointmentID
	 * @return	The review object as a ReviewDto.
	 * @throws IllegalArgumentException
	 */
	@PostMapping(value = { "/reviews/createReview", "/reviews/createReview/" })
	public ReviewDto createReview(@RequestParam("reviewText") String reviewText, @RequestParam("rating") int rating,
			@RequestParam("name_reviewee") String name_reviewee, @RequestParam("name_reviewer") String name_reviewer,
			@RequestParam("appointmentID") long appointmentID) throws IllegalArgumentException {
		// formatter : on 
		Time createdTime = new Time(Calendar.getInstance().getTimeInMillis());
		Date createdDate = new Date(Calendar.getInstance().getTimeInMillis());
		Person reviewee = service.getPersonByUsername(name_reviewee);
		Person reviewer = service.getPersonByUsername(name_reviewer);
		Appointment appointment = service.getAppointmentByAppointmentID(appointmentID);
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
	 * @param name_reviewee
	 * @return An ArrayList of ReviewDto objects.
	 */
	@GetMapping(value = {"/reviews/reviewsByReviewee","/reviews/reviewsByReviewee/"})
	public List<ReviewDto> getReviewsByReviewee(@RequestParam("name_reviewee") String name_reviewee){
		Person reviewee = service.getPersonByUsername(name_reviewee);
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
	 * @param name_reviewer
	 * @return An ArrayList of ReviewDto objects.
	 */
	@GetMapping(value = {"/reviews/reviewsByReviewer","/reviews/reviewsByReviewer/"})
	public List<ReviewDto> getReviewsByReviewer(@RequestParam("name_reviewer") String name_reviewer){
		Person reviewer = service.getPersonByUsername(name_reviewer);
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
	 * @param appointmentID
	 * @return An ArrayList of ReviewDto objects.
	 */
	@GetMapping(value = {"/reviews/reviewsByAppointment","/reviews/reviewsByAppointment/"})
	public List<ReviewDto> getReviewsByAppointment(@RequestParam("appointmentID") long appointmentID){
		Appointment appointment = service.getAppointmentByAppointmentID(appointmentID);
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
	
	@GetMapping(value = {"/availabilities/getById", "/availabilities/getById/"})
	public AvailabilityDto getAvailabilityById(@RequestParam("availabilityId") long availabilityId) {
		Availability avail;
		avail = service.getAvailabilityById(availabilityId);
		return convertToDto(avail);
	}
	
	@PostMapping(value = {"/availabilities/deleteById", "/availabilities/deleteById/"})
	public AvailabilityDto deleteAvailabilityById(@RequestParam("availabilityId") long availabilityId) {
		Availability avail;
		avail = service.deleteAvailabilityById(availabilityId);
		return convertToDto(avail);
	}
	


	
	/*----------- ROOM ----------*/
	
	/**
	 * Create Room using service method and convert to RoomDto
	 * @param roomID
	 * @param isBig
	 * @return RoomDto
	 * @throws IllegalArgumentException
	 */
	@PostMapping(value = { "/rooms/createRoom", "/rooms/createRoom/" })
	public RoomDto createRoom(@RequestParam("roomID") String roomID, @RequestParam("isBig") String isBig) throws IllegalArgumentException {
		long room_id = Long.parseLong(roomID);
		boolean is_big = Boolean.parseBoolean(isBig);
		Room room = service.createRoom(room_id, is_big);
		return convertToDto(room);
	}
	
	/**
	 * Returns a specific room from a given room ID
	 * @param roomID
	 * @return
	 */
	@GetMapping(value = { "/rooms/roomID", "/rooms/roomID/" })
	public RoomDto getRoomByRoomID(@RequestParam("roomID") String roomID) {
		long room_id = Long.parseLong(roomID);
		Room room = service.getRoomByRoomID(room_id);
		RoomDto roomDto = convertToDto(room);
		return roomDto;
	}
	
	/**
	 * Returns all rooms
	 * @return
	 */
	@GetMapping(value = { "/rooms", "/rooms/" })
	public List<RoomDto> getAllRooms() {
		List<RoomDto> roomDto = new ArrayList<>();
		for (Room room : service.getAllRooms()) {
			roomDto.add(convertToDto(room));
		}
		return roomDto;
	}
	
	/**
	 * Returns all rooms with the BIG attribute
	 * @return
	 */
	@GetMapping(value = { "/rooms/big", "/rooms/big/" })
	public List<RoomDto> getAllBigRooms() {
		List<RoomDto> roomDto = new ArrayList<>();
		for (Room room : service.getRoomByRoomBig(true)) {
			roomDto.add(convertToDto(room));
		}
		return roomDto;
	}
	
	/**
	 * Returns all rooms with the Big attribute being FALSE
	 * @return
	 */
	@GetMapping(value = { "/rooms/small", "/rooms/small/" })
	public List<RoomDto> getAllSmallRooms() {
		List<RoomDto> roomDto = new ArrayList<>();
		for (Room room : service.getRoomByRoomBig(false)) {
			roomDto.add(convertToDto(room));
		}
		return roomDto;
	}
	
	/*----------- SPECIFIC COURSE ----------*/
	/**
	 * Create SpecificCourse from service method and convert to SpecificCourseDto
	 * @param hourlyRate
	 * @param tutorUsername
	 * @param courseID
	 * @return SpecificCourseDto object
	 * @throws IllegalArgumentException
	 */
	@PostMapping(value = { "/specificCourses/create", "/specificCourses/create/" })
	public SpecificCourseDto createSpecificCourse(@RequestParam("hourlyRate") String hourlyRate, @RequestParam("tutorUsername") String tutorUsername, 
			@RequestParam("courseID") String courseID) throws IllegalArgumentException {
		Tutor t = (Tutor) service.getPersonByUsername(tutorUsername);
		Course c = (Course) service.getCourseByID(courseID);
		double rate = Double.parseDouble(hourlyRate);
		SpecificCourse sc = service.createSpecificCourse(t, c, rate);
		return convertToDto(sc);
	}
	
	/**
	 * Will go retrieve a list of courses that a specific tutor teaches
	 * @param tutorUsername
	 * @return scDto
	 */
	@GetMapping(value = { "/specificCourses/tutor", "/specificCourses/tutor/" })
	public List<SpecificCourseDto> getSpecificCoursesOfTutor(@RequestParam("username") String tutorUsername) {
		List<SpecificCourse> scourses = service.getSpecificCourseByTutor(tutorUsername);
		List<SpecificCourseDto> scDto = new ArrayList<>();
		for (SpecificCourse sc : scourses) {
			scDto.add(convertToDto(sc));
		}
		return scDto;
	}
	
	@PostMapping(value = {"/specificCourses/delete", "/specificCourses/delete/"})
	public SpecificCourseDto deleteSpecificCourse(@RequestParam("specificCourseID") long specificCourseID) {
		SpecificCourse sc;
		sc = service.deleteSpecificCourse(specificCourseID);
		return convertToDto(sc);
	}
	
	

	/**
	 * Will go retrieve all specific courses that are taught
	 * @return scDto
	 */
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
	
	/**
	 * Converts a DTO into a domain object
	 * @param pDto
	 * @return
	 */
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
	
	/**
	 * Converts a Review object into a DTO for the user to view
	 * @param rev
	 * @return reviewDto
	 */
	private ReviewDto convertToDto(Review rev) {
		if(rev == null) {
			throw new IllegalArgumentException("There is no such review");
		}
		ReviewDto reviewDto;
		if(rev.getReviewee() == null || rev.getReviewer() == null || rev.getAppointment() == null) {
			reviewDto = new ReviewDto(rev.getReviewText(), rev.getRating());
		}else if(rev.getCreatedDate() == null || rev.getCreatedTime() == null) {
			reviewDto = new ReviewDto(rev.getReviewText(), rev.getRating(), rev.getReviewee().getUsername(), rev.getReviewer().getUsername(), rev.getAppointment().getAppointmentID());
		}else {
			reviewDto = new ReviewDto(rev.getReviewText(), rev.getRating(), rev.getReviewee().getUsername(), rev.getReviewer().getUsername(), rev.getAppointment().getAppointmentID(),
					rev.getCreatedDate(), rev.getCreatedTime());
		}
		return reviewDto;
	}
	/**
	 * Converts a Appointment object into a DTO for the user to view
	 * @param appt
	 * @return apptDto
	 */
	private AppointmentDto convertToDto(Appointment appt) {
		if (appt == null) {
				throw new IllegalArgumentException("There is no such Appointment!");
		}
		PersonDto tutor = convertToDto(appt.getTutor());
		RoomDto room = convertToDto(appt.getRoom());
		Set<PersonDto> st = new HashSet<PersonDto>();
		for(int i=0; i<appt.getStudent().size(); i++) {
			PersonDto s = convertToDto((Person) appt.getStudent().toArray()[i]);
			st.add(s);
		}
		AppointmentDto apptDto = new AppointmentDto(appt.getAppointmentID(), appt.getDate(), appt.getStartTime(), appt.getEndTime(), appt.getStatus(), tutor, room, st); 
		return apptDto;
		
	}
	/**
	 * Converts a Person object into a DTO for the user to view
	 * @param p
	 * @return personDto
	 */
	private PersonDto convertToDto(Person p) {
		if(p == null) {
			throw new IllegalArgumentException("There is no such Person!");
		}
		PersonDto personDto = new PersonDto(p.getFirstName(), p.getLastName(), p.getUsername(), p.getPersonType(), p.getEmail(), p.getPassword(), p.getSexe(), p.getAge());
		return personDto;
	}
	
	/**
	 * Converts a Availability object into a DTO for the user to view
	 * @param a
	 * @return availabilityDto
	 */
	private AvailabilityDto convertToDto(Availability a) {
		if(a == null) {
			throw new IllegalArgumentException("There is no such Person!");
		}
		AvailabilityDto availabilityDto = new AvailabilityDto(a.getAvailabilityID(), a.getTutor(),a.getDate(),a.getCreatedDate(),a.getStartTime(),a.getEndTime());
		return availabilityDto;
	}
	
	/**
	 * Converts a Course object into a DTO for the user to view
	 * @param c
	 * @return courseDto
	 */
	private CourseDto convertToDto(Course c) {
		if(c == null) {
			throw new IllegalArgumentException("There is no such Course!");
		}
		CourseDto courseDto = new CourseDto(c.getCourseID(), c.getName(), c.getLevel(), c.getSubject());
		return courseDto;
	}
	
	/**
	 * Converts a SpecificCourse object into a DTO for the user to view
	 * @param sc
	 * @return specificCourseDto
	 */
	private SpecificCourseDto convertToDto(SpecificCourse sc) {
		if(sc == null) {
			throw new IllegalArgumentException("There is no such Specific Course!");
		}
		SpecificCourseDto specificCourseDto = new SpecificCourseDto(sc.getHourlyRate(), sc.getTutor().getUsername(), sc.getCourse().getCourseID(), sc.getSpecificCourseID());
		return specificCourseDto;
	}
	
	/**
	 * Converts a Room object into a DTO for the user to view
	 * @param room
	 * @return croomDto
	 */
	private RoomDto convertToDto(Room room) {
		if(room == null) {
			throw new IllegalArgumentException("There is no such Room!");
		}
		RoomDto roomDto = new RoomDto(room.getRoomID(), room.isBig());
		return roomDto;
	}
	
}