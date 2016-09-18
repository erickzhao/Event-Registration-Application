package ca.mcgill.ecse321.eventregistration.persistence;

import java.util.Iterator;

import ca.mcgill.ecse321.eventregistration.model.Event;
import ca.mcgill.ecse321.eventregistration.model.Participant;
import ca.mcgill.ecse321.eventregistration.model.Registration;
import ca.mcgill.ecse321.eventregistration.model.RegistrationManager;

public class PersistenceEventRegistration {

	private static void initializeXStream(){
		PersistenceXStream.setFilename("eventregistration.xml");
		PersistenceXStream.setAlias("event", Event.class);
		PersistenceXStream.setAlias("participant", Participant.class);
		PersistenceXStream.setAlias("registration", Registration.class);
		PersistenceXStream.setAlias("manager", RegistrationManager.class);
	}
	
	public static void loadEventRegistrationModel(){
		RegistrationManager rm = RegistrationManager.getInstance();
		PersistenceEventRegistration.initializeXStream();
		RegistrationManager rm2 = (RegistrationManager) PersistenceXStream.loadFromXMLwithXStream();
		
		if (rm2!= null){
			//copy loaded model into singleton instance of RegistrationManager
			Iterator<Participant> participantIterator = rm2.getParticipants().iterator();
			while (participantIterator.hasNext()){
				rm.addParticipant(participantIterator.next());
			}
			Iterator<Event> eventIterator = rm2.getEvents().iterator();
			while (eventIterator.hasNext()){
				rm.addEvent(eventIterator.next());
			}
			Iterator<Registration> registrationIterator = rm2.getRegistrations().iterator();
			while (registrationIterator.hasNext()){
				rm.addRegistration(registrationIterator.next());
			}
		}
	}
}
