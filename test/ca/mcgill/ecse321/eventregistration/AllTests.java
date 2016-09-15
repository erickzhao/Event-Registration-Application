package ca.mcgill.ecse321.eventregistration;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import ca.mcgill.ecse321.eventregistration.controller.TestEventRegistrationController;
import ca.mcgill.ecse321.eventregistration.persistence.TestPersistenceXStream;

@RunWith(Suite.class)
@SuiteClasses({TestEventRegistrationController.class, TestPersistenceXStream.class})
public class AllTests {

}
