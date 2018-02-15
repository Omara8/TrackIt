<?php

include_once 'dbTrackit.php';

class User{

private $db;
private $db_table = "driver";

public function __construct(){
$this->db = new DbConnect();
}
public function createNewRegisterUser($username, $password, $firstname,$middlename,$lastname,$email,$age,$mobile,$company,$address,$ssn,$dln){

$query = "insert into driver (username,password,firstname,middlename,lastname,email,age,mobile,company,address,ssn,dln)
 values ('$username','$password','$firstname','$middlename','$lastname','$email','$age','$mobile','$company','$address','$ssn','$dln'))";
$inserted = mysqli_query($this->db->getDb(), $query);
echo $inserted ;


if($inserted == 1)
{
	$json['success'] = 1;
}
else
{
	$json['success'] = 0;
}

mysqli_close($this->db->getDb());

return $json;
}
public function checkNewRegisterUserExist($username)
{
	$query="select username from " . $this->db_table ."where username = '$username'"
	$result = mysqli_query($this->db->getDb(), $query);
	
	if($result==1)
	{
		$json=1;
	}
	else{
		$json=0;
	}
	
	return $json;
}
}
?>