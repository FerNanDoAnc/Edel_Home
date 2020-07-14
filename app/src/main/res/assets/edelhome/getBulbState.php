<?php

include 'connection.php';
// $var_switch_id = $_POST['switch_id'];
$var_switch_id = 5;
# Generando la consulta
$consulta= "call getBulbState('".$var_switch_id."')";
# guardando el resultado de la consulta
$resultado = $connection -> query($consulta) or die('Error al obtener el estado del foco: '.mysqli_error($connection));
 while($row=$resultado -> fetch_array()){
     # Añadiendo resultado en una variable
     $users[] = array_map('utf8_encode', $row);
 }
 echo json_encode($users);
 #cerrando conexion
 $resultado -> close();
 
?>