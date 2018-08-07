package com.apimoney;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 * Classe de Testes categoria
 * 
 * @author luan Almeida
 *
 */
@ExtendWith(SpringExtension.class)
@DisplayName("Testes da classe Categoria")
@SpringBootTest
public class CategoriaTest {

	@Test
	@DisplayName("Teste do fodase")
	void fodase() {
		System.out.println("Teste do fodase");
	}
}
