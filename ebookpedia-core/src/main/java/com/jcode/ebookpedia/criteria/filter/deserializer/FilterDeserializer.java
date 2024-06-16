package com.jcode.ebookpedia.criteria.filter.deserializer;

import java.io.IOException;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jcode.ebookpedia.criteria.filter.BooleanFilter;
import com.jcode.ebookpedia.criteria.filter.CollectionFilter;
import com.jcode.ebookpedia.criteria.filter.ZonedDateTimeFilter;
import com.jcode.ebookpedia.criteria.filter.Filter;
import com.jcode.ebookpedia.criteria.filter.FilterOperator;
import com.jcode.ebookpedia.criteria.filter.NumberFilter;
import com.jcode.ebookpedia.criteria.filter.StringFilter;

public class FilterDeserializer extends JsonDeserializer<Filter> {

	@Override
	public Filter deserialize(JsonParser jsonParser, DeserializationContext context) throws IOException {
		ObjectMapper mapper = (ObjectMapper) jsonParser.getCodec();
        JsonNode node = mapper.readTree(jsonParser);
        String fieldName = node.get("fieldName").asText();
        FilterOperator comparison = FilterOperator.valueOf(node.get("filterOperator").asText());
        JsonNode value = node.get("value");
        
        if (value.isNull()) {
        	return null;
        }
        else if (value.isArray()) {
            return mapper.treeToValue(node, CollectionFilter.class);
        }
        else if (value.isBoolean()) {
            return new BooleanFilter(fieldName, comparison, value.asBoolean()); 
        }
        else if (value.isNumber()) {
            return new NumberFilter(fieldName, comparison, value.numberValue());
        }
        else if (value.isTextual()) {
        	try {
        		return new ZonedDateTimeFilter(fieldName, comparison, ZonedDateTime.parse(value.asText(), DateTimeFormatter.ISO_DATE_TIME));
        	}
        	catch (DateTimeParseException ex) {
        		return new StringFilter(fieldName, comparison, value.asText());	
			}
        }
        throw new IllegalArgumentException("Unknown filter type");
	}
	

}
