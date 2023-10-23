package com.ch.comp228.lab5;
/* Author: Chun Hin Yim
 * Date: 22/7/2023
 * Description: it develops a GUI Java application with data access capabilities.
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;

public class SQLAction {
	private Connection connection;

	public SQLAction(Connection connection) {
		this.connection = connection;
	}//constructor 

	public void display() {
		//frame
		JFrame frame = new JFrame("Action Selection");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(500, 150);

		//buttons
		JButton insertButton = new JButton("Insert");
		JButton updateButton = new JButton("Update Existing Player Info");
		JButton displayButton = new JButton("Display");

		//event listeners for buttons
		insertButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Open InsertClass or perform insert action
				InsertSQL insertSQL = new InsertSQL(connection);
			}
		});
		updateButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Open UpdateClass or perform update action
				UpdateSQL updateSQL = new UpdateSQL(connection);
			}
		});

		displayButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Open DisplayClass or perform display action
				DisplaySQL displaySQL = new DisplaySQL(connection);
			}
		});

		//setting up the panel and put it in the frame
		JPanel panel = new JPanel(new GridBagLayout());
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.gridx = 0;
		constraints.gridy = 0;
		constraints.anchor = GridBagConstraints.CENTER;
		constraints.insets = new Insets(5, 5, 5, 5);
		panel.add(insertButton, constraints);

		constraints.gridx = 1;
		panel.add(updateButton, constraints);

		constraints.gridx = 2;
		panel.add(displayButton, constraints);

		frame.add(panel);
		frame.setVisible(true);
	}
}
