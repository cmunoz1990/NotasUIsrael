
<?php
$hostname_localhost = "localhost" ;
$database_localhost = "notasuisrael" ;
$username_localhost = "root" ;
$password_localhost = "" ;

$json = array ();

	if (isset ($_GET ["codusuario"]) && isset ($_GET ["codigosemestre"])){

		$codusuario = $_GET [ 'codusuario' ];
		$codigosemestre=$_GET['codigosemestre'];
		
		$conexion = mysqli_connect ( $hostname_localhost , $username_localhost , $password_localhost , $database_localhost );
		
		$insert="INSERT INTO usuario_semestre(codigo_usuario,codigo_semestre) 
		VALUES ('{$codigousuario}', '{$codigosemestre}')" ;
		$resultado_insert = mysqli_query ( $conexion , $insert );
		
		if ( $resultado_insert ) {
			$consulta = "SELECT * FROM usuario_semestre WHERE codigo_usuario = '{$codigousuario}'" ;
			$resultado = mysqli_query ( $conexion , $consulta );
			
			if ( $registro = mysqli_fetch_array ( $resultado )) {
				$json [ 'usuarioSemestre' ] [] = $registro ;
			}
			mysqli_close ( $conexion );
			echo json_encode ( $json );
		}
		else {
			$resulta [ "codigousuario" ] = 0 ;
			$resulta [ "codigosemestre" ] = 0 ;
			$json [ 'usuarioSemestre' ] [] = $resulta ;
			echo  json_encode ( $json );
		}
		
	}
	else {
			$resulta [ "codigousuario" ] = 0 ;
			$resulta [ "codigosemestre" ] = 0 ;
			$json [ 'usuarioSemestre' ] [] = $resulta ;
			echo  json_encode ($json);
		}
?>