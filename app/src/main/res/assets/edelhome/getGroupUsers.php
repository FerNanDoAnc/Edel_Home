<?php

include 'connection.php';
$var_id = $_POST['group_id'];
// $var_id = 1;
# Generando la consulta
$consulta= "call getGroupUsers('".$var_id."')";
# guardando el resultado de la consulta
$resultado = $connection -> query($consulta) or die('Error al actualizar los usuarios del grupo '.mysqli_error($connection));
 while($row=$resultado -> fetch_array()){
     # Añadiendo resultado en una variable
     $users[] = array_map('utf8_encode', $row);
 }
 echo json_encode($users);
 #cerrando conexion
 $resultado -> close();
 
?>