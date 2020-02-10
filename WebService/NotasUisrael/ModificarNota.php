<?php
$hostname_localhost = "localhost" ;
$database_localhost = "notasuisrael" ;
$username_localhost = "root" ;
$password_localhost = "" ;

$json = array ();

	if (isset($_GET ["seguimientop"]) 
		&& isset($_GET ["examenp"]) 
		&& isset($_GET ["seguimientos"])
		&& isset($_GET ["examens"])
		&& isset($_GET ["supletorio"])
	    && isset($_GET ["codigonota"])) {

		$seguimientop= $_GET [ 'seguimientop' ];
		$examenp= $_GET [ 'examenp' ];
		$seguimientos= $_GET [ 'seguimientos' ];
		$examens= $_GET [ 'examens' ];
		$supletorio = $_GET [ 'supletorio' ];
		$codigonota=$_GET['codigonota'];

		$conexion = mysqli_connect ( $hostname_localhost , $username_localhost , $password_localhost , $database_localhost );
		
		$insert="UPDATE notas SET seguimientop_nota='{$seguimientop}', examenp_nota='{$examenp}', seguimientos_nota='{$seguimientos}', examens_nota='{$examens}', supletorio_nota='{$supletorio}' WHERE codigo_nota='{$codigonota}'";
		$resultado_insert = mysqli_query ( $conexion , $insert);
		
		if ( $resultado_insert ) {
			
			$consulta = "SELECT * FROM notas WHERE codigo_nota = '{$codigonota}'" ;
			$resultado = mysqli_query ( $conexion , $consulta );
			
			if ( $registro = mysqli_fetch_array ( $resultado )) {
				$json [ 'nota' ] [] = $registro ;
			}
			mysqli_close ( $conexion );
			echo json_encode ( $json );
		}
		else {
			
			$resulta [ "seguimientop" ] = "no" ;
			$resulta [ "examenp" ] = 0 ;
			$resulta [ "seguimientos" ] = 0 ;
			$resulta [ "examens" ] = 0 ;
			$resulta [ "supletorio" ] = 0;
			$resulta [ "codigomateria" ] = 0 ;
			$json [ 'nota' ] [] = $resulta ;
			echo  json_encode ( $json );
			
		}		
	}
	else {
			$resulta [ "seguimientop" ] = "noooo" ;
			$resulta [ "examenp" ] = 0 ;
			$resulta [ "seguimientos" ] = 0 ;
			$resulta [ "examens" ] = 0 ;
			$resulta [ "supletorio" ] = 0;
			$resulta [ "codigomateria" ] = 0 ;
			$json [ 'nota' ] [] = $resulta ;
			echo  json_encode ( $json );	
		}
?>