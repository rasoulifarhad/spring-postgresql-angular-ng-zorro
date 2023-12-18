package com.farhad.example.ngzorro.person.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.farhad.example.ngzorro.person.controller.model.request.CreatePersonRequest;
import com.farhad.example.ngzorro.person.controller.model.request.EditPersonRequest;
import com.farhad.example.ngzorro.person.controller.model.request.PersonFilterRequest;
import com.farhad.example.ngzorro.person.dto.PageDto;
import com.farhad.example.ngzorro.person.dto.PersonDto;
import com.farhad.example.ngzorro.person.service.PersonService;

import lombok.RequiredArgsConstructor;


@RestController
@RequestMapping("/api/persons")
@RequiredArgsConstructor
public class PersonController {

	private final PersonService personService;

	@PostMapping
	public UUID create(@RequestBody CreatePersonRequest createPersonRequest) {
		return personService.save(PersonDto.builder()
				.firstName(createPersonRequest.getFirstName())
				.lastName(createPersonRequest.getLastName())
				.address(createPersonRequest.getAddress())
				.age(createPersonRequest.getAge())
				.build());
	}

	@GetMapping("/{id}")
	public PersonDto find(@PathVariable("id") UUID id) {
		return personService.find(id);
	}

	@PutMapping("/{id}")
	public UUID save(@PathVariable("id") UUID id, @RequestBody EditPersonRequest editPersonRequest) {
		return personService.save(PersonDto.builder()
				.id(editPersonRequest.getId())
				.firstName(editPersonRequest.getFirstName())
				.lastName(editPersonRequest.getLastName())
				.address(editPersonRequest.getAddress())
				.age(editPersonRequest.getAge())
				.build());
	}

	@DeleteMapping("/{id}")
	public void delete(@PathVariable("id") UUID id) {
		personService.deletePerson(id);
	}

	@PostMapping("/search")
	public PageDto<PersonDto> list(@RequestBody PersonFilterRequest personFilterRequest) {
		return personService.filter(personFilterRequest.getPersonFilter(), personFilterRequest.getPageInfo());
	}
	
	@GetMapping
	public List<PersonDto> all() {
		return personService.all();
	}
	
}
