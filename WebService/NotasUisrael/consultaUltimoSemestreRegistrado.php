
<?php
$hostname_localhost = "localhost" ;
$database_localhost = "notasuisrael" ;
$username_localhost = "root" ;
$password_localhost = "" ;

$json = array ();

	if (isset ($_GET)) {
		
		$conexion = mysqli_connect ( $hostname_localhost , $username_localhost , $password_localhost , $database_localhost );
		
			$consulta = "SELECT * FROM materias ORDER BY codigo_materia DESC LIMIT 1";
			$resultado = mysqli_query ( $conexion , $consulta );
			
			if ( $registro = mysqli_fetch_array ( $resultado )) {
				$json [ 'ultimamateria' ] [] = $registro ;
			}else{
				$resutar['codigo']=0;
				$json [ 'ultimamateria' ] [] = $resulta ;
			}

			mysqli_close ( $conexion );
			echo json_encode ( $json );
		}
		else {
			$resulta ["codigo"] = 0 ;
			$json ['ultimamateria'] [] = $resulta;
			echo  json_encode ($json);
			
		}
	 
?>