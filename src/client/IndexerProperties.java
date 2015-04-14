package client;

import java.util.Properties;
import org.apache.commons.lang3.StringUtils;

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
	
	public String[][] getValuesProperty() {
		String[] recordStrings = getProperty("values").split(";", 200);
		int fieldCount = recordStrings[0].split(",", 200).length;
		String[][] values = new String[recordStrings.length][fieldCount];
		for(int i = 0; i < recordStrings.length; i++) {
			values[i] = StringUtils.split(recordStrings[i], ",");
		}
		return values;
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
	
	public void setValuesProperty(String[][] values) {
		String[] recordStrings = new String[values.length];
		for(int i = 0; i < values.length; i++) {
			recordStrings[i] = StringUtils.join(values[i], ",");
		}
		String finalValuesString = StringUtils.join(recordStrings, ";");
		this.setProperty("values", finalValuesString);
	}
}
