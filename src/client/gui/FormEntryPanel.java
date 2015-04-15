package client.gui;

import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
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
	private List<FormEntryTextField> textFields;
	private boolean updatingModel;

	public FormEntryPanel(BatchState batchState) {
		super();
		this.batchState = batchState;
		this.updatingModel = false;
		batchState.addListener(this);
		this.setLayout(new BorderLayout());
	}
	
	private DocumentListener docListener = new DocumentListener() {
		@Override
		public void removeUpdate(DocumentEvent e) {
			updateModel();
		}
		
		@Override
		public void insertUpdate(DocumentEvent e) {
			updateModel();
		}
		
		@Override
		public void changedUpdate(DocumentEvent e) {
			updateModel();
		}
	};
	
	public void updateModel() {
		this.updatingModel = true;
		int record = recordNumList.getSelectedIndex();
		for(FormEntryTextField tf : textFields) {
			tf.spellCheck();
			if(tf.hasFocus())
				batchState.setValue(record, tf.getFieldNum(), tf.getText());
		}
		this.updatingModel = false;
	}

	@Override
	public void valueChanged(Cell cell, String newValue) {
		if(cell.record != batchState.getSelectedCell().record || updatingModel)
			return;
		else {
			textFields.get(cell.field).setText(newValue);
			textFields.get(cell.field).spellCheck();
		}
	}
		
	@Override
	public void selectedCellChanged(Cell newSelectedCell) {
		recordNumList.setSelectedIndex(newSelectedCell.record);
		textFields.get(newSelectedCell.field).requestFocusInWindow();
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

					for(FormEntryTextField tf : textFields) {
						tf.setText(batchState.getValue(c.record, tf.getFieldNum()));
						tf.spellCheck();
					}

				}
			}
		});

		recordNumList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		//recordNumList.setPreferredSize(new Dimension(100, 100));
		JScrollPane scrollPane = new JScrollPane(recordNumList);
		scrollPane.setPreferredSize(new Dimension(100, 100));
		this.add(scrollPane, BorderLayout.WEST);

		GridLayout gl = new GridLayout(batchState.getProject().getFields().size(), 2);
		gl.setVgap(5);
		this.formInputPanel = new JPanel(gl);
		this.textFields = new ArrayList<>();
		int fieldNum = 0; // TODO: start at 1 or 0?
		for(Field f : batchState.getProject().getFields()) {
			formInputPanel.add(new JLabel(f.getTitle()));
			FormEntryTextField textField = new FormEntryTextField(batchState, fieldNum);
			textField.getDocument().addDocumentListener(docListener);
			formInputPanel.add(textField);
			textFields.add(textField);
			fieldNum++;
		}
		
		JScrollPane scrollPane2 = new JScrollPane(formInputPanel);
		scrollPane.setPreferredSize(new Dimension(100, textFields.size()*15));

		this.add(scrollPane2, BorderLayout.CENTER);
		recordNumList.setSelectedIndex(0);
	}

	public void setFocusForField() {
		textFields.get(batchState.getSelectedCell().field).requestFocusInWindow();
	}
}
