package com.ch.comp228.lab5;
/* Author: Chun Hin Yim
 * Date: 22/7/2023
 * Description: it develops a GUI Java application with data access capabilities.
 */
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DisplaySQL {
	//connection, Frame, ComboBox, Jtable
	private Connection connection;
	private JFrame frame;
	private JComboBox<Integer> playerIdComboBox;
	private JTable table;

	//constructor 
	public DisplaySQL(Connection connection) {
		this.connection = connection;
		frame = new JFrame("Display Player and Played Games Information");
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		playerIdComboBox = new JComboBox<>();
		fetchPlayerIds(); //fetching the player ids

		JButton displayButton = new JButton("Display");
		//adding event listener to display button
		displayButton.addActionListener(e -> displayPlayerInfo());

		JPanel panel = new JPanel();
		panel.add(new JLabel("Select Player ID:"));
		panel.add(playerIdComboBox);
		panel.add(displayButton);

		frame.add(panel, BorderLayout.NORTH);

		table = new JTable();
		JScrollPane scrollPane = new JScrollPane(table);
		frame.add(scrollPane, BorderLayout.CENTER);

		frame.pack();
		frame.setVisible(true);
	}

	private void fetchPlayerIds() {
		try {
			// Fetch player IDs from the Player table and populate the player ID combo box
			PreparedStatement statement = connection.prepareStatement("SELECT player_id FROM Player");
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				int playerId = resultSet.getInt("player_id");
				playerIdComboBox.addItem(playerId);
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
			JOptionPane.showMessageDialog(null, "Failed to fetch player IDs: " + ex.getMessage());
		}
	}

	private void displayPlayerInfo() {
		// Get the selected player ID from the combo box
		int selectedPlayerId = (int) playerIdComboBox.getSelectedItem();
		try {
			// Fetch player and played games information for the selected player ID from the
			// PlayerAndGame table
			PreparedStatement statement = connection.prepareStatement(
					"SELECT pag.player_game_id, pag.game_id, pag.player_id, pag.playing_date, pag.score, p.first_name, p.last_name, g.game_title "
							+ "FROM PlayerAndGame pag " + "JOIN Player p ON pag.player_id = p.player_id "
							+ "JOIN Game g ON pag.game_id = g.game_id " + "WHERE pag.player_id = ?");
			statement.setInt(1, selectedPlayerId);
			ResultSet resultSet = statement.executeQuery();

			// Prepare column names for the table
			String[] columnNames = { "Player Game ID", "Player Name", "Game Name", "Playing Date", "Score" };

			// Create a list to hold the table data
			List<Object[]> data = new ArrayList<>();

			while (resultSet.next()) {
				// Retrieve data for each row
				int playerGameId = resultSet.getInt("player_game_id");
				String playerFullName = resultSet.getString("first_name") + " " + resultSet.getString("last_name");
				String gameTitle = resultSet.getString("game_title");
				String playingDate = resultSet.getString("playing_date");
				int score = resultSet.getInt("score");

				// Create an array to hold the row data
				Object[] row = { playerGameId, playerFullName, gameTitle, playingDate, score };

				// Add the row data to the list
				data.add(row);
			}

			// Create a default table model and set the data
			DefaultTableModel tableModel = new DefaultTableModel(data.toArray(new Object[0][0]), columnNames);

			// Set the table model to the JTable
			table.setModel(tableModel);

		} catch (SQLException ex) {
			ex.printStackTrace();
			JOptionPane.showMessageDialog(null, "Failed to fetch player information: " + ex.getMessage());
		}
	}
}
