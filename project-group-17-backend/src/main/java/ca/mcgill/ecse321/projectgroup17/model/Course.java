package ca.mcgill.ecse321.projectgroup17.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Set;
import javax.persistence.OneToMany;

@Entity
public class Course{
   private String courseID;

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
private String level;

public void setLevel(String value) {
    this.level = value;
}
public String getLevel() {
    return this.level;
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
