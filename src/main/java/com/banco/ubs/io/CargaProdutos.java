package com.banco.ubs.io;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

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

	static FileFilter filter = new FileFilter() {
		public boolean accept(File file) {
			return file.getName().endsWith(".json");
		}
	};

	public void cargaParalela() throws InterruptedException {

		ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
		  
		  Runnable task1 = () -> {
			  JSONParser jsonParser = new JSONParser();
				try {
					JSONObject jsonObject = (JSONObject) jsonParser
							.parse(new BufferedReader(new InputStreamReader(new FileInputStream(
									"D:\\DESENVOLVIMENTO\\WORKSPACES\\workspace_banco_ubs\\ubs\\src\\main\\resources\\arquivos\\data_1.json"))));
					JSONArray jsonArray = (JSONArray) jsonObject.get("data");
					for (int j = 0; j < jsonArray.size(); j++) {
						JSONObject jO = (JSONObject) jsonArray.get(j);
						Estoque est = estoqueService.persistir(criaEstoque(jO));
						log.info("Estoque criado:{}", est.toString());
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
	            try {
	                TimeUnit.SECONDS.sleep(2);
	            } catch (InterruptedException ex) {
	                throw new IllegalStateException(ex);
	            }
	        };

	        Runnable task2 = () -> {
	        	JSONParser jsonParser = new JSONParser();
				try {
					JSONObject jsonObject = (JSONObject) jsonParser
							.parse(new BufferedReader(new InputStreamReader(new FileInputStream(
									"D:\\DESENVOLVIMENTO\\WORKSPACES\\workspace_banco_ubs\\ubs\\src\\main\\resources\\arquivos\\data_2.json"))));
					JSONArray jsonArray = (JSONArray) jsonObject.get("data");
					for (int j = 0; j < jsonArray.size(); j++) {
						JSONObject jO = (JSONObject) jsonArray.get(j);
						Estoque est = estoqueService.persistir(criaEstoque(jO));
						log.info("Estoque criado:{}", est.toString());
					}

				} catch (Exception e) {
					e.printStackTrace();
				}
	            try {
	                TimeUnit.SECONDS.sleep(4);
	            } catch (InterruptedException ex) {
	                throw new IllegalStateException(ex);
	            }
	        };

	        Runnable task3 = () -> {
	        	JSONParser jsonParser = new JSONParser();
				try {
					JSONObject jsonObject = (JSONObject) jsonParser
							.parse(new BufferedReader(new InputStreamReader(new FileInputStream(
									"D:\\DESENVOLVIMENTO\\WORKSPACES\\workspace_banco_ubs\\ubs\\src\\main\\resources\\arquivos\\data_3.json"))));
					JSONArray jsonArray = (JSONArray) jsonObject.get("data");
					for (int j = 0; j < jsonArray.size(); j++) {
						JSONObject jO = (JSONObject) jsonArray.get(j);
						Estoque est = estoqueService.persistir(criaEstoque(jO));
						log.info("Estoque criado:{}", est.toString());
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
	            try {
	                TimeUnit.SECONDS.sleep(3);
	            } catch (InterruptedException ex) {
	                throw new IllegalStateException(ex);
	            }
	        };
	        
	        Runnable task4 = () -> {
	        	 
	        	JSONParser jsonParser = new JSONParser();
	        	try {
	        		JSONObject jsonObject = (JSONObject) jsonParser
	        				.parse(new BufferedReader(new InputStreamReader(new FileInputStream(
	        						"D:\\DESENVOLVIMENTO\\WORKSPACES\\workspace_banco_ubs\\ubs\\src\\main\\resources\\arquivos\\data_4.json"))));
	        		JSONArray jsonArray = (JSONArray) jsonObject.get("data");
	        		for (int j = 0; j < jsonArray.size(); j++) {
	        			JSONObject jO = (JSONObject) jsonArray.get(j);
	        			Estoque est = estoqueService.persistir(criaEstoque(jO));
	        			log.info("Estoque criado:{}", est.toString());
	        		}
	        	} catch (Exception e) {
	        		e.printStackTrace();
	        	}
	            try {
	                TimeUnit.SECONDS.sleep(5);
	            } catch (InterruptedException ex) {
	                throw new IllegalStateException(ex);
	            }
	        };
	        
	        
	        Future<?> taskStatus1 = executorService.submit(task1);
	        Future<?> taskStatus2 = executorService.submit(task2);
	        Future<?> taskStatus3 = executorService.submit(task3);
	        Future<?> taskStatus4 = executorService.submit(task4);
	        
	        
	        
	        while(!taskStatus1.isDone() || !taskStatus2.isDone() || !taskStatus3.isDone() || !taskStatus4.isDone()){
	        	  System.out.println("Task 1 and Task 2 and Task 3 and Task 4 are not yet complete....sleeping");
	        	  Thread.sleep(1000);
	        	}
	        executorService.shutdown();
	}

	public void carga() throws IOException {
		JSONParser jsonParser = new JSONParser();
		Instant startTime = Instant.now();
		try {
			File dir = new File(
					"D:\\DESENVOLVIMENTO\\WORKSPACES\\workspace_banco_ubs\\ubs\\src\\main\\resources\\arquivos");
			Long qtdArquivos = dir.length();

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
			Instant endTime = Instant.now();
			Duration totalTime = Duration.between(startTime, endTime);
			log.info("Tempo de execucao da carga:{}", totalTime.getSeconds());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private Estoque criaEstoque(JSONObject jO) {
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
