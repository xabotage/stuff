package client.gui.table;

import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.TableCellRenderer;

@SuppressWarnings("serial")
public class IndexerCellRenderer extends JTextField implements TableCellRenderer {

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int column) {
		// TODO Auto-generated method stub
		if(column == 0)
			return new JLabel((String)value);
		else if(!isSelected)
			return new JLabel((String)value);
		else {
			setBackground(table.getSelectionBackground());
			this.setText((String)value);
			return this;
		}
	}

}
