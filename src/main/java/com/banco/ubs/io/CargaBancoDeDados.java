package com.banco.ubs.io;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.banco.ubs.entities.Estoque;
import com.banco.ubs.service.EstoqueService;

@Component
public class CargaBancoDeDados {
	private static final Logger log = LoggerFactory.getLogger(CargaBancoDeDados.class);

	@Autowired
	private EstoqueService estoqueService;

	private Boolean isDone = false;

	public void criaThreads(List<Estoque> listaEstoque) throws InterruptedException {
		this.execThread(task(listaEstoque));
		this.setIsDone(true);

	}

	private void execThread(Runnable task) {
		try {
			log.info("Executando thread");
			ExecutorService executorService = Executors.newSingleThreadExecutor();
			executorService.submit(task);
		
			executorService.shutdown();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private Runnable task(List<Estoque> listaEstoque) {
		log.info("Criando thread");
		Runnable task = () -> {
			listaEstoque
			.stream()
			.forEach( m -> estoqueService.persistir(m));
		};
		return task;
	}

	public Boolean getIsDone() {
		return isDone;
	}

	public void setIsDone(Boolean isDone) {
		this.isDone = isDone;
	}
}