package com.farhad.example.ngzorro.person.jpa.utils;

import java.util.List;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import lombok.experimental.UtilityClass;

@UtilityClass
public class JpaSpecifications {

	public <T> Specification<T> upperLink(String value, String columnName) {
		return !StringUtils.hasText(value) ? null
				: (root, query, criteriaBuilder) -> criteriaBuilder.like(
						criteriaBuilder.upper(
								root.get(columnName)),
						"%" + value.toUpperCase() + "%");
	}
	
	public <T> Specification<T> checkList(List<?> values, String columnName ) {
		return (values == null || values.isEmpty()) ?
				null :
				(root, query, criteriaBuilder) -> root.get(columnName).in(values);
	}
}
