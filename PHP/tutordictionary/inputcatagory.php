<?php
include('connection.php');
if (isset($_POST['catagory'])) {
	$catagory = trim(ucwords(strtolower($_POST["catagory"])));
	function getnewwid() {
		include('connection.php');
		if($res = mysqli_query($con,"SELECT `catagory_code` FROM `catagory` WHERE 1 ORDER BY `catagory_code` DESC LIMIT 1")){
			if($row = mysqli_fetch_array($res)){
				//list($alpha,$numeric) = sscanf($row[0], "%[A-Z]%d");
				$numbers = preg_replace('/[^0-9]/', '',$row[0]);
				$letters = preg_replace('/[^a-zA-Z]/', '', $row[0]);
				$temp_new=$numbers+1;
				return $letters.$temp_new;
			}else{return 'c1000';}
		}else{return false;}
	}
	
	function ckeak($temp_word) {
		include('connection.php');
		if($res = mysqli_query($con,"SELECT `catagory` FROM `catagory` WHERE `catagory`='{$temp_word}'")){
			if($row = mysqli_fetch_array($res)){return false;	
			}else{return true;}
		}else{return false;}
	}
	if($catagory){
		$catagory_code=getnewwid();
		if(ckeak($catagory)){
			$sql_insert_notification = "INSERT INTO `catagory`(`catagory`, `catagory_code`) VALUES ('{$catagory}','{$catagory_code}')";
			if(mysqli_query($con,$sql_insert_notification)){
				echo "Success";
			}else{
				echo "failed";
			}
		}else{
			echo "<script>alert('already exist');</script>";
		}
	}
	
}?>
<html>
	<body>
		<div id="formdiv">
		<center>
			<br/>
			<br/>
			<form method="post" action="inputcatagory.php">
					<input type="text" name="catagory"  placeholder="word"><br/>
					<br/>
					<input type="submit"  value="submit" name="submit" />
			</form> 
		</center>
		</div>
		<p id="status">
		</p>
	</body>
</html>
