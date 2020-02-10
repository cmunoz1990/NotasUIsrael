
<?php
$hostname_localhost = "localhost" ;
$database_localhost = "notasuisrael" ;
$username_localhost = "root" ;
$password_localhost = "" ;

$json = array ();

	if (isset($_GET ["codigo"])&&isset ($_GET ["nombre"]) 
		&& isset($_GET ["apellido"]) 
		) {

		$codigo = $_GET [ 'codigo' ];
		$nombre = $_GET [ 'nombre' ];
		$apellido = $_GET [ 'apellido' ];
		
		
		$conexion = mysqli_connect ( $hostname_localhost , $username_localhost , $password_localhost , $database_localhost );
		
		$insert="INSERT INTO pru(
		cod,
		nom,
		ape) VALUES (
		'{$codigo}', '{$nombre}', '{$apellido}')" ;
		$resultado_insert = mysqli_query ( $conexion , $insert );
		
		if ( $resultado_insert ) {
			$consulta = "SELECT * FROM pru WHERE cod = '{$codigo}'" ;
			$resultado = mysqli_query ( $conexion , $consulta );
			
			if ( $registro = mysqli_fetch_array ( $resultado )) {
				$json [ 'prue' ] [] = $registro ;
			}
			mysqli_close ( $conexion );
			echo json_encode ( $json );
		}
		else {
			$resulta [ "codigo" ] = 0 ;
			$resulta [ "nombre" ] = 'Sin registro' ;
			$resulta [ "apellido" ] = 'Sin registro' ;
			
			$json [ 'prue' ] [] = $resulta ;
			echo  json_encode ( $json );
		}
		
	}
	else {
			$resulta [ "codigo" ] = 0 ;
			$resulta [ "nombre" ] = 'sin ws' ;
			$resulta [ "apellido" ] = 'Sin ws' ;
			
			$json [ 'prue' ] [] = $resulta ;
			echo  json_encode ($json);
			
		}
?>