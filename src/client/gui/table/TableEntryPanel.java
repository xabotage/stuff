package client.gui.table;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import client.state.BatchState;
import client.state.BatchState.BatchStateListener;
import client.state.BatchState.Cell;

@SuppressWarnings("serial")
public class TableEntryPanel extends JPanel implements BatchStateListener {
	private JTable table;
	private BatchState batchState;
	private IndexerTableModel tableModel;
	private boolean isUpdating;

	public TableEntryPanel(BatchState batchState) {
		super();
		this.batchState = batchState;
		batchState.addListener(this);
		this.addMouseListener(mouseAdapter);
		this.setLayout(new BorderLayout());
		this.isUpdating = false;
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
	
	private ListSelectionListener listSelectionListener = new ListSelectionListener() {
		@Override
		public void valueChanged(ListSelectionEvent e) {
			if(!e.getValueIsAdjusting()) {
				Cell c = batchState.new Cell();
				c.field = table.getSelectedColumn() - 1;
				if(c.field < 0)
					c.field = 0;
				c.record = table.getSelectedRow();
				batchState.setSelectedCell(c);
			}
		}
	};
	
	@Override
	public void valueChanged(Cell cell, String newValue) {
		if(isUpdating)
			return;
		table.setValueAt(newValue, cell.record, cell.field + 1);
		isUpdating = false;
	}

	@Override
	public void selectedCellChanged(Cell newSelectedCell) {
		table.changeSelection(newSelectedCell.record, newSelectedCell.field + 1, false, false);
	}

	@Override
	public void batchLoaded() {
		tableModel = new IndexerTableModel(batchState.getValues());
		table = new JTable(tableModel);
		//table.setRowHeight(30);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setCellSelectionEnabled(true);
		table.getTableHeader().setReorderingAllowed(false);
		tableModel.addTableModelListener(new TableModelListener() {
			@Override
			public void tableChanged(TableModelEvent e) {
				Cell c = batchState.new Cell();
				c.record = e.getFirstRow();
				c.field = e.getColumn() - 1;
				isUpdating = true;
				batchState.setValue(c, (String)tableModel.getValueAt(e.getFirstRow(), e.getColumn()));
			}
		});
		table.getSelectionModel().addListSelectionListener(listSelectionListener);
		
		TableColumnModel columnModel = table.getColumnModel();
		columnModel.getSelectionModel().addListSelectionListener(listSelectionListener);
		for (int i = 0; i < tableModel.getColumnCount(); ++i) {
			TableColumn column = columnModel.getColumn(i);
			column.setPreferredWidth(100);
			if(i > 0) {
				column.setCellRenderer(new IndexerCellRenderer());
				column.setHeaderValue(batchState.getProject().getFields().get(i - 1).getTitle());
			} else {
				column.setHeaderValue("Row");
			}
		}	
		
		JScrollPane scroller = new JScrollPane(table);
		table.setFillsViewportHeight(true);
		scroller.setPreferredSize(new Dimension(this.getWidth(), this.getHeight()));
		this.add(scroller, BorderLayout.CENTER);
	}
}
