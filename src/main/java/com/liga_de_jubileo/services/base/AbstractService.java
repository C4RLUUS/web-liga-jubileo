package com.liga_de_jubileo.services.base;

import java.util.List;
import java.util.Map;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.web.reactive.function.client.WebClient;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.liga_de_jubileo.converters.AbstractConverter;
import com.liga_de_jubileo.repository.AbstractRepository;


public class AbstractService<T, D, E> extends BaseService<T, D, E>{

	private AbstractRepository<T> repository;
	
	private AbstractConverter<T> converter;
	

	public AbstractService(WebClient.Builder webClientBuilder, AbstractRepository<T> repository, AbstractConverter<T> converter) {
		super(webClientBuilder , new ObjectMapper());
		this.repository = repository;
		this.converter = converter; 
	}
	
	// Estos método se utilizarán cuando el servicio de la entidad necesite consultar con nuestra bbdd
	public List<D> findAll() {
		return this.converter.convertToDTOList(this.repository.findAll(), this.getDClass()); 
	}
	
	public D findById(Long id) {
		T entity = this.repository.findById(id).orElseGet(null); 
		if(entity != null) {
			
			return this.converter.convertToDTO(entity, this.getDClass()); 
		}else {
			throw new RuntimeException("La entidad que quieres convertir no existe"); 
		}
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
    		List<E> externalResponse = super.fetchData(urlBase, endpoint, method, headers, params, body, new ParameterizedTypeReference<List<E>>() {});
    		List<T> enititiesList = this.converter.convertExternalDTOToEnityList(externalResponse, getTClass()); 
    		
    		return this.converter.convertToDTOList(enititiesList, getDClass()); 
    		
    	}catch (Exception e) {
			// TODO: handle exception
    		throw e; 
		}
    	
    	
    }
	
	
	
	
	
	/*protected D updateEntity() {
		T ent
	}*/
	
}
