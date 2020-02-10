
<?php
$hostname_localhost = "localhost" ;
$database_localhost = "notasuisrael" ;
$username_localhost = "root" ;
$password_localhost = "" ;

$json = array ();

	if (isset ($_GET ["materia"])&&isset ($_GET ["codigosemestre"])){

		$materia = $_GET [ 'materia' ];
		$codigosemestre=$_GET['codigosemestre'];
		
		$conexion = mysqli_connect ( $hostname_localhost , $username_localhost , $password_localhost , $database_localhost );
		
		$insert="INSERT INTO materias(descripcion_materia,codigo_semestre) 
		VALUES ('{$materia}', '{$codigosemestre}')" ;
		$resultado_insert = mysqli_query ( $conexion , $insert );
		
		if ( $resultado_insert ) {
			$consulta = "SELECT * FROM materias WHERE descripcion_materia = '{$materia}'" ;
			$resultado = mysqli_query ( $conexion , $consulta );
			
			if ( $registro = mysqli_fetch_array ( $resultado )) {
				$json [ 'materia' ] [] = $registro ;
			}
			mysqli_close ( $conexion );
			echo json_encode ( $json );
		}
		else {
			$resulta [ "codigo" ] = 0 ;
			$resulta [ "descripcionmateria" ] = 'Sin registro' ;
			$resulta [ "codigosemestre" ] = 0 ;
			
			$json [ 'materia' ] [] = $resulta ;
			echo  json_encode ( $json );
		}
		
	}
	else {
			$resulta [ "codigo" ] = 0 ;
			$resulta [ "descripcionmateria" ] = 'ws no Reporta' ;
			$resulta [ "codigosemestre" ] = 0 ;
			$json ['materia'] [] = $resulta;
			echo  json_encode ($json);
		}
?>