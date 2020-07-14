<?php
include 'connection.php';
$var_usuario = $_POST['usuario'];
$var_password = $_POST['password'];

$sentencia = $connection->prepare("call doLogin(?,?)");
$sentencia -> bind_param('ss',$var_usuario,$var_password);
$sentencia -> execute();

$resultado = $sentencia->get_result();
if ($fila = $resultado->fetch_assoc()) {
         echo json_encode($fila,JSON_UNESCAPED_UNICODE);     
}
$sentencia->close();
$connection->close();
?>