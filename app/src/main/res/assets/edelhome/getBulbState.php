<?php

include 'connection.php';
$var_switch_id = $_POST['switch_id'];
# Generando la consulta
$consulta= "call getBulbState('".$var_switch_id."')";
# guardando el resultado de la consulta
$resultado = $connection -> query($consulta);
 while($row=$resultado -> fetch_array()){
     # Añadiendo resultado en una variable
     $users[] = array_map('utf8_enconde', $row);
 }
 echo json_encode($users);
 #cerrando conexion
 $resultado -> close();
 
?>