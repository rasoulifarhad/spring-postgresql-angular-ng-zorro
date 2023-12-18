package com.farhad.example.ngzorro.person.controller.model.request;

import lombok.Data;

@Data
public class CreatePersonRequest {

	private String firstName;
	private String lastName;
	private int age;
	private String address;

}
