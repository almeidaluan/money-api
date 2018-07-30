package com.apimoney.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.apimoney.models.Pessoa;

public interface PessoaRepository extends JpaRepository<Pessoa, Long> {

}
