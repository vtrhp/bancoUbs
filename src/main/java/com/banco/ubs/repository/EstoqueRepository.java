package com.banco.ubs.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.banco.ubs.entities.Estoque;

public interface EstoqueRepository extends JpaRepository<Estoque, Long>{
	@Transactional(readOnly = true)
	Optional<Estoque> findById(Long id);

}
