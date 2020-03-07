package com.banco.ubs.io;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Iterator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.banco.ubs.entities.Estoque;
import com.banco.ubs.service.impl.EstoqueServiceImpl;

public class CargaProdutos {

	public static void main(String[] args) throws IOException {
		Estoque estoque = new Estoque();
		EstoqueServiceImpl estoqueService;
		JSONParser jsonParser = new JSONParser();

		try {
			estoqueService = new EstoqueServiceImpl();

			File dir = new File(
					"D:\\DESENVOLVIMENTO\\WORKSPACES\\workspace_banco_ubs\\ubs\\src\\main\\resources\\arquivos");
			File[] files = dir.listFiles(filter);

			for (int i = 0; i < files.length; i++) {

				JSONObject jsonObject = (JSONObject) jsonParser
						.parse(new BufferedReader(new InputStreamReader(new FileInputStream(files[i]))));

				JSONArray jsonArray = (JSONArray) jsonObject.get("data");

				for (int j = 0; j < jsonArray.size(); j++) {
					JSONObject jO = (JSONObject) jsonArray.get(i);

					estoque.setProduto((String) jO.get("product"));
					estoque.setQuantidade(Integer.valueOf((String) jO.get("quantity").toString()) );
					String preco = (String)jO.get("price");
					String p = preco.substring(1, preco.length());
					estoque.setPreco(Double.valueOf(p));
					estoque.setTipo((String) jO.get("type"));
					estoque.setIndustria((String) jO.get("industry"));
					estoque.setOrigem((String) jO.get("origin"));
					estoqueService.persistir(estoque);
				}

				Iterator<String> iterator = jsonArray.iterator();
				while (iterator.hasNext()) {
					System.out.println(iterator.next());
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
