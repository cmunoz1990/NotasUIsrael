
<?php
$hostname_localhost = "localhost" ;
$database_localhost = "notasuisrael" ;
$username_localhost = "root" ;
$password_localhost = "" ;

$json = array ();

	if (isset ($_GET ["codigosemestre"])) {

		$codigosemestre = $_GET [ 'codigosemestre' ];
				
		$conexion = mysqli_connect ( $hostname_localhost , $username_localhost , $password_localhost , $database_localhost );
		
			$consulta = "SELECT  materias.descripcion_materia, materias.codigo_materia FROM semestre LEFT JOIN materias	ON semestre.codigo_semestre=materias.codigo_semestre WHERE semestre.codigo_semestre='{$codigosemestre}'";

			$resultado = mysqli_query ( $conexion , $consulta );
			
			while($registro=mysqli_fetch_array($resultado)){
				$json['sememate'][]=$registro;
			}
			mysqli_close ( $conexion );
			echo json_encode ( $json );
		}else {
			$resulta ["codigo"] = 0 ;
			$json ['sememate'] [] = $resulta;
			echo  json_encode ($json);
			
		}
	 
?>