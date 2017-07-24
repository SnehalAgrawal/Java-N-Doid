<?php
require_once '../include/DbHandler.php';
require_once '../include/PassHash.php';
require '.././libs/Slim/Slim.php';
\Slim\Slim::registerAutoloader();
$app = new \Slim\Slim();
$user_id = NULL;

function authenticate(\Slim\Route $route) {
    $headers = apache_request_headers();
    $response = array();
    $app = \Slim\Slim::getInstance();
    if (isset($headers['authorization'])) {
        $db = new DbHandler();
        $api_key = $headers['authorization'];
        if (!$db->isValidApiKey($api_key)) {
            $response["error"] = true;
            $response["message"] = "Access Denied. Invalid Api key";
            echoRespnse(401, $response);
            $app->stop();
        } else {
            global $user_id;
            $user_id = $db->getUserId($api_key);
        }
    } else {
        $response["error"] = true;
        $response["message"] = "Api key is misssing";
        echoRespnse(400, $response);
        $app->stop();
    }
}

$app->post('/register', function() use ($app) {
            verifyRequiredParams(array('name', 'email', 'password'));
            $response = array();
            $name = $app->request->post('name');
            $email = $app->request->post('email');
            $password = $app->request->post('password');
            validateEmail($email);
            $db = new DbHandler();
            $res = $db->createUser($name, $email, $password);
            if ($res == USER_CREATED_SUCCESSFULLY) {
                $response["error"] = false;
                $response["message"] = "You are successfully registered";
            } else if ($res == USER_CREATE_FAILED) {
                $response["error"] = true;
                $response["message"] = "Oops! An error occurred while registereing";
            } else if ($res == USER_ALREADY_EXISTED) {
                $response["error"] = true;
                $response["message"] = "Sorry, this email already existed";
            }
            echoRespnse(201, $response);
        });

$app->post('/login', function() use ($app) {
            verifyRequiredParams(array('email', 'password'));
            $email = $app->request()->post('email');
            $password = $app->request()->post('password');
            $response = array();
            $db = new DbHandler();
            if ($db->checkLogin($email, $password)) {
                $user = $db->getUserByEmail($email);
                if ($user != NULL) {
                    $response["error"] = false;
                    $response['name'] = $user['name'];
                    $response['email'] = $user['email'];
                    $response['apiKey'] = $user['api_key'];
                    $response['createdAt'] = $user['created_at'];
                } else {
                    $response['error'] = true;
                    $response['message'] = "An error occurred. Please try again";
                }
            } else {
                $response['error'] = true;
                $response['message'] = 'Login failed. Incorrect credentials';
            }
            echoRespnse(200, $response);
        });

$app->get('/tasks', 'authenticate', function() {
            global $user_id;
            $response = array();
            $db = new DbHandler();
            $result = $db->getAllUserTasks($user_id);
            $response["error"] = false;
            $response["tasks"] = array();
            while ($task = $result->fetch_assoc()) {
                $tmp = array();
                $tmp["id"] = $task["id"];
                $tmp["task"] = $task["task"];
                $tmp["status"] = $task["status"];
                $tmp["createdAt"] = $task["created_at"];
                array_push($response["tasks"], $tmp);
            }
            echoRespnse(200, $response);
        });


$app->get('/tasks/:id', 'authenticate', function($task_id) {
            global $user_id;
            $response = array();
            $db = new DbHandler();
            $result = $db->getTask($task_id, $user_id);
            if ($result != NULL) {
                $response["error"] = false;
                $response["id"] = $result["id"];
                $response["task"] = $result["task"];
                $response["status"] = $result["status"];
                $response["createdAt"] = $result["created_at"];
                echoRespnse(200, $response);
            } else {
                $response["error"] = true;
                $response["message"] = "The requested resource doesn't exists";
                echoRespnse(404, $response);
            }
        });

