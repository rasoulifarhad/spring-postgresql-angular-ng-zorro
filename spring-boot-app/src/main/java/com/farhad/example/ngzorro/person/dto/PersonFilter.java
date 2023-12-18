package com.farhad.example.ngzorro.person.dto;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class PersonFilter {

	private String firstName;
	private String lastName;
	private List<Integer> age;
	private String address;
}
