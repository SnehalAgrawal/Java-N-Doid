<?php

	function textmsg($temp_contact,$temp_sender_id,$temp_msg){
		if(is_numeric($temp_contact )){
			if(strlen($temp_contact)==10){
				if(trim($temp_msg) && trim($temp_sender_id)){
					/*$mssg = urlencode($msg);
					$api_url = "http://sms.bulksms.tech/app/smsapi/index.php?key=357E3B2537F39F&routeid=288&type=text&contacts=".$contact."&senderid="$sender_id."&msg=".$mssg."";
					$ch = curl_init();
					curl_setopt($ch, CURLOPT_URL, $api_url);
					curl_setopt($ch, CURLOPT_HEADER, 0);
					curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);
					$output = curl_exec($ch);
					curl_close($ch);*/
				}else{
					echo "msg is empty";
				}
			}else{
				echo "cantact is not proper";
			}
		}else{
			echo "contact is not numeric";
		}		
	}

	function func_single_msg($temp_title,$temp_body,$temp_sclass,$temp_ssection,$temp_uid,$temp_type,$temp_token,$temp_server_key){
		$msg = array(
			'notification' => array(
				'type' =>$temp_type,
				'title' =>$temp_title,
				'body' =>$temp_body,
				'sclass' =>$temp_sclass,
				'ssection' =>$temp_ssection,
				'uid' =>$temp_uid
			));
		$fields = array('registration_ids' 	=> array($temp_token),'data'=> $msg);
		$headers = array('Authorization: key='.$temp_server_key,'Content-Type: application/json');
      	$ch = curl_init();
      	curl_setopt( $ch,CURLOPT_URL, 'http://fcm.googleapis.com/fcm/send' );
      	curl_setopt( $ch,CURLOPT_POST, true );
      	curl_setopt( $ch,CURLOPT_HTTPHEADER, $headers );
      	curl_setopt( $ch,CURLOPT_RETURNTRANSFER, true );
      	curl_setopt( $ch,CURLOPT_SSL_VERIFYPEER, false );
      	curl_setopt( $ch,CURLOPT_POSTFIELDS, json_encode( $fields ) );
      	//print_r(json_encode( $fields ));
      	$result = curl_exec($ch );
		//echo $result;
      	curl_close( $ch );
	}
	
	
	function func_topic_all_msg($temp_title,$temp_body,$temp_topic,$temp_type,$temp_click_action_package_name,$temp_server_key){
		$postData = array(
			'condition'=> "'{$temp_topic}' in topics",
        	'notification' => array(
				'title' =>$temp_title,
				'body' =>$temp_body,
				'click_action'=>$temp_click_action_package_name,
				'color'=>'#ff0000',
				'time_to_live' => 2,419,200,
				'sound'=>"sound",
				'vibrate'	=> 1),
				'data'=>array(
					'type'=>$temp_type,
					'notification' => array(
						'type' =>$temp_type,
						'title' =>$temp_title,
						'body' =>$temp_body,
		)));
			
		$curl = curl_init('http://fcm.googleapis.com/fcm/send');
   		curl_setopt_array($curl, array(CURLOPT_POST => true,CURLOPT_RETURNTRANSFER => true,CURLOPT_HTTPHEADER => array('Authorization:key='.$temp_server_key,'Content-Type:application/json'),CURLOPT_POSTFIELDS => json_encode($postData)));
    	$response = curl_exec($curl);
    	//echo $response;
    	if($response == false){
     		die(curl_error($curl));
	    }
		$responseData = json_decode($response, true);	
	}
	
	
	function func_topic_class_section_msg($temp_title,$temp_body,$temp_topic,$temp_type,$temp_class,$temp_section,$temp_click_action_package_name,$temp_server_key){
		$postData = array(
			'condition'=> "'{$temp_topic}' in topics",
			'notification' => array(
				'title' =>$temp_title,
				'body' =>$temp_body,
				'click_action'=>$temp_click_action_package_name,
				'color'=>'#ff0000',
				'time_to_live' =>  2,419,200,
				'sound'=>"sound",
				'vibrate'	=> 1),
				'data'=>array(
					'type'=>$temp_type,
					'sclass'=>$temp_class ,
					'ssection'=>$temp_section,
					'notification' => array(
						'type' =>$temp_type,
						'title' =>$temp_title,
						'body' =>$temp_body,
						'sclass'=>$temp_class ,
						'ssection'=>$temp_section)));
		$curl = curl_init('http://fcm.googleapis.com/fcm/send');
		curl_setopt_array($curl, array(CURLOPT_POST => true,
		CURLOPT_RETURNTRANSFER => true,
		CURLOPT_HTTPHEADER => array('Authorization:key='.$temp_server_key,'Content-Type:application/json'),CURLOPT_POSTFIELDS => json_encode($postData)));
		$response = curl_exec($curl);
		//echo $response;
		if($response === false){die(curl_error($curl));}
		$responseData = json_decode($response, true);	
	}
	function func_feedback_mail($feed_type,$feedback_for,$feedback_description,$parents_name,$parents_contact,$email_address){
		require("class.phpmailer.php");
		/*include('connection.php');	
		mysqli_query($con,'SET character_set_results=utf8');
		mysqli_query($con,'SET names=utf8');
		mysqli_query($con,'SET character_set_client=utf8');
		mysqli_query($con,'SET character_set_connection=utf8');
		mysqli_query($con,'SET character_set_results=utf8');
		mysqli_query($con,'SET collation_connection=utf8_general_ci');*/
		$email_subject = $feed_type.' regarding '.$feedback_for;
		$email_body = "Hello sir,<br>".$feedback_description."<br>"."Thanks and Regards"."<br>".$parents_name."<br>".$parents_contact;
		$mail = new PHPMailer;
		$mail->IsSMTP();
		$mail->Host = "mail.hashtroop.com";
		$mail->Port = 25;
		$mail->SMTPAuth = true;
		$mail->Username = "contact@hashtroop.com";
		$mail->Password = "kgNg54&3";
		$mail->From = 'contact@hashtroop.com';
		$mail->FromName = $parents_name;
		$mail->AddAddress($email_address);
		$mail->addReplyTo("noreply@gmail.com", "Reply");
		$mail->IsHTML(true);
		$mail->Subject = $email_subject;
		$mail->Body    = $email_body;
		$mail->AltBody = 'Feedback from parent';
		if(!$mail->Send()) {
			print_r($mail);
			/*echo 'Message could not be sent.';
			echo 'Mailer Error: ' . $mail->ErrorInfo;*/
			exit;
		}
		//echo 'Message has been sent';
		exit(0);
	}
	
	
	
	/*textmsg($contact,$sender_id,$msg);
	func_single_msg($temp_title,$temp_body,$temp_token,$temp_type,$temp_server_key);
	func_topic_all_msg($title,$body,$topic,$type,$click_action_package_name,$server_key);
	func_topic_class_section_msg($temp_title,$temp_body,$temp_topic,$temp_type,$temp_class,$temp_section,$click_action_package_name,$server_key);*/
?>