$app->post('/tasks', 'authenticate', function() use ($app) {
            verifyRequiredParams(array('task'));
            $response = array();
            $task = $app->request->post('task');
            global $user_id;
            $db = new DbHandler();
            $task_id = $db->createTask($user_id, $task);
            if ($task_id != NULL) {
                $response["error"] = false;
                $response["message"] = "Task created successfully";
                $response["task_id"] = $task_id;
                echoRespnse(201, $response);
            } else {
                $response["error"] = true;
                $response["message"] = "Failed to create task. Please try again";
                echoRespnse(200, $response);
            }            
        });

$app->put('/tasks/:id', 'authenticate', function($task_id) use($app) {
            verifyRequiredParams(array('task', 'status'));
            global $user_id;            
            $task = $app->request->put('task');
            $status = $app->request->put('status');
            $db = new DbHandler();
            $response = array();
            $result = $db->updateTask($user_id, $task_id, $task, $status);
            if ($result) {
                $response["error"] = false;
                $response["message"] = "Task updated successfully";
            } else {
                $response["error"] = true;
                $response["message"] = "Task failed to update. Please try again!";
            }
            echoRespnse(200, $response);
        });

$app->delete('/tasks/:id', 'authenticate', function($task_id) use($app) {
            global $user_id;
            $db = new DbHandler();
            $response = array();
            $result = $db->deleteTask($user_id, $task_id);
            if ($result) {
                $response["error"] = false;
                $response["message"] = "Task deleted succesfully";
            } else {
                $response["error"] = true;
                $response["message"] = "Task failed to delete. Please try again!";
            }
            echoRespnse(200, $response);
        });
		
$app->post('/imageupload', 'authenticate', function() use ($app) {
		$headers = apache_request_headers();
		if (isset($headers['uploaded_file'])) {
		} else {
		}
		if(isset($_FILES['uploaded_file'])){
			$target_path=filename($_FILES['uploaded_file']['name']);
		}
		$response = array();
		if (isset($_FILES['uploaded_file']) && move_uploaded_file($_FILES['uploaded_file']['tmp_name'], $target_path)) {
			$response["error"] = false;
            $response["message"] = dirname(__FILE__)."/".$target_path;
		} else {
			$response["error"] = true;
            $response["message"] = "Failed to upload file. Please try again!";
		}
		echoRespnse(200, $response);
		});

$app->get('/imageupload/:id', function($image_id) use ($app) {
		$image = file_get_contents('../uploaded/123.jpg');
		$finfo = new finfo(FILEINFO_MIME_TYPE);
		$app->response->header('Content-Type', 'content-type: ' . $finfo->buffer($image));
		echo $image;
    });
	
function filename($name) {
	if (!is_dir('../uploaded')) {
			mkdir('../uploaded', 0777, TRUE);
	}
	$target_path = "../uploaded/".date("Y_m_d_h_i_sa");
	$array = explode('.', $name);
	$extension = end($array);
	return $target_path.".".$extension;
}

function verifyRequiredParams($required_fields) {
    $error = false;
    $error_fields = "";
    $request_params = array();
    $request_params = $_REQUEST;
    if ($_SERVER['REQUEST_METHOD'] == 'PUT') {
        $app = \Slim\Slim::getInstance();
        parse_str($app->request()->getBody(), $request_params);
    }
    foreach ($required_fields as $field) {
        if (!isset($request_params[$field]) || strlen(trim($request_params[$field])) <= 0) {
            $error = true;
            $error_fields .= $field . ', ';
        }
    }
    if ($error) {
        $response = array();
        $app = \Slim\Slim::getInstance();
        $response["error"] = true;
        $response["message"] = 'Required field(s) ' . substr($error_fields, 0, -2) . ' is missing or empty';
        echoRespnse(400, $response);
        $app->stop();
    }
}

function validateEmail($email) {
    $app = \Slim\Slim::getInstance();
    if (!filter_var($email, FILTER_VALIDATE_EMAIL)) {
        $response["error"] = true;
        $response["message"] = 'Email address is not valid';
        echoRespnse(400, $response);
        $app->stop();
    }
}

function echoRespnse($status_code, $response) {
    $app = \Slim\Slim::getInstance();
    $app->status($status_code);
    $app->contentType('application/json');
    echo json_encode($response);
}

$app->run();
?>