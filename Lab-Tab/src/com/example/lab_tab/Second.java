package com.example.lab_tab;

 
import android.app.TabActivity;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
 
public class Second extends TabActivity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.second);
         
        TabHost tabHost = getTabHost();
         
        // Tab for Photos
        TabSpec photospec = tabHost.newTabSpec("Voice");
        // setting Title and Icon for the Tab
        photospec.setIndicator("Voice", getResources().getDrawable(R.drawable.micx));
        Intent photosIntent = new Intent(this, mic.class);
        photospec.setContent(photosIntent);
         
        // Tab for Songs
        TabSpec songspec = tabHost.newTabSpec("Manual");        
        songspec.setIndicator("Manual", getResources().getDrawable(R.drawable.manx));
        Intent songsIntent = new Intent(this, manual.class);
        songspec.setContent(songsIntent);
  
         
        // Adding all TabSpec to TabHost
    //    tabHost.addTab(photospec); // Adding photos tab
        tabHost.addTab(songspec); // Adding songs tab
        tabHost.addTab(photospec); // Adding photos tab
         // Adding videos tab
    }
}