package com.galactoise.homeorchestration.service.resource.provider;

import java.util.HashSet;

import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.Provider;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;

/** 
 * This can be used if there is a reason to filter any fields that would be returned to the user
 * @author Eric
 *
 */
@Provider
public class ObjectMapperProvider implements ContextResolver<ObjectMapper> {

	@Override
	public ObjectMapper getContext(Class<?> type) {
		SimpleFilterProvider filterProvider = new SimpleFilterProvider();
		filterProvider.setFailOnUnknownId(false);
		filterProvider.setDefaultFilter(SimpleBeanPropertyFilter.serializeAllExcept(new HashSet<String>()));
		
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.writer(filterProvider);
		objectMapper.setFilters(filterProvider);
		return objectMapper;
	}

}
