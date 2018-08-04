package com.apimoney.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.apimoney.models.Pessoa;
import com.apimoney.repository.PessoaRepository;

@Service
public class PessoaService {

	@Autowired
	private PessoaRepository repository;

	public void atualizaPropriedadeAtivo(long codigo, boolean ativo) {
		Pessoa pessoaAtualizada = buscaPeloCodigo(codigo);
		pessoaAtualizada.setAtivo(ativo);
		repository.save(pessoaAtualizada);
	}

	public Pessoa buscaPeloCodigo(long codigo) {

		Optional<Pessoa> pessoaSalva = repository.findById(codigo);

		if (pessoaSalva == null) {
			throw new EmptyResultDataAccessException(1);
		}
		return pessoaSalva.get();
	}
}
