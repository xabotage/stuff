package client.state;

import java.util.ArrayList;
import java.util.List;

import shared.model.Batch;
import shared.model.Project;

public class BatchState {
	
	private String[][] values;
	private Cell selectedCell;
	private List<BatchStateListener> listeners;
	private String batchImageUrl;

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
	
	public String getValue(Cell cell) {
		return values[cell.record][cell.field];
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
	
	public void loadBatch(Project project, String imageUrl) {
		values = new String[project.getRecordsPerImage()][project.getFields().size()];
		selectedCell = new Cell();
		selectedCell.record = 0;
		selectedCell.field = 0;
		this.project = project;
		batchImageUrl = imageUrl;
		for (BatchStateListener l : listeners) {
			l.batchLoaded();
		}
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
