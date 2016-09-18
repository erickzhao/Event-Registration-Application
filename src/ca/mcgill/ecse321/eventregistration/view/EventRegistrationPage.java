package ca.mcgill.ecse321.eventregistration.view;

import java.awt.Color;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;

import ca.mcgill.ecse321.eventregistration.controller.EventRegistrationController;
import ca.mcgill.ecse321.eventregistration.controller.InvalidInputException;

public class EventRegistrationPage extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8062635784771606869L;
	
	//UI elements
	private JLabel errorMessage;
	private JTextField participantNameTextField;
	private JLabel participantNameLabel;
	private JButton addParticipantButton;
	
	//data elements
	private String error = null;
	/** Creates new form EventRegistrationPage */
	public EventRegistrationPage(){
		initComponents();
		refreshData();
	}
	
	/** This method is called from within the constructor to initialize the form.
	 */
	private void initComponents(){
		
		//elements for error message
		errorMessage = new JLabel();
		errorMessage.setForeground(Color.RED);
		
		//elements for participant
		participantNameTextField = new JTextField();
		participantNameLabel = new JLabel();
		addParticipantButton = new JButton();
		
		//global settings and listeners
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setTitle("Event Registration");
		
		participantNameLabel.setText("Name:");
		addParticipantButton.setText("Add Participant");
		addParticipantButton.addActionListener(new java.awt.event.ActionListener(){
			public void actionPerformed(java.awt.event.ActionEvent evt){
				addParticipantButtonActionPerformed(evt);
			}
		});
		
		//layout
		GroupLayout layout = new GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);
		layout.setHorizontalGroup(
				layout.createParallelGroup()
				.addComponent(errorMessage)
				.addGroup(layout.createSequentialGroup()
				.addComponent(participantNameLabel)
				.addGroup(layout.createParallelGroup()
					.addComponent(participantNameTextField,200,200,400)
					.addComponent(addParticipantButton)))
				);
		layout.linkSize(SwingConstants.HORIZONTAL, new java.awt.Component[] {addParticipantButton, participantNameTextField});
	
			layout.setVerticalGroup(
					layout.createSequentialGroup()
					.addComponent(errorMessage)
					.addGroup(layout.createParallelGroup()
							.addComponent(participantNameLabel)
							.addComponent(participantNameTextField))
					.addComponent(addParticipantButton)
					);
			
			pack();
	}
	
	private void refreshData(){
		//error
		errorMessage.setText(error);
		//participant
		participantNameTextField.setText("");
		
		//needed because of window size changing when error msg appears
		pack();
	}
	
	private void addParticipantButtonActionPerformed(java.awt.event.ActionEvent evt){
		//call the controller
		EventRegistrationController erc = new EventRegistrationController();
		error = null;
		try {
			erc.createParticipant(participantNameTextField.getText());
		} catch (InvalidInputException e) {
			error = e.getMessage();
		}
		//update visuals
		refreshData();
	}
}
