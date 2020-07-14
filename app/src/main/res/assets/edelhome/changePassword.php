<?php

include 'connection.php';
# declaramos las variables a usar, $_POST significa los datos que hemos enviado a este archivo
$var_user_id = $_POST['user_id'];
$var_new_pass = $_POST['new_pass'];
# realizamos la consulta llamando al procedimiento
$consulta = "call changePassword('".$var_user_id."','".$var_new_pass."')";
# mandamos la consulta, si sale error matamos la conexion y ejecutamos el error
mysqli($connection,$consulta) or die (mysqli_error());
# Cerramos la conexion
mysqli_close($connection);

?>