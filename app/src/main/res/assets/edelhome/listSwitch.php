<?php

include 'connection.php';
$var_group_id = $_GET['group_id'];
# declaramos las variables a usar, $_POST significa los datos que hemos enviado a este archivo
$sql ="call getGroupSwitches('".$var_group_id."')";
$datos=Array();
$result=mysqli_query($connection,$sql);
while($row=mysqli_fetch_object($result)){
	$datos[]=$row;
}
echo json_encode($datos);
# Cerramos la conexion
mysqli_close($connection);
?>
