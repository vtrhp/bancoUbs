package com.banco.ubs.io;

import java.io.File;
import java.io.FileFilter;
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
import java.util.stream.Stream;

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
import com.banco.ubs.utils.ConfigProperties;

@Component
public class CargaEstoque {
	private static final Logger log = LoggerFactory.getLogger(CargaEstoque.class);

	@Autowired
	private EstoqueService estoqueService;

	@Autowired
	ConfigProperties configProp;

	private Boolean isDone = false;

	static FileFilter filter = new FileFilter() {
		public boolean accept(File file) {
			return file.getName().endsWith(".json");
		}
	};

	public void criaThreads() throws InterruptedException {
		Stream.of(leDiretorio()).forEach(f -> this.execThread(task(f)));
		this.setIsDone(true);
	}

	private void execThread(Runnable task) {
		try {
			ExecutorService executorService = Executors.newSingleThreadExecutor();
			 Future<?> taskStatus = executorService.submit(task);
		        while(!taskStatus.isDone()){
		        	log.info("A task ainda nao foi concluida....sleeping");
		        	  Thread.sleep(1000);
		        	}
			executorService.shutdown();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private Runnable task(File path) {
		int arq1 = estoqueService.findCount().isPresent() ? (int) estoqueService.findCount().get().intValue() + 1 : 0;
		Runnable task = () -> {
			CharBuffer charBuffer = null;
			Path pathToRead = null;
			try {
				pathToRead = Paths.get(path.toString());
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
				if(estoqueService.findOne().isPresent() && !getIsDone() && estoqueService.findCount().get().intValue() < jsonArray.size()) {
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
		return task;
	}

	private File[] leDiretorio() {
    	File dir = new File(configProp.getConfigValue("prop.dir"));
        File[] files = dir.listFiles(filter);
        return files;
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