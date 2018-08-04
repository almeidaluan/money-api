package com.apimoney.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.apimoney.models.Lancamento;
import com.apimoney.models.Pessoa;
import com.apimoney.repository.LancamentoRepository;
import com.apimoney.repository.PessoaRepository;

@Service
public class LancamentoService {

	@Autowired
	public LancamentoRepository repository;

	@Autowired
	private PessoaRepository pessoaRepository;

	public Lancamento salvaLancamento(Lancamento lancamento) {
		Optional<Pessoa> pessoa = pessoaRepository.findById(lancamento.getPessoa().getCodigo());

		if (pessoa == null || pessoa.get().isInativo()) {
			throw new PessoaInexistenteOuInativaException();
		}

		return repository.save(lancamento);

	}

	public Lancamento buscaLancamento(long codigo) {

		Optional<Lancamento> lancamentoSalvo = repository.findById(codigo);

		if (lancamentoSalvo == null) {
			throw new EmptyResultDataAccessException(1);
		}

		return lancamentoSalvo.get();
	}
}
