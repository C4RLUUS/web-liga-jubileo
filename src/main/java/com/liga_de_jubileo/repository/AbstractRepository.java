package com.liga_de_jubileo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AbstractRepository<T> extends JpaRepository<T, Long> {

}
