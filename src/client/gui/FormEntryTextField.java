package client.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import client.state.BatchState;
import client.state.BatchState.Cell;

public class FormEntryTextField extends JTextField implements ActionListener {
	private int fieldNum;
	private BatchState batchState;
	private boolean isSpelledCorrectly;
	private JPopupMenu popUpMenu;
	private JMenuItem seeSuggestions;
	
	public FormEntryTextField(BatchState bState, int fieldNum) {
		super();
		this.setEditable(true);
		this.setEnabled(true);
		this.batchState = bState;
		this.fieldNum = fieldNum;
		this.isSpelledCorrectly = true;
		this.setPreferredSize(new Dimension(15, 50));
		this.addFocusListener(new FocusListener() {
			@Override
			public void focusLost(FocusEvent e) {
				spellCheck();
			}
			
			@Override
			public void focusGained(FocusEvent e) {
				Cell c = batchState.new Cell();
				c.record = batchState.getSelectedCell().record;
				c.field = getFieldNum();
				batchState.setSelectedCell(c);
			}
		});

		this.addMouseListener(mouseAdapter);
		popUpMenu = new JPopupMenu();
		seeSuggestions = new JMenuItem("See Suggestions");
		seeSuggestions.addActionListener(this);
		popUpMenu.add(seeSuggestions);
	}

	public int getFieldNum() {
		return fieldNum;
	}

	public void setFieldNum(int fieldNum) {
		this.fieldNum = fieldNum;
	}

	public boolean isSpelledCorrectly() {
		return isSpelledCorrectly;
	}

	public void setSpelledCorrectly(boolean isSpelledCorrectly) {
		this.isSpelledCorrectly = isSpelledCorrectly;
	}
	
	public void spellCheck() {
		if(!batchState.knownDataContainsValueAtField(this.getText(), fieldNum))
			isSpelledCorrectly = false;
		else
			isSpelledCorrectly = true;
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Color bg = null;
		if(isSpelledCorrectly) {
			bg = new Color(255, 255, 255);
		} else {
			bg = new Color(255, 0, 0);
		}
		this.setBackground(bg);
	}

	private MouseAdapter mouseAdapter = new MouseAdapter() {
		@Override
		public void mouseReleased(MouseEvent e) {
			showPopup(e);
		}

		@Override
		public void mousePressed(MouseEvent e) {
			showPopup(e);
		}
		
		public void showPopup(MouseEvent e) {
			if (e.isPopupTrigger()) {
				if(!isSpelledCorrectly) {
					batchState.setSelectedCell(batchState.getSelectedCell().record, fieldNum);
					popUpMenu.show(e.getComponent(), e.getX(), e.getY());
				}
			}
		}
		
	};

	@Override
    public void actionPerformed(ActionEvent event) {
		 JMenuItem menu = (JMenuItem) event.getSource();
		 if (menu == seeSuggestions) {
			 KnownDataList knownData = new KnownDataList(batchState);
			 JScrollPane scroller = new JScrollPane(knownData);
			 int option = JOptionPane.showOptionDialog(this, scroller, "Suggested Values", JOptionPane.OK_CANCEL_OPTION, 
				JOptionPane.PLAIN_MESSAGE, null, null, null);
			 if(option == JOptionPane.OK_OPTION) {
				 String newVal = (String)knownData.getSelectedValue();
				 batchState.setValue(batchState.getSelectedCell(), newVal);
			 }
		 }
	}
}
