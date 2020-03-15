package com.banco.ubs.io;

import java.io.IOException;
import java.nio.CharBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.EnumSet;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.banco.ubs.entities.Estoque;
import com.banco.ubs.service.EstoqueService;

@Component
public class CargaEstoque {
	private static final Logger log = LoggerFactory.getLogger(CargaEstoque.class);

	@Autowired
	private EstoqueService estoqueService;

	private Boolean isDone = false;

	public void criaThreads() throws InterruptedException {

		int arq1 = estoqueService.findCount().isPresent() ? (int) estoqueService.findCount().get().intValue() : 0;

		Runnable task1 = () -> {
			CharBuffer charBuffer = null;
			Path pathToRead = null;
			try {
				pathToRead = Paths.get(
						"D:\\DESENVOLVIMENTO\\WORKSPACES\\workspace_banco_ubs\\ubs\\src\\main\\resources\\arquivos\\data_1.json");
			} catch (Exception e1) {
				e1.printStackTrace();
			}

			try (FileChannel fileChannel = (FileChannel) Files.newByteChannel(pathToRead,
					EnumSet.of(StandardOpenOption.READ))) {

				MappedByteBuffer mappedByteBuffer = fileChannel.map(FileChannel.MapMode.READ_ONLY, 0,
						fileChannel.size());

				if (mappedByteBuffer != null) {
					charBuffer = Charset.forName("UTF-8").decode(mappedByteBuffer);
				}
				JSONParser jsonParser = new JSONParser();
				JSONObject jsonObject = (JSONObject) jsonParser.parse(charBuffer.toString());
				JSONArray jsonArray = (JSONArray) jsonObject.get("data");

				if(estoqueService.findOne().isPresent() && getIsDone()) {

					for (int j = arq1; j < jsonArray.size(); j++) {
						JSONObject jO = (JSONObject) jsonArray.get(j);
						Estoque est = estoqueService.persistir(criaEstoque(jO));
						log.info("Estoque criado:{}", est.toString());
					}

				} else {

					for (int j = 0; j < jsonArray.size(); j++) {
						JSONObject jO = (JSONObject) jsonArray.get(j);
						Estoque est = estoqueService.persistir(criaEstoque(jO));
						log.info("Estoque criado:{}", est.toString());
					}

				}
			} catch (IOException | ParseException e1) {
				e1.printStackTrace();
			}

		};
		
		Runnable task2 = () -> {
			CharBuffer charBuffer = null;
			Path pathToRead = null;
			try {
				pathToRead = Paths.get(
						"D:\\DESENVOLVIMENTO\\WORKSPACES\\workspace_banco_ubs\\ubs\\src\\main\\resources\\arquivos\\data_2.json");
			} catch (Exception e1) {
				e1.printStackTrace();
			}

			try (FileChannel fileChannel = (FileChannel) Files.newByteChannel(pathToRead,
					EnumSet.of(StandardOpenOption.READ))) {

				MappedByteBuffer mappedByteBuffer = fileChannel.map(FileChannel.MapMode.READ_ONLY, 0,
						fileChannel.size());

				if (mappedByteBuffer != null) {
					charBuffer = Charset.forName("UTF-8").decode(mappedByteBuffer);
				}
				JSONParser jsonParser = new JSONParser();
				JSONObject jsonObject = (JSONObject) jsonParser.parse(charBuffer.toString());
				JSONArray jsonArray = (JSONArray) jsonObject.get("data");

				if(estoqueService.findOne().isPresent() && getIsDone()) {

					for (int j = arq1; j < jsonArray.size(); j++) {
						JSONObject jO = (JSONObject) jsonArray.get(j);
						Estoque est = estoqueService.persistir(criaEstoque(jO));
						log.info("Estoque criado:{}", est.toString());
					}

				} else {

					for (int j = 0; j < jsonArray.size(); j++) {
						JSONObject jO = (JSONObject) jsonArray.get(j);
						Estoque est = estoqueService.persistir(criaEstoque(jO));
						log.info("Estoque criado:{}", est.toString());
					}

				}
			} catch (IOException | ParseException e1) {
				e1.printStackTrace();
			}

		};
		
		Runnable task3 = () -> {
			CharBuffer charBuffer = null;
			Path pathToRead = null;
			try {
				pathToRead = Paths.get(
						"D:\\DESENVOLVIMENTO\\WORKSPACES\\workspace_banco_ubs\\ubs\\src\\main\\resources\\arquivos\\data_3.json");
			} catch (Exception e1) {
				e1.printStackTrace();
			}

			try (FileChannel fileChannel = (FileChannel) Files.newByteChannel(pathToRead,
					EnumSet.of(StandardOpenOption.READ))) {

				MappedByteBuffer mappedByteBuffer = fileChannel.map(FileChannel.MapMode.READ_ONLY, 0,
						fileChannel.size());

				if (mappedByteBuffer != null) {
					charBuffer = Charset.forName("UTF-8").decode(mappedByteBuffer);
				}
				JSONParser jsonParser = new JSONParser();
				JSONObject jsonObject = (JSONObject) jsonParser.parse(charBuffer.toString());
				JSONArray jsonArray = (JSONArray) jsonObject.get("data");

				if(estoqueService.findOne().isPresent() && getIsDone()) {

					for (int j = arq1; j < jsonArray.size(); j++) {
						JSONObject jO = (JSONObject) jsonArray.get(j);
						Estoque est = estoqueService.persistir(criaEstoque(jO));
						log.info("Estoque criado:{}", est.toString());
					}

				} else {

					for (int j = 0; j < jsonArray.size(); j++) {
						JSONObject jO = (JSONObject) jsonArray.get(j);
						Estoque est = estoqueService.persistir(criaEstoque(jO));
						log.info("Estoque criado:{}", est.toString());
					}

				}
			} catch (IOException | ParseException e1) {
				e1.printStackTrace();
			}

		};
		
		Runnable task4 = () -> {
			CharBuffer charBuffer = null;
			Path pathToRead = null;
			try {
				pathToRead = Paths.get(
						"D:\\DESENVOLVIMENTO\\WORKSPACES\\workspace_banco_ubs\\ubs\\src\\main\\resources\\arquivos\\data_4.json");
			} catch (Exception e1) {
				e1.printStackTrace();
			}

			try (FileChannel fileChannel = (FileChannel) Files.newByteChannel(pathToRead,
					EnumSet.of(StandardOpenOption.READ))) {

				MappedByteBuffer mappedByteBuffer = fileChannel.map(FileChannel.MapMode.READ_ONLY, 0,
						fileChannel.size());

				if (mappedByteBuffer != null) {
					charBuffer = Charset.forName("UTF-8").decode(mappedByteBuffer);
				}
				JSONParser jsonParser = new JSONParser();
				JSONObject jsonObject = (JSONObject) jsonParser.parse(charBuffer.toString());
				JSONArray jsonArray = (JSONArray) jsonObject.get("data");

				if(estoqueService.findOne().isPresent() && getIsDone()) {

					for (int j = arq1; j < jsonArray.size(); j++) {
						JSONObject jO = (JSONObject) jsonArray.get(j);
						Estoque est = estoqueService.persistir(criaEstoque(jO));
						log.info("Estoque criado:{}", est.toString());
					}

				} else {

					for (int j = 0; j < jsonArray.size(); j++) {
						JSONObject jO = (JSONObject) jsonArray.get(j);
						Estoque est = estoqueService.persistir(criaEstoque(jO));
						log.info("Estoque criado:{}", est.toString());
					}

				}
			} catch (IOException | ParseException e1) {
				e1.printStackTrace();
			}

		};

		this.execCarga(task1, task2, task3, task4);
	}
	
	private void execCarga(Runnable task1, Runnable task2, Runnable task3, Runnable task4) throws InterruptedException {
		ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
		 Future<?> taskStatus1 = executorService.submit(task1);
	        while(!taskStatus1.isDone()){
	        	  System.out.println("Task 1 are not yet complete....sleeping");
	        	  Thread.sleep(1000);
	        	}
	        Future<?> taskStatus2 = executorService.submit(task2);
	        while(!taskStatus2.isDone()){
	        	  System.out.println("Task 2 are not yet complete....sleeping");
	        	  Thread.sleep(1000);
	        	}
	        Future<?> taskStatus3 = executorService.submit(task3);
	        while(!taskStatus3.isDone() ){
	        	  System.out.println("Task 3 are not yet complete....sleeping");
	        	  Thread.sleep(1000);
	        	}
	        Future<?> taskStatus4 = executorService.submit(task4);
	        while(!taskStatus4.isDone()){
	        	  System.out.println("Task 4 are not yet complete....sleeping");
	        	  Thread.sleep(1000);
	        	}
		executorService.shutdown();
		this.setIsDone(true);
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

	public Boolean getIsDone() {
		return isDone;
	}

	public void setIsDone(Boolean isDone) {
		this.isDone = isDone;
	}
}
