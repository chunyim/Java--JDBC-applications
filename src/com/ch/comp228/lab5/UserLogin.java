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
import java.sql.DriverManager;
import java.sql.SQLException;

public class UserLogin {
	// MySQL db_url
	private static final String DB_URL = "jdbc:mysql://localhost:3306/lab5";
	// MySQL user name
	private static final String USERNAME = "root";
	// MySQL password
	private static final String PASSWORD = "123456";

	// using JFrame and declare all the field and buttons below
	private JFrame frame;
	private JTextField usernameField;
	private JPasswordField passwordField;
	private JButton loginButton;

	// constructor
	public UserLogin() {
		//on frame
		frame = new JFrame("User Login"); // frame name
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // exit on close
		frame.setSize(300, 150); // size

		//on fields and button
		usernameField = new JTextField(20);
		passwordField = new JPasswordField(20);
		loginButton = new JButton("Login");
		//button's event listener
		loginButton.addActionListener(new ActionListener() { // add event listener on login button
			public void actionPerformed(ActionEvent e) {
				String username = usernameField.getText();
				String password = new String(passwordField.getPassword());

				try {
					// Register Driver Class
					Class.forName("com.mysql.cj.jdbc.Driver");
					// Connection to the database
					Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/lab5", username,
							password);
					JOptionPane.showMessageDialog(null, "Connected to the database successfully!");

					// instantiate SQLAction
					SQLAction sqlAction = new SQLAction(connection);
					sqlAction.display();

				} catch (ClassNotFoundException ex) {
					ex.printStackTrace();
					JOptionPane.showMessageDialog(null, "MySQL Connector/J library not found!");
				} catch (SQLException ex) {
					ex.printStackTrace();
					JOptionPane.showMessageDialog(null, "Failed to connect to the database!");
				}
			}
		});
		JPanel panel = new JPanel(new GridBagLayout());
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.gridx = 0; //set the grid coordinates
		constraints.gridy = 0; //set the grid coordinates
		constraints.insets = new Insets(5, 5, 5, 5); // padding
		panel.add(new JLabel("Username:"), constraints);

		constraints.gridy = 1;
		panel.add(new JLabel("Password:"), constraints);

		constraints.gridx = 1;
		constraints.gridy = 0;
		panel.add(usernameField, constraints);

		constraints.gridy = 1;
		panel.add(passwordField, constraints);

		constraints.gridx = 0;
		constraints.gridy = 2;
		constraints.gridwidth = 2;
		panel.add(loginButton, constraints);

		frame.add(panel);
		frame.setVisible(true);
	}

	public static void main(String[] args) {
		new UserLogin();
	}
}