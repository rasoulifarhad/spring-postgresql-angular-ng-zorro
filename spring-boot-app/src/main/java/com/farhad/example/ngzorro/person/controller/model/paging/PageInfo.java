package com.farhad.example.ngzorro.person.controller.model.paging;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PageInfo {

	private int pageNumber;
	private int pageSize;
	private List<Sorting> sort;

}
