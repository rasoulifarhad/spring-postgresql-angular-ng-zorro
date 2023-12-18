package com.farhad.example.ngzorro.person.controller.model.request;

import com.farhad.example.ngzorro.person.controller.model.paging.PageInfo;
import com.farhad.example.ngzorro.person.dto.PersonFilter;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class PersonFilterRequest {

	private PageInfo pageInfo;
	private PersonFilter personFilter;
}
