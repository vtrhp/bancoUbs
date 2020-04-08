package com.banco.ubs.service.impl;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.banco.ubs.dto.EstoqueDTO;
import com.banco.ubs.dto.LojistaDTO;
import com.banco.ubs.entities.Estoque;
import com.banco.ubs.io.CargaBancoDeDados;
import com.banco.ubs.io.CargaEstoque;
import com.banco.ubs.io.CargaEstoqueEmMemoria;
import com.banco.ubs.repository.EstoqueRepository;
import com.banco.ubs.service.EstoqueService;
import com.banco.ubs.utils.Utils;

@Service
public class EstoqueServiceImpl implements EstoqueService {

	private static final Logger log = LoggerFactory.getLogger(EstoqueServiceImpl.class);

	@Autowired
	private EstoqueRepository estoqueRepository;

	@Autowired
	private CargaEstoque cargaEstoque;

	@Autowired
	private CargaEstoqueEmMemoria cargaEstoqueEmMemoria;
	
	@Autowired
	private CargaBancoDeDados cargaBancoDeDados;

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

	public List<Estoque> buscaPorProdutoEmMemoria(String produto) {
		return carregaEmMemoria()
				.stream()
				.distinct()
				.filter(e -> e.getProduto().equals(produto))
				.collect(Collectors.toList());
	}

	public List<LojistaDTO> calculaQtdPorLoja(List<EstoqueDTO> dto, String produto, Integer qtd) {
		List<LojistaDTO> lojista = new ArrayList<LojistaDTO>();
		List<EstoqueDTO> listEstoqueDto = new ArrayList<EstoqueDTO>();
		dto.stream().distinct().forEach(e -> {
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
				lojista.add(
						new LojistaDTO(listEstoqueDto, listEstoqueDto.stream().mapToInt(i -> i.getQuantidade()).sum(),
								listEstoqueDto.stream().mapToDouble(i -> i.getVolume()).sum(),
								listEstoqueDto.stream().mapToDouble(i -> i.getPreco()).average()));
			} else {
				for (int i = 0; i < qtd; i++) {
					EstoqueDTO estoqueDto = new EstoqueDTO();
					estoqueDto.setQuantidade(Math.floorDiv(e.getQuantidade(), qtd));
					estoqueDto.setPreco(e.getPreco());
					estoqueDto.setVolume((Math.floorDiv(e.getQuantidade(), qtd)) * e.getPreco());
					listEstoqueDto.add(estoqueDto);
				}
				lojista.add(
						new LojistaDTO(listEstoqueDto, listEstoqueDto.stream().mapToInt(i -> i.getQuantidade()).sum(),
								listEstoqueDto.stream().mapToDouble(i -> i.getVolume()).sum(),
								listEstoqueDto.stream().mapToDouble(i -> i.getPreco()).average()));
			}
		});
		return lojista;
	}

	private List<Estoque> carregaEmMemoria() {
		List<Estoque> listaEstoque = new ArrayList<Estoque>();
		Instant startTime = Instant.now();
		for (Object jsonObject : cargaEstoqueEmMemoria.carregaJsons()) {
			listaEstoque.add(Utils.criaEstoque((JSONObject) jsonObject));
		}
		Instant endTime = Instant.now();
		Duration totalTime = Duration.between(startTime, endTime);
		log.info("Tempo de execucao da carga em memoria: {} segudos", totalTime.getSeconds());
		try {
			cargaBancoDeDados.criaThreads(listaEstoque);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return listaEstoque;
	}

	@Override
	public Optional<Estoque> findOne() {
		return estoqueRepository.findOne();
	}

	@Override
	public Optional<Long> findCount() {
		return estoqueRepository.findCount();
	}
}