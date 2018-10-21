package com.example.lab_tab;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import android.app.PendingIntent;
import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.telephony.SmsManager;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.ToggleButton;

 public class MyLL implements LocationListener {
		LocationListener locationListener;
		ToggleButton mySwitch;
		SeekBar bar;
		String message;
		 TextToSpeech tts;
		byte[] msgBuffer;
		ImageButton f,b,l,r,stop,help;
		int state=0;
		SmsManager smsManager;
		 PendingIntent pi;
		 String phoneNumber="09797577606";
		 String msg="hey success";
		 LocationManager locationManager;
			String longitude;
	        String latitude ;
	        String cityName = null;
	        Geocoder gcd ;
	        List<Address> addresses;
		
		 String et;

	   
	    private String s;
        @Override
        public void onProviderDisabled(String provider) {}

        @Override
        public void onProviderEnabled(String provider) {}

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {}

		@Override
		public void onLocationChanged(Location loc) {
			// TODO Auto-generated method stub
			 longitude = "Longitude: " + loc.getLongitude();
	         latitude = "Latitude: " + loc.getLatitude();
	        
	        gcd = new Geocoder(null, Locale.getDefault());
	        
	         try {
	             addresses = gcd.getFromLocation(loc.getLatitude(),
	                     loc.getLongitude(), 1);
	             if (addresses.size() > 0) {
	                 System.out.println(addresses.get(0).getLocality());
	                 cityName = addresses.get(0).getLocality();
	             }
	         }
	         catch (IOException e) {
	             e.printStackTrace();
	         }
	  s = longitude + "\n" + latitude + "\n\nMy Current City is: "
	             + cityName;
	        
			
		}

		public String getS() {
			return s;
		}

		public void setS(String s) {
			this.s = s;
		}
    }