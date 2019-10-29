package ca.mcgill.ecse321.projectgroup17.dto;

public class RoomDto {
	
	private long roomId;
	private boolean isBig;
	
	public RoomDto() {
		
	}
	
	public RoomDto(long roomId) {
		this(roomId, false);
	}
	
	public RoomDto(long roomId, boolean isBig) {
		this.roomId = roomId;
		this.isBig = isBig;
	}
	
	//Getters and Setters for DTO
	
	public long getRoomId() {
		return roomId;
	}
	
	public void setRoomId(long roomId) {
		this.roomId = roomId;
	}
	
	public boolean getIsBig() {
		return isBig;
	}
	
	public void setIsBig(boolean isBig) {
		this.isBig = isBig;
	}
}
