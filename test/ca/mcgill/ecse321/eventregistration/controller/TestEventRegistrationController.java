package ca.mcgill.ecse321.eventregistration.controller;

import static org.junit.Assert.*;

import java.io.File;
import java.sql.Time;
import java.sql.Date;
import java.util.Calendar;

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
	
	@Test
	public void testCreateEvent(){
		RegistrationManager rm = RegistrationManager.getInstance();
		assertEquals(0, rm.getEvents().size());
		
		String name = "Soccer Game";
		Calendar c = Calendar.getInstance();
		c.set(2016,Calendar.OCTOBER,16,9,00,0);
		Date eventDate = new Date(c.getTimeInMillis());
		Time startTime = new Time (c.getTimeInMillis());
		c.set(2016,Calendar.OCTOBER,16,10,30,0);
		Time endTime = new Time(c.getTimeInMillis());
		
		EventRegistrationController erc = new EventRegistrationController();
		try{
			erc.createEvent(name, eventDate, startTime, endTime);
		} catch (InvalidInputException e){
			//check that no error occurred
			fail();
		}
		
		//check model in memory
		checkResultEvent(name, eventDate, startTime, endTime, rm);
		
		RegistrationManager rm2 = (RegistrationManager) PersistenceXStream.loadFromXMLwithXStream();
		
		//check file contents
		checkResultEvent(name, eventDate, startTime, endTime, rm2);
	}
	
	@Test
	public void testCreateEventNull(){
		RegistrationManager rm = RegistrationManager.getInstance();
		assertEquals(0, rm.getEvents().size());
		
		String name = null;
		Date eventDate = null;
		Time startTime = null;
		Time endTime = null;
		
		String error = null;
		EventRegistrationController erc = new EventRegistrationController();
		try{
			erc.createEvent(name, eventDate, startTime, endTime);
		} catch (InvalidInputException e) {
			error = e.getMessage();
		}
		
		//check error
		assertEquals("Event name cannot be empty! Event date cannot be empty! Event start time cannot be empty! Event end time cannot be empty!", error);
		
		// check model in memory
		assertEquals(0, rm.getEvents().size());
	}
	
	@Test
	public void testCreateEventEmpty(){
		RegistrationManager rm = RegistrationManager.getInstance();
		assertEquals(0, rm.getEvents().size());
		
		String name = "";
		Calendar c = Calendar.getInstance();
		c.set(2016,Calendar.OCTOBER,16,9,00,0);
		Date eventDate = new Date(c.getTimeInMillis());
		Time startTime = new Time (c.getTimeInMillis());
		c.set(2016,Calendar.OCTOBER,16,10,30,0);
		Time endTime = new Time(c.getTimeInMillis());
		
		String error = null;
		EventRegistrationController erc = new EventRegistrationController();
		try{
			erc.createEvent(name, eventDate, startTime, endTime);
		} catch (InvalidInputException e) {
			error = e.getMessage();
		}
		
		// check error
		assertEquals("Event name cannot be empty!", error);
		
		// check model in memory
		assertEquals(0, rm.getEvents().size());
	}		
	
	@Test
	public void testCreateEventSpaces(){
		RegistrationManager rm = RegistrationManager.getInstance();
		assertEquals(0, rm.getEvents().size());
		
		String name = "";
		Calendar c = Calendar.getInstance();
		c.set(2016,Calendar.OCTOBER,16,9,00,0);
		Date eventDate = new Date(c.getTimeInMillis());
		Time startTime = new Time (c.getTimeInMillis());
		c.set(2016,Calendar.OCTOBER,16,10,30,0);
		Time endTime = new Time(c.getTimeInMillis());
		
		String error = null;
		EventRegistrationController erc = new EventRegistrationController();
		try{
			erc.createEvent(name, eventDate, startTime, endTime);
		} catch (InvalidInputException e) {
			error = e.getMessage();
		}
		
		// check error
		assertEquals("Event name cannot be empty!", error);
		
		// check model in memory
		assertEquals(0, rm.getEvents().size());
	}
	
	@Test
	public void testCreateEventEndTimeBeforeStartTime(){
		RegistrationManager rm = RegistrationManager.getInstance();
		assertEquals(0, rm.getEvents().size());
		
		String name = "Soccer Game";
		Calendar c = Calendar.getInstance();
		c.set(2016,Calendar.OCTOBER,16,9,00,0);
		Date eventDate = new Date(c.getTimeInMillis());
		Time startTime = new Time (c.getTimeInMillis());
		c.set(2016,Calendar.OCTOBER,16,8,59,59);
		Time endTime = new Time(c.getTimeInMillis());
		
		String error = null;
		EventRegistrationController erc = new EventRegistrationController();
		try{
			erc.createEvent(name, eventDate, startTime, endTime);
		} catch (InvalidInputException e) {
			error = e.getMessage();
		}
		
		// check error
		assertEquals("Event end time cannot be before event start time!", error);
		
		// check model in memory
		assertEquals(0, rm.getEvents().size());
	}
	
	
	
	@Test
	public void testRegister(){
		RegistrationManager rm = RegistrationManager.getInstance();
		assertEquals(0, rm.getRegistrations().size());
		
		String participantName = "Oscar";
		Participant participant = new Participant(participantName);
		rm.addParticipant(participant);
		assertEquals(1, rm.getParticipants().size());
		
		String eventName = "Soccer Game";
		Calendar c = Calendar.getInstance();
		c.set(2016,Calendar.OCTOBER,16,9,00,0);
		Date eventDate = new Date(c.getTimeInMillis());
		Time startTime = new Time(c.getTimeInMillis());
		c.set(2016, Calendar.OCTOBER,16,10,30,0);
		Time endTime = new Time (c.getTimeInMillis());
		Event event = new Event(eventName, eventDate, startTime, endTime);
		rm.addEvent(event);
		assertEquals(1, rm.getEvents().size());
		
		EventRegistrationController erc = new EventRegistrationController();
		try{
			erc.register(participant, event);
		} catch (InvalidInputException e){
			//check that no error occurred
			fail();
		}
		
		//check model in memory
		checkResultRegister(participantName, eventName, eventDate, startTime, endTime, rm);
		
		RegistrationManager rm2 = (RegistrationManager) PersistenceXStream.loadFromXMLwithXStream();
		
		//check file contents
		checkResultRegister(participantName, eventName, eventDate, startTime, endTime, rm2);
	}
	
	@Test
	public void testRegisterNull(){
		RegistrationManager rm = RegistrationManager.getInstance();
		assertEquals(0, rm.getRegistrations().size());
		
		Participant participant = null;
		assertEquals(0, rm.getParticipants().size());
		
		Event event = null;
		assertEquals(0, rm.getEvents().size());
		
		String error = null;
		EventRegistrationController erc = new EventRegistrationController();
		try{
			erc.register(participant, event);
		} catch (InvalidInputException e) {
			error = e.getMessage();
		}
		
		// check error
		assertEquals("Participant needs to be selected for registration! Event needs to be selected for registration!", error);
		
		// check model in memory
		assertEquals(0, rm.getRegistrations().size());
		assertEquals(0, rm.getParticipants().size());
		assertEquals(0, rm.getEvents().size());
	
	}
	
	@Test
	public void testRegisterParticipantAndEventDoNotExist() {
		RegistrationManager rm = RegistrationManager.getInstance();
		assertEquals(0, rm.getRegistrations().size());
		
		String nameP = "Oscar";
		Participant participant = new Participant(nameP);
		assertEquals(0, rm.getParticipants().size());
		
		String nameE = "Soccer Game";
		Calendar c = Calendar.getInstance();
		c.set(2016,Calendar.OCTOBER,16,9,00,0);
		Date eventDate = new Date(c.getTimeInMillis());
		Time startTime = new Time(c.getTimeInMillis());
		c.set(2016, Calendar.OCTOBER,16,10,30,0);
		Time endTime = new Time(c.getTimeInMillis());
		Event event = new Event(nameE, eventDate, startTime, endTime);
		assertEquals(0, rm.getEvents().size());
		
		String error = null;
		EventRegistrationController erc = new EventRegistrationController();
		try{
			erc.register(participant, event);
		} catch (InvalidInputException e){
			error = e.getMessage();
		}
		
		// check error
		assertEquals("Participant does not exist! Event does not exist!", error);
		
		// check model in memory
		assertEquals(0, rm.getRegistrations().size());
		assertEquals(0, rm.getParticipants().size());
		assertEquals(0, rm.getEvents().size());
	}

	private void checkResultParticipant(String name, RegistrationManager rm) {
		assertEquals(1, rm.getParticipants().size());
		assertEquals(name, rm.getParticipant(0).getName());
		assertEquals(0, rm.getEvents().size());
		assertEquals(0, rm.getRegistrations().size());
	}
	
	private void checkResultEvent(String name, Date eventDate, Time startTime, Time endTime, RegistrationManager rm){
		assertEquals(0, rm.getParticipants().size());
		assertEquals(1, rm.getEvents().size());
		assertEquals(name, rm.getEvent(0).getName());
		assertEquals(eventDate.toString(), rm.getEvent(0).getEventDate().toString());
		assertEquals(startTime.toString(),rm.getEvent(0).getStartTime().toString());
		assertEquals(endTime.toString(),rm.getEvent(0).getEndTime().toString());
		assertEquals(0, rm.getRegistrations().size());
	}
	
	private void checkResultRegister (String participantName, String eventName, Date eventDate, Time startTime, Time endTime, RegistrationManager rm){
		assertEquals(1, rm.getParticipants().size());
		assertEquals(participantName, rm.getParticipant(0).getName());
		assertEquals(1, rm.getEvents().size());
		assertEquals(eventName, rm.getEvent(0).getName());
		assertEquals(eventDate.toString(), rm.getEvent(0).getEventDate().toString());
		assertEquals(startTime.toString(), rm.getEvent(0).getStartTime().toString());
		assertEquals(endTime.toString(), rm.getEvent(0).getEndTime().toString());
		assertEquals(1, rm.getRegistrations().size());
		assertEquals(rm.getEvent(0), rm.getRegistration(0).getEvent());
		assertEquals(rm.getParticipant(0), rm.getRegistration(0).getParticipant());
	}

}
