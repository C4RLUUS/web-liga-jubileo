package com.liga_de_jubileo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean 
public interface AbstractRepository<T> extends JpaRepository<T, Long> {

}
