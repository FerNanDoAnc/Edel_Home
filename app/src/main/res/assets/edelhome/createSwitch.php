<?php

include 'connection.php';
# declaramos las variables a usar, $_POST significa los datos que hemos enviado a este archivo
$var_place = $_POST['place'];
$var_bulb_state = $_POST['bulb_state'];
$var_group_id = $_POST['group_id'];

# realizamos la consulta llamando al procedimiento
$consulta = "call createSwitch('".$var_place."','".$var_bulb_state."','".$var_group_id."')";
# mandamos la consulta, si sale error matamos la conexion y ejecutamos el error
mysqli_query($connection,$consulta) or die ("Hubo un error al crear un switch".mysqli_error($connection));
# Cerramos la conexion
mysqli_close($connection);

?>