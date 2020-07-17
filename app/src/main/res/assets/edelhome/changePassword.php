<?php

include 'connection.php';

$var_user_id = $_POST['user_id'];
$var_new_pass = $_POST['new_pass'];
$var_actual_pass = $_POST['actual_pass'];

$changePassQuery = "call changePassword('".$var_user_id."','".$var_new_pass."','".$var_actual_pass."')";

$verificateQuery = $connection->prepare("call verificateUser(?,?)");
$verificateQuery -> bind_param('ss',$var_user_id, $var_actual_pass);
$verificateQuery -> execute();

$resultado = $verificateQuery->get_result();
if ($fila = $resultado->fetch_assoc()) {
    $verificateQuery->close();
    mysqli_query($connection,$changePassQuery);
    echo 'OK';
} else {
    http_response_code(400);
}
$connection->close();

?>