package com.paypal.bfs.test.employeeserv.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.paypal.bfs.test.employeeserv.entity.AddressEntity;

public interface AddressRepository extends JpaRepository<AddressEntity, Long> { 
    
    

}
