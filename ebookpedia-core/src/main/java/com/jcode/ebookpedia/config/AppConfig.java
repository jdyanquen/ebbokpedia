package com.jcode.ebookpedia.config;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.UUID;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jcode.ebookpedia.criteria.Criteria;
import com.jcode.ebookpedia.criteria.filter.Filter;
import com.jcode.ebookpedia.criteria.filter.FilterOperator;
import com.jcode.ebookpedia.criteria.filter.NumberFilter;
import com.jcode.ebookpedia.criteria.filter.StringFilter;
import com.jcode.ebookpedia.criteria.page.Page;
import com.jcode.ebookpedia.criteria.sort.Sort;
import com.jcode.ebookpedia.criteria.sort.Sort.Direction;


@Configuration
@ComponentScan({ "com.jcode.ebookpedia" })
public class AppConfig {

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	public static void main(String[] args) throws JsonProcessingException {
		System.out.println(UUID.randomUUID());
		System.out.println("Passwd: " + new BCryptPasswordEncoder().encode("123"));

		List<Filter> filters = new ArrayList<>();
		filters.add(new StringFilter("sumary", FilterOperator.LK, "Aprenda Java como si estuviera en primero."));
		filters.add(new NumberFilter("score", FilterOperator.GE, 4));

		List<Sort> sorts = new ArrayList<>();
		sorts.add(new Sort("createdAt", Direction.DESC));

		Page page = new Page(0, 50);

		ObjectMapper mapper = new ObjectMapper();
		String jsonString = mapper.writeValueAsString(new Criteria(filters, sorts, page));

		System.out.println(">>>>>");

		System.out.println("q=" + Base64.getUrlEncoder().encodeToString(jsonString.getBytes()));

		System.out.println("<<<<<");
	}
}
