package com.jcode.ebookpedia.config;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.jcode.ebookpedia.criteria.Criteria;
import com.jcode.ebookpedia.criteria.filter.Filter;
import com.jcode.ebookpedia.criteria.filter.FilterOperator;
import com.jcode.ebookpedia.criteria.filter.NumberFilter;
import com.jcode.ebookpedia.criteria.filter.StringFilter;
import com.jcode.ebookpedia.criteria.filter.ZonedDateTimeFilter;
import com.jcode.ebookpedia.criteria.page.Page;
import com.jcode.ebookpedia.criteria.sort.Sort;
import com.jcode.ebookpedia.criteria.sort.Sort.Direction;


@Configuration
@ComponentScan({ "com.jcode.ebookpedia" })
public class AppConfig {
	

	public static void main(String[] args) throws JsonProcessingException {
		System.out.println("Passwd: " + new BCryptPasswordEncoder().encode("123"));

		List<Filter> filters = new ArrayList<>();
		filters.add(new StringFilter("sumary", FilterOperator.CN, "pring"));
		filters.add(new NumberFilter("score", FilterOperator.GE, 4));
		filters.add(new ZonedDateTimeFilter("createdAt", FilterOperator.LT, ZonedDateTime.now()));

		List<Sort> sorts = new ArrayList<>();
		sorts.add(new Sort("createdAt", Direction.DESC));

		Page page = new Page(0, 50);


		ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(new JavaTimeModule());
		mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
		String jsonString = mapper.writeValueAsString(new Criteria(filters, sorts, page));

		System.out.println(">>>>>");

		System.out.println("data=" + Base64.getUrlEncoder().encodeToString(jsonString.getBytes()));

		System.out.println("<<<<<");
	}
}
