package ca.mcgill.ecse321.projectgroup17.dao;

import java.sql.Date;
import java.sql.Time;
import java.util.List;

//not sure if still need these?
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import org.springframework.data.repository.CrudRepository;

//include the classes from the model
import ca.mcgill.ecse321.projectgroup17.model.Course;
import ca.mcgill.ecse321.projectgroup17.model.Person;
import ca.mcgill.ecse321.projectgroup17.model.Appointment;
import ca.mcgill.ecse321.projectgroup17.model.Course;
import ca.mcgill.ecse321.projectgroup17.model.Person;
import ca.mcgill.ecse321.projectgroup17.model.Appointment;


@Repository
public class ProjectGroup17Repository {

	@Autowired
	EntityManager entityManager;
	
	@Transactional
	public Person createPerson(String name) {
		Person p = new Person();
		p.setUsername(name);
		entityManager.persist(p);
		return p;
	}
	
	@Transactional
	public Person getPerson(String name) {
		Person p = entityManager.find(Person.class, name);
		return p;
	}

}


