package ca.mcgill.ecse321.projectgroup17.controller;

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
import ca.mcgill.ecse321.projectgroup17.service.ProjectGroup17Service;

@CrossOrigin(origins = "*")
@RestController
public class ProjectGroup17RestController {
	
	@Autowired
	ProjectGroup17Service service;
	
	
	/*----------- PERSON ----------*/
	
	@PostMapping(value = { "/persons", "/persons/" })
	public PersonDto createPerson(@RequestParam("firstName") String firstName, @RequestParam("lastName") String lastName, @RequestParam("username") String username, @RequestParam("personType") String personType, @RequestParam("password") String password, @RequestParam("email") String email, @RequestParam("sexe") String sexe, @RequestParam("age") long age) throws IllegalArgumentException {
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
	
	private PersonDto convertToDto(Person p) {
		if(p == null) {
			throw new IllegalArgumentException("There is no such Person!");
		}
		PersonDto personDto = new PersonDto(p.getFirstName(), p.getLastName(), p.getUsername(), p.getPersonType(), p.getEmail(), p.getSexe(), p.getAge());
		//personDto.setEvents(createEventDtosForPerson(p));
		return personDto;
	}
	
	
	/*----------- COURSE ----------*/
	
	
	
	
	
	/*----------- REVIEW ----------*/
	
	
	
	
	
	/*----------- AVAILABILITY ----------*/
	
	
	

	
	/*----------- ROOM ----------*/
	
	
	
	
	
	/*----------- SPECIFIC COURSE ----------*/

}
