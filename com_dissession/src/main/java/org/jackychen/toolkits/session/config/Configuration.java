package org.jackychen.toolkits.session.config;

import java.io.*;
import java.util.Properties;

public class Configuration {

	public static final String SERVERS = "servers";
	public static final String MAX_IDLE = "maxIdle";
	public static final String INIT_IDLE_CAPACITY = "initIdleCapacity";
	public static final String SESSION_TIMEOUT = "sessionTimeout";
	public static final String TIMEOUT = "timeout";
	public static final String POOLSIZE = "poolSize";
	public static final String CFG_NAME = ".cfg.properties";
	public static final String DOMAIN="domain";
	private static Configuration instance;
	private Properties config;
	protected Configuration() {
		config = new Properties();
		String basedir = System.getProperty("user.dir");
		File file = new File(basedir, ".cfg.properties");
		try {
			boolean exist = file.exists();
			if (!exist)
				file.createNewFile();
			config.load(new FileInputStream(file));
			if (!exist) {
				config.setProperty("servers", "192.168.128.131:2181,192.168.128.132:2181,192.168.128.147:2181");
				config.setProperty("maxIdle", "8");
				config.setProperty("initIdleCapacity", "4");
				config.setProperty("sessionTimeout", "1");
				config.setProperty("timeout", "5000");
				config.setProperty("poolSize", "5000");
				config.store(new FileOutputStream(file), "");
				config.setProperty("domain",".chenzhao.com");
			}
		} catch (Exception ex) {
		}
	}

	public static Configuration getInstance() {
		if (instance == null)
			instance = new Configuration();
		return instance;
	}

	public String getString(String key, String defaultValue) {
		if (config != null)
			return config.getProperty(key) == null ? defaultValue : config
					.getProperty(key);
		else
			return defaultValue;
	}

	public String getString(String key) {
		return getString(key, null);
	}

	public static String getServers() {
		return SERVERS;
	}

	public String toString() {
		return (new StringBuilder()).append("Configuration [config=")
				.append(config).append("]").toString();
	}
}
