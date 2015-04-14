package client.gui;

import java.util.List;

import javax.swing.JList;
import javax.swing.ListSelectionModel;

import spell.SpellCorrector;
import client.state.BatchState;

@SuppressWarnings({ "serial", "rawtypes" })
public class KnownDataList extends JList {
	public KnownDataList(BatchState batchState) {
		super();
		this.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		int field = batchState.getSelectedCell().field;
		SpellCorrector corr = new SpellCorrector();
		List<String> knownDataVals = batchState.getKnownDataForField(field);
		String inputValue = batchState.getValue(batchState.getSelectedCell());
		this.setListData(corr.getSimilarWords(inputValue, knownDataVals).toArray());
	}
}
