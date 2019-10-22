package ca.mcgill.ecse321.projectgroup17.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;

import java.util.Set;
import javax.persistence.OneToMany;
import javax.persistence.Id;

@Entity
public class Room{
   private boolean big;

public void setBig(boolean value) {
    this.big = value;
}
public boolean isBig() {
    return this.big;
}
private Set<Appointment> appointment1;

@OneToMany(mappedBy="room" )
public Set<Appointment> getAppointment1() {
   return this.appointment1;
}

public void setAppointment1(Set<Appointment> appointment1s) {
   this.appointment1 = appointment1s;
}

private long roomID;

public void setRoomID(long value) {
    this.roomID = value;
}
@Id 
public long getRoomID() {
    return this.roomID;
}
}
