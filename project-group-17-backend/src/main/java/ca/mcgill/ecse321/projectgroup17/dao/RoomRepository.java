package ca.mcgill.ecse321.projectgroup17.dao;


import java.util.List;

import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321.projectgroup17.model.Room;

public interface RoomRepository extends CrudRepository<Room, Long> {
	
	Room findByRoomID(long roomID);
	
	List<Room> findByBig(boolean isBig);

}
