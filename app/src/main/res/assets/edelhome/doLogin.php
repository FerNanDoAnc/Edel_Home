<?php
include 'connection.php';
$var_usuario = $_POST['usuario'];
$var_password = $_POST['password'];

date_default_timezone_set('America/Lima');
$var_last_login = date('d/m/Y', time());

$sentencia = "call doLogin('".$var_usuario."','".$var_password."','".$var_last_login."')";
$result = mysqli_query($connection,$sentencia);

while($row=mysqli_fetch_object($result)){
	$datos=$row;
}
echo json_encode($datos,JSON_UNESCAPED_UNICODE);

mysqli_close($connection);
?>

