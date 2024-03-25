package com.jcode.ebookpedia.server.dao;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.jcode.ebookpedia.client.AppConstants;
import com.jcode.ebookpedia.client.filter.Filter;
import com.jcode.ebookpedia.server.model.Identifiable;


public abstract class AbstractDAO<T extends Identifiable, K extends Serializable> implements DAO<T, K> {

	@PersistenceContext
	private EntityManager entityManager;

	private final Class<T> persistentClass;

	protected AbstractDAO() {
		this.persistentClass = (Class<T>) ((ParameterizedType) this.getClass().getGenericSuperclass())
				.getActualTypeArguments()[0];
	}

	protected EntityManager getEntityManager() {
		return this.entityManager;
	}

	public T get(K key) {
		return getEntityManager().find(persistentClass, key);
	}

	@Override
	public T save(T entity) {
		if (isNew(entity)) {
			return persist(entity);
		}
		return update(entity);
	}

	public T persist(T entity) {
		getEntityManager().persist(entity);
		return entity;
	}

	public T update(T entity) {
		return getEntityManager().merge(entity);
	}

	public void delete(T entity) {
		getEntityManager().remove(entity);
	}

	@Override
	public void deleteByPK(K id) {
		T entity = get(id);
		if (entity == null) {
			throw new IllegalStateException(String.format("No %s entity found for ID '%s'", persistentClass.getName() ,id));
		}
		getEntityManager().remove(entity);
	}

	@Override
	public List<T> findAll() {
		String queryStr = "SELECT e FROM " + persistentClass.getName() + " e";
		Query query = getEntityManager().createQuery(queryStr, persistentClass);
		return query.getResultList();
	}
	
	@Override
	public List<T> find(Integer pageNumber, Filter<?>... filters) {
		int limit = AppConstants.RECORDS_PER_PAGE;
		int offset = ((pageNumber - 1) * limit) + 1;
		return find(offset, limit, null, List.of(filters));
	}
	
	public List<T> find(int offset, int limit, Collection<Filter<?>> filters) {
		return find(offset, limit, null, filters);
	}
	
	public List<T> find(Filter<?>... filters) {
		return find(-1, -1, null, List.of(filters));
	}

	public List<T> find(int offset, int limit, String order, Collection<Filter<?>> filters) {
		
		if (filters == null)
			filters = Collections.emptyList();
		
		Query query = null;
		String alias = persistentClass.getName().substring(0, 3).toLowerCase();
		StringBuilder queryBuilder = new StringBuilder();
		queryBuilder.append(" FROM ");
		queryBuilder.append(persistentClass.getName());
		queryBuilder.append(" ");
		queryBuilder.append(alias);
		queryBuilder.append(" WHERE true ");
		
		int index = 1;
		for (Filter<?> filter : filters) {
			queryBuilder.append(" AND ");
			queryBuilder.append(alias);
			queryBuilder.append(".");
			queryBuilder.append(filter.getFieldName());
			queryBuilder.append(" ");
			queryBuilder.append(filter.getFilterOperator());
			queryBuilder.append(" ?");
			queryBuilder.append(index++);
		}
		
		
		if (offset >= 0 && limit > 0) {
			String countQuery = " SELECT count(*) " + queryBuilder.toString();
			query = getEntityManager().createQuery(countQuery, persistentClass);
	
			index = 1;
			for (Filter<?> filter : filters) {
				query.setParameter(index++, filter.getValue());
			}
			
			int totalRecords = (int) query.getSingleResult();
			offset = Math.min(offset, totalRecords);
			limit = Math.min(limit, totalRecords);
		}
		
		if (order != null && !order.trim().isEmpty()) {
			queryBuilder.append(" ORDER BY ");
			queryBuilder.append(alias);
			queryBuilder.append(".");
			queryBuilder.append(order);
		}
		
		String resultQuery = " SELECT " + alias + queryBuilder.toString();
		query = getEntityManager().createQuery(resultQuery, persistentClass);
		if (offset >= 0 && limit > 0) {
			query.setFirstResult(offset);
			query.setMaxResults(limit);
		}
		
		index = 1;
		for (Filter<?> filter : filters) {
			query.setParameter(index++, filter.getValue());
		}
		return query.getResultList();
	}
	
	protected boolean isNew(T entity) {
		return entity != null && entity.getId() == null;
	}

}
