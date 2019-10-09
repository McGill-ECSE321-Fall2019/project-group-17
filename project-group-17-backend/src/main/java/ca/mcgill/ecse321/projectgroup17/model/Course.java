package ca.mcgill.ecse321.projectgroup17.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
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

	private Set<SpecificCourse> specificCourses;

<<<<<<< HEAD
	@OneToMany(mappedBy="course")
=======
	@OneToMany(mappedBy="course" )
>>>>>>> 28563c99bd477427df1a197dd949b34cd11a1ff8
	public Set<SpecificCourse> getSpecificCourses() {
		return this.specificCourses;
	}

<<<<<<< HEAD
	public void setSpecificCourses(Set<SpecificCourse> specificCourses) {
=======
	public void setHourlyRate(Set<SpecificCourse> specificCourses) {
>>>>>>> 28563c99bd477427df1a197dd949b34cd11a1ff8
		this.specificCourses = specificCourses;
	}

}
