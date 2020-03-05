package com.banco.ubs.io;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import org.json.JSONObject;



public class CargaProdutos {

	public static void main(String[] args) throws IOException {
	     JSONObject json = null;
	        try {
	            FileReader fileReader = new FileReader("/ubs/src/main/resources/arquivos/data_1.json");
	            BufferedReader bufferedReader = new BufferedReader(fileReader);
	            List<String> collect = bufferedReader.lines().collect(Collectors.toList());
	            StringBuilder jsonTemp = new StringBuilder();
	            for (String s : collect) {
	                jsonTemp.append(s);
	            }
	            json = new JSONObject(jsonTemp.toString());
	            System.out.println(json.toString());
	            fileReader.close();
	        } catch (IOException e) {
	            e.printStackTrace();
	            System.err.println("Não foi possível ler o arquivo json");
	        }
	}

}
