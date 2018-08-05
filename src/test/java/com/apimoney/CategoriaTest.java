package com.apimoney;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;

@RunWith(JUnitPlatform.class)
public class CategoriaTest {

	@BeforeAll
	static void InicializaRecursosExternos() {

		System.out.println("Inicializando Recursos externos");
	}

	@BeforeEach
	void inicializarObjetosMock() {
		System.out.println("Inicializando objetos mock...");
	}

	@Test
	void algumTeste() {
		System.out.println("Executando algum teste...");
		assertTrue(true);
	}

	@Test
	void outroTeste() {
		assumeTrue(true);
		System.out.println("Executando outro teste...");
		assertNotEquals(1, 42, "Porque estes valores não são iguais?");
	}

	@Test
	@Disabled
	void disabilitarTeste() {
		System.exit(1);
	}

	@AfterEach
	void parando() {
		System.out.println("Parando...");
	}

	@AfterAll
	static void liberarRecursosExternos() {
		System.out.println("Liberando os recursos externos...");
	}
}
