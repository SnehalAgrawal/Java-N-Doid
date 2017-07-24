<?php

	include('connection.php');
	if(get_magic_quotes_gpc()) {
		$from_line_no = stripslashes($_GET['line_no']);
	} else {
		$from_line_no = $_GET['line_no'];
	}
	$to_line_no =10;
	
	$sql="SELECT c.`catagory`, c.`catagory_code`,(SELECT count(w.`sno`) FROM `words` w WHERE w.`catagory`=c.`catagory_code`) as count FROM `catagory` c WHERE 1 ORDER BY `catagory` ASC LIMIT {$from_line_no},{$to_line_no}";	
	if($res = mysqli_query($con,$sql)){
		$result = array();
		while($row = mysqli_fetch_array($res)){
			array_push($result,array(
			'catagory'=>$row[0],
			'catagory_code'=>$row[1],
			'count'=>$row[2]
			));
		}
		echo json_encode(array("cat_list"=>$result));		
	}else{
		echo 'failed';
	}
	
	mysqli_close($con);
?>
 
