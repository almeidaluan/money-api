package com.apimoney.resource;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.apimoney.event.RecursoCriadoEvent;
import com.apimoney.models.Categoria;
import com.apimoney.repository.CategoriaRepository;

@RestController
@RequestMapping("/categorias")
public class CategoriaResource {

	@Autowired
	private CategoriaRepository categoriaRepository;

	@Autowired
	private ApplicationEventPublisher publisher;

	@GetMapping
	public List<Categoria> listaDeCategorias() {

		List<Categoria> listaCategorias = this.categoriaRepository.findAll();
		return listaCategorias;
	}

	@PostMapping
	public ResponseEntity<Categoria> salvaCategoria(@Valid @RequestBody Categoria categoria,
			HttpServletResponse response) {

		Categoria categoriaSalva = categoriaRepository.save(categoria);

		publisher.publishEvent(new RecursoCriadoEvent(this, response, categoriaSalva.getCodigo()));

		// form antiga sem publisher / caso queira a forma antiga é so color do listener
		// a parte do location
		// return ResponseEntity.created(uri).body(categoriaSalva);
		return ResponseEntity.status(HttpStatus.CREATED).body(categoriaSalva);
	}

	@GetMapping("/{codigo}")
	public ResponseEntity<Categoria> buscaCategoriaPorId(@PathVariable Long codigo) {
		Optional<Categoria> categoriaRetornada = categoriaRepository.findById(codigo);
		return categoriaRetornada != null ? ResponseEntity.ok(categoriaRetornada.get())
				: ResponseEntity.notFound().build();
	}

	@DeleteMapping("/{codigo}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deletaCategoria(@PathVariable Long codigo) {
		Optional<Categoria> categoriaRetornada = categoriaRepository.findById(codigo);
		categoriaRepository.delete(categoriaRetornada.get());

	}
}
