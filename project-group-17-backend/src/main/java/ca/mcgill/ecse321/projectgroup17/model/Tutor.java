package ca.mcgill.ecse321.projectgroup17.model;

import javax.persistence.Entity;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.OneToMany;
import javax.persistence.ManyToMany;

@Entity
public class Tutor extends Person{
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
   
   private Set<SpecificCourse> specificCourse;
   
   @OneToMany(mappedBy="tutor" )
   public Set<SpecificCourse> getSpecificCourse() {
      return this.specificCourse;
   }
   
   public void setSpecificCourse(Set<SpecificCourse> specificCourses) {
      this.specificCourse = specificCourses;
   }
   
   }
