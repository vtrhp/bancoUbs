package com.banco.ubs.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.banco.ubs.entities.Estoque;
import com.banco.ubs.repository.EstoqueRepository;
import com.banco.ubs.service.EstoqueService;

@Service
public class EstoqueServiceImpl implements EstoqueService{
	@Autowired
	private EstoqueRepository estoqueRepository;

	@Override
	public Optional<Estoque> buscarPorId(Long id) {
		return estoqueRepository.findById(id);
	}

	@Override
	public Estoque persistir(Estoque estoque) {
		return estoqueRepository.save(estoque);
	}

}
