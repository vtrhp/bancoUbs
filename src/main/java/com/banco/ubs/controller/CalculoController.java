package com.banco.ubs.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.banco.ubs.dto.EstoqueDTO;
import com.banco.ubs.dto.LojistaDTO;
import com.banco.ubs.entities.Estoque;
import com.banco.ubs.io.CargaProdutos;
import com.banco.ubs.response.Response;
import com.banco.ubs.service.EstoqueService;
import com.banco.ubs.service.impl.EstoqueServiceImpl;

@RestController
@RequestMapping("/api/loja")
@CrossOrigin("*")
public class CalculoController {

	@Autowired
	private CargaProdutos cp;
	
	@Autowired
	private EstoqueServiceImpl es;

	@GetMapping(path = "/calculo/{produto}/{qtd}")
	public ResponseEntity<Response<LojistaDTO>> calcula(@PathVariable("produto") String produto,
			@PathVariable("qtd") Integer qtd) {
		Response<LojistaDTO> response = new Response<LojistaDTO>();
		try {
			cp.carga();
			
			List<EstoqueDTO> dto = converterEstoqueDTO(es.buscaPorProduto(produto));
			es.calculaQtdPorLoja(dto, qtd).forEach( l -> response.setData(l));
		} catch (Exception e) {
			e.printStackTrace();
		}
		//List<Estoque> es = new ArrayList<Estoque>();
		//response.setData(es);
		return ResponseEntity.ok(response);

	}
	
	private List<EstoqueDTO> converterEstoqueDTO(List<Estoque> es) {
		EstoqueDTO dto = new EstoqueDTO();
		List<EstoqueDTO> list = new ArrayList<EstoqueDTO>();
		es.stream().forEach(e -> {
			dto.setProduto(e.getProduto());
			dto.setQuantidade(e.getQuantidade());
			dto.setPreco(e.getPreco());
			dto.setVolume(e.getVolume());
			list.add(dto);
		});
		
		return list;
	}

}
