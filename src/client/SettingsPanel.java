package client;

import java.awt.FlowLayout;
import java.awt.event.*;

import javax.swing.*;

import static servertester.views.Constants.*;

@SuppressWarnings({"serial", "rawtypes", "unchecked"})
public class SettingsPanel extends JPanel {
	
	private JTextField _hostTextField;
	private JTextField _portTextField;
	private JButton _executeButton;
	private JTextField userNameField;
	private JTextField passwordField;
	private SearchController controller;
	
	public SettingsPanel() {
		super();
		setLayout(new FlowLayout());
		add(new JLabel("HOST:"));
		_hostTextField = new JTextField(30);
		add(_hostTextField);
		_hostTextField.setMinimumSize(_hostTextField.getPreferredSize());

		add(new JLabel("PORT:"));
		_portTextField = new JTextField(10);
		_portTextField.setMinimumSize(_portTextField.getPreferredSize());
		add(_portTextField);

		_executeButton = new JButton("Log In");
		add(_executeButton);	
		/*
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
		
		add(Box.createRigidArea(TRIPLE_HSPACE));

		_executeButton = new JButton("Log In");
		add(_executeButton);	
		add(Box.createRigidArea(DOUBLE_HSPACE));
		*/
		
		setMaximumSize(getPreferredSize());
		
		_executeButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				controller.getProjectsFromServer();
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
	
	public String getUserName() {
		return userNameField.getText();
	}

	public String getPassword() {
		return userNameField.getText();
	}

	public SearchController getController() {
		return controller;
	}

	public void setController(SearchController controller) {
		this.controller = controller;
	}
}

