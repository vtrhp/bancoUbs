package com.banco.ubs.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.OptionalDouble;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.banco.ubs.dto.EstoqueDTO;
import com.banco.ubs.dto.LojistaDTO;
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
	
	public Optional<Estoque> buscaPorProdutoQuantidadePreco(String produto, Integer quantidade, Double preco) {
		return estoqueRepository.findByProdutoAndQuantidadeAndPreco(produto, quantidade, preco);
	}
	
	@Override
	public List<Estoque> buscaTodos(){
		return estoqueRepository.findAll();
	}

	@Override
	public List<Estoque> buscaPorProduto(String produto) {
		return estoqueRepository.findByProduto(produto);
	}
	
	
	public List<LojistaDTO> calculaQtdPorLoja(List<EstoqueDTO> dto, Integer qtd ) {
		//TODO: Concluir o calculo de produtos para lojistas
		List<LojistaDTO> lojista = new ArrayList<LojistaDTO>();
		List<EstoqueDTO> listEstoqueDto = new ArrayList<EstoqueDTO>();
		Integer qtdTotal = dto.stream().mapToInt(EstoqueDTO::getQuantidade).sum();
		Double financeiro = dto.stream().mapToDouble(EstoqueDTO::getPreco).sum();
		OptionalDouble precoMedio = dto.stream().mapToDouble(EstoqueDTO::getPreco).average();
		dto.forEach(e ->{
			if(e.getQuantidade() % qtd > 0) {
				for(int i = 0; i< qtd; i++) {
					EstoqueDTO estoqueDto = new EstoqueDTO();
					if(i==qtd) {
						estoqueDto.setQuantidade(Math.floorDiv(e.getQuantidade(),qtd)-1);
						estoqueDto.setPreco(e.getPreco());
						estoqueDto.setVolume((e.getQuantidade()/qtd) * e.getPreco());
						listEstoqueDto.add(estoqueDto);
					} else {
						estoqueDto.setQuantidade(Math.floorDiv(e.getQuantidade(),qtd));
						estoqueDto.setPreco(e.getPreco());
						estoqueDto.setVolume((Math.floorDiv(e.getQuantidade(),qtd)) * e.getPreco());
						listEstoqueDto.add(estoqueDto);
					}
				}
				lojista.add(new LojistaDTO(listEstoqueDto,qtdTotal,financeiro,precoMedio ));
			} else {
				for(int i = 0; i< qtd; i++) {
					EstoqueDTO estoqueDto = new EstoqueDTO();
					estoqueDto.setQuantidade(Math.floorDiv(e.getQuantidade(),qtd));
					estoqueDto.setPreco(e.getPreco());
					estoqueDto.setVolume((Math.floorDiv(e.getQuantidade(),qtd)) * e.getPreco());
					listEstoqueDto.add(estoqueDto);
					lojista.add(new LojistaDTO(listEstoqueDto,qtdTotal,financeiro,precoMedio ));
				}
				lojista.add(new LojistaDTO(listEstoqueDto,qtdTotal,financeiro,precoMedio ));
			}
		});
		return lojista;
	}
	

}
