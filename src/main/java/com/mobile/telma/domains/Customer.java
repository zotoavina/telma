package com.mobile.telma.domains;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="customer")
public class Customer {

  @Id
  public String id;

  public String firstName;
  public String lastName;
  public Object details;

  public Customer() {}

  
  

  public Customer(String firstName, String lastName, Object details) {
	super();
	this.firstName = firstName;
	this.lastName = lastName;
	this.details = details;
}




public String getId() {
	return id;
 }



	public void setId(String id) {
		this.id = id;
	}



public String getFirstName() {
	return firstName;
}



public void setFirstName(String firstName) {
	this.firstName = firstName;
}



public String getLastName() {
	return lastName;
}



public void setLastName(String lastName) {
	this.lastName = lastName;
}



public Object getDetails() {
	return details;
}



public void setDetails(Object details) {
	this.details = details;
}



@Override
  public String toString() {
    return String.format(
        "Customer[id=%s, firstName='%s', lastName='%s']",
        id, firstName, lastName);
  }
  

}