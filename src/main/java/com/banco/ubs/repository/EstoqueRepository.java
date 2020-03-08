package com.banco.ubs.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.banco.ubs.entities.Estoque;

@Repository
public interface EstoqueRepository extends JpaRepository<Estoque, Long> {
	@Transactional(readOnly = true)
	Optional<Estoque> findById(Long id);

	@Query(value = "SELECT * FROM ESTOQUE e WHERE e.produto = ? and e.quantidade = ? and e.preco = ?", nativeQuery = true)
	Optional<Estoque> findByProdutoAndQuantidadeAndPreco(String produto, Integer quantidade, Double preco);
	
	List<Estoque> findAll();
	
	@Query(value = "SELECT * FROM ESTOQUE e WHERE e.produto = ?", nativeQuery = true)
	List<Estoque> findByProduto(String produto);
	
	@Query(value = "SELECT * FROM ESTOQUE LIMIT 1", nativeQuery = true)
	Optional<Estoque> findOne();
}
