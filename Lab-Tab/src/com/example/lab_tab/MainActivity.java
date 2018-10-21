package com.example.lab_tab;

import java.io.DataOutputStream;
import java.lang.reflect.Method;
import java.util.Set;



import android.os.Bundle;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;


public class MainActivity extends Activity{
	 private static final int REQUEST_ENABLE_BT = 1;

	 

	 private TextView text;

	   private BluetoothAdapter myBluetoothAdapter;

	   private Set<BluetoothDevice> pairedDevices;
	   BluetoothDevice rmdevice;
	   BluetoothSocket clientSocket;
	  private static DataOutputStream os;
	  final Context c=this;
	  Intent intent;
	  Button b;
	  private String[] add;
	   

public static DataOutputStream getOs() {
		return os;
	}

	public  void setOs(DataOutputStream os) {
		MainActivity.os = os;
	}

private ListView myListView;

	   private ArrayAdapter<String> BTArrayAdapter;
	 
	   ToggleButton s;
	   
	   

		@Override
		protected void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			
			setContentView(R.layout.activity_main);
			
			b=(Button)findViewById(R.id.button1);
			b.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View arg0) {

				    // get paired devices

					      pairedDevices = myBluetoothAdapter.getBondedDevices();
					    
					       

					      // put it's one to the adapter

					      for(BluetoothDevice device : pairedDevices){
					    	  

					          BTArrayAdapter.add(device.getName()+ "\n" + device.getAddress());
					         
			                 }
					      

					   }
				
			});
			
			
			
			
			
			
			
			 myBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
		      if(myBluetoothAdapter == null) {
		    	   text = (TextView) findViewById(R.id.suptex);
		    	   
			       
			        

			          text.setText(" ");

		      }
		      else {
		    	   text = (TextView) findViewById(R.id.suptex);
		    	   
			       
			        

			          text.setText("  ");


		    	   s = (ToggleButton)findViewById(R.id.s);
		    	   
			          myListView = (ListView)findViewById(R.id.lv);

			 	     

			          // create the arrayAdapter that contains the BTDevices, and set it to the ListView

			          BTArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);

			          myListView.setAdapter(BTArrayAdapter);
			          myListView.setOnItemClickListener(new OnItemClickListener() {
	                     

						@Override
						public void onItemClick(AdapterView<?> a, View v,
								int position, long id) {
							// TODO Auto-generated method stub
							add=new String[100];
							int i=0;
							
							  pairedDevices = myBluetoothAdapter.getBondedDevices();
							
						   // put it's one to the adapter

						      for(BluetoothDevice device : pairedDevices){
						    	  add[i]=device.getAddress();
						    	  i++;
	 
				                 }
						      Toast.makeText(getApplicationContext(),add[position] +"connecting",

						                 Toast.LENGTH_LONG).show();
					           
							
							
							 try{
							 rmdevice = myBluetoothAdapter.getRemoteDevice(add[position]);

					            Method m = rmdevice.getClass().getMethod("createRfcommSocket", new Class[] {int.class});

					            clientSocket =  (BluetoothSocket) m.invoke(rmdevice, 1);

					            clientSocket.connect();
					            Toast.makeText(getApplicationContext(),add[position] +"connected",

						                 Toast.LENGTH_LONG).show();
					           

					           os = new DataOutputStream(clientSocket.getOutputStream());
					           
					           
					          Intent intent = new Intent(c, Second.class);
					   	startActivity(intent);
					           
					           

					         //  new clientSock().start();
					        } catch (Exception e) {
					            e.printStackTrace();
					            Log.e("BLUETOOTH", e.getMessage());
					            Toast.makeText(getApplicationContext(),add[position] +"connection fail",

						                 Toast.LENGTH_LONG).show();
					           
					        }
							
						}
	                  });
			          
			    	  s.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			               public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
			            		 if (isChecked) {
			            			  if (!myBluetoothAdapter.isEnabled()) {

			         			         Intent turnOnIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);

			         			         startActivityForResult(turnOnIntent, REQUEST_ENABLE_BT);

			         		 
			         			      }

			         			      else{

			         			         Toast.makeText(getApplicationContext(),"Bluetooth is already on",

			         			                 Toast.LENGTH_SHORT).show();

			         			      }

			            			 
			            			 
			            			 
			            		 }
			            		 else{
			            			  myBluetoothAdapter.disable();

			            		      text.setText("Status: Disconnected");

			            		       

			            		      Toast.makeText(getApplicationContext(),"Bluetooth turned off",

			            		              Toast.LENGTH_SHORT).show();
			            		 }
			               }
			    	  });
			
		      }
		}
		@Override
		public boolean onCreateOptionsMenu(Menu menu) {
			// Inflate the menu; this adds items to the action bar if it is present.
			getMenuInflater().inflate(R.menu.main, menu);
			return true;
		}
		
		 final BroadcastReceiver bReceiver = new BroadcastReceiver() {
			  

		        public void onReceive(Context context, Intent intent) {

		            String action = intent.getAction();

	            // When discovery finds a device

		            if (BluetoothDevice.ACTION_FOUND.equals(action)) {

	                // Get the BluetoothDevice object from the Intent

		                 BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);

		                 // add the name and the MAC address of the object to the arrayAdapter

		                 BTArrayAdapter.add(device.getAddress());

		                 BTArrayAdapter.notifyDataSetChanged();
		               

		            }

		        }

		    };

		    @Override

			   protected void onDestroy() {

			       // TODO Auto-generated method stub

			       super.onDestroy();
			       unregisterReceiver(bReceiver);

			   }
	   

}
