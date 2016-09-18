package ca.mcgill.ecse321.eventregistration.controller;

import java.sql.Time;
import java.sql.Date;

import ca.mcgill.ecse321.eventregistration.model.Event;
import ca.mcgill.ecse321.eventregistration.model.Participant;
import ca.mcgill.ecse321.eventregistration.model.Registration;
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
	
	public void createEvent(String name, Date date, Time startTime, Time endTime) throws InvalidInputException{
		Event e = new Event(name, date, startTime, endTime);
		RegistrationManager rm = RegistrationManager.getInstance();
		rm.addEvent(e);
		PersistenceXStream.saveToXMLwithXStream(rm);
	}
	
	public void register(Participant participant, Event event) throws InvalidInputException{
		RegistrationManager rm = RegistrationManager.getInstance();
		Registration r = new Registration(participant, event);
		rm.addRegistration(r);
		PersistenceXStream.saveToXMLwithXStream(rm);
	}

}
