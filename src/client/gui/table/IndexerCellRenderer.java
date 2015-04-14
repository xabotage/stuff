package client.gui.table;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.TableCellRenderer;

@SuppressWarnings("serial")
public class IndexerCellRenderer extends JTextField implements TableCellRenderer {
	private Color misSpelledColor = new Color(255f, 128f, 128f);
	public IndexerCellRenderer() {
		super();
		//this.setComponentPopupMenu(new PopUpMenu());
	}

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int column) {
		// TODO Auto-generated method stub
		if(column == 0)
			return new JLabel((String)value);
		else if(!isSelected) {
			setBackground(misSpelledColor);
			return new JLabel((String)value);
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
