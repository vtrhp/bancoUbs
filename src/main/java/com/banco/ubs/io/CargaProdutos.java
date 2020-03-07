package com.banco.ubs.io;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.banco.ubs.entities.Estoque;
import com.banco.ubs.service.EstoqueService;
@Component
public class CargaProdutos {
	private static final Logger log = LoggerFactory.getLogger(CargaProdutos.class);
	@Autowired
	private EstoqueService estoqueService;
	
	public  void carga() throws IOException {
		JSONParser jsonParser = new JSONParser();
		try {
			File dir = new File(
					"D:\\DESENVOLVIMENTO\\WORKSPACES\\workspace_banco_ubs\\ubs\\src\\main\\resources\\arquivos");
			File[] files = dir.listFiles(filter);
			for (int i = 0; i < files.length; i++) {

				JSONObject jsonObject = (JSONObject) jsonParser
						.parse(new BufferedReader(new InputStreamReader(new FileInputStream(files[i]))));
				JSONArray jsonArray = (JSONArray) jsonObject.get("data");
				for (int j = 0; j < jsonArray.size(); j++) {
					JSONObject jO = (JSONObject) jsonArray.get(j);
					Estoque est = estoqueService.persistir(criaEstoque(jO));
					log.info("Estoque criado:{}", est.toString());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private Estoque criaEstoque(JSONObject jO) {
		Estoque estoque = new Estoque(); 
		estoque.setProduto((String) jO.get("product"));
		estoque.setQuantidade(Integer.valueOf((String) jO.get("quantity").toString()));
		String preco = (String)jO.get("price");
		String p = preco.substring(1, preco.length());
		estoque.setPreco(Double.valueOf(p));
		estoque.setTipo((String) jO.get("type"));
		estoque.setIndustria((String) jO.get("industry"));
		estoque.setOrigem((String) jO.get("origin"));
		estoque.setVolume(Math.floor(estoque.getPreco() * estoque.getQuantidade()));
		return estoque;
	}

	static FileFilter filter = new FileFilter() {
		public boolean accept(File file) {
			return file.getName().endsWith(".json");
		}
	};

}
