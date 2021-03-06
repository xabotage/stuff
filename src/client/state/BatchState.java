package client.state;

import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import shared.model.Project;

public class BatchState {
	
	private String[][] values;
	private Cell selectedCell;
	private List<BatchStateListener> listeners;
	private String batchImageUrl;
	private Map<Integer, List<String>> knownDataMap;

	public String getUrlBase() {
		return urlBase;
	}

	public void setUrlBase(String urlBase) {
		this.urlBase = urlBase;
	}

	private String urlBase;
	
	private Project project;
	
	public String getBatchImageUrl() {
		return batchImageUrl;
	}

	public void setBatchImageUrl(String batchImageUrl) {
		this.batchImageUrl = batchImageUrl;
	}

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public BatchState() {
		selectedCell = null;
		listeners = new ArrayList<BatchStateListener>();
		batchImageUrl = "";
	}
	
	public BatchState(int records, int fields) {
		values = new String[records][fields];
		selectedCell = null;
		listeners = new ArrayList<BatchStateListener>();
		batchImageUrl = "";
	}
	
	public void addListener(BatchStateListener l) {
		listeners.add(l);
	}
	
	public void setValue(Cell cell, String value) {
		
		values[cell.record][cell.field] = value;
		
		for (BatchStateListener l : listeners) {
			l.valueChanged(cell, value);
		}
	}

	public void setValue(int record, int field, String value) {
		Cell c = new Cell();
		c.field = field;
		c.record = record;
		setValue(c, value);
	}
	
	public String getValue(Cell cell) {
		return values[cell.record][cell.field];
	}

	public String getValue(int record, int field) {
		return values[record][field];
	}
	
	public String[][] getValues() {
		return values;
	}

	public void setValues(String[][] values) {
		this.values = values;
	}

	public void setSelectedCell(Cell selCell) {
		selectedCell = selCell;
		for (BatchStateListener l : listeners) {
			l.selectedCellChanged(selCell);
		}
	}
	
	public void setSelectedCell(int record, int field) {
		Cell c = new Cell();
		c.record = record;
		c.field = field;
		setSelectedCell(c);
	}
	
	public void loadBatch(Project project, String imageUrl, String[][] newValues) {
		values = newValues;
		knownDataMap = new HashMap<Integer, List<String>>();
		selectedCell = new Cell();
		selectedCell.record = 0;
		selectedCell.field = 0;
		this.project = project;
		batchImageUrl = imageUrl;
		for (BatchStateListener l : listeners) {
			l.batchLoaded();
		}
	}
	
	public List<String> getKnownDataForField(int field) {
		if(project == null)
			return null;
		
		if(knownDataMap.containsKey(field))
			return knownDataMap.get(field);
		
		List<String> data = new ArrayList<String>();
		String knownDataFile = this.project.getFields().get(field).getKnownData();

		if(knownDataFile == null || knownDataFile.equals(""))
			return data;

		try {
			InputStream fileStream = new URL(getUrlBase() + knownDataFile).openStream();
			Scanner scan = new Scanner(fileStream);
			while(scan.hasNextLine()) {
				String line = scan.nextLine();
				for(String val : line.split(","))
					data.add(val);
			}
			scan.close();
			fileStream.close();
		} catch (Exception e) {
			e.printStackTrace();
			return data;
		}
		knownDataMap.put(field, data);
		return data;
	}
	
	public boolean knownDataContainsValueAtField(String value, int field) {
		if(value == null || value.equals(""))
			return true;
		
		List<String> knownData = getKnownDataForField(field);
		if(knownData == null || knownData.size() == 0)
			return true;

		for(String known : knownData) {
			if(known.toLowerCase().equals(value.toLowerCase()))
				return true;
		}
		return false;
	}
	
	public Cell getSelectedCell() {
		return selectedCell;
	}

	public class Cell {
		public int record;
		public int field;
	}

	public interface BatchStateListener {
		void valueChanged(Cell cell, String newValue);
		void selectedCellChanged(Cell newSelectedCell);
		void batchLoaded();
	}

}
