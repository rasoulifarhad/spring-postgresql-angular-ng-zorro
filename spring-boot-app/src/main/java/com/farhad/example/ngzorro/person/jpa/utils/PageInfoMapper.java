package com.farhad.example.ngzorro.person.jpa.utils;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.util.CollectionUtils;

import com.farhad.example.ngzorro.person.controller.model.paging.PageInfo;
import com.farhad.example.ngzorro.person.controller.model.paging.Sorting;

import lombok.experimental.UtilityClass;

@UtilityClass
public class PageInfoMapper {

	public PageRequest toPageRequest(PageInfo pageInfo) {
		return pageInfo.getSort() != null ? PageRequest.of(pageInfo.getPageNumber() - 1,
														   pageInfo.getPageSize(),
														   Sort.by(mapToOrders(pageInfo.getSort()))	) :
				PageRequest.of(pageInfo.getPageNumber() - 1, pageInfo.getPageSize());
	}

	private List<Sort.Order> mapToOrders(List<Sorting> sortingList) {
		return CollectionUtils.isEmpty(sortingList) ? Collections.emptyList() :
				sortingList.stream()
					.map(PageInfoMapper::toOrder)
						.collect(Collectors.toList());
	}

	private Sort.Order toOrder(Sorting sorting) {
		return new Sort.Order(Sort.Direction.fromString(sorting.getOrder().name()), sorting.getColumn());
	}
}
