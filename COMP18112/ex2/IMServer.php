<?php


/*
 *  IMServer.php - Simple instant messaging server - David Thorne / AIG / 14-01-2009
 *
 * 
 *  This PHP script simply acts like a shared dictionary. A client can either set a
 *  value with a 'set' command, read it back again using a 'get' command, or remove
 *  a value using an 'unset' command. Any other form of input will have no effect.
 *
 *  SET:
 *     IMServer.php?action=set&key=KEYTOSET&value=VALUETOSET
 *     returns nothing
 *
 *  GET:
 *     IMServer.php?action=get&key=KEYTOSET
 *     returns the stored message
 *
 *  UNSET:
 *     IMServer.php?action=unset&key=KEYTOSET
 *     returns nothing
 *
 */



// Get parameters from query string
$action =  $_GET['action'];
$key = $_GET['key'];
$value = $_GET['value'];


// Read in values from file
$uri = $_SERVER['REQUEST_URI'];
if (preg_match('/~[a-zA-Z0-9]+/', $uri))
{
  $username = preg_replace('/[^~]*~([a-zA-Z0-9]*).*/', '$1', $uri);
}
else
{
  $username = 'aig';
}
define("DATA_FILE",
    "/tmp/im.".$username.".data");
$_APP = array();
if (file_exists(DATA_FILE))
{
	// Read data file
	$file = fopen(DATA_FILE, "r");
	if ($file)
	{
		$data = fread($file, filesize(DATA_FILE));
		fclose($file);
		// build application variables from data file
		$_APP = unserialize($data);
	}
}


// Deal with request
switch ($action)
{

  case 'get':
    // Return the dictionary value
    print $_APP[$key];
    break;

  case 'set':
    // Set the dictionary value
    $_APP[$key] = $value;
    break;
    
  case 'unset':
    // Unset a dictionary item
    unset($_APP[$key]);
    break;
    
  default:
    // for any other action, just print out 
    // a reassuring message
    echo "<html><body><h1>COMP18112 IM Server</h1>";
    if (file_exists(DATA_FILE))
    {
       include DATA_FILE;
    }
    echo "</body></html>";
    break;
  
}


// Write data back to file
$data = serialize($_APP);
$file = fopen(DATA_FILE, "w");
if ($file)
{
	fwrite($file, $data);
	fclose($file);
}


?>
