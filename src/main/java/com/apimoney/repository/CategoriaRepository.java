package com.apimoney.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.apimoney.models.Categoria;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {

}
