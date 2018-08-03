package com.apimoney.exceptionHandler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
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
