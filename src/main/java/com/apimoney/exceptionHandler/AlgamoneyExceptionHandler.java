package com.apimoney.exceptionHandler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.NoSuchElementException;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.apimoney.service.PessoaInexistenteOuInativaException;

/**
 * Classe para capturar Exceptions na Aplicação
 * 
 * @author luan
 *
 */

/**
 * ControllerAdvice olha pra toda aplicacao / tem que passar a mensagem no
 * properties mensagem.invalida=Mensagem inv\u00E1lida para funcionar lembrar
 * LocaleContextHolder.getLocale() config no application
 * 
 * @author luan
 *
 */
@ControllerAdvice
public class AlgamoneyExceptionHandler extends ResponseEntityExceptionHandler {

	@Autowired
	private MessageSource messageSource;

	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {

		String mensagemUsuario = messageSource.getMessage("mensagem.invalida", null, Locale.US);

		String mensagemDesenvolvedor = ex.getCause().getMessage();

		List<Erro> erros = Arrays.asList(new Erro(mensagemDesenvolvedor, mensagemUsuario));

		return handleExceptionInternal(ex, erros, headers, HttpStatus.BAD_REQUEST, request);
	}

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {

		List<Erro> listaDeErros = criaListaErros(ex.getBindingResult());

		return handleExceptionInternal(ex, listaDeErros, headers, HttpStatus.BAD_REQUEST, request);
	}

	/**
	 * Exceção criada para tratar a segunda tentativa de deletar um recurso depois
	 * dele ja ter sido deletado // NoSuch voce pega no console na tentativa de
	 * deletar novamente lança a Exception // lembre-se de nao ter nada no body do
	 * postman para não causar erro e lançar a exception tratada
	 * 
	 * @param ex
	 * @param request
	 * @return
	 */
	@ExceptionHandler({ NoSuchElementException.class, EmptyResultDataAccessException.class })
	public ResponseEntity<Object> handleNoSuchElementException(NoSuchElementException ex, WebRequest request) {

		String mensagemUsuario = messageSource.getMessage("recurso.nao-encontrado", null, Locale.US);

		String mensagemDesenvolvedor = ex.toString();

		List<Erro> erros = Arrays.asList(new Erro(mensagemDesenvolvedor, mensagemUsuario));

		return handleExceptionInternal(ex, erros, new HttpHeaders(), HttpStatus.NOT_FOUND, request);

	}

	/**
	 * Quando for feito um POST de lancamento caso não tenha os ids corretos dos
	 * dados cadastrados no banco(pessoa,categoria) vai ser lançado uma Exception de
	 * {@link DataIntegrityViolationException}
	 * 
	 * @param ex
	 * @param request
	 * @return
	 */
	@ExceptionHandler({ DataIntegrityViolationException.class })
	public ResponseEntity<Object> handleDataIntegrityViolationException(DataIntegrityViolationException ex,
			WebRequest request) {

		String mensagemUsuario = messageSource.getMessage("recurso.operacao-nao-permitida", null, Locale.US);
		String mensagemDesenvolvedor = ExceptionUtils.getRootCauseMessage(ex);

		List<Erro> erros = Arrays.asList(new Erro(mensagemDesenvolvedor, mensagemUsuario));
		return handleExceptionInternal(ex, erros, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
	}

	@ExceptionHandler({ PessoaInexistenteOuInativaException.class })
	public ResponseEntity<Object> handlePessoaInexistenteOuInativaException(PessoaInexistenteOuInativaException ex,
			WebRequest request) {

		String mensagemUsuario = messageSource.getMessage("recurso.inexistente-ou-inativa", null, Locale.US);
		String mensagemDesenvolvedor = ExceptionUtils.getRootCauseMessage(ex);

		List<Erro> erros = Arrays.asList(new Erro(mensagemDesenvolvedor, mensagemUsuario));
		return handleExceptionInternal(ex, erros, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
	}

	private List<Erro> criaListaErros(BindingResult ex) {

		List<Erro> erros = new ArrayList<>();

		for (FieldError fieldError : ex.getFieldErrors()) {

			String mensagemUsuario = messageSource.getMessage(fieldError, Locale.US);
			String mensagemDesenvolvedor = fieldError.toString();

			erros.add(new Erro(mensagemDesenvolvedor, mensagemUsuario));
		}

		return erros;
	}

	public static class Erro {
		private String mensagemDesenvolvedor;
		private String mensagemUsuario;

		public String getMensagemDesenvolvedor() {
			return mensagemDesenvolvedor;

		}

		private Erro(String mensagemDesenvolvedor, String mensagemUsuario) {
			this.mensagemDesenvolvedor = mensagemDesenvolvedor;
			this.mensagemUsuario = mensagemUsuario;

		}

		public void setMensagemDesenvolvedor(String mensagemDesenvolvedor) {
			this.mensagemDesenvolvedor = mensagemDesenvolvedor;
		}

		public String getMensagemUsuario() {
			return mensagemUsuario;
		}

		public void setMensagemUsuario(String mensagemUsuario) {
			this.mensagemUsuario = mensagemUsuario;
		}

	}
}
