package ca.mcgill.ecse321.projectgroup17.model;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorValue;

import java.util.Set;
import javax.persistence.ManyToMany;

@Entity
@Inheritance
@DiscriminatorValue("Student")
public class Student extends Person{
   private Set<Appointment> appointment;
   
   @ManyToMany(mappedBy="student" )
   public Set<Appointment> getAppointment() {
      return this.appointment;
   }
   
   public void setAppointment(Set<Appointment> appointments) {
      this.appointment = appointments;
   }
   
   }
