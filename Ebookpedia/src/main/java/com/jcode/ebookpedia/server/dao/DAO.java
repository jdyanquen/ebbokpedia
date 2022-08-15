package com.jcode.ebookpedia.server.dao;

import java.io.Serializable;
import java.util.List;

import com.jcode.ebookpedia.client.filter.Filter;
import com.jcode.ebookpedia.server.model.Identifiable;

public interface DAO<T extends Identifiable, K extends Serializable> {
	
	T save(T entity);
	
	T get(K key);
	
	void delete(T entity);

	void deleteByPK(K key);
	
	List<T> findAll();
	
	List<T> find(Integer pageNumber, Filter<?>... filters);

}
