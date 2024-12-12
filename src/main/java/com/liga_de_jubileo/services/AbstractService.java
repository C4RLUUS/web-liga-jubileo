package com.liga_de_jubileo.services;

import java.util.List;

import com.liga_de_jubileo.converters.AbstractConverter;
import com.liga_de_jubileo.repository.AbstractRepository;

public class AbstractService<T, D> {

	private AbstractRepository<T> repository;
	
	private AbstractConverter<T> converter;
	
	private Class<D> dtoClass; 
	
	private Class<T> entityClass; 

	public AbstractService(AbstractRepository<T> repository, AbstractConverter<T> converter,Class<T> entityClass, Class<D> dtoClass) {
		super();
		this.repository = repository;
		this.converter = converter; 
		this.entityClass = entityClass; 
		this.dtoClass = dtoClass; 
	}
	
	public List<D> findAll() {
		return this.converter.convertToDTOList(this.repository.findAll(), this.dtoClass); 
	}
	
	public D findById(Long id) {
		return this.converter.convertToDTO(this.repository.findById(id), this.dtoClass); 
	}
	
	public D create(D dto) {
		this.repository.save(this.converter.convertToEntity(dto, this.entityClass)); 
		return dto; 
	}
	
	
	/*protected D updateEntity() {
		T ent
	}*/
	
}
