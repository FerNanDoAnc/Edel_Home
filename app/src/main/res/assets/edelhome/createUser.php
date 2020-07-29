<?php

include 'connection.php';
# declaramos las variables a usar, $_POST significa los datos que hemos enviado a este archivo
$var_username = $_POST['username'];
$var_email = $_POST['email'];
$var_pass = $_POST['pass'];
#$var_administrador = $_POST['administrador'];
#$var_group_id = $_POST['group_id'];

# realizamos la consulta llamando al procedimiento
$consulta="INSERT INTO usuario(username,email,pass) VALUES('"$var_username."','".$var_email."','".$var_pass."');"
#$consulta = "call createUser('".$var_username."','".
#$var_email."','".$var_pass."')
# mandamos la consulta, si sale error matamos la conexion y ejecutamos el error
mysqli_query($connection,$consulta) or die ("Hubo un error al crear el usuario".mysqli_error($connection));
# Cerramos la conexion
mysqli_close($connection);

?>