
<?php
$hostname_localhost = "localhost" ;
$database_localhost = "notasuisrael" ;
$username_localhost = "root" ;
$password_localhost = "" ;

$json = array ();

	if (isset ($_GET ["correo"])) {

		$correo = $_GET [ 'correo' ];
				
		$conexion = mysqli_connect ( $hostname_localhost , $username_localhost , $password_localhost , $database_localhost );
		
			$consulta = "SELECT * FROM usuario WHERE correo_usuario = '{$correo}'" ;
			$resultado = mysqli_query ( $conexion , $consulta );
			
			if ( $registro = mysqli_fetch_array ( $resultado )) {
				$json [ 'usuario' ] [] = $registro ;
			}else{
				$resutar['codigo']=0;
				$json [ 'usuario' ] [] = $resulta ;
			}

			mysqli_close ( $conexion );
			echo json_encode ( $json );
		}
		else {
			$resulta ["codigo"] = 0 ;
			$json ['usuario'] [] = $resulta;
			echo  json_encode ($json);
			
		}
	 
?>