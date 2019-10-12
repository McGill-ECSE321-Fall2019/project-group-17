package ca.mcgill.ecse321.projectgroup17;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@SpringBootApplication
public class ProjectGroup17Application {

	public static void main(String[] args) {
		SpringApplication.run(ProjectGroup17Application.class, args);
	}

 	 @RequestMapping("/")
 	 public String greeting(){
  		 return "Hello world from project-group-17-backend!";
 	 }

}
