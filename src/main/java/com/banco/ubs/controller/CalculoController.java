package com.banco.ubs.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.banco.ubs.dto.EstoqueDTO;
import com.banco.ubs.dto.LojistaDTO;
import com.banco.ubs.entities.Estoque;
import com.banco.ubs.response.Response;
import com.banco.ubs.service.impl.EstoqueServiceImpl;

@RestController
@RequestMapping("/api/loja")
@CrossOrigin("*")
@SpringBootApplication
public class CalculoController {
	
	private static final Logger log = LoggerFactory.getLogger(CalculoController.class);

	@Autowired
	private EstoqueServiceImpl es;

	@GetMapping(path = "/calculo/{produto}/{qtd}")
	public ResponseEntity<Response<List<LojistaDTO>>> calcula(@PathVariable("produto") String produto,
			@PathVariable("qtd") Integer qtd) {
		Response<List<LojistaDTO>> response = new Response<List<LojistaDTO>>();
		try {
			
			List<Estoque> listaEstoque = es.buscaPorProdutoEmMemoria(produto);
				
			List<EstoqueDTO> dto = converterEstoqueDTO(listaEstoque);
			response.setData(es.calculaQtdPorLoja(dto, produto, qtd));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ResponseEntity.ok(response);
	}

	private List<EstoqueDTO> converterEstoqueDTO(List<Estoque> es) {
		List<EstoqueDTO> list = es.stream()
		.map( e -> new EstoqueDTO(e.getQuantidade(), e.getPreco(), e.getVolume()))
		.collect(Collectors.toList());
		return list;
	}

}