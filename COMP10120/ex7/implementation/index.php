<!DOCTYPE html>
<html>
	<head>
		<title>James Peach</title>
		<base href="/mysql/"/>
		<link rel="stylesheet" type="text/css" href="style.css">
		<link rel="stylesheet" type="text/css" href="libs/snippet/main.css">
		<script src="libs/jquery/main.js"></script>
		<script src="libs/snippet/main.js"></script>
		<script>
			$(function(){
				$("pre").snippet("html",{style:"nedit"});
			});
		</script>
	</head>
	<body>
		<nav>
			<ul>
      			<li><a href="./">ABOUT ME</a></li>
		        <li><a class="active" href="implementation">IMPLEMENTATION</a></li>
		        <li><a href="php">PHP - Fun!</a></li>
		        <li><a href="mysql/mysql">MYSQL - Fun!</a></li>
			</ul>
		</nav>
		<div id="wrapper">
			<h1>Implementation ...</h1>
			<p>I created the markup for the document first.</p>
			<p>This included all the content i needed to show and used the correct tags and classes i would need to style the document.</p>
			<pre class="html"><?php
				echo htmlspecialchars(file_get_contents("../index.html"));
		    ?></pre>
			<p>Then i created a <span class="code">style.css</span> file to add style to the document</p>
			<p>When i was happy with the design i created this second page</p>
			<h1>Things i used to create this page ...</h1>
			<ul>
				<li>HTML</li>
				<li>CSS</li>
				<li>PHP</li>
				<li>MYSQL</li>
				<li><img src="implementation/st2-logo.png" width="20" height="20" alt="sublime text 2 logo">SUBLIME TEXT 2 <small>(editor)</small></li>
			</ul>
			
		</div>
	</body>
</html>

