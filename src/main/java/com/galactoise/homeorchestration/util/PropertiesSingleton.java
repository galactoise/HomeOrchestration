package com.galactoise.homeorchestration.util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Logger;

public class PropertiesSingleton {

	protected static final Logger LOGGER = Logger.getLogger(PropertiesSingleton.class.getName());
	
	private static PropertiesSingleton propertiesSingletonInstance;
	private Properties properties;
	
	private static final String CONFIG_PATH_PROVIDED = "config.properties";
	private static final String CONFIG_PATH_PACKAGED = "config" + File.separator + "config.properties";
	
	private PropertiesSingleton(){
		InputStream inputStream = PropertiesSingleton.class.getClassLoader().getResourceAsStream(CONFIG_PATH_PROVIDED);
		
		if(inputStream == null){
			LOGGER.info("Could not load config at " + CONFIG_PATH_PROVIDED);
			inputStream = PropertiesSingleton.class.getClassLoader().getResourceAsStream(CONFIG_PATH_PACKAGED);
			if(inputStream == null){
				LOGGER.info("Could not load config at " + CONFIG_PATH_PACKAGED);
				return;
			}else{
				LOGGER.warning("Using packaged configuration - sensitive values are likely incorrect.");
			}
		}
		properties = new Properties();
		try {
			properties.load(inputStream);
		} catch (IOException e) {
			LOGGER.info("Could not load config.");
		}
		
	}
	
	public static PropertiesSingleton getPropertiesSingletonInstance(){
		if(propertiesSingletonInstance == null){
			propertiesSingletonInstance = new PropertiesSingleton();
		}
		return propertiesSingletonInstance;
	}
	
	public Properties getProperties(){
		return properties;
	}
}
