package com.jcode.ebookpedia.postgres;

import java.lang.reflect.Field;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.springframework.stereotype.Component;

import com.jcode.ebookpedia.criteria.Criteria;
import com.jcode.ebookpedia.criteria.CriteriaMapper;

@Component
public class PostgresCriteriaMapper implements CriteriaMapper {
	
	private static final String DATE_TIME_FORMAT = "yyyy/MM/dd HH:mm:ss.SSS Z";

	@Override
	public String map(Criteria criteria) {
		return map(criteria, 0);
	}
	
	private String map(Object object, int level) {
		if (object == null) {
			return "";
		}
		else if (object instanceof String || object instanceof Number || object instanceof Boolean || object instanceof Enum) {
			return object.toString().replace("'", "''");
		}
		else if (object instanceof ZonedDateTime) {
			return ((ZonedDateTime)object).format(DateTimeFormatter.ofPattern(DATE_TIME_FORMAT));
		}
		else if (object instanceof Collection) {
			Collection<?> collection = (Collection<?>) object;
			return mapArray(collection.toArray(), level);
		}
		else if (object instanceof Object[]) {
			Object[] array = (Object[]) object; 
			return mapArray(array, level);
		}
		return mapObject(object, level);
	}
	
	private String mapArray(Object[] array, int level) {
		StringBuilder builder = new StringBuilder();
		for (Object object : array) {
			if (builder.length() == 0) {
				builder.append(generateEscapeCharacters(level));
				builder.append("{");
			}
			else {
				builder.append(generateEscapeCharacters(level));
				builder.append(",");
			}
			builder.append(map(object, level + 1));
		}
		if (builder.length() > 0) {
			builder.append(generateEscapeCharacters(level));
			builder.append("}");
		}
		return builder.toString();
	}
	
	private String mapObject(Object object, int level) {
		StringBuilder builder = new StringBuilder();
		List<Field> fields = new ArrayList<>();
		fields.addAll(Arrays.asList(object.getClass().getDeclaredFields()));
		if (object.getClass().getSuperclass() != null) {
			fields.addAll(Arrays.asList(object.getClass().getSuperclass().getDeclaredFields()));
		}
		try {
			for (Field field : fields) {
				field.setAccessible(true);
				if (builder.length() == 0) {
					builder.append(generateEscapeCharacters(level));
					builder.append("(");
				}
				else {
					builder.append(generateEscapeCharacters(level));
					builder.append(",");
				}
				builder.append(map(field.get(object), level + 1));
			}
			if (builder.length() > 0) {
				builder.append(generateEscapeCharacters(level));
				builder.append(")");
			}
			
		} catch (IllegalAccessException ex) {
			throw new IllegalArgumentException(ex);
		}
		return builder.toString();
	}
	
	private String generateEscapeCharacters(int level) {
		StringBuilder builder = new StringBuilder();
		int count = 0;
		int index = 1;
		while (count < level) {
			builder.append("\\");
			if (index++ % 2 != 0) {
				count++;
			}
		}
		return builder.toString();
	}

}
