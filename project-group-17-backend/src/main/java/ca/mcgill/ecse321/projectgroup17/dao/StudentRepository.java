package ca.mcgill.ecse321.projectgroup17.dao;

import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321.projectgroup17.model.Student;

public interface StudentRepository extends CrudRepository<Student, Long>{

}
