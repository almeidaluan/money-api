package com.apimoney.resource;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.apimoney.event.RecursoCriadoEvent;
import com.apimoney.models.Pessoa;
import com.apimoney.repository.PessoaRepository;

@RestController
@RequestMapping("/pessoas")
public class PessoaResource {

	@Autowired
	private PessoaRepository repository;
	
	@Autowired
	private ApplicationEventPublisher publisher;

	@GetMapping
	@Cacheable(value = "listaPessoas")
	public List<Pessoa> listaPessoas() {
		List<Pessoa> listaDePessoas = repository.findAll();
		return listaDePessoas;
	}

	/**
	 * @param pessoa
	 * @param response
	 * @return
	 */
	@PostMapping
	public ResponseEntity<Pessoa> salvaPessoa(@RequestBody Pessoa pessoa, HttpServletResponse response) {
		
		Pessoa pessoaSalva = repository.save(pessoa);
		
		publisher.publishEvent(new RecursoCriadoEvent(this, response,pessoa.getCodigo()));
		
		return ResponseEntity.status(HttpStatus.CREATED).body(pessoaSalva);
	}

	@GetMapping("/{codigo}")
	public ResponseEntity<Pessoa> buscaPeloCodigo(@PathVariable long codigo) {
		Optional<Pessoa> pessoa = repository.findById(codigo);
		if (pessoa != null)
			return ResponseEntity.ok(pessoa.get());
		else
			return ResponseEntity.notFound().build();
	}

	/**
	 * Pega uma pessoa faz uma copia com BeanUtils ignorando o codigo que permanece
	 * o mesmo
	 * 
	 * @param codigo
	 * @param pessoa
	 * @return
	 */
	@PutMapping("/{codigo}")
	public ResponseEntity<Pessoa> atualizar(@PathVariable long codigo, @Valid @RequestBody Pessoa pessoa) {

		Optional<Pessoa> pessoaSalva = repository.findById(codigo);

		if (pessoaSalva == null) {
			throw new EmptyResultDataAccessException(1);
		}

		BeanUtils.copyProperties(pessoa, pessoaSalva.get(), "codigo");
		repository.save(pessoaSalva.get());
		return ResponseEntity.ok(pessoaSalva.get());
	}

	@PatchMapping("/{codigo}/ativo")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void atualizaAtivo(@PathVariable long codigo, @RequestBody Boolean ativo) {
		Optional<Pessoa> pessoaSalva = repository.findById(codigo);
		pessoaSalva.get().setAtivo(ativo);
		repository.save(pessoaSalva.get());
	}
}
