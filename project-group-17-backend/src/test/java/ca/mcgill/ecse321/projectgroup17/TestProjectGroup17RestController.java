package ca.mcgill.ecse321.projectgroup17;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;

import ca.mcgill.ecse321.projectgroup17.controller.ProjectGroup17RestController;
import ca.mcgill.ecse321.projectgroup17.dao.PersonRepository;
import ca.mcgill.ecse321.projectgroup17.model.*;
import ca.mcgill.ecse321.projectgroup17.service.ProjectGroup17Service;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.ArgumentMatchers.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@WebMvcTest(value = ProjectGroup17RestController.class, secure = false)
public class TestProjectGroup17RestController {

	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private ProjectGroup17Service service;
	
	@Mock
	private PersonRepository personDao;
	
	
	private static final String PERSON_KEY = "TestPerson";
	private static final String NONEXISTING_KEY = "NotAPerson";
	
	
	@Test
	public void testGetPersonByUsername() throws Exception {

		when(service.getPersonByUsername(anyString())).thenAnswer( (InvocationOnMock invocation) -> {
			if(invocation.getArgument(0).equals(PERSON_KEY)) {
				Person person = new Person();
				person.setUsername(PERSON_KEY);
				return person;
			} else {
				return null;
			}
		});
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get(
				"/persons/getByUsername?username="+PERSON_KEY).accept(
				MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		String expected = "{\"firstName\":null,\"lastName\":null,\"username\":\"TestPerson\",\"email\":null,\"password\":null,\"sexe\":null,\"age\":0,\"personType\":null,\"name\":\"null null\"}";

		assertEquals(expected, result.getResponse().getContentAsString());
		
	}
	
	@Test
	public void testCreatePerson() throws Exception {
		
		String personType = "Tutor";
		String firstName = "John";
		String lastName = "Smith";
		String username = "johnsmith123";
		String password = "pass123";
		String email = "john.smith@mail.ca";
		String sexe = "male";
		long age = 29L;
		
		when(service.createPerson(anyString(), anyString(), anyString(), anyString(), anyString(), anyString(), anyString(), anyLong())).thenAnswer( (InvocationOnMock invocation) -> {
			if(invocation.getArgument(3).equals(username)) {
				Person person = new Person();
				person.setFirstName(firstName);
				person.setLastName(lastName);
				person.setUsername(username);
				person.setEmail(email);
				person.setPassword(password);
				person.setSexe(sexe);
				person.setAge(age);
				return person;
			} else {
				return null;
			}
		});
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post(
				"/persons/createPerson?firstName="+firstName+"&lastName="+lastName+"&username="+username+"&personType="+personType
				+"&password="+password+"&email="+email+"&sexe="+sexe+"&age="+age).accept(
				MediaType.APPLICATION_JSON);
			
		
		
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		String expected = "{\"firstName\":\"John\",\"lastName\":\"Smith\",\"username\":\"johnsmith123\","
				+ "\"email\":\"john.smith@mail.ca\",\"password\":\"pass123\",\"sexe\":null,\"age\":29,"
				+ "\"personType\":null,\"name\":\"John Smith\"}";
		assertEquals(expected, result.getResponse().getContentAsString());
	}
}
