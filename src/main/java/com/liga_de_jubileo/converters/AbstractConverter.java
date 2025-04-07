package com.liga_de_jubileo.converters;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public abstract class AbstractConverter<T, D, E> {
	
		@Autowired
	    public ObjectMapper objectMapper;

	    // Inyectar el ObjectMapper configurado
	    /*public AbstractConverter(ObjectMapper objectMapper) {
	        this.objectMapper = objectMapper;
	    }*/

	    // Convertir de una entidad a un DTO
	    public D convertToDTO(T entity, Class<D> dtoClass) {
	        return objectMapper.convertValue(entity, dtoClass);
	    }

	    // Convertir de un DTO a una entidad
	    public T convertToEntity(D dto, Class<T> entityClass) {
	        return objectMapper.convertValue(dto, entityClass);
	    }
	    
	 // Convertir una lista de entidades a una lista de DTOs
	    public List<D> convertToDTOList(List<T> entities, Class<D> dtoClass) {
	    	List<D> list =  entities.stream()
	                .map(entity -> convertToDTO(entity, dtoClass))
	                .collect(Collectors.toList());
	    	return list; 
	    }

	    // Convertir una lista de DTOs a una lista de entidades
	    public List<T> convertToEntityList(List<D> dtos, Class<T> entityClass) {
	        return dtos.stream()
	                .map(dto -> convertToEntity(dto, entityClass))
	                .collect(Collectors.toList());
	    }
	    
	    
	    // Convertir de una externalDto a una Entidad
	    public T convertExternalDtoToEntity(E externalDto, Class<T> entityClass) {
	        return objectMapper.convertValue(externalDto, entityClass);
	    }
	    
	 // Convertir una lista de externalDtos a una lista de entidades
	    public List<T> convertExternalDTOToEnityList(List<E> externalDtosList, Class<T> entityList) {
	    	List<T> list = externalDtosList.stream()
	                .map(externalDto -> convertExternalDtoToEntity(externalDto, entityList))
	                .collect(Collectors.toList());
	    	return list; 
	    }
	    
	    public List<E> convertJsonStringToExternalResponseObj(String json, Class<E> externalClass) throws JsonMappingException, JsonProcessingException{
	    	return objectMapper.readValue(json, objectMapper.getTypeFactory().constructCollectionType(List.class,externalClass)); 
	    }

}
