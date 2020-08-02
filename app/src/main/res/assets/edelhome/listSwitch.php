<?php

include 'connection.php';
$group_id=$_GET['group_id'];

$consulta ="call getGroupSwitches('".$group_id."')";
$resultado=$connection -> query($consulta);

while($fila=$resultado -> fetch_array()){
    $switch[] = array_map('utf8_encode',$fila);
}

echo json_encode($switch);
$resultado -> close();
?>