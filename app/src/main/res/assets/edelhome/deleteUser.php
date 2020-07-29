<?php

include 'connection.php';
# declaramos las variables a usar, $_POST significa los datos que hemos enviado a este archivo

$var_user_id = $_POST['user_id'];
#$var_username = $_POST['username'];
#$var_email = $_POST['email'];
#$var_administrador = $_POST['administrador'];
# realizamos la consulta llamando al procedimiento
#delete from usuario where user_id='".$var_user_id."';
$consulta = "delete from usuario where user_id='".$var_user_id."'";
# mandamos la consulta, si sale error matamos la conexion y ejecutamos el error
mysqli_query($connection,$consulta) or die ('Error al actualizar la data del usuario '.mysqli_error($connection));
# Cerramos la conexion
mysqli_close($connection);

?>