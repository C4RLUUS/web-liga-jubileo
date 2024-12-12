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
	    public <D, E> D convertToDTO(E entity, Class<D> dtoClass) {
	        return objectMapper.convertValue(entity, dtoClass);
	    }

	    // Convertir de un DTO a una entidad
	    public <E, D> E convertToEntity(D dto, Class<E> entityClass) {
	        return objectMapper.convertValue(dto, entityClass);
	    }
	    
	 // Convertir una lista de entidades a una lista de DTOs
	    public <D, E> List<D> convertToDTOList(List<E> entities, Class<D> dtoClass) {
	        return entities.stream()
	                .map(entity -> convertToDTO(entity, dtoClass))
	                .collect(Collectors.toList());
	    }

	    // Convertir una lista de DTOs a una lista de entidades
	    public <E, D> List<E> convertToEntityList(List<D> dtos, Class<E> entityClass) {
	        return dtos.stream()
	                .map(dto -> convertToEntity(dto, entityClass))
	                .collect(Collectors.toList());
	    }
	    
	    /* public <E,D> E updateEntity(D dto) {
	    } */

}
