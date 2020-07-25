<?php

include 'connection.php';
# declaramos las variables a usar, $_POST significa los datos que hemos enviado a este archivo
$sql ="select * from switch";
$datos=Array();
$result=mysqli_query($connection,$sql);
while($row=mysqli_fetch_object($result)){
	$datos[]=$row;
}
echo json_encode($datos);
# Cerramos la conexion
mysqli_close($connection);
?>