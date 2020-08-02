<?php
include 'connection.php';
$var_usuario = $_POST['usuario'];
$var_password = $_POST['password'];

date_default_timezone_set('America/Lima');
$var_last_login = $date = date('d/m/Y', time());

$sentencia = $connection->prepare("call doLogin(?,?,?)");
$sentencia -> bind_param('sss',$var_usuario,$var_password,$var_last_login);
$sentencia -> execute();

$resultado = $sentencia->get_result();
if ($fila = $resultado->fetch_assoc()) {
         echo json_encode($fila,JSON_UNESCAPED_UNICODE);     
}
$sentencia->close();
$connection->close();
?>