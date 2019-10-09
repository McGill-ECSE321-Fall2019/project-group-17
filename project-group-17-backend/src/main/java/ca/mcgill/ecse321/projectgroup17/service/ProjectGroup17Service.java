package ca.mcgill.ecse321.projectgroup17.service;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ca.mcgill.ecse321.projectgroup17.dao.AvailabilityRepository;
import ca.mcgill.ecse321.projectgroup17.model.Course;
import ca.mcgill.ecse321.projectgroup17.model.Person;
import ca.mcgill.ecse321.projectgroup17.model.Availability;
import ca.mcgill.ecse321.projectgroup17.model.Appointment;
import ca.mcgill.ecse321.projectgroup17.model.Course;
import ca.mcgill.ecse321.projectgroup17.model.Level;
import ca.mcgill.ecse321.projectgroup17.model.Review;
import ca.mcgill.ecse321.projectgroup17.model.Room;
import ca.mcgill.ecse321.projectgroup17.model.SpecificCourse;
import ca.mcgill.ecse321.projectgroup17.model.Student;
import ca.mcgill.ecse321.projectgroup17.model.Tutor;


@Service
public class ProjectGroup17Service {

	@Autowired
	EventRepository eventRepository;
	@Autowired
	PersonRepository personRepository;
	@Autowired
	RegistrationRepository registrationRepository;

	@Transactional
	public Person createPerson(String name) {
		Person person = new Person();
		person.setName(name);
		personRepository.save(person);
		return person;
	}

	@Transactional
	public Person getPerson(String name) {
		Person person = personRepository.findPersonByName(name);
		return person;
	}





}
