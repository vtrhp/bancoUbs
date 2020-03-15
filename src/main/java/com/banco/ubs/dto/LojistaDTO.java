package com.banco.ubs.dto;

import java.util.List;
import java.util.OptionalDouble;

public class LojistaDTO {
	
	public LojistaDTO(List<EstoqueDTO> estoqueDTO, Integer qtdTotal, Double financeiro, OptionalDouble precoMedio) {
		super();
		this.estoqueDTO = estoqueDTO;
		this.qtdTotal = qtdTotal;
		this.financeiro = financeiro;
		this.precoMedio = precoMedio;
	}
	private String loja;
	private List<EstoqueDTO> estoqueDTO;
	private Integer qtdTotal;
	private Double financeiro;
	private OptionalDouble precoMedio;
	
	public String getLoja() {
		return loja;
	}

	public void setLoja(String loja) {
		this.loja = loja;
	}

	public List<EstoqueDTO> getEstoqueDTO() {
		return estoqueDTO;
	}

	public void setEstoqueDTO(List<EstoqueDTO> estoqueDTO) {
		this.estoqueDTO = estoqueDTO;
	}

	public Integer getQtdTotal() {
		return qtdTotal;
	}

	public void setQtdTotal(Integer qtdTotal) {
		this.qtdTotal = qtdTotal;
	}

	public Double getFinanceiro() {
		return financeiro;
	}

	public void setFinanceiro(Double financeiro) {
		this.financeiro = financeiro;
	}

	public OptionalDouble getPrecoMedio() {
		return precoMedio;
	}

	public void setPrecoMedio(OptionalDouble precoMedio) {
		this.precoMedio = precoMedio;
	}

}
