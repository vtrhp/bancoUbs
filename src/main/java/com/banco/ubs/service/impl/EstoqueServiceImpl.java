package com.banco.ubs.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.banco.ubs.dto.EstoqueDTO;
import com.banco.ubs.dto.LojistaDTO;
import com.banco.ubs.entities.Estoque;
import com.banco.ubs.repository.EstoqueRepository;
import com.banco.ubs.service.EstoqueService;

@Service
public class EstoqueServiceImpl implements EstoqueService {

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
	public List<Estoque> buscaTodos() {
		return estoqueRepository.findAll();
	}

	@Override
	public List<Estoque> buscaPorProduto(String produto) {
		return estoqueRepository.findByProduto(produto);
	}

	public List<LojistaDTO> calculaQtdPorLoja(List<EstoqueDTO> dto, String produto, Integer qtd) {
		List<LojistaDTO> lojista = new ArrayList<LojistaDTO>();
		List<EstoqueDTO> listEstoqueDto = new ArrayList<EstoqueDTO>();
		Integer qtdTotal = this.findTotalByQuantidade(produto);
		Double financeiro = this.findTotalByFinanceiro(produto);
		Double precoMedio = this.findPrecoMedio(produto);
		dto.forEach(e -> {
			if (e.getQuantidade() % qtd > 0) {
				for (int i = 0; i < qtd; i++) {
					EstoqueDTO estoqueDto = new EstoqueDTO();
					if (i == qtd) {
						estoqueDto.setQuantidade(Math.floorDiv(e.getQuantidade(), qtd) - 1);
						estoqueDto.setPreco(e.getPreco());
						estoqueDto.setVolume((e.getQuantidade() / qtd) * e.getPreco());
						listEstoqueDto.add(estoqueDto);
					} else {
						estoqueDto.setQuantidade(Math.floorDiv(e.getQuantidade(), qtd));
						estoqueDto.setPreco(e.getPreco());
						estoqueDto.setVolume((Math.floorDiv(e.getQuantidade(), qtd)) * e.getPreco());
						listEstoqueDto.add(estoqueDto);
					}
				}
				lojista.add(new LojistaDTO(listEstoqueDto, qtdTotal, financeiro, precoMedio));
			} else {
				for (int i = 0; i < qtd; i++) {
					EstoqueDTO estoqueDto = new EstoqueDTO();
					estoqueDto.setQuantidade(Math.floorDiv(e.getQuantidade(), qtd));
					estoqueDto.setPreco(e.getPreco());
					estoqueDto.setVolume((Math.floorDiv(e.getQuantidade(), qtd)) * e.getPreco());
					listEstoqueDto.add(estoqueDto);
					lojista.add(new LojistaDTO(listEstoqueDto, qtdTotal, financeiro, precoMedio));
				}
				lojista.add(new LojistaDTO(listEstoqueDto, qtdTotal, financeiro, precoMedio));
			}
		});
		return lojista;
	}

	@Override
	public Integer findTotalByQuantidade(String produto) {
		return estoqueRepository.findTotalByQuantidade(produto);
	}

	@Override
	public Double findTotalByFinanceiro(String produto) {
		return estoqueRepository.findTotalByFinanceiro(produto);
	}

	@Override
	public Double findPrecoMedio(String produto) {
		return estoqueRepository.findPrecoMedio(produto);
	}

	@Override
	public Integer findMaxLinhaArquivo1() {
		return estoqueRepository.findMaxLinhaArquivo1();
	}

	@Override
	public Integer findMaxLinhaArquivo2() {
		return estoqueRepository.findMaxLinhaArquivo2();
	}

	@Override
	public Integer findMaxLinhaArquivo3() {
		return estoqueRepository.findMaxLinhaArquivo3();
	}

	@Override
	public Integer findMaxLinhaArquivo4() {
		return estoqueRepository.findMaxLinhaArquivo4();
	}

}
