package com.banco.ubs.service;

import java.util.List;
import java.util.Optional;

import com.banco.ubs.entities.Estoque;

public interface EstoqueService {
	
	Optional<Estoque> buscarPorId(Long id);

	Estoque persistir(Estoque estoque);
	
	Optional<Estoque> buscaPorProdutoQuantidadePreco(String produto, Integer quantidade, Double preco);
	
	List<Estoque> buscaTodos();
	
	List<Estoque> buscaPorProduto(String produto);
	
	Integer findTotalByQuantidade(String produto);
	
	Double findTotalByFinanceiro(String produto);
	
	Double findPrecoMedio(String produto);
}
