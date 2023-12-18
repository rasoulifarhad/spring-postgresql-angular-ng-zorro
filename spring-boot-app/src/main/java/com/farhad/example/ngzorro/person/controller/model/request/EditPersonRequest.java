package com.farhad.example.ngzorro.person.controller.model.request;

import java.util.UUID;

import lombok.Data;

@Data
public class EditPersonRequest {

	private UUID id;
	private String firstName;
	private String lastName;
	private int age;
	private String address;

}
