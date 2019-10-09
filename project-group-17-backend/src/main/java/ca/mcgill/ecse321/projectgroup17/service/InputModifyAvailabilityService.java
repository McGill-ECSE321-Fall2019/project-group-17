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

@Service
public class InputModifyAvailabilityService {

	
	@Autowired
	AvailabilityRepository availabilityRepository;

	@Transactional
	public Availability createAvailability(Date date) {
		Availability a = new Availability();
		a.setDate(date);
		availabilityRepository.save(a);
		return a;
	}

	@Transactional
	public Availability getAvailability(Date date) {
		Availability a = availabilityRepository.findAvailabilityByDate(date);
		return a;
	}

	@Transactional
	public List<Availability> getAllAvailabilities() {
		return toList(availabilityRepository.findAll());
	}
	
	private <T> List<T> toList(Iterable<T> iterable){
		List<T> resultList = new ArrayList<T>();
		for (T t : iterable) {
			resultList.add(t);
		}
		return resultList;
	}


}
