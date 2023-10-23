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

public class UpdateSQL {
	//fields
    private Connection connection;
    private JFrame frame;
    private JComboBox<Integer> playerIdComboBox;
    private JTextField firstNameField;
    private JTextField lastNameField;
    private JTextField addressField;
    private JTextField postalCodeField;
    private JTextField provinceField;
    private JTextField phoneNumberField;
    private JButton updateButton;

    //constructor
	public UpdateSQL(Connection connection) {
		this.connection = connection;
		frame = new JFrame("Update Player Information");
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
        playerIdComboBox = new JComboBox<>();
        fetchPlayerIds(); 
        
        firstNameField = new JTextField(20);
        lastNameField = new JTextField(20);
        addressField = new JTextField(20);
        postalCodeField = new JTextField(10);
        provinceField = new JTextField(20);
        phoneNumberField = new JTextField(20);

        updateButton = new JButton("Update Player");
        updateButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                updatePlayer();
            }
		});
        
        //panel
        JPanel panel = new JPanel(new GridLayout(8, 2));
        panel.add(new JLabel("Select Player ID:"));
        panel.add(playerIdComboBox);
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

        panel.add(updateButton);

        //putting the panel inside the frame
        frame.add(panel);
        frame.pack();
        frame.setVisible(true);
	}

	private void updatePlayer() {
	      int selectedPlayerId = (int) playerIdComboBox.getSelectedItem();
	        String firstName = firstNameField.getText();
	        String lastName = lastNameField.getText();
	        String address = addressField.getText();
	        String postalCode = postalCodeField.getText();
	        String province = provinceField.getText();
	        String phoneNumber = phoneNumberField.getText();

	        try {
	            PreparedStatement statement = connection.prepareStatement("UPDATE Player SET first_name = ?, last_name = ?, address = ?, postal_code = ?, province = ?, phone_number = ? WHERE player_id = ?");
	            statement.setString(1, firstName);
	            statement.setString(2, lastName);
	            statement.setString(3, address);
	            statement.setString(4, postalCode);
	            statement.setString(5, province);
	            statement.setString(6, phoneNumber);
	            statement.setInt(7, selectedPlayerId);
	            int rowsAffected = statement.executeUpdate();

	            if (rowsAffected > 0) {
	                JOptionPane.showMessageDialog(null, "Player information updated successfully!");
	            } else {
	                JOptionPane.showMessageDialog(null, "Failed to update player information.");
	            }
	        } catch (SQLException ex) {
	            ex.printStackTrace();
	            JOptionPane.showMessageDialog(null, "Failed to update player information: " + ex.getMessage());
	        }

	}
	public void fetchPlayerIds() {
		try {
			PreparedStatement statement = connection.prepareStatement("SELECT player_id FROM Player");
			ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int playerId = resultSet.getInt("player_id");
                playerIdComboBox.addItem(playerId);
            }
		}
		catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Failed to fetch player IDs: " + ex.getMessage());
        }
	}

}// UpdateSQL ends
