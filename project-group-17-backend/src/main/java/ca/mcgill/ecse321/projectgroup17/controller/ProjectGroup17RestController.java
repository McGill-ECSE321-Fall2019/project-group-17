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
		// @formatter:on
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
		Appointment appt = service.createAppointment(realDate, realEndTime, realStartTime, roomId, tutorUsername, status);
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
	
	
	
	
	
	/*----------- REVIEW ----------*/
	
	@PostMapping(value = { "/reviews/createReview", "/reviews/createReview/" })
	public ReviewDto createReview(@RequestParam("reviewText") String reviewText, @RequestParam("rating") int rating,
			@RequestParam("createdTime") Time createdTime, @RequestParam("createdDate") Date createdDate,
			@RequestParam("reviewee") Person reviewee, @RequestParam("reviewer") Person reviewer,
			@RequestParam("appointment") Appointment appointment) throws IllegalArgumentException {
		
		// formatter : on 
		Review review = service.createReview(reviewText, rating, createdTime, createdDate, reviewee, reviewer, appointment);
		return convertToDto(review);
		
	}
	
	@GetMapping(value = { "/reviews", "/reviews/"})
	public List<ReviewDto> getAllReviews(){
		List<Review> reviews = service.getAllReviews();
		List<ReviewDto> reviewsDto = new ArrayList<ReviewDto>();
		for(Review review : reviews) {
			reviewsDto.add(convertToDto(review));
		}
		return reviewsDto;		
	}
	
	
	/*----------- AVAILABILITY ----------*/
	
	@PostMapping(value = { "/availabilities", "/availabilities/" })
	public AvailabilityDto createAvailability(@RequestParam("tutorUsername")String tutorUsername, @RequestParam("date")Date date, @RequestParam("createdDate")Date createdDate, @RequestParam("startTime")Time startTime, @RequestParam("endTime")Time endTime) throws IllegalArgumentException {
		// @formatter:on
		Tutor tutor = (Tutor) service.getPersonByUsername(tutorUsername);
		Availability availability = service.createAvailability(tutor,date,createdDate,startTime,endTime);
		return convertAvailabilityToDto(availability);
	}
	
	
	@GetMapping(value = { "/availabilities", "/availabilities/" })
	public List<AvailabilityDto> getAllAvailabilities() {
		List<AvailabilityDto> availabilityDtos = new ArrayList<>();
		for (Availability availability : service.getAllAvailabilities()) {
			availabilityDtos.add(convertAvailabilityToDto(availability));
		}
		return availabilityDtos;
	}
	
	private AvailabilityDto convertAvailabilityToDto(Availability a) {
		if(a == null) {
			throw new IllegalArgumentException("There is no such Person!");
		}
		AvailabilityDto availabilityDto = new AvailabilityDto(a.getTutor(),a.getDate(),a.getCreatedDate(),a.getStartTime(),a.getEndTime());
		return availabilityDto;
	}

	
	/*----------- ROOM ----------*/
	
	
	
	
	
	/*----------- SPECIFIC COURSE ----------*/

	
	
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
	
}
