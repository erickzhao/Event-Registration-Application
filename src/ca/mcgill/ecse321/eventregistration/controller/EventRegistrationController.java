package ca.mcgill.ecse321.eventregistration.controller;

import ca.mcgill.ecse321.eventregistration.model.Participant;
import ca.mcgill.ecse321.eventregistration.model.RegistrationManager;
import ca.mcgill.ecse321.eventregistration.persistence.PersistenceXStream;

public class EventRegistrationController {
	
	public EventRegistrationController(){
	}
	
	public void createParticipant(String name){
		Participant p = new Participant(name);
		RegistrationManager rm = RegistrationManager.getInstance();
		rm.addParticipant(p);
		PersistenceXStream.saveToXMLwithXStream(rm);
	}

}
