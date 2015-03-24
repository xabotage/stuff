package client;

import java.awt.event.*;

import javax.swing.*;

import static servertester.views.Constants.*;

@SuppressWarnings({"serial", "rawtypes", "unchecked"})
public class SettingsPanel extends JPanel {
	
	private JTextField _hostTextField;
	private JTextField _portTextField;
	private JButton _executeButton;
	
	public SettingsPanel() {
		super();
		
		setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
		
		add(Box.createRigidArea(DOUBLE_HSPACE));
		add(new JLabel("HOST:"));
		add(Box.createRigidArea(SINGLE_HSPACE));
		
		_hostTextField = new JTextField(30);
		_hostTextField.setMinimumSize(_hostTextField.getPreferredSize());
		add(_hostTextField);
		add(Box.createRigidArea(TRIPLE_HSPACE));
		
		add(new JLabel("PORT:"));
		add(Box.createRigidArea(SINGLE_HSPACE));
		
		_portTextField = new JTextField(10);
		_portTextField.setMinimumSize(_portTextField.getPreferredSize());
		add(_portTextField);
		add(Box.createRigidArea(TRIPLE_HSPACE));
		
		add(new JLabel("OPERATION:"));
		add(Box.createRigidArea(SINGLE_HSPACE));
		
		add(Box.createRigidArea(TRIPLE_HSPACE));

		_executeButton = new JButton("Execute");
		add(_executeButton);	
		add(Box.createRigidArea(DOUBLE_HSPACE));
		
		setMaximumSize(getPreferredSize());
		
		_executeButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				//getController().executeOperation();
			}
		});
	}
	
	public void setHost(String value) {
		_hostTextField.setText(value);
	}

	public String getHost() {
		return _hostTextField.getText();
	}

	public void setPort(String value) {
		_portTextField.setText(value);
	}

	public String getPort() {
		return _portTextField.getText();
	}
}

