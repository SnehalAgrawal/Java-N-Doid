<?php
	require_once('connection.php');
	$cat_code = $_POST['cat_code'];
	$last_wid = $_POST['last_wid'];
	$to_line_no =50;
	if($last_wid=='NA'){
		$sql="SELECT w.`word`, w.`meaning_english`, w.`meaning_hindi`, w.`description`, w.`wid`, (SELECT c.`catagory` FROM `catagory` c WHERE c.`catagory_code`=w.`catagory`) as catagory FROM `words` w WHERE `catagory`='{$cat_code}' ORDER BY w.`wid` ASC  LIMIT {$to_line_no}";	
	}else{
		$sql="SELECT w.`word`, w.`meaning_english`, w.`meaning_hindi`, w.`description`, w.`wid`, (SELECT c.`catagory` FROM `catagory` c WHERE c.`catagory_code`=w.`catagory`) as catagory FROM `words` w WHERE `catagory`='{$cat_code}' AND w.`wid`>'{$last_wid}' ORDER BY w.`wid` ASC  LIMIT {$to_line_no}";	
	}
	//echo $sql;
	if($res = mysqli_query($con,$sql)){
		$result=array();
		while($row = mysqli_fetch_array($res)){
			array_push($result,array(
				'word'=>$row[0],
				'meaning_english'=>$row[1],
				'meaning_hindi'=>$row[2],
				'description'=>$row[3],
				'wid'=>$row[4],
				'catagory'=>$row[5],
				'catagory_code'=>$cat_code
			));
		}
		echo json_encode(array("w_list"=>$result));		
	}else{echo 'failed';}
	mysqli_close($con);
?>
