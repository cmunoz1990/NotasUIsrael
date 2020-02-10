
<?php
$hostname_localhost = "localhost" ;
$database_localhost = "notasuisrael" ;
$username_localhost = "root" ;
$password_localhost = "" ;

$json = array ();

	if (isset ($_GET ["codigomateria"])) {

		$codigomateria = $_GET [ 'codigomateria' ];
				
		$conexion = mysqli_connect ( $hostname_localhost , $username_localhost , $password_localhost , $database_localhost );
		
			$consulta = "SELECT  * FROM notas WHERE codigo_materia='{$codigomateria}'";

			$resultado = mysqli_query ( $conexion , $consulta );
			
			if ( $registro = mysqli_fetch_array ( $resultado )) {
				$json [ 'matenota' ] [] = $registro ;
			}else{
				$resutar['codigo']=50;
				$json [ 'matenota' ] [] = $resulta ;
			}

			mysqli_close ( $conexion );
			echo json_encode ( $json );
		}
		else {
			$resulta ["codigo"] = 51 ;
			$json ['matenota'] [] = $resulta;
			echo  json_encode ($json);
			
		}
?>