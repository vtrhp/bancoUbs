package com.banco.ubs.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "estoque")
public class Estoque implements Serializable {

	private static final long serialVersionUID = -665795381297923619L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String produto;
	private Integer quantidade;
	private Double preco;
	private String tipo;
	private String industria;
	private String origem;
	private Double volume;
	private Integer arquivo1;
	private Integer arquivo2;
	private Integer arquivo3;
	private Integer arquivo4;

	@Column(name = "id")
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "produto")
	public String getProduto() {
		return produto;
	}

	public void setProduto(String produto) {
		this.produto = produto;
	}

	@Column(name = "quantidade")
	public Integer getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
	}

	@Column(name = "preco")
	public Double getPreco() {
		return preco;
	}

	public void setPreco(Double preco) {
		this.preco = preco;
	}

	@Column(name = "tipo")
	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	@Column(name = "industria")
	public String getIndustria() {
		return industria;
	}

	public void setIndustria(String industria) {
		this.industria = industria;
	}

	@Column(name = "origem")
	public String getOrigem() {
		return origem;
	}

	public void setOrigem(String origem) {
		this.origem = origem;
	}

	@Column(name = "volume")
	public Double getVolume() {
		return volume;
	}

	public void setVolume(Double volume) {
		this.volume = volume;
	}

	public Integer getArquivo1() {
		return arquivo1;
	}

	public void setArquivo1(Integer arquivo1) {
		this.arquivo1 = arquivo1;
	}

	public Integer getArquivo2() {
		return arquivo2;
	}

	public void setArquivo2(Integer arquivo2) {
		this.arquivo2 = arquivo2;
	}

	public Integer getArquivo3() {
		return arquivo3;
	}

	public void setArquivo3(Integer arquivo3) {
		this.arquivo3 = arquivo3;
	}

	public Integer getArquivo4() {
		return arquivo4;
	}

	public void setArquivo4(Integer arquivo4) {
		this.arquivo4 = arquivo4;
	}

	@Override
	public String toString() {
		return "Estoque [id=" + id + ", produto=" + produto + ", quantidade=" + quantidade + ", preco=" + preco
				+ ", tipo=" + tipo + ", industria=" + industria + ", origem=" + origem + ", volume=" + volume + "]";
	}

}
