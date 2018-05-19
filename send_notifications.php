<?php

function send_notification($tokens,$message)
{
	$url="https://fcm.googleapis.com/fcm/send";
	$fields=array(
	'registration_ids' => $tokens,
	'data' => $message
	);
	
	$headers = array(
	'Authorization:key=AAAAs_6arac:APA91bHcqb9QkH2O-q47zPVR0CaAeRyGMvZidnJky0c1w4AVUtiK1nKv2tRvICIbRfQagjwkzgs-eGTMReaQkdxWlxyo1c59VRTjg4RKHfn0JPqm0P_0E4MmM4ocXCG9g3kFWAO70Zl6',
	'Content-Type: application/json'
	);
	
	$ch = curl_init();
curl_setopt( $ch,CURLOPT_URL, $url );
curl_setopt( $ch,CURLOPT_POST, true );
curl_setopt( $ch,CURLOPT_HTTPHEADER, $headers );
curl_setopt( $ch,CURLOPT_RETURNTRANSFER, true );
curl_setopt( $ch,CURLOPT_SSL_VERIFYPEER, false );
curl_setopt( $ch,CURLOPT_POSTFIELDS, json_encode( $fields ) );
$result = curl_exec($ch );

if($result===FALSE){
	die('Curl failed: '. curl_error($ch));
}
curl_close( $ch );
return $result;
}
require __DIR__.'/vendor/autoload.php';
use Kreait\Firebase;use Kreait\Firebase\Factory;
use Kreait\Firebase\ServiceAccount;

// This assumes that you have placed the Firebase credentials in the same directory
// as this PHP file.
$serviceAccount = ServiceAccount::fromJsonFile(__DIR__.'/GpsLocation-7f9932aa231c.json');

$firebase = (new Factory)
    ->withServiceAccount($serviceAccount)
    ->withDatabaseUri('https://gpslocation-1519567817653.firebaseio.com/')
    ->create();
	
$database=$firebase->getDatabase();
$reference=$database->getReference('Devices/');

$count=0;

$keys=$reference->getChildKeys();
$size=sizeof($keys);
$x=0;
while($count<$size)
{
$reference2=$database->getReference('Devices/'.$keys[$count]);
$snapshot2=$reference2->getSnapshot();
$present=$snapshot2->getChild('present')->getValue();
if($present=="1")
{
$devices[$x]=$snapshot2->getChild('token_num')->getValue();
$x++;
}
$count++;
}

$message=array("message" => "PLEASE CHECK THE VALUE UPDATED");
$message_status= send_notification($devices, $message);
echo $message_status;

?>