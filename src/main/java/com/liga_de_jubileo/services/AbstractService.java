package com.liga_de_jubileo.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.web.reactive.function.client.WebClient;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.liga_de_jubileo.converters.AbstractConverter;
import com.liga_de_jubileo.repository.AbstractRepository;
import com.liga_de_jubileo.services.base.BaseService;


public class AbstractService<T, D, E> extends BaseService<T, D, E>{

	private AbstractRepository<T> repository;
	
	private AbstractConverter<T> converter;


	public AbstractService(AbstractRepository<T> repository, AbstractConverter<T> converter) {
		super(WebClient.builder(), new ObjectMapper());
		this.repository = repository;
		this.converter = converter; 
	}
	
	// Estos método se utilizarán cuando el servicio de la entidad necesite consultar con nuestra bbdd
	public List<D> findAll() {
		return this.converter.convertToDTOList(this.repository.findAll(), this.getDClass()); 
	}
	
	public D findById(Long id) {
		return this.converter.convertToDTO(this.repository.findById(id), this.getDClass()); 
	}
	
	public D create(D dto) {
		this.repository.save(this.converter.convertToEntity(dto, this.getTClass())); 
		return dto; 
	}
	
	/*
	// Si los datos los tenemos que sacar desde la api del fantasy se usará esta (haré una función para que sea más facil utilizarlo)
	// * Comprendiendo la reflexión  para no tener que pasar todo por el constructor
    // Esto ya se haría en diferentes métodos en los que cogería los datos que queremos recibir a modo de DtoExternalResponse
     */
                
    protected List<D> fetchData(String urlBase, String endpoint, HttpMethod method, Map<String, String> headers,  
    		Map<String, String> params, E body){
    	
    	try {
    		E externalResponse = super.fetchData(urlBase, endpoint, method, headers, params, body, new ParameterizedTypeReference<E>() {}); 
    		return new ArrayList<D>(); 
    		
    	}catch (Exception e) {
			// TODO: handle exception
    		throw e; 
		}
    	
    	
    }
	
	
	
	
	
	/*protected D updateEntity() {
		T ent
	}*/
	
}
