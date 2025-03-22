package com.liga_de_jubileo.converters;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class AbstractConverter<T> {
	
		@Autowired
	    private ObjectMapper objectMapper;

	    // Inyectar el ObjectMapper configurado
	    /*public AbstractConverter(ObjectMapper objectMapper) {
	        this.objectMapper = objectMapper;
	    }*/

	    // Convertir de una entidad a un DTO
	    public <D> D convertToDTO(T entity, Class<D> dtoClass) {
	        return objectMapper.convertValue(entity, dtoClass);
	    }

	    // Convertir de un DTO a una entidad
	    public <D> T convertToEntity(D dto, Class<T> entityClass) {
	        return objectMapper.convertValue(dto, entityClass);
	    }
	    
	 // Convertir una lista de entidades a una lista de DTOs
	    public <D> List<D> convertToDTOList(List<T> entities, Class<D> dtoClass) {
	        return entities.stream()
	                .map(entity -> convertToDTO(entity, dtoClass))
	                .collect(Collectors.toList());
	    }

	    // Convertir una lista de DTOs a una lista de entidades
	    public <D> List<T> convertToEntityList(List<D> dtos, Class<T> entityClass) {
	        return dtos.stream()
	                .map(dto -> convertToEntity(dto, entityClass))
	                .collect(Collectors.toList());
	    }
	    
	    
	    // Convertir de una externalDto a una Entidad
	    public <E> T convertExternalDtoToEntity(E externalDto, Class<T> entityClass) {
	        return objectMapper.convertValue(externalDto, entityClass);
	    }
	    
	 // Convertir una lista de externalDtos a una lista de entidades
	    public <E> List<T> convertExternalDTOToEnityList(List<E> externalDtosList, Class<T> entityList) {
	        return externalDtosList.stream()
	                .map(entity -> convertExternalDtoToEntity(externalDtosList, entityList))
	                .collect(Collectors.toList());
	    }

}
