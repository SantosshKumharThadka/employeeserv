package com.paypal.bfs.test.employeeserv.entity;

import java.io.Serializable;
import java.sql.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "employee")
@Data
public class EmployeeEntity implements Serializable {

    public EmployeeEntity() {
	// TODO Auto-generated constructor stub
    }

    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "date_of_birth")
    //@Temporal(TemporalType.DATE)
    private Date dateOfBirth;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "employeeEntity")
    private AddressEntity addressEntity;

   

}
