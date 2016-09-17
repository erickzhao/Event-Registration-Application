package ca.mcgill.ecse321.eventregistration.controller;

import static org.junit.Assert.*;

import java.io.File;

import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

import ca.mcgill.ecse321.eventregistration.controller.EventRegistrationController;
import ca.mcgill.ecse321.eventregistration.model.Event;
import ca.mcgill.ecse321.eventregistration.model.Participant;
import ca.mcgill.ecse321.eventregistration.model.Registration;
import ca.mcgill.ecse321.eventregistration.model.RegistrationManager;
import ca.mcgill.ecse321.eventregistration.persistence.PersistenceXStream;

public class TestEventRegistrationController {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		PersistenceXStream.setFilename("test"+File.separator+"ca"+File.separator+"mcgill"+File.separator+"ecse321"+File.separator+"eventregistration"+File.separator+"controller"+File.separator+"data.xml");
		PersistenceXStream.setAlias("event",Event.class);
		PersistenceXStream.setAlias("participant", Participant.class);
		PersistenceXStream.setAlias("registration", Registration.class);
		PersistenceXStream.setAlias("manager",RegistrationManager.class);
	}

	@After
	public void tearDown() throws Exception {
		//clear all registrations
		RegistrationManager rm = RegistrationManager.getInstance();
		rm.delete();
	}

	@Test
	public void testCreateParticipant() {
		RegistrationManager rm = RegistrationManager.getInstance();
		assertEquals(0, rm.getParticipants().size());
		
		String name = "Oscar";
		
		EventRegistrationController erc = new EventRegistrationController();
		try{
			erc.createParticipant(name);
		} catch(InvalidInputException e){
			fail();
		}
		
		checkResultParticipant(name, rm);
		
		RegistrationManager rm2 = (RegistrationManager)PersistenceXStream.loadFromXMLwithXStream();
		
		//check file contents
		checkResultParticipant(name, rm2);
		
	}
	
	@Test
	public void testCreateParticipantNull(){
		RegistrationManager rm = RegistrationManager.getInstance();
		assertEquals(0, rm.getParticipants().size());
		
		String name = null;
		
		String error = null;
		EventRegistrationController erc = new EventRegistrationController();
		try{
			erc.createParticipant(name);
		} catch (InvalidInputException e){
			error = e.getMessage();
		}
		
		//check error
		assertEquals("Participant name cannot be empty!", error);
		
		//check no change in memory
		assertEquals(0, rm.getParticipants().size());
	}
	
	@Test
	public void testCreateParticipantEmpty(){
		RegistrationManager rm = RegistrationManager.getInstance();
		assertEquals(0, rm.getParticipants().size());
		
		String name = "";
		
		String error = null;
		EventRegistrationController erc = new EventRegistrationController();
		try{
			erc.createParticipant(name);
		} catch (InvalidInputException e){
			error = e.getMessage();
		}
		
		//check error
		assertEquals("Participant name cannot be empty!", error);
		
		//check no change in memory
		assertEquals(0, rm.getParticipants().size());
	}
	
	@Test
	public void testCreateParticipantSpaces(){
		RegistrationManager rm = RegistrationManager.getInstance();
		assertEquals(0, rm.getParticipants().size());
		
		String name = " ";
		
		String error = null;
		EventRegistrationController erc = new EventRegistrationController();
		try{
			erc.createParticipant(name);
		} catch (InvalidInputException e){
			error = e.getMessage();
		}
		
		//check error
		assertEquals("Participant name cannot be empty!", error);
		
		//check no change in memory
		assertEquals(0, rm.getParticipants().size());
	}

	private void checkResultParticipant(String name, RegistrationManager rm) {
		assertEquals(1, rm.getParticipants().size());
		assertEquals(name, rm.getParticipant(0).getName());
		assertEquals(0, rm.getEvents().size());
		assertEquals(0, rm.getRegistrations().size());
	}

}
