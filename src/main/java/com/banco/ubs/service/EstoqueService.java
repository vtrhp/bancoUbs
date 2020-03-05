package com.banco.ubs.service;

import java.util.Optional;

import com.banco.ubs.entities.Estoque;

public interface EstoqueService {
	Optional<Estoque> buscarPorId(Long id);

	Estoque persistir(Estoque estoque);
}
