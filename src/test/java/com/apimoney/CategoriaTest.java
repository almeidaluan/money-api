package com.apimoney;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import java.nio.charset.Charset;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;

import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;



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
	private MockMvc mockMvc;
	
	private CategoriaResource resource;

	  @Test
	   void methodCreateShouldReturnStatus400() throws Exception{
	        mockMvc.perform(post("/categorias")
	                .contentType(contentType))
	            .andExpect(status().isBadRequest());
	    }	
	  @Test
	  void methodFindAllShouldReturnAllCategories() throws Exception {
		  
		  
		  mockMvc.perform(get("/categorias")
				  .contentType(MediaType.APPLICATION_JSON))
		  		.andExpect(status().isOk());
		  
	  }
	  
	  @Test
	  void methodDelete() throws Exception {
		  mockMvc.perform(delete("/categorias/"+"123")).andExpect(status().isUnauthorized());
	
	  }
	  
}
