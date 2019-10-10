package ca.mcgill.ecse321.projectgroup17.dao;

import java.util.ArrayList;

import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321.projectgroup17.model.Person;

public interface PersonRepository extends CrudRepository<Person, String>{

	ArrayList<Person> findAll();

	ArrayList<Person> findByFirstName(String firstName);

	ArrayList<Person> findByLastName(String lastName);

	Person findByEmail(String email);

	Person findByUsername(String username);

	ArrayList<Person> findByFirstNameAndLastName(String firstName, String lastName);

	ArrayList<Person> findByPersonType(String personType);

}