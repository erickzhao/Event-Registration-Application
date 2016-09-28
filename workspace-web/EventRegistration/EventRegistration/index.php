<!DOCTYPE html>
<html>
	<head> 
		<meta charset="UTF-8">
		<title> Event Registration </title>
		<style>
			.error {color:#FF0000;}
		</style>
	</head>
	<body>
		<?php 
			require_once "model/Participant.php";
			require_once "model/Registration.php";
			require_once "model/RegistrationManager.php";
			require_once "model/Event.php";
			require_once "persistence/PersistenceEventRegistration.php";
			session_start();
			
			//Retreive data from the model
			$pm = new PersistenceEventRegistration();
			$rm = $pm->loadDataFromStore();
			
			echo "<h1>Register</h1>";
			echo "<form action='register.php' method='post'>";
			echo "<fieldset>";
			echo "<p>Name? <select name='participantspinner'>";
			foreach ($rm->getParticipants() as $participant){
				echo "<option>" . $participant->getName() . "</option>";
			}
			echo "</select><span class='error'>";
			if (isset($_SESSION['errorRegisterParticipant']) && !empty($_SESSION['errorRegisterParticipant'])){
				echo " * " . $_SESSION["errorRegisterParticipant"];
			}
			echo "</span></p>";
			echo "<p>Event? <select name='eventspinner'>";
			foreach ($rm->getEvents() as $event){
				echo "<option>" . $event->getName() . "</option>";
			}
			echo "</select><span class='error'>";
			if (isset($_SESSION['errorRegisterEvent']) && !empty($_SESSION['errorRegisterEvent'])){
				echo " * " . $_SESSION["errorRegisterEvent"];
			}
			echo "</span></p>";
			echo "<p><input type='submit' value='Register'/></p>";
			echo "</fieldset>";
			echo "</form>";
		?>
		<h1> Add Participant </h1>
		<form action = "addparticipant.php" method="post">
			<fieldset>
			<br>
			<p>Name? <input type="text" name="participant_name" placeholder="Enter your name here"/>
			<span class="error">
			<?php
			if (isset($_SESSION['errorParticipantName']) && !empty($_SESSION['errorParticipantName'])){
				echo " * " . $_SESSION["errorParticipantName"];
			}
			?>
			</span></p>	
			<p><input type="submit" value="Add Participant"/></p>
			<br>
			</fieldset>
		</form>

		<h1>Add Event</h1>
		<form action = "addevent.php" method="post">
			<fieldset>
			<br>
			<p> Event Name: <input type="text" name="event_name" placeholder="Enter event name here"/>
			<span class="error">
			<?php
			if (isset($_SESSION['errorEventName']) && !empty($_SESSION['errorEventName'])){
				echo " * " . $_SESSION["errorEventName"];
			} 
			?>
			</span></p>
			<br>
			<p> Event Date: <input type="date" name="event_date" value="<?php echo date('Y-m-d'); ?>" />
			<span class="error">
			<?php
			if (isset($_SESSION['errorEventDate']) && !empty($_SESSION['errorEventDate'])){
				echo " * " . $_SESSION["errorEventDate"];
			} 
			?>
			</span></p>
			<br>
			<p> Event Start Time: <input type="time" name="starttime" value="<?php echo date('H:i'); ?>" />
			<span class="error">
			<?php
			if (isset($_SESSION['errorEventstarttime']) && !empty($_SESSION['errorEventstarttime'])){
				echo " * " . $_SESSION["errorEventstarttime"];
			} 
			?>
			</span></p>
			<br>
			<p> Event End Time: <input type="time" name="endtime" value="<?php echo date('H:i'); ?>" />
			<span class="error">
			<?php
			if (isset($_SESSION['errorEventendtime']) && !empty($_SESSION['errorEventendtime'])){
				echo " * " . $_SESSION["errorEventendtime"];
			} 
			?>
			</span></p>
			<br>
			<input type="submit" value="Add event">	
			<br>
			</fieldset>
		</form>
	</body>
</html>

