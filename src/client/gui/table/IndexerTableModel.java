package client.gui.table;

import javax.swing.table.AbstractTableModel;

@SuppressWarnings("serial")
public class IndexerTableModel extends AbstractTableModel {
	private String[][] tableValues;

	public IndexerTableModel(String[][] batchStateValues) {
		int l1 = batchStateValues.length;
		int l2 = batchStateValues[0].length;
		tableValues = new String[l1][l2 + 1];
		for(int i = 0; i < l1; i++) {
			for(int j = 0; j <= l2; j++) {
				if(j == 0)
					tableValues[i][j] = Integer.toString(i + 1);
				else
					tableValues[i][j] = batchStateValues[i][j-1];
			}
		}
	}

	@Override
	public int getRowCount() {
		return tableValues.length;
	}

	@Override
	public int getColumnCount() {
		return tableValues[0].length;
	}
	
	@Override
	public boolean isCellEditable(int row, int column) {
		return column > 0;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		return tableValues[rowIndex][columnIndex];
	}

	@Override
	public void setValueAt(Object value, int row, int column) {
		if (row >= 0 && row < getRowCount() && column >= 1
				&& column < getColumnCount()) {
			
			this.tableValues[row][column] = (String)value;
			this.fireTableCellUpdated(row, column);
		} else {
			throw new IndexOutOfBoundsException();
		}		
	}
}
