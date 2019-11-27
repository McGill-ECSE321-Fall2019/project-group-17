package ca.mcgill.ecse321.projectgroup17.dao;

import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321.projectgroup17.model.*;

public interface MessageRepository extends CrudRepository<Message, Long> {
	
	Message findByMessageId(long messageId);
}
