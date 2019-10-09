package ca.mcgill.ecse321.projectgroup17.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.util.Set;
import javax.persistence.OneToMany;

@Entity
public class Course{
   private int courseID;

public void setCourseID(int value) {
    this.courseID = value;
}
@Id
public int getCourseID() {
    return this.courseID;
}
private String name;

public void setName(String value) {
    this.name = value;
}
public String getName() {
    return this.name;
}
private Level level;

public void setLevel(Level value) {
    this.level = value;
}
public Level getLevel() {
    return this.level;
}
   private Tutor tutor;
   
   @ManyToOne(optional=false)
   public Tutor getTutor() {
      return this.tutor;
   }
   
   public void setTutor(Tutor tutor) {
      this.tutor = tutor;
   }
   
   private Set<HourlyRate> hourlyRate;
   
   @OneToMany(mappedBy="course" )
   public Set<HourlyRate> getHourlyRate() {
      return this.hourlyRate;
   }
   
   public void setHourlyRate(Set<HourlyRate> hourlyRates) {
      this.hourlyRate = hourlyRates;
   }
   
   }
