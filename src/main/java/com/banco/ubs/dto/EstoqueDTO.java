package com.banco.ubs.dto;

public class EstoqueDTO {
	
	public EstoqueDTO() {}
	
	public EstoqueDTO(Integer quantidade, Double preco, Double volume) {
		super();
		this.quantidade = quantidade;
		this.preco = preco;
		this.volume = volume;
	}

	private Integer quantidade;
	private Double preco;
	private Double volume;

	public Integer getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
	}

	public Double getPreco() {
		return preco;
	}

	public void setPreco(Double preco) {
		this.preco = preco;
	}

	public Double getVolume() {
		return volume;
	}

	public void setVolume(Double volume) {
		this.volume = volume;
	}

}
