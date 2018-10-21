package com.example.lab_tab;
 
import java.io.IOException;

import java.util.ArrayList;
 
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.telephony.SmsManager;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;
 
public class mic extends Activity implements
 RecognitionListener {
 
 private TextView returnedText;
 private ToggleButton toggleButton;
 private ProgressBar progressBar;
 private SpeechRecognizer speech = null;
 private Intent recognizerIntent;
 private String LOG_TAG = "VoiceRecognitionActivity";
 String msg;
 @Override
 protected void onCreate(Bundle savedInstanceState) {
 super.onCreate(savedInstanceState);
 setContentView(R.layout.mic);
 returnedText = (TextView) findViewById(R.id.txtv1);
 progressBar = (ProgressBar) findViewById(R.id.pro1);
 toggleButton = (ToggleButton) findViewById(R.id.tob1);
 
 progressBar.setVisibility(View.INVISIBLE);
 speech = SpeechRecognizer.createSpeechRecognizer(getApplicationContext());
 speech.setRecognitionListener(mic.this);
 recognizerIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
 recognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_PREFERENCE,
 "en");
 recognizerIntent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE,
 this.getPackageName());
 recognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
 RecognizerIntent.LANGUAGE_MODEL_WEB_SEARCH);
 recognizerIntent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 3);
 //recognizerIntent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, "com.package.name");
 
 toggleButton.setOnCheckedChangeListener(new OnCheckedChangeListener() {
 
 @Override
 public void onCheckedChanged(CompoundButton buttonView,
 boolean isChecked) {
	 if (isChecked) {
		 progressBar.setVisibility(View.VISIBLE);
		 progressBar.setIndeterminate(true);
		 speech.startListening(recognizerIntent);
		 }else {
 progressBar.setIndeterminate(false);
 progressBar.setVisibility(View.INVISIBLE);
 speech.stopListening();
 }
 }
 });
 
 }
 
 @Override
 public void onResume() {
 super.onResume();
 }
 
 @Override
 protected void onPause() {
 super.onPause();
 if (speech != null) {
 speech.destroy();
 Log.i(LOG_TAG, "destroy");
 }
 
 }
 
 @Override
 public void onBeginningOfSpeech() {
 Log.i(LOG_TAG, "onBeginningOfSpeech");
 progressBar.setIndeterminate(false);
 progressBar.setMax(10);
 }
 
 @Override
 public void onBufferReceived(byte[] buffer) {
 Log.i(LOG_TAG, "onBufferReceived: " + buffer);
 }
 
 @Override
 public void onEndOfSpeech() {
 Log.i(LOG_TAG, "onEndOfSpeech");
 progressBar.setIndeterminate(true);
 toggleButton.setChecked(false);
 }
 
 @Override
 public void onError(int errorCode) {
 String errorMessage = getErrorText(errorCode);
 Log.d(LOG_TAG, "FAILED " + errorMessage);
 returnedText.setText(errorMessage);
 toggleButton.setChecked(false);
 }
 
 @Override
 public void onEvent(int arg0, Bundle arg1) {
 Log.i(LOG_TAG, "onEvent");
 }
 
 @Override
 public void onPartialResults(Bundle arg0) {
 Log.i(LOG_TAG, "onPartialResults");
 }
 
 @Override
 public void onReadyForSpeech(Bundle arg0) {
 Log.i(LOG_TAG, "onReadyForSpeech");
 }
 
 @Override
 public void onResults(Bundle results) {
 Log.i(LOG_TAG, "onResults");
 ArrayList<String> matches = results
 .getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
 String text = "";
 if(matches.contains("go")||matches.contains("goal")||matches.contains("cool")||matches.contains("call")){
		//resultText.setText("You are saying left");
	 Toast.makeText(getApplicationContext()," go", 11).show();
 try {
	MainActivity.getOs().writeByte(100);
} catch (IOException e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
}
 }
  if(matches.contains("stop")||matches.contains("stopped")){
		//resultText.setText("You are saying left");
	 Toast.makeText(getApplicationContext(), "stop", 11).show();
try {
	MainActivity.getOs().writeByte(0);
} catch (IOException e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
}
}
  if(matches.contains("left")||matches.contains("loved")||matches.contains("lust")){
		//resultText.setText("You are saying left");
	 Toast.makeText(getApplicationContext(), "left", 11).show();
try {
	MainActivity.getOs().writeChar('L');
} catch (IOException e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
}
}
  if(matches.contains("right")){
		//resultText.setText("You are saying left");
	 Toast.makeText(getApplicationContext(), "right", 11).show();
try {
	MainActivity.getOs().writeChar('R');
} catch (IOException e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
}
}
  if(matches.contains("help me")||matches.contains("slap me")||matches.contains("hip me")||matches.contains("have me")||matches.contains("me")||matches.contains("tell me")||matches.contains("happy")||matches.contains("at me")||matches.contains("family")||matches.contains("call maine")){
		//resultText.setText("You are saying left");
	 Toast.makeText(getApplicationContext(), "help me", 11).show();
	 SmsManager smsManager = SmsManager.getDefault();
		 GPSTracker gps = new GPSTracker(mic.this);

     
	// Check if GPS enabled
     if(gps.canGetLocation()) {

          double latitude = gps.getLatitude();
         double longitude = gps.getLongitude();
         msg="Help Me! I am at- \nLat: " + latitude + "\nLong: " + longitude;

         // \n is for new line
         Toast.makeText(getApplicationContext(), "Sending........", Toast.LENGTH_SHORT).show();
     } else {
         // Can't get location.
         // GPS or network is not enabled.
         // Ask user to enable GPS/network in settings.
         gps.showSettingsAlert();
     }
	       SmsManager sms = SmsManager.getDefault();
	   	 PendingIntent pi = PendingIntent.getActivity(this, 0, new Intent(this, Second.class), 0);  
	 	
        sms.sendTextMessage("09797577606", null, msg, pi,null);       

		Toast.makeText(getApplicationContext(), "Message sent!", 11);
	
}
 
text=matches.toString();
  Toast.makeText(getApplicationContext(), text, 11).show();
 returnedText.setText(text);
 
 
 }
 
 @Override
 public void onRmsChanged(float rmsdB) {
 Log.i(LOG_TAG, "onRmsChanged: " + rmsdB);
 progressBar.setProgress((int) rmsdB);
 }
 
 public static String getErrorText(int errorCode) {
 String message;
 switch (errorCode) {
 case SpeechRecognizer.ERROR_AUDIO:
 message = "Audio recording error";
 break;
 case SpeechRecognizer.ERROR_CLIENT:
 message = "Client side error";
 break;
 case SpeechRecognizer.ERROR_INSUFFICIENT_PERMISSIONS:
 message = "Insufficient permissions";
 break;
 case SpeechRecognizer.ERROR_NETWORK:
 message = "Network error";
 break;
 case SpeechRecognizer.ERROR_NETWORK_TIMEOUT:
 message = "Network timeout";
 break;
 case SpeechRecognizer.ERROR_NO_MATCH:
 message = "No match";
 break;
 case SpeechRecognizer.ERROR_RECOGNIZER_BUSY:
 message = "RecognitionService busy";
 break;
 case SpeechRecognizer.ERROR_SERVER:
 message = "error from server";
 break;
 case SpeechRecognizer.ERROR_SPEECH_TIMEOUT:
 message = "No speech input";
 break;
 default:
 message = "Didn't understand, please try again.";
 break;
 }
 return message;
 }
 
}