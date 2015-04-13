package client;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JPanel;
import javax.swing.JTable;

import client.state.BatchState;
import client.state.BatchState.BatchStateListener;
import client.state.BatchState.Cell;

@SuppressWarnings("serial")
public class TableEntryPanel extends JPanel implements BatchStateListener {
	private JTable table;
	private BatchState batchState;
	private IndexerTableModel tableModel;

	public TableEntryPanel(BatchState batchState) {
		super();
		this.batchState = batchState;
		batchState.addListener(this);
		this.addMouseListener(mouseAdapter);
	}
	
	private MouseAdapter mouseAdapter = new MouseAdapter() {
		@Override
		public void mouseReleased(MouseEvent e) {

			if (e.isPopupTrigger()) {
				
				final int row = table.rowAtPoint(e.getPoint());
				final int column = table.columnAtPoint(e.getPoint());
				
				if (row >= 0 && row < table.getRowCount() &&
						column >= 0 && column < table.getColumnCount()) {
					Cell newCell = batchState.new Cell();
					newCell.record = row;
					newCell.field = column;
					batchState.setSelectedCell(newCell);
				}
			}
		}
		
	};
	
	@Override
	public void valueChanged(Cell cell, String newValue) {
		// TODO Auto-generated method stub
	}

	@Override
	public void selectedCellChanged(Cell newSelectedCell) {
		// TODO Auto-generated method stub
	}

	@Override
	public void batchLoaded() {
		tableModel = new IndexerTableModel();
		table = new JTable(tableModel);
	}
}
