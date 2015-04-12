package client;

import java.util.Properties;

@SuppressWarnings("serial")
public class IndexerProperties extends Properties {
	public double getDoubleProperty(String key) {
		return Double.parseDouble(this.getProperty(key));
	}

	public int getIntProperty(String key) {
		return Integer.parseInt(this.getProperty(key));
	}
	
	public boolean getBoolProperty(String key) {
		return Boolean.parseBoolean(this.getProperty(key));
	}
	
	public void setIntProperty(String key, int prop) {
		this.setProperty(key, Integer.toString(prop));
	}
	
	public void setBoolProperty(String key, boolean prop) {
		this.setProperty(key, Boolean.toString(prop));
	}
	
	public void setDoubleProperty(String key, double prop) {
		this.setProperty(key, Double.toString(prop));
	}
}
