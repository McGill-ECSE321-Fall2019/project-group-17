package ca.mcgill.ecse321.projectgroup17.model;

import javax.persistence.Entity;
import java.util.Set;
import javax.persistence.OneToMany;
import javax.persistence.CascadeType;
import javax.persistence.ManyToMany;

@Entity
public class Tutor extends Person{
   private int hourlyRate;

public void setHourlyRate(int value) {
    this.hourlyRate = value;
}
public int getHourlyRate() {
    return this.hourlyRate;
}
   private Set<Course> course;
   
   @OneToMany(mappedBy="tutor" )
   public Set<Course> getCourse() {
      return this.course;
   }
   
   public void setCourse(Set<Course> courses) {
      this.course = courses;
   }
   
   private Set<Availability> availability;
   
   @OneToMany(mappedBy="tutor" , cascade={CascadeType.ALL})
   public Set<Availability> getAvailability() {
      return this.availability;
   }
   
   public void setAvailability(Set<Availability> availabilitys) {
      this.availability = availabilitys;
   }
   
   private Set<Appointment> appointment;
   
   @ManyToMany(mappedBy="tutor" )
   public Set<Appointment> getAppointment() {
      return this.appointment;
   }
   
   public void setAppointment(Set<Appointment> appointments) {
      this.appointment = appointments;
   }
   
   }
