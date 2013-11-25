<?php


	$name = isset($_REQUEST['name'])?$_REQUEST['name']:"";
	$email= isset($_REQUEST['email'])?$_REQUEST['email']:"";

	$valid = isset($_REQUEST['submit'])
		&&$name!=""&&$email!=""
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
		        <li><a class="active" href="php">PHP - Fun!</a></li>
		        <li><a href="mysql">MYSQL - Fun!</a></li>
			</ul>
		</nav>
		<div id="wrapper">
			<h1>PHP Fun!</h1>
			<form action="." method="post" id="form">
				<input type="text" placeholder="Name" name="name" value="<?=$name?>"/>
				<input type="text" placeholder="Name@Email.com" name="email" value="<?=$email?>"/>
				<hr/>				
				<input type="submit" value="Go" name="submit"/>
			</form>
			<?php if($valid):?>
			<h1>Oh thanks....</h1>
			<p>Its <b><?=$name?>!</b> How <?=$isspecial?"EXTRAORDINARILY ":""?>brilliant</p>
			<p>And you can send <?=$name?> and email <a href='mailto:<?=$email?>' >here</a><small> (<?=$email?>)</small></p>
				<h1></h1>				
				<a href='.'>again?</a>
			<?php endif; ?>
		</div>
	</body>
</html>

