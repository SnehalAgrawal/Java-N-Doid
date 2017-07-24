<?php

	include('connection.php');

	$sql="SELECT `word`, `wordtype`, `definition` FROM `dic_data` WHERE 1";	
	if($res = mysqli_query($con,$sql)){
		$result = array();
		while($row = mysqli_fetch_array($res)){
			array_push($result,array(
			'catagory'=>$row[0],
			'catagory_code'=>$row[1],
			'count'=>$row[2]
			));
		}
		echo json_encode(array("dic_data"=>$result));		
	}else{
		echo 'failed';
	}
	
	mysqli_close($con);
?>
 
