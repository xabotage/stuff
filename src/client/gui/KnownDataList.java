package client.gui;

import java.awt.Dimension;

import javax.swing.JList;
import javax.swing.ListSelectionModel;

import client.state.BatchState;

public class KnownDataList extends JList {
	public KnownDataList(BatchState batchState) {
		super();
		this.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		int field = batchState.getSelectedCell().field;
		
		this.setListData(batchState.getKnownDataForField(field).toArray());
	}
}
