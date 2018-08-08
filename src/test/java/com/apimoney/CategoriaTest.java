package com.apimoney;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import java.io.IOException;
import java.nio.charset.Charset;
import org.springframework.web.context.WebApplicationContext;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import org.hamcrest.core.Is;
import org.junit.Before;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.mock.http.MockHttpOutputMessage;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import com.apimoney.models.Categoria;
import com.apimoney.resource.CategoriaResource;

/**
 * Classe de Testes categoria
 * 
 * @author luan Almeida
 *
 */
@ExtendWith(SpringExtension.class)
@DisplayName("Testes da classe Categoria")
@SpringBootTest
@AutoConfigureMockMvc
public class CategoriaTest {

	private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
			MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));
	
	@Autowired
	private CategoriaResource resource;

	@Autowired
	private MockMvc mockMvc;

	  @Test
	    public void methodCreateShouldReturnStatus400() throws Exception{
	        mockMvc.perform(post("/categorias")
	                .contentType(contentType))
	            .andExpect(status().isBadRequest());
	    }	
}
