package com.paypal.bfs.test.employeeserv.tests;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.paypal.bfs.test.employeeserv.api.EmployeeResource;
import com.paypal.bfs.test.employeeserv.api.model.Address;
import com.paypal.bfs.test.employeeserv.api.model.Employee;
import com.paypal.bfs.test.employeeserv.entity.AddressEntity;
import com.paypal.bfs.test.employeeserv.entity.EmployeeEntity;
import com.paypal.bfs.test.employeeserv.impl.EmployeeResourceImpl;
import com.paypal.bfs.test.employeeserv.repository.EmployeeRepository;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = { EmployeeResource.class, EmployeeResourceImpl.class })

public class EmployeeResourceTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EmployeeRepository empRepository;

    Optional<EmployeeEntity> empEntity = Optional.empty();

    @Before
    public void setUp() {
	EmployeeEntity employeeEntity = new EmployeeEntity();
	AddressEntity addrEntity = new AddressEntity();
	addrEntity.setAddressId(1L);
	addrEntity.setCity("city");
	addrEntity.setLine1("line 1");
	addrEntity.setLine2("line2");
	addrEntity.setState("state");
	addrEntity.setCountry("Country");
	addrEntity.setZipCode(123456);
	employeeEntity.setAddressEntity(addrEntity);
	employeeEntity.setId(1L);
	employeeEntity.setDateOfBirth(java.sql.Date.valueOf("2020-12-12"));
	employeeEntity.setFirstName("fname");
	employeeEntity.setLastName("lastName");
	empEntity = Optional.of(employeeEntity);
	this.mockMvc = webAppContextSetup(webApplicationContext).build();

    }

    @Test
    public void shouldReturnSuccessMessage_GET() throws Exception {
	given(empRepository.findById(1L)).willReturn(empEntity);
	this.mockMvc.perform(get("/v1/bfs/employees/{id}", 1)).andDo(print()).andExpect(status().isOk())
		.andExpect(content().string(containsString("city")));
    }

    @Test
    public void shouldReturnNotFoundMessage_GET() throws Exception {

	given(empRepository.findById(1L)).willReturn(empEntity);

	this.mockMvc.perform(get("/v1/bfs/employees/{id}", 0)).andDo(print()).andExpect(status().isNotFound())
		.andExpect(content().string(containsString("message")));
    }

    @Test
    public void shouldReturnBadRequestMessage_POST() throws Exception {

	given(empRepository.findById(1L)).willReturn(empEntity);
	given(empRepository.save(empEntity.get())).willReturn(empEntity.get());

	this.mockMvc.perform(post("/v1/bfs/employees")).andDo(print()).andExpect(status().isBadRequest())
		.andExpect(content().string(containsString("")));
    }

    @Test
    public void shouldReturnConflictMessage_POST() throws Exception {

	given(empRepository.findById(1L)).willReturn(empEntity);
	given(empRepository.save(empEntity.get())).willReturn(empEntity.get());
	Employee employee = createEmployee();

	this.mockMvc
		.perform(post("/v1/bfs/employees").content(asJsonString(employee))
			.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
		.andDo(print()).andExpect(status().isConflict()).andExpect(content().string(containsString("")));
    }
    
    
    @Test
    public void shouldReturnOKMessage_POST() throws Exception {

	given(empRepository.findById(1L)).willReturn(empEntity);
	given(empRepository.save(empEntity.get())).willReturn(empEntity.get());
	Employee employee = createEmployee();
employee.setId(2);
	this.mockMvc
		.perform(post("/v1/bfs/employees").content(asJsonString(employee))
			.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
		.andDo(print()).andExpect(status().isCreated()).andExpect(content().string(containsString("address")));
    }

    private  String asJsonString(final Object obj) {
	try {
	    return new ObjectMapper().writeValueAsString(obj);
	} catch (Exception e) {
	    throw new RuntimeException(e);
	}
    }
    
    

    private Employee createEmployee() {
	Employee employee = new Employee();
	Address addr = new Address();

	addr.setCity("city");
	addr.setLine1("line 1");
	addr.setLine2("line2");
	addr.setState("state");
	addr.setCountry("Country");
	addr.setZipCode(123456);
	employee.setAddress(addr);
	employee.setId(1);
	employee.setDateOfBirth("2020-12-12");
	employee.setFirstName("fname");
	employee.setLastName("lastName");
	return employee;

    }
}
