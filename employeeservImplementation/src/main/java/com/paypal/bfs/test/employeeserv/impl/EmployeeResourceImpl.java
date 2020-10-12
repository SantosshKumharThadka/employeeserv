package com.paypal.bfs.test.employeeserv.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.paypal.bfs.test.employeeserv.api.EmployeeResource;
import com.paypal.bfs.test.employeeserv.api.model.Employee;
import com.paypal.bfs.test.employeeserv.entity.EmployeeEntity;
import com.paypal.bfs.test.employeeserv.exception.DateFormatIncorrectException;
import com.paypal.bfs.test.employeeserv.exception.EmployeeAlreadyExistsException;
import com.paypal.bfs.test.employeeserv.exception.EmployeeNotFoundException;
import com.paypal.bfs.test.employeeserv.exception.GenericException;
import com.paypal.bfs.test.employeeserv.repository.EmployeeRepository;
import com.paypal.bfs.test.employeeserv.util.EmployeeServUtil;

/**
 * Implementation class for employee resource.
 */
@RestController
public class EmployeeResourceImpl implements EmployeeResource {

    @Autowired
    private EmployeeRepository empRepository;

    @Override
    public ResponseEntity<Employee> employeeGetById(String id) {

	Optional<EmployeeEntity> emp = empRepository.findById(Long.valueOf(id));
	if (!emp.isPresent()) {
	    throw new EmployeeNotFoundException("Employee Not found for  id- " + id);
	}
	try {
	    Employee employee = EmployeeServUtil.transformToModel(emp.get());
	    return new ResponseEntity<Employee>(employee, HttpStatus.OK);
	} catch (Exception e) {
	    e.printStackTrace();
	    throw new GenericException("Something really went wrong. Please try again later");
	}
    }

    @Override
    public ResponseEntity<Employee> createEmployee(Employee employee) {

	Optional<EmployeeEntity> emp = empRepository.findById(Long.valueOf(employee.getId()));

	if (emp.isPresent()) {
	    throw new EmployeeAlreadyExistsException("Duplicate employee found in request");

	}
	try {
	    EmployeeEntity employeeEntity = EmployeeServUtil.transformToEntity(employee);
	    if (employeeEntity != null)
		empRepository.save(employeeEntity);
	    Employee employeeResponse = EmployeeServUtil.transformToModel(employeeEntity);
	    return new ResponseEntity<Employee>(employeeResponse, HttpStatus.CREATED);

	}catch (DateFormatIncorrectException de) {
	    de.printStackTrace();
	    throw new DateFormatIncorrectException(de.getMessage());
	} 
	catch (Exception e) {
	    e.printStackTrace();
	    throw new GenericException("Something really went wrong. Please try again later");
	}

    }

}
