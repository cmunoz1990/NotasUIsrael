
<?php
$hostname_localhost = "localhost" ;
$database_localhost = "notasuisrael" ;
$username_localhost = "root" ;
$password_localhost = "" ;

$json = array ();

	if (isset ($_GET ["codigousuario"])) {

		$codigousuario = $_GET [ 'codigousuario' ];
				
		$conexion = mysqli_connect ( $hostname_localhost , $username_localhost , $password_localhost , $database_localhost );
		
			$consulta = "SELECT  semestre.codigo_semestre, semestre.descripcion_semestre FROM usuario_semestre LEFT JOIN semestre ON usuario_semestre.codigo_semestre=semestre.codigo_semestre WHERE usuario_semestre.codigo_usuario='{$codigousuario}'" ;
			$resultado = mysqli_query ( $conexion , $consulta );
			
			while($registro=mysqli_fetch_array($resultado)){
				$json['ususeme'][]=$registro;
			}
			mysqli_close ( $conexion );
			echo json_encode ( $json );
		}else {
			$resulta ["codigo"] = 0 ;
			$json ['ususeme'] [] = $resulta;
			echo  json_encode ($json);
			
		}
	 
?>