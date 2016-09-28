<?php
require_once 'C:\Users\Andre\Desktop\Andrei\Projects\ECSE321\EventRegistrationApplication\workspace-web\EventRegistration\EventRegistration\persistence\PersistenceEventRegistration.php';
require_once 'C:\Users\Andre\Desktop\Andrei\Projects\ECSE321\EventRegistrationApplication\workspace-web\EventRegistration\EventRegistration\model\Participant.php';
require_once 'C:\Users\Andre\Desktop\Andrei\Projects\ECSE321\EventRegistrationApplication\workspace-web\EventRegistration\EventRegistration\model\RegistrationManager.php';

class PersistenceEventRegistrationTest extends PHPUnit_Framework_TestCase{
	protected $pm;
	
	protected function setUp(){
		$this->pm = new PersistenceEventRegistration();
	}
	
	protected function tearDown(){
		
	}
	public function testPersistence(){
		//1. Creates test data
		$rm = RegistrationManager::getInstance();
		$participant = new Participant("Frank");
		$rm->addParticipant($participant);
		
		//2. Write all the data
		$this->pm->writeDataToStore($rm);
		
		//3. Clear data from memory
		$rm->delete();
		
		$this->assertEquals(0, count($rm->getParticipants()));
		
		//4. Load back in
		$rm = $this->pm->loadDataFromStore();
		
		//5. Check that we got it back
		$this->assertEquals(1,count($rm->getParticipants()));
		$myParticipant = $rm->getParticipant_index(0);
		$this->assertEquals("Frank", $myParticipant->getName());
	}
}
?>