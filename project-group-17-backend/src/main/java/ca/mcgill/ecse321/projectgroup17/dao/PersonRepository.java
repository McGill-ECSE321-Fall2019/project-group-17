package ca.mcgill.ecse321.projectgroup17.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321.projectgroup17.model.Person;

public interface PersonRepository extends CrudRepository<Person, String>{

	List<Person> findAll();

	List<Person> findByFirstName(String firstName);

	List<Person> findByLastName(String lastName);

	Person findByEmail(String email);

	Person findByUsername(String username);

	List<Person> findByFirstNameAndLastName(String firstName, String lastName);

	List<Person> findByPersonType(String personType);

	void deleteAll();
}