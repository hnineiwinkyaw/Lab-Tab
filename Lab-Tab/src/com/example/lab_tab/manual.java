package com.example.lab_tab;
 
import java.io.IOException;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.Toast;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.ToggleButton;
 
public class manual extends Activity {
	 GPSTracker gps;
	LocationListener locationListener;
	ToggleButton mySwitch;
	SeekBar bar;
	String message;
	 TextToSpeech tts;
	byte[] msgBuffer;
	ImageButton f,b,l,r,stop,help,alarm;
	int state=0;
	SmsManager smsManager;
	 PendingIntent pi;
	 String phoneNumber="09797577606";
	 String msg="hey success";
	 LocationManager locationManager;
		double longitude;
        double latitude ;
        String cityName = null;
        Geocoder gcd ;
        List<Address> addresses;
	 Context c=this;
	 String et;
	 private static int timeHour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
		private static int timeMinute = Calendar.getInstance().get(Calendar.MINUTE);
		AlarmManager alarmManager;
		private PendingIntent pendingIntent;
    
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.manual);
   	 pi = PendingIntent.getActivity(this, 0, new Intent(this, Second.class), 0);  
		
        mySwitch=(ToggleButton)findViewById(R.id.switch1);
        f=(ImageButton)findViewById(R.id.up);
     
       b=(ImageButton)findViewById(R.id.down);
        l=(ImageButton)findViewById(R.id.left);
         r=(ImageButton)findViewById(R.id.right);
         stop=(ImageButton)findViewById(R.id.stop);
         help=(ImageButton)findViewById(R.id.help);
         
         
         
         f.setOnClickListener(new OnClickListener() {
			
			@SuppressLint("ShowToast")
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if(state==1){
				   try {        	
	                   MainActivity.getOs().writeChar('F');
	                
	                } catch (Exception e1) {
	                    e1.printStackTrace();
	                    return;
	                }
				
			}
			else{
				Toast.makeText(getApplicationContext(), "Please turn the power button", 11);
			}}
		});
       
        help.setOnClickListener(new OnClickListener() {
 			
 			@SuppressLint("ShowToast")
 			@Override
 			public void onClick(View arg0) {
 				// TODO Auto-generated method stub
 				 smsManager = SmsManager.getDefault();
 				
 				
 				
 				
 				
 				 gps = new GPSTracker(manual.this);

                 // Check if GPS enabled
                 if(gps.canGetLocation()) {

                      latitude = gps.getLatitude();
                     longitude = gps.getLongitude();
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
			        sms.sendTextMessage(phoneNumber, null, msg, pi,null);       
		
 				Toast.makeText(getApplicationContext(), "Message sent!", 11);
 			
 				}
 		});
        stop.setOnClickListener(new OnClickListener() {
 			
 			@Override
 			public void onClick(View arg0) {
 				// TODO Auto-generated method stub
 				   try {        	
 	                   MainActivity.getOs().writeByte(0);
 	                
 	                } catch (Exception e1) {
 	                    e1.printStackTrace();
 	                    return;
 	                }
 				
 			}
 		});
        r.setOnClickListener(new OnClickListener() {
        	
 			
 			@Override
 			public void onClick(View arg0) {
 				// TODO Auto-generated method stub
 				if(state==1){
 				   try {        	
 	                   MainActivity.getOs().writeChar('R');
 	                
 	                } catch (Exception e1) {
 	                    e1.printStackTrace();
 	                    return;
 	                }}
 				else{
 					Toast.makeText(getApplicationContext(), "Please turn the power button", 11);
 				}
 			}
 		});
         l.setOnClickListener(new OnClickListener() {
 			
 			@Override
 			public void onClick(View arg0) {
 				// TODO Auto-generated method stub
 				if(state==1){
 				   try {        	
 	                   MainActivity.getOs().writeChar('L');
 	                
 	                } catch (Exception e1) {
 	                    e1.printStackTrace();
 	                    return;
 	                }}
 				else{
 					Toast.makeText(getApplicationContext(), "Please turn the power button", 11);
 				}
 				
 			}
 		});
         b.setOnClickListener(new OnClickListener() {
 			
 			@Override
 			public void onClick(View arg0) {
 				// TODO Auto-generated method stub
 				if(state==1){
 				   try {        	
 	                   MainActivity.getOs().writeChar('B');
 	                
 	                } catch (Exception e1) {
 	                    e1.printStackTrace();
 	                    return;
 	                }}
 				else{
 					Toast.makeText(getApplicationContext(), "Please turn the power button", 11);
 				}
 				
 			}
 		});
        
        bar=(SeekBar)findViewById(R.id.seekBar1);
        bar.setMax(255);
        bar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {

                @Override
                public void onStopTrackingTouch(SeekBar arg0) {
                 // TODO Auto-generated method stub

                }

                @Override
                public void onStartTrackingTouch(SeekBar arg0) {
                 // TODO Auto-generated method stub

                }

                @Override
                public void onProgressChanged(SeekBar sb, int progress, boolean arg2) {
                	if(state==1){
       message=String.valueOf(progress);
                 if(progress>235){
                	warn();
                    
                    Toast.makeText(getApplicationContext(),"Slow Down", Toast.LENGTH_SHORT

            	                ).show();
              
                 }
                
                    
                try {
                	// message=String.valueOf(progress);
                //	 msgBuffer= message.getBytes();
                 //   MainActivity.getOs().writeBytes("255");
                 //   MainActivity.getOs().write(msgBuffer);
                   MainActivity.getOs().writeByte(progress);
                   // anything you want
                  
                	
                  //  MainActivity.getOs().writeInt(progress); // anything you want
                   
                } catch (Exception e1) {
                    e1.printStackTrace();
                    return;
                }}
                	else{
        				Toast.makeText(getApplicationContext(), "Please turn the power button", 11);
        			}
                
                    
                    
                }

        		
               });

        mySwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            	 if (isChecked) {
                     // do something when check is selected
            		 
            		 
            	state=1;
            		 
                 } else {
                     //do something when unchecked
                	 
                	 state=0;
                		message=null;
                   	 try {
        		        	bar.setProgress(0);
        		        	 message="0";
        		              MainActivity.getOs().writeByte(0);// anything you want
        		          
        		         //   MainActivity.getOs().writeInt(0); // anything you want
        		           
        		        } catch (Exception e1) {
        		            e1.printStackTrace();
        		            return;
        		        }
                   	
                	 
                	 
                	 
                	 
                 }
            }
        });
    }
	public void warn() {
		// TODO Auto-generated method stub
		 et="Slow Down";
	        tts=new TextToSpeech(c, new TextToSpeech.OnInitListener() {

	            @Override
	            public void onInit(int status) {
	                // TODO Auto-generated method stub
	                if(status == TextToSpeech.SUCCESS){
	                    int result=tts.setLanguage(Locale.US);
	                    if(result==TextToSpeech.LANG_MISSING_DATA ||
	                            result==TextToSpeech.LANG_NOT_SUPPORTED){
	                        Log.e("error", "This Language is not supported");
	                    }
	                    else{
	                        ConvertTextToSpeech();
	                    }
	                }
	                else
	                    Log.e("error", "Initilization Failed!");
	            }
	        });


	    }

	  
	    private void ConvertTextToSpeech() {
	        // TODO Auto-generated method stub
	        String text = et;
	       
	            tts.speak(text, TextToSpeech.QUEUE_FLUSH, null);
	       
	    }
	    
	    private Location getLastBestLocation() {
	        Location locationGPS = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
	        Location locationNet = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

	        long GPSLocationTime = 0;
	        if (null != locationGPS) { GPSLocationTime = locationGPS.getTime(); }

	        long NetLocationTime = 0;

	        if (null != locationNet) {
	            NetLocationTime = locationNet.getTime();
	        }

	        if ( 0 < GPSLocationTime - NetLocationTime ) {
	            return locationGPS;
	        }
	        else {
	            return locationNet;
	        }
	    }
	
	

}

