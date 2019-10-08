package ca.mcgill.ecse321.projectgroup17.dao;

import java.sql.Date;
import java.sql.Time;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


// include the classes from the model


@Repository
public class ProjectGroup17Repository {

	@Autowired
	EntityManager entityManager;
	
	


}
