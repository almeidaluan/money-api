package com.apimoney.resource;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.apimoney.event.RecursoCriadoEvent;
import com.apimoney.models.Lancamento;
import com.apimoney.repository.LancamentoRepository;
import com.apimoney.service.LancamentoService;
import com.apimoney.vo.LancamentoVO;

@RestController
@RequestMapping("/lancamentos")
public class LancamentoResource {

	@Autowired
	private LancamentoRepository repository;

	@Autowired
	private ApplicationEventPublisher publisher;

	@Autowired
	private LancamentoService service;

	/**
	 * Retorna tudo caso não passe nada na QueryString
	 * 
	 * @param lancamentoVO
	 * @return
	 */
	@GetMapping
	public List<Lancamento> listaLancamentos(LancamentoVO lancamentoVO) {
		return repository.filtraPorDataDescricao(lancamentoVO);
	}

	@GetMapping("/{codigo}")
	public ResponseEntity<Lancamento> buscaLancamento(@PathVariable long codigo) {
		Lancamento lancamentoRetornado = service.buscaLancamento(codigo);
		if (lancamentoRetornado != null)
			return ResponseEntity.ok(lancamentoRetornado);
		else
			return ResponseEntity.notFound().build();
	}

	@PostMapping
	public ResponseEntity<Lancamento> salvaLancamento(@RequestBody Lancamento lancamento,
			HttpServletResponse response) {

		Lancamento lancamentoSalvo = service.salvaLancamento(lancamento);
		publisher.publishEvent(new RecursoCriadoEvent(this, response, lancamentoSalvo.getCodigo()));
		return ResponseEntity.status(HttpStatus.CREATED).body(lancamentoSalvo);

	}
}
