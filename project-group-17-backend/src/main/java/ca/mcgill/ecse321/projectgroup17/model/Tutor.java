package ca.mcgill.ecse321.projectgroup17.model;

import javax.persistence.Entity;
import javax.persistence.Inheritance;

import java.util.Set;
import javax.persistence.OneToMany;
import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.ManyToMany;

@Entity
@Inheritance
@DiscriminatorValue("Tutor")
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
   
   private Set<SpecificCourse> specificCourses;
   
   @OneToMany(mappedBy="tutor")
   public Set<SpecificCourse> getSpecificCourses() {
      return this.specificCourses;
   }
  

   @OneToMany(mappedBy="tutor" )
   public Set<SpecificCourse> getSpecificCourse() {
      return this.specificCourses;
   }
   
   public void setSpecificCourses(Set<SpecificCourse> specificCourse) {
      this.specificCourses = specificCourse;
   }
   
   }
