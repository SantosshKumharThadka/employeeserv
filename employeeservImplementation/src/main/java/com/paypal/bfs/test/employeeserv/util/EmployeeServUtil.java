package com.paypal.bfs.test.employeeserv.util;

import com.paypal.bfs.test.employeeserv.api.model.Address;
import com.paypal.bfs.test.employeeserv.api.model.Employee;
import com.paypal.bfs.test.employeeserv.entity.AddressEntity;
import com.paypal.bfs.test.employeeserv.entity.EmployeeEntity;
import com.paypal.bfs.test.employeeserv.exception.DateFormatIncorrectException;

public class EmployeeServUtil {
    /**
     * utility class to transform model to entity class
     * 
     * @param employee
     * @return
     */
    public static EmployeeEntity transformToEntity(Employee employee) {

	EmployeeEntity employeeEntity = new EmployeeEntity();
	AddressEntity addressEntity = new AddressEntity();
	Address address = employee.getAddress();
	if (employee != null && address != null) {
	    try {
		employeeEntity.setDateOfBirth(java.sql.Date.valueOf(employee.getDateOfBirth()));
	    } catch (Exception e) {
		throw new DateFormatIncorrectException("date of birth format should be in 'yyyy-mm-dd', please correct and resend");
	    }
	    employeeEntity.setFirstName(employee.getFirstName());
	    employeeEntity.setLastName(employee.getLastName());
	    employeeEntity.setId(employee.getId().longValue());
	    addressEntity.setCity(address.getCity());
	    addressEntity.setLine1(address.getLine1());
	    addressEntity.setLine2(address.getLine2());
	    addressEntity.setState(address.getState());
	    addressEntity.setCity(address.getCity());
	    addressEntity.setCountry(address.getCountry());
	    addressEntity.setZipCode(address.getZipCode());
	    employeeEntity.setAddressEntity(addressEntity);
	    addressEntity.setEmployeeEntity(employeeEntity);

	    return employeeEntity;
	} else {
	    return null;
	}

    }

    /**
     * utility to transform entity to model class
     * 
     * @param employeeEntity
     * @return
     */
    public static Employee transformToModel(EmployeeEntity employeeEntity) {

	Employee employee = new Employee();
	Address address = new Address();
	AddressEntity addressEntity = employeeEntity.getAddressEntity();
	employee.setDateOfBirth(employeeEntity.getDateOfBirth().toString());
	employee.setFirstName(employeeEntity.getFirstName());
	employee.setLastName(employeeEntity.getLastName());
	employee.setId(employeeEntity.getId().intValue());
	address.setCity(addressEntity.getCity());
	address.setLine1(addressEntity.getLine1());
	address.setLine2(addressEntity.getLine2());
	address.setState(addressEntity.getState());
	address.setCity(addressEntity.getCity());
	address.setCountry(addressEntity.getCountry());
	address.setZipCode(addressEntity.getZipCode());
	employee.setAddress(address);

	return employee;

    }

}
