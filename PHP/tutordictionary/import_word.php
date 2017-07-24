<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
<title>Exam Schedule upload</title>
<style type="text/css">
body {background: #E3F4FC;font: normal 14px/30px Helvetica, Arial, sans-serif;color: #2b2b2b;}
a {color:#898989;font-size:14px;font-weight:bold;text-decoration:none;}
a:hover {color:#CC0033;}
h1 {font: bold 14px Helvetica, Arial, sans-serif;}
s1 {font: bold 20px Helvetica, Arial, sans-serif;color: #CC0033;}
h2 {font: bold 14px Helvetica, Arial, sans-serif;color: #898989;}
#container {background: #CCC;margin: 100px auto;width: 945px;}
#form{padding: 20px 150px;}
#form input{margin-bottom: 20px;}
</style>
</head>
<body>
<div id="container">
<div id="form">

<?php
include "connection.php";
//$deleterecords = "TRUNCATE TABLE `exam_schedule`";
//mysqli_query($con,$deleterecords);
	/*function getnewwid() {
		include('connection.php');
		if($res = mysqli_query($con,"SELECT `wid` FROM `words` WHERE 1 ORDER BY `wid` DESC LIMIT 1")){
			if($row = mysqli_fetch_array($res)){
				//list($alpha,$numeric) = sscanf($row[0], "%[A-Z]%d");
				$numbers = preg_replace('/[^0-9]/', '',$row[0]);
				$letters = preg_replace('/[^a-zA-Z]/', '', $row[0]);
				$temp_new=$numbers+1;
				return $letters.$temp_new;
			}else{return 'w1000';}
		}else{return false;}
	}*/
	
	

if (isset($_POST['submit'])) {
	if (is_uploaded_file($_FILES['filename']['tmp_name'])) {
		echo "<h1>" . "File ". $_FILES['filename']['name'] ." uploaded successfully." . "</h1>";
		//echo "<h2>Displaying contents:</h2>";
		readfile($_FILES['filename']['tmp_name']);
		//echo '<br>';
	}
	$handle = fopen($_FILES['filename']['tmp_name'], "r");
	$c = 1;
	while (($data = fgetcsv($handle, 1000, ",")) !== FALSE) {
		//if($c >= 6){
		//$wid=getnewwid();
		$word=trim(ucwords(strtolower($data[0])));
		$meaning_english=trim(strtolower($data[1]));
		$meaning_hindi=trim(strtolower($data[2]));
		$description=trim(strtolower($data[3]));
		$wid=trim(ucwords(strtolower($data[4])));
		$catagory=trim(ucwords(strtolower($data[5])));
		
		$import="INSERT INTO `words`(`word`, `meaning_english`, `meaning_hindi`, `description`, `wid`, `catagory`) 
								VALUES ('{$word}','{$meaning_english}','{$meaning_hindi}','{$description}','{$wid}','{$catagory}')";
			if(mysqli_query($con,$import)){
				echo 'success<br>';
			}else{
				echo 'failed<br>';
			}
			$c = $c + 1;
		//}else{
		//	$c++;
		//}
	}
	fclose($handle);
	print "Import done";
}else {
	print "Upload <s1>Exam Schedule</s1>\n";
	print "<form enctype='multipart/form-data' method='post'>";
	print "File name to import:<br />\n";
	print "<input size='50' type='file' name='filename'><br />\n";
	print "<input type='submit' name='submit' value='Upload'></form>";
	print "<s1>Note:</s1> The script will remove all the data from table and will enter all the new deatils in table so please get your table backup by exporting the table.";
}
?>
</div>
</div>
</body>
</html>
