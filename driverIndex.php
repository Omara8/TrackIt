<?php
include_once 'driverTrackit.php';

$username="";
$password="";
$firstname="";
$middlename="";
$lastname="";
$email="";
$age="";
$mobile="";
$company="";
$address="";
$ssn="";
$dln="";
if(isset($_POST['username']))
{
	$username = $_POST['username'];
}
if(isset($_POST['password']))
{
	$password = $_POST['password'];
}
if(isset($_POST['firstname']))
{
	$firstname = $_POST['firstname'];
}
if(isset($_POST['middlename']))
{
	$middlename = $_POST['middlename'];
}
if(isset($_POST['email']))
{
	$email = $_POST['email'];
}
if(isset($_POST['age']))
{
	$age = $_POST['age'];
}
if(isset($_POST['mobile']))
{
	$mobile = $_POST['mobile'];
}
if(isset($_POST['company']))
{
	$company = $_POST['company'];
}
if(isset($_POST['address']))
{
	$address = $_POST['address'];
}
if(isset($_POST['SSN']))
{
	$ssn = $_POST['SSN'];
}
if(isset($_POST['DLN']))
{
	$dln = $_POST['DLN'];
}

// Instance of a User class
$userObject = new User();

// Registration of new user
if(!empty($username) && !empty($password) && !empty($firstname) && !empty($middlename) && !empty($lastname) && !empty($email) && !empty($age) &&
!empty($mobile) && !empty($company) && !empty($address) && !empty($ssn) && !empty($dln))
{
	$userExist=$userObject->checkNewRegisterUserExist($username);
	if($userExist==0)
	{
		$hashed_password = md5($password);
		$json_registration = $userObject->createNewRegisterUser($username, $hashed_password, $firstname,$middlename,$lastname,$email,$age,$mobile,$company
							$address,$ssn,$dln);
	}
}


?>