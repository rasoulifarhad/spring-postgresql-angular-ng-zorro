package com.farhad.example.ngzorro.person.jpa.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.farhad.example.ngzorro.person.jpa.model.PersonEntity;

public interface PersonRepository extends JpaRepository<PersonEntity,UUID>, JpaSpecificationExecutor<PersonEntity>{

	
}
