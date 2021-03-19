package com.mobile.telma.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import com.mobile.telma.domains.Customer;

@Repository
public class CustomerRepository {

	@Autowired
	private MongoTemplate mongoTemplate;
	
	public List<Customer> findAll(){
		return mongoTemplate.findAll(Customer.class);
	}
	
	public void save(Customer customer) {
	   mongoTemplate.save(customer);
	}
	
}
