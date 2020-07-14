<?php

include 'connection.php';
# declaramos las variables a usar, $_POST significa los datos que hemos enviado a este archivo
$var_switch_id = $_POST['switch_id'];
$var_place = $_POST['place'];
# realizamos la consulta llamando al procedimiento
$consulta = "call editSwitch('".$var_switch_id."','".$var_place."')";
# mandamos la consulta, si sale error matamos la conexion y ejecutamos el error
mysqli($connection,$consulta) or die (mysqli_error());
# Cerramos la conexion
mysqli_close($connection);

?>