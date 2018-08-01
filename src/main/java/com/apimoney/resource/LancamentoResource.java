package com.apimoney.resource;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.apimoney.models.Lancamento;
import com.apimoney.repository.LancamentoRepository;

@RestController
@RequestMapping("/lancamentos")
public class LancamentoResource {

	@Autowired
	private LancamentoRepository repository;

	@GetMapping
	public List<Lancamento> listaLancamentos() {
		return repository.findAll();
	}

	@GetMapping("/{codigo}")
	public ResponseEntity<Lancamento> buscaLancamento(@PathVariable long codigo) {
		Optional<Lancamento> lancamentoRetornado = repository.findById(codigo);
		if (lancamentoRetornado.get() != null)
			return ResponseEntity.ok(lancamentoRetornado.get());
		else
			return ResponseEntity.notFound().build();
	}
}
