package client.gui;

import javax.swing.JEditorPane;

import client.state.BatchState;
import client.state.BatchState.BatchStateListener;
import client.state.BatchState.Cell;

@SuppressWarnings("serial")
public class FieldHelpPanel extends JEditorPane implements BatchStateListener {
	private BatchState batchState;
	
	public FieldHelpPanel(BatchState batchState) {
		this.batchState = batchState;
		this.setEditable(false);
		batchState.addListener(this);
		setContentType("text/html");
	}

	@Override
	public void valueChanged(Cell cell, String newValue) {
		// TODO Auto-generated method stub

	}

	@Override
	public void selectedCellChanged(Cell newSelectedCell) {
		try {
			this.setPage(batchState.getUrlBase() + batchState.getProject().getFields().get(newSelectedCell.field).getHelpUrl());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void batchLoaded() {
		// TODO Auto-generated method stub

	}
}
