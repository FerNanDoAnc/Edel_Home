<?php
 $hostname = 'localhost';
 $username = 'root';
 $password = '1234';
 $database = 'edelhome';

 $connection = new mysqli($hostname,$username,$password,$database);
    if($connection -> connect_errno){
        echo "Ha ocurrido un error inesperado, estamos trabajando para solucionarlo";
    }
?>