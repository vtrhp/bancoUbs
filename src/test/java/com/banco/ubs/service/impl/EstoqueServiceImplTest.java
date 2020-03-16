package com.banco.ubs.service.impl;

import static org.junit.jupiter.api.Assertions.fail;

import java.util.List;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import com.banco.ubs.entities.Estoque;

@RunWith(SpringRunner.class)
@SpringBootTest
@Sql("/sql/delete_all_data.sql")
class EstoqueServiceImplTest {
	@Autowired
	private EstoqueServiceImpl estoqueService;

	@Test
	void testPersistir() {
		Estoque es = estoqueService.persistir(criaEstoque());
		Assert.assertTrue(es.getProduto().equals(("Teste")));
	}

	@Test
	void testBuscaPorProdutoQuantidadePreco() {
		fail("Not yet implemented");
	}

	@Test
	void testBuscaTodos() {
		Assert.assertTrue(!estoqueService.buscaTodos().isEmpty());
	}

	@Test
	void testBuscaPorProduto() {
		estoqueService.persistir(criaEstoque());
		List<Estoque> es = estoqueService.buscaPorProduto("Teste");
		Assert.assertTrue(!es.isEmpty());
	}

	@Test
	void testCalculaQtdPorLoja() {
		fail("Not yet implemented");
	}

	@Test
	void testFindOne() {
		estoqueService.persistir(criaEstoque());
		Assert.assertTrue(estoqueService.findOne().isPresent());
	}

	@Test
	void testFindCount() {
		estoqueService.persistir(criaEstoque());
		Assert.assertTrue(estoqueService.findCount().isPresent());
	}
	
	private Estoque criaEstoque() {
		Estoque estoque = new Estoque();
		estoque.setProduto("Teste");
		estoque.setPreco(1.0);
		estoque.setQuantidade(5);
		estoque.setVolume(10.0);
		estoque.setOrigem("SP");
		estoque.setIndustria("TESTE");
		return estoque;
	}

}
