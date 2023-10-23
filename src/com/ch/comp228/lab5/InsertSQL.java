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
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class InsertSQL {
	//connection
	private Connection connection;
	//frame and buttons
	private JFrame frame;
	private JButton gameButton;
	private JButton playerButton;
	private JButton playerAndGameButton;

	public InsertSQL(Connection connection) { //constructor
		this.connection = connection;
		frame = new JFrame("Insert Record");
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		gameButton = new JButton("Insert into Game");
		//event listener 
		gameButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				insertGameRecord();
			}
		});

		playerButton = new JButton("Insert into Player");
		//event listener 
		playerButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				insertPlayerRecord();
			}
		});

		playerAndGameButton = new JButton("Insert into PlayerAndGame");
		//event listener 
		playerAndGameButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				insertPlayerAndGameRecord();
			}
		});

		//instantiate a panel and add buttons 
		JPanel panel = new JPanel(new GridLayout(3, 1));
		panel.add(gameButton);
		panel.add(playerButton);
		panel.add(playerAndGameButton);

		//put the panel into the frame
		frame.add(panel);
		frame.pack();
		frame.setVisible(true);
	}

	private void insertGameRecord() { //insert data
		JTextField titleField = new JTextField(20);

		JPanel panel = new JPanel(new GridLayout(2, 2));
		panel.add(new JLabel("Game Title:"));
		panel.add(titleField);

		int result = JOptionPane.showConfirmDialog(null, panel, "Insert Game Record", JOptionPane.OK_CANCEL_OPTION);
		if (result == JOptionPane.OK_OPTION) {
			String gameTitle = titleField.getText();

			try {
				PreparedStatement statement = connection.prepareStatement("INSERT INTO Game (game_title) VALUES (?)");
				statement.setString(1, gameTitle);
				int rowsAffected = statement.executeUpdate();

				if (rowsAffected > 0) {
					JOptionPane.showMessageDialog(null, "Game record inserted successfully!");
				} else {
					JOptionPane.showMessageDialog(null, "Failed to insert game record.");
				}
			} catch (SQLException ex) {
				ex.printStackTrace();
				JOptionPane.showMessageDialog(null, "Failed to insert game record: " + ex.getMessage());
			}
		}
	}

	private void insertPlayerRecord() {
		JTextField firstNameField = new JTextField(20);
		JTextField lastNameField = new JTextField(20);
		JTextField addressField = new JTextField(20);
		JTextField postalCodeField = new JTextField(10);
		JTextField provinceField = new JTextField(20);
		JTextField phoneNumberField = new JTextField(20);

		//add the Jlabel and textField inside the panel
		JPanel panel = new JPanel(new GridLayout(6, 2));
		panel.add(new JLabel("First Name:"));
		panel.add(firstNameField);
		panel.add(new JLabel("Last Name:"));
		panel.add(lastNameField);
		panel.add(new JLabel("Address:"));
		panel.add(addressField);
		panel.add(new JLabel("Postal Code:"));
		panel.add(postalCodeField);
		panel.add(new JLabel("Province:"));
		panel.add(provinceField);
		panel.add(new JLabel("Phone Number:"));
		panel.add(phoneNumberField);

		int result = JOptionPane.showConfirmDialog(null, panel, "Insert Player Record", JOptionPane.OK_CANCEL_OPTION);
		if (result == JOptionPane.OK_OPTION) { //if user press 'ok' button
			String firstName = firstNameField.getText();
			String lastName = lastNameField.getText();
			String address = addressField.getText();
			String postalCode = postalCodeField.getText();
			String province = provinceField.getText();
			String phoneNumber = phoneNumberField.getText();

			try {
				PreparedStatement statement = connection.prepareStatement( //setting up the prepared statement 
						"INSERT INTO Player (first_name, last_name, address, postal_code, province, phone_number) VALUES (?, ?, ?, ?, ?, ?)");
				statement.setString(1, firstName);
				statement.setString(2, lastName);
				statement.setString(3, address);
				statement.setString(4, postalCode);
				statement.setString(5, province);
				statement.setString(6, phoneNumber);
				int rowsAffected = statement.executeUpdate(); //returns the number of rows affected by the operation

				if (rowsAffected > 0) { //to check if the row is inserted or not 
					JOptionPane.showMessageDialog(null, "Player record inserted successfully!");
				} else {
					JOptionPane.showMessageDialog(null, "Failed to insert player record.");
				}
			} catch (SQLException ex) {
				ex.printStackTrace();
				JOptionPane.showMessageDialog(null, "Failed to insert player record: " + ex.getMessage());
			}
		}
	}

	private void insertPlayerAndGameRecord() {
		//JComboBox and Text field
		JComboBox<String> gameTitleComboBox = new JComboBox<>();
		JComboBox<String> playerFNameComboBox = new JComboBox<>();
		JTextField playingDateField = new JTextField(20);
		JTextField scoreField = new JTextField(10);

		// Fetch available game IDs and player IDs from the respective tables
		try {
			PreparedStatement gameStatement = connection.prepareStatement("SELECT game_title FROM Game");
			ResultSet gameResultSet = gameStatement.executeQuery();
			while (gameResultSet.next()) { //looping thru the game title and add to the combo box
				String gameTitle = gameResultSet.getString("game_title");
				gameTitleComboBox.addItem(gameTitle);
			}

			PreparedStatement playerStatement = connection.prepareStatement("SELECT first_name FROM Player");
			ResultSet playerResultSet = playerStatement.executeQuery();
			while (playerResultSet.next()) { //looping thru the player name and add to the combo box
				String playerFName = playerResultSet.getString("first_name");
				playerFNameComboBox.addItem(playerFName);
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
			JOptionPane.showMessageDialog(null, "Failed to fetch game and player IDs: " + ex.getMessage());
		}

		JPanel panel = new JPanel(new GridLayout(4, 2));
		panel.add(new JLabel("Game Title:"));
		panel.add(gameTitleComboBox);
		panel.add(new JLabel("Player First Name:"));
		panel.add(playerFNameComboBox);
		panel.add(new JLabel("Playing Date (YYYY-MM-DD):"));
		panel.add(playingDateField);
		panel.add(new JLabel("Score:"));
		panel.add(scoreField);

		int result = JOptionPane.showConfirmDialog(null, panel, "Insert PlayerAndGame Record",
				JOptionPane.OK_CANCEL_OPTION);
		if (result == JOptionPane.OK_OPTION) { //if pressed OK
			String gameTitle = (String) gameTitleComboBox.getSelectedItem();
			String playerFName = (String) playerFNameComboBox.getSelectedItem();
			String playingDateStr = playingDateField.getText();
			int score = Integer.parseInt(scoreField.getText());

			try {

				// Retrieve game_id and player_id using the selected gameTitle and playerFName
				int gameId = getGameIdByTitle(gameTitle);
				int playerId = getPlayerIdByFirstName(playerFName);

				// Parse the playingDateStr to a java.sql.Date object
				java.sql.Date playingDate = java.sql.Date.valueOf(playingDateStr);

				PreparedStatement statement = connection.prepareStatement(
						"INSERT INTO PlayerAndGame (game_id, player_id, playing_date, score) VALUES (?, ?, ?, ?)");
				statement.setInt(1, gameId);
				statement.setInt(2, playerId);
				statement.setDate(3, playingDate);
				statement.setInt(4, score);
				int rowsAffected = statement.executeUpdate();

				if (rowsAffected > 0) {
					JOptionPane.showMessageDialog(null, "PlayerAndGame record inserted successfully!");
				} else {
					JOptionPane.showMessageDialog(null, "Failed to insert PlayerAndGame record.");
				}
			} catch (SQLException ex) {
				ex.printStackTrace();
				JOptionPane.showMessageDialog(null, "Failed to insert PlayerAndGame record: " + ex.getMessage());
			} catch (NumberFormatException ex) {
				ex.printStackTrace();
				JOptionPane.showMessageDialog(null,
						"Invalid score format. Please enter a valid integer value for the score.");
			} catch (IllegalArgumentException ex) {
				ex.printStackTrace();
				JOptionPane.showMessageDialog(null,
						"Invalid date format. Please enter a valid date in the format YYYY-MM-DD for the playing date.");
			}
		}
	}

	// Helper methods to retrieve game_id and player_id using game title and player
	// first name
	private int getGameIdByTitle(String gameTitle) throws SQLException {
		PreparedStatement statement = connection.prepareStatement("SELECT game_id FROM Game WHERE game_title = ?");
		statement.setString(1, gameTitle);
		ResultSet resultSet = statement.executeQuery();
		if (resultSet.next()) {
			return resultSet.getInt("game_id");
		}
		return -1; // Return -1 if game title not found
	}

	private int getPlayerIdByFirstName(String firstName) throws SQLException {
		PreparedStatement statement = connection.prepareStatement("SELECT player_id FROM Player WHERE first_name = ?");
		statement.setString(1, firstName);
		ResultSet resultSet = statement.executeQuery();
		if (resultSet.next()) {
			return resultSet.getInt("player_id");
		}
		return -1; // Return -1 if player first name not found
	}
} // InsertSQL ends
