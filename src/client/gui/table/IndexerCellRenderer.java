package client.gui.table;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.TableCellRenderer;

import client.state.BatchState;

@SuppressWarnings("serial")
public class IndexerCellRenderer extends JTextField implements TableCellRenderer {
	private BatchState batchState;
	private Color misSpelledColor = new Color(255, 28, 28);

	public IndexerCellRenderer(BatchState batchState) {
		super();
		this.batchState = batchState;
	}

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int column) {
		// TODO Auto-generated method stub
		if(column == 0)
			return new JLabel((String)value);
		else if(!isSelected) {
			JLabel returnLabel = new JLabel((String)value);
			if(!batchState.knownDataContainsValueAtField((String)value, column - 1)) {
				//returnLabel.setBackground(misSpelledColor);
				returnLabel.setForeground(misSpelledColor);
			} else
				returnLabel.setBackground(table.getBackground());
			return returnLabel;
		} else {
			setBackground(table.getSelectionBackground());
			this.setText((String)value);
			return this;
		}
	}
	
	class PopUpMenu extends JPopupMenu {
	    JMenuItem suggestionsMenu;
	    public PopUpMenu(){
	        suggestionsMenu = new JMenuItem("Click Me!");
	        add(suggestionsMenu);
	    }
	}

}
