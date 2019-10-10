package ca.mcgill.ecse321.projectgroup17.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Set;
import javax.persistence.OneToMany;

@Entity
public class Course{
   private String courseID;
   
   private enum Level {
	   HIGHSCHOOL, CEGEP, UNIVERISTY;
   }

public void setCourseID(String value) {
    this.courseID = value;
}
@Id
public String getCourseID() {
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

public void setLevel(String value) {
	Level level = Level.valueOf(value);
    this.level = level;
}
public String getLevel() {
    return this.level.toString();
}
private Set<SpecificCourse> specificCourse;

@OneToMany(mappedBy="course" )
public Set<SpecificCourse> getSpecificCourse() {
   return this.specificCourse;
}

public void setSpecificCourse(Set<SpecificCourse> specificCourses) {
   this.specificCourse = specificCourses;
}

private String subject;

public void setSubject(String value) {
    this.subject = value;
}
public String getSubject() {
    return this.subject;
}
}
