<?php

include 'connection.php';
$group_id=$_GET['group_id'];

$consulta ="select * from switch where group_id='$group_id'";
$resultado=$connection -> query($consulta);

while($fila=$resultado -> fetch_array()){
    $switch[] = array_map('utf8_encode',$fila);
}

echo json_encode($switch);
$resultado -> close();

#include 'connection.php';
#$sql ="select * from switch";
#$datos=Array();
#$result=mysqli_query($connection,$sql);
#while($row=mysqli_fetch_object($result)){
#	$datos[]=$row;
#}
#echo json_encode($datos);
#mysqli_close($connection);
?>