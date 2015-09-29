<?php
/**
 * Note that the salt here is randomly generated.
 * Never use a static salt or one that is not randomly generated.
 *
 * For the VAST majority of use-cases, let password_hash generate the salt randomly for you
 */

$pw = "rasmuslerdorf";

$salt = mcrypt_create_iv(22, MCRYPT_DEV_URANDOM);


$options = [
    'cost' => 11,
    'salt' => $salt,
];


echo "pw:";
var_dump($pw);

echo "salt:";
var_dump($salt);

echo "hash:";
var_dump (password_hash(
	$pw, 
	PASSWORD_BCRYPT,
	$options));
?>