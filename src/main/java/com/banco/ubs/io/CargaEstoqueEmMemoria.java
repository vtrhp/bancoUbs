package com.banco.ubs.io;

import java.io.File;
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
import java.util.stream.Stream;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.banco.ubs.utils.ConfigProperties;
import com.banco.ubs.utils.Utils;

@Component
public class CargaEstoqueEmMemoria {
	private static final Logger log = LoggerFactory.getLogger(CargaEstoqueEmMemoria.class);

	@Autowired
	ConfigProperties configProp;

	private Boolean isDone = false;

	public JSONArray carregaJsons() {
		JSONArray jsonArray = new JSONArray();
		Stream.of(leDiretorio()).forEach(p -> {
			CharBuffer charBuffer = null;
			Path pathToRead = null;
			try {
				pathToRead = Paths.get(p.toString());
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
				jsonArray.addAll((JSONArray) jsonObject.get("data"));
			} catch (IOException | ParseException e1) {
				e1.printStackTrace();
			}
		});
		return jsonArray;
	}

	private File[] leDiretorio() {
		File dir = new File(configProp.getConfigValue("prop.dir"));
		File[] files = dir.listFiles(Utils.filter);
		return files;
	}
}