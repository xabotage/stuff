package client.gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.JTextField;

import client.state.BatchState;
import client.state.BatchState.Cell;

public class FormEntryTextField extends JTextField {
	private int fieldNum;
	private BatchState batchState;
	private boolean isSpelledCorrectly;
	
	public FormEntryTextField(BatchState bState, int fieldNum) {
		super();
		this.setEditable(true);
		this.setEnabled(true);
		this.batchState = bState;
		this.fieldNum = fieldNum;
		this.isSpelledCorrectly = true;
		this.addFocusListener(new FocusListener() {
			@Override
			public void focusLost(FocusEvent e) {
				//batchState.setValue(batchState.getSelectedCell(), getText());
			}
			
			@Override
			public void focusGained(FocusEvent e) {
				// TODO Auto-generated method stub
				Cell c = batchState.new Cell();
				c.record = batchState.getSelectedCell().record;
				c.field = getFieldNum();
				batchState.setSelectedCell(c);
			}
		});
	}

	public int getFieldNum() {
		return fieldNum;
	}

	public void setFieldNum(int fieldNum) {
		this.fieldNum = fieldNum;
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

}
