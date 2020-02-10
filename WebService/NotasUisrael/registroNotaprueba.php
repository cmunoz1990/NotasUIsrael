<?php
	$hostname_localhost = "localhost" ;
	$database_localhost = "notasuisrael" ;
	$username_localhost = "root" ;
	$password_localhost = "" ;

	$n1= 1;
	$n2= 11;
	$n3= 12;
	$n4= 13;

	$conexion = mysqli_connect ( $hostname_localhost , $username_localhost , $password_localhost , $database_localhost );
	if($conexion){
		echo "si con ";
	}else {
		echo "noCon";
	}

	$insert="INSERT INTO pruebanota(nota1,nota2,nota3,nota4) 
		VALUES(4,4,4,4)";
	$resultado_insert = mysqli_query ( $conexion , $insert);
			
	if ( $resultado_insert ) {
		echo "insertado";
	}
	else {
		echo "no insertado";		
	}		
?>