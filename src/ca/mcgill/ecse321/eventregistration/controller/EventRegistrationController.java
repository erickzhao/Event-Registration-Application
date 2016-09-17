package ca.mcgill.ecse321.eventregistration.controller;

import ca.mcgill.ecse321.eventregistration.model.Participant;
import ca.mcgill.ecse321.eventregistration.model.RegistrationManager;
import ca.mcgill.ecse321.eventregistration.persistence.PersistenceXStream;

public class EventRegistrationController {
	
	public EventRegistrationController(){
	}
	
	public void createParticipant(String name) throws InvalidInputException{
		
		if (name==null || name.trim().length() == 0){
			throw new InvalidInputException("Participant name cannot be empty!");
		}
		
		Participant p = new Participant(name);
		RegistrationManager rm = RegistrationManager.getInstance();
		rm.addParticipant(p);
		PersistenceXStream.saveToXMLwithXStream(rm);
	}

}
