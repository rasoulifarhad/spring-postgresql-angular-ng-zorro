package com.farhad.example.ngzorro.person.service;

import static java.util.stream.Collectors.toList;
import static org.springframework.data.jpa.domain.Specification.where;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.farhad.example.ngzorro.person.controller.model.paging.PageInfo;
import com.farhad.example.ngzorro.person.dto.PageDto;
import com.farhad.example.ngzorro.person.dto.PersonDto;
import com.farhad.example.ngzorro.person.dto.PersonFilter;
import com.farhad.example.ngzorro.person.exception.PersonNotFoundException;
import com.farhad.example.ngzorro.person.jpa.model.PersonEntity;
import com.farhad.example.ngzorro.person.jpa.repository.PersonRepository;
import com.farhad.example.ngzorro.person.jpa.utils.JpaSpecifications;
import com.farhad.example.ngzorro.person.jpa.utils.PageInfoMapper;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class PersonService {

	private final PersonRepository personRepository;

	public UUID save(PersonDto personDto) {
		return personDto.getId() != null ? saveModified(personDto) : saveNew(personDto);
	}

	public PersonDto find(UUID id) {
		return personRepository.findById(id)
			.map(this::toPersonDto)
				.orElseThrow(PersonNotFoundException::new);
	}

	private UUID saveNew(PersonDto personDto) {
		PersonEntity entity = fillPersonEntity(personDto, new PersonEntity());
		entity.setId(UUID.randomUUID());
		PersonEntity saved = personRepository.save(entity);
		return saved.getId();
	}

	private PersonEntity fillPersonEntity(PersonDto personDto, PersonEntity entity) {
		entity.setFirstName(personDto.getFirstName());
		entity.setLastName(personDto.getLastName());
		entity.setAge(personDto.getAge());
		entity.setAddress(personDto.getAddress());
		return entity;
	}

	private UUID saveModified(PersonDto personDto) {
		PersonEntity entity = personRepository.findById(personDto.getId())
			.map(ent -> fillPersonEntity(personDto, ent))
				.orElseThrow(PersonNotFoundException::new);
		personRepository.save(entity);
		return personDto.getId();
	}

	public void deletePerson(UUID id) {
		PersonEntity byId = personRepository.findById(id)
				.orElseThrow(PersonNotFoundException::new);
		personRepository.delete(byId);
	}

	public PageDto<PersonDto> filter(PersonFilter filter, PageInfo pageInfo) {
		PageRequest pageRequest = PageInfoMapper.toPageRequest(pageInfo);
		Page<PersonEntity> personEntityPage = personRepository.findAll(applyWhere(filter), pageRequest);
		return PageDto.<PersonDto>builder()
			.rows(personEntityPage.getContent()
				.stream()
				.map(this::toPersonDto)
				.collect(toList()))
			.totalElements(personEntityPage.getTotalElements())
			.totalPages(personEntityPage.getTotalPages())
			.build();
	}

	private Specification<PersonEntity> applyWhere(PersonFilter filter) {
		Specification<PersonEntity> firstName = JpaSpecifications.upperLink(filter.getFirstName(), "firstName");
		Specification<PersonEntity> lastName = JpaSpecifications.upperLink(filter.getLastName(), "lastName");
		Specification<PersonEntity> address = JpaSpecifications.upperLink(filter.getAddress(), "address");
		Specification<PersonEntity> age = JpaSpecifications.checkList(filter.getAge(), "age");

		return where(firstName)
			.and(lastName)
			.and(address)
				.and(age);
	}

	private PersonDto toPersonDto(PersonEntity entity) {
		return PersonDto.builder()
			.id(entity.getId())
			.firstName(entity.getFirstName())
			.lastName(entity.getLastName())
			.age(entity.getAge())
			.address(entity.getAddress())
			.build();
	}
	
	public List<PersonDto> all() {
		return personRepository.findAll()
					.stream()
					.map(this::toPersonDto)
				.collect(toList());
	}
}
