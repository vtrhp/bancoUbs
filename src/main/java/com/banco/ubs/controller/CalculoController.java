package com.banco.ubs.controller;

import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import com.banco.ubs.io.CargaProdutos_V2;
import com.banco.ubs.response.Response;
import com.banco.ubs.service.impl.EstoqueServiceImpl;

@RestController
@RequestMapping("/api/loja")
@CrossOrigin("*")
public class CalculoController {
	private static final Logger log = LoggerFactory.getLogger(CargaProdutos.class);

	@Autowired
	private CargaProdutos cp;
	
	@Autowired
	private CargaProdutos_V2 cp_V2;

	@Autowired
	private EstoqueServiceImpl es;

	@GetMapping(path = "/calculo/{produto}/{qtd}")
	public ResponseEntity<Response<List<LojistaDTO>>> calcula(@PathVariable("produto") String produto,
			@PathVariable("qtd") Integer qtd) {
		Response<List<LojistaDTO>> response = new Response<List<LojistaDTO>>();
		try {
			if (cp.getIsDone() == false && es.findOne().equals(Optional.empty())) {
				
				Instant startTime = Instant.now();
				//cp.cargaParalela();
				cp_V2.cargaParalela();
				Instant endTime = Instant.now();
				Duration totalTime = Duration.between(startTime, endTime);
				log.info("Tempo de execucao da carga:{}", totalTime.getSeconds());
			}

			List<EstoqueDTO> dto = converterEstoqueDTO(es.buscaPorProduto(produto));
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
