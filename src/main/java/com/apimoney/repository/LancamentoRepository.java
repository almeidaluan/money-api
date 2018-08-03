package com.apimoney.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.apimoney.models.Lancamento;

public interface LancamentoRepository extends JpaRepository<Lancamento, Long> {

}
