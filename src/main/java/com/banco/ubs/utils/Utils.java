package com.banco.ubs.utils;

import java.io.FileInputStream;
import java.util.Properties;

public class Utils {

	public static Properties getProp() {
		try {
			Properties props = new Properties();
			FileInputStream file = new FileInputStream("D:\\DESENVOLVIMENTO\\WORKSPACES\\workspace_banco_ubs\\ubs\\src\\main\\resources\\application.properties");
			props.load(file);
			return props;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

}
