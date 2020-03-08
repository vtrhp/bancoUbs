package com.banco.ubs.dto;

import java.util.List;

public class LojistaDTO {
	
	public LojistaDTO(List<EstoqueDTO> estoqueDTO, Integer qtdTotal, Double financeiro, Double precoMedio) {
		super();
		this.estoqueDTO = estoqueDTO;
		this.qtdTotal = qtdTotal;
		this.financeiro = financeiro;
		this.precoMedio = precoMedio;
	}

	private List<EstoqueDTO> estoqueDTO;
	private Integer qtdTotal;
	private Double financeiro;
	private Double precoMedio;

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

	public Double getPrecoMedio() {
		return precoMedio;
	}

	public void setPrecoMedio(Double precoMedio) {
		this.precoMedio = precoMedio;
	}

}
