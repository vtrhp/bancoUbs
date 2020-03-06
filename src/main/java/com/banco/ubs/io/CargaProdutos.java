package com.banco.ubs.io;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import org.json.JSONObject;
import org.json.simple.parser.JSONParser;

import com.banco.ubs.entities.Estoque;
import com.banco.ubs.service.impl.EstoqueServiceImpl;

public class CargaProdutos {

	public static void main(String[] args) throws IOException {
		JSONObject jsonObject;
	     JSONParser parser = new JSONParser();
	     Estoque estoque = new Estoque();
	     EstoqueServiceImpl estoqueService;
	     StringBuilder json = new StringBuilder();
	        try {
	        	estoqueService = new EstoqueServiceImpl();
	        	File dir = new File("D:\\DESENVOLVIMENTO\\WORKSPACES\\workspace_banco_ubs\\ubs\\src\\main\\resources\\arquivos");
	        	File[] files = dir.listFiles(filter);
	        	for(int i = 0; i<files.length; i++) {
	             BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(files[i])));
	            
	             String linha = br.readLine();

	             while(linha != null) {
	                 System.out.println(linha);
	                 jsonObject = (JSONObject) parser.parse(linha.toString());
	                 estoque.setProduto((String) jsonObject.get("product"));
	                 estoque.setQuantidade(Integer.valueOf((String)jsonObject.get("quantity")));
	                 estoque.setPreco(Double.valueOf((String) jsonObject.get("price")));
	                 estoque.setTipo((String) jsonObject.get("type"));
	                 estoque.setIndustria((String) jsonObject.get("industry"));
	                 estoque.setOrigem((String) jsonObject.get("origin"));
	                 estoqueService.persistir(estoque);
	             }
	        	}
	             } catch (Exception e) {
	            	 e.printStackTrace();
				}
	}

	static FileFilter filter = new FileFilter() {
		public boolean accept(File file) {
			return file.getName().endsWith(".json");
		}
	};
}
