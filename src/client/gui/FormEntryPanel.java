package client.gui;

import java.awt.*;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import shared.model.Field;
import client.state.BatchState;
import client.state.BatchState.BatchStateListener;
import client.state.BatchState.Cell;

@SuppressWarnings({"serial", "rawtypes"})
public class FormEntryPanel extends JPanel implements BatchStateListener {
	private BatchState batchState;
	private JList recordNumList;
	private JPanel formInputPanel;

	public FormEntryPanel(BatchState batchState) {
		super();
		this.batchState = batchState;
		batchState.addListener(this);
		this.setLayout(new BorderLayout());
	}

	@Override
	public void valueChanged(Cell cell, String newValue) {
		// TODO Auto-generated method stub
	}
		
	@Override
	public void selectedCellChanged(Cell newSelectedCell) {
		recordNumList.setSelectedIndex(newSelectedCell.record);
	}

	@Override
	public void batchLoaded() {
		DefaultListModel<String> lm = new DefaultListModel<String>();
		for(int i = 1; i <= batchState.getProject().getRecordsPerImage(); i++)
			lm.addElement(Integer.toString(i));

		this.recordNumList = new JList(lm);
		recordNumList.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				if (!e.getValueIsAdjusting()) {
					JList source = (JList) e.getSource();
					Cell c = batchState.new Cell();
					c.record = source.getSelectedIndex();
					c.field = batchState.getSelectedCell().field;
					batchState.setSelectedCell(c);
				}
			}
		});

		recordNumList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		recordNumList.setPreferredSize(new Dimension(100, 100));
		recordNumList.setSelectedIndex(0);
		JScrollPane scrollPane = new JScrollPane(recordNumList);
		this.add(scrollPane, BorderLayout.WEST);

		this.formInputPanel = new JPanel(new GridLayout(batchState.getProject().getFields().size(), 2));
		int fieldNum = 0; // TODO: start at 1 or 0?
		for(Field f : batchState.getProject().getFields()) {
			formInputPanel.add(new JLabel(f.getTitle()));
			FormEntryTextField textField = new FormEntryTextField(batchState, fieldNum);
			formInputPanel.add(textField);
			fieldNum++;
		}
		
		this.add(formInputPanel, BorderLayout.CENTER);
	}
}
