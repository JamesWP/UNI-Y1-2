<?php include_once("../config.inc.php");

	$db = new mysqli($database_host,$database_user,$database_pass,$database_name);

	if(isset($_REQUEST['name']))
		$name = $db->escape_string($_REQUEST['name']);
	else $name = '';
	if(isset($_REQUEST['email']))
		$email= $db->escape_string($_REQUEST['email']);
	else $email = '';

	if($db -> connect_error){
		die("Database connection error");
	}
	$valid = $name!=""&&$email!=""
		&&filter_var($email, FILTER_VALIDATE_EMAIL);
	
	$isspecial = $valid && in_array($name,array('James','Fran','Alex'));
?>
<!DOCTYPE html>
<html>
	<head>
		<title>James Peach</title>
		<base href="/mysql/"/>
		<link rel="stylesheet" type="text/css" href="style.css">
	</head>
	<body>
		<nav>
			<ul>
		        <li><a href="./">ABOUT ME</a></li>
		        <li><a href="implementation">IMPLEMENTATION</a></li>
		        <li><a href="php">PHP - Fun!</a></li>
		        <li><a class="active" href="mysql">MYSQL - Fun!</a></li>
			</ul>
		</nav>
		<div id="wrapper">
			<h1>MYSQL Fun!</h1>
			<form action="mysql/" method="post" id="form">
				<input type="text" placeholder="Name" name="name" value="<?=$name?>"/>
				<input type="text" placeholder="Name@Email.com" name="email" value="<?=$email?>"/>
				<hr/>				
				<input type="submit" value="Go" name="submit"/>
			</form>
			<?php if($isspecial): ?>
				<h1>Your name reminds me of someone...</h1>
			<?php endif; ?>
			<?php 
			if($valid){
				$query = "insert into address (name,email) values ('$name','$email')";
				if($db->query($query)){
					// success
					echo "<hr/><b class='centre'>Yay successfully added to table!</b><hr/>";
				}else if(mysqli_errno($db)==1062){
					echo "<hr/><b class='centre'>Whoops someone with that name already exists so i could not add you to the database sorry...</b><hr/>";
				}else{
					echo "<hr/><b class='centre'>Whoops could not add you to the database sorry... (".mysqli_errno($db).")</b><hr/>";
				}
			}
			?>

			<h1>Table of addresses</h1>
			<?php

				$db = new mysqli($database_host,$database_user,$database_pass,$database_name);

				if($db -> connect_error){
					die("Database connection error");
				}
				$query = "select
					id,
					name,
					email,
					case when name='$name' && email='$email'
					 then 'found'
					 else '' end as `class`
					from address;";
				if($results = $db->query($query)){
					
					echo "<table>";
						echo "<thead><th>ID</th><th>Name</th><th>E-Mail</th></thead>";
					while($row = $results->fetch_assoc()){
						echo "<tr class='".$row['class']."'>";
							echo "<td>".$row['id']."</td>";
							echo "<td>".$row['name']."</td>";
							echo "<td>".$row['email']."</td>";
						echo "</tr>";
					}
					echo "</table>";

					$results -> close();
				}else{
					// error!
					die("Error");
				}


				$db -> close();
				?>
		</div>
	</body>
</html>