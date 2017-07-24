<?php

	include('connection.php');
	if(get_magic_quotes_gpc()) {
		$last_date = stripslashes($_POST['last_date']);
	} else {
		$last_date = $_POST['last_date'];
	}
	if($last_date=='NA'){
		$sql="SELECT `word`,`meaning_enlish`,`meaning_hindi`,`discription`,`wid`,`date_time` FROM `wordofthat` WHERE `date_time`<= now() ORDER BY `date_time` ASC";	
	}else{
		$sql="SELECT `word`,`meaning_enlish`,`meaning_hindi`,`discription`,`wid`,`date_time` FROM `wordofthat` WHERE `date_time`>'{$last_date}' ORDER BY `date_time` ASC";	
	}
	if($res = mysqli_query($con,$sql)){
		$result = array();
		while($row = mysqli_fetch_array($res)){
			$ymd = date("Y-m-d", strtotime($row[5]));
			array_push($result,array(
			'word'=>$row[0],
			'meaning_english'=>$row[1],
			'meaning_hindi'=>$row[2],
			'description'=>$row[3],
			'wid'=>$row[4],
			'date_time'=>$ymd
			));
		}
		echo json_encode(array("wod_list"=>$result));		
	}else{
		echo 'failed';
	}
	
	mysqli_close($con);
?>
 
