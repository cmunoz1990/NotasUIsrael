
<?php
$hostname_localhost = "localhost" ;
$database_localhost = "notasuisrael" ;
$username_localhost = "root" ;
$password_localhost = "" ;

$json = array ();

	if (isset ($_GET ["nombre"]) 
		&& isset($_GET ["apellido"]) 
		&& isset($_GET ["carrera"])
		&& isset($_GET ["correo"])
		&& isset($_GET ["password"])
		&& isset($_GET ["foto"])
		&& isset($_GET ["validar"])) {

		$nombre = $_GET [ 'nombre' ];
		$apellido = $_GET [ 'apellido' ];
		$carrera = $_GET [ 'carrera' ];
		$correo = $_GET [ 'correo' ];
		$password = $_GET [ 'password' ];
		$foto = $_GET [ 'foto' ];
		$validar = $_GET [ 'validar' ];
		
		$conexion = mysqli_connect ( $hostname_localhost , $username_localhost , $password_localhost , $database_localhost );
		
		$insert="INSERT INTO usuario(
		nombre_usuario,
		apellido_usuario,
		carrera_usuario,
		correo_usuario,
		password_usuario,
		foto_usuario,
		validar_usuario) VALUES (
		'{$nombre}', '{$apellido}', '{$carrera}', '{$correo}', '{$password}', '{$foto}', '{$validar}')" ;
		$resultado_insert = mysqli_query ( $conexion , $insert );
		
		if ( $resultado_insert ) {
			$consulta = "SELECT * FROM usuario WHERE nombre_usuario = '{$nombre}'" ;
			$resultado = mysqli_query ( $conexion , $consulta );
			
			if ( $registro = mysqli_fetch_array ( $resultado )) {
				$json [ 'usuario' ] [] = $registro ;
			}
			mysqli_close ( $conexion );
			echo json_encode ( $json );
		}
		else {
			$resulta [ "codigo" ] = 0 ;
			$resulta [ "nombre" ] = 'Sin registro' ;
			$resulta [ "apellido" ] = 'Sin registro' ;
			$resulta [ "carrera" ] = 'Sin registro' ;
			$resulta [ "correo" ] = 'Sin registro' ;
			$resulta [ "password" ] = 'Sin registro' ;
			$resulta [ "foto" ] = 'Sin registro' ;
			$resulta [ "validar" ] = 'Sin registro' ;
			$json [ 'usuario' ] [] = $resulta ;
			echo  json_encode ( $json );
		}
		
	}
	else {
			$resulta ["codigo"] = 0 ;
			$resulta ["nombre"] = 'Ws No retorna' ;
			$resulta ["apellido"] = 'Ws No retorna' ;
			$resulta ["carrera"] = 'Ws No retorna' ;
			$resulta ["correo"] = 'Ws No retorna' ;
			$resulta ["password"] = 'Ws No retorna' ;
			$resulta ["foto"] = 'Ws No retorna' ;
			$resulta ["validar"] = 'Ws No retorna' ;
			$json ['usuario'] [] = $resulta;
			echo  json_encode ($json);
			var_dump($resulta);	
		}
?>