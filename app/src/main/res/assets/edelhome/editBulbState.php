<?php

include 'connection.php';
# declaramos las variables a usar, $_POST significa los datos que hemos enviado a este archivo
$var_switch_id = $_POST['switch_id'];
$var_bulb_state = $_POST['bulb_state'];

# realizamos la consulta llamando al procedimiento
$consulta = "call editBulbState('".$var_switch_id."','".$var_bulb_state."')";
# mandamos la consulta, si sale error matamos la conexion y ejecutamos el error
mysqli_query($connection,$consulta) or die ("Hubo un error al cambiar el estado del foco".mysqli_error($connection));
# Cerramos la conexion
mysqli_close($connection);

?>