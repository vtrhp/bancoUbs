package com.banco.ubs.utils;

import org.json.simple.JSONObject;

import com.banco.ubs.entities.Estoque;

public class Utils {
	
	public static Estoque criaEstoque(JSONObject jO) {
		Estoque estoque = new Estoque();
		estoque.setProduto((String) jO.get("product"));
		estoque.setQuantidade(Integer.valueOf((String) jO.get("quantity").toString()));
		String preco = (String) jO.get("price");
		String p = preco.substring(1, preco.length());
		estoque.setPreco(Double.valueOf(p));
		estoque.setTipo((String) jO.get("type"));
		estoque.setIndustria((String) jO.get("industry"));
		estoque.setOrigem((String) jO.get("origin"));
		estoque.setVolume(Math.floor(estoque.getPreco() * estoque.getQuantidade()));
		return estoque;
	}
}
