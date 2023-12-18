package com.farhad.example.ngzorro.person.controller.model.paging;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class Sorting {

	private String column;
	private SortOrder order;

}
