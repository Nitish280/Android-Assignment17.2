package com.example.lenovo.android172;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    //declaration

    TextView textView;
    Button button;
    BoundService boundService;
    boolean serviceBound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //setting up UI component
        textView=(TextView)findViewById(R.id.textView);
        button=(Button)findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textView.setText(boundService.getTime());
            }
        });
    }

    @Override
    protected void onStart() {//method onStart
        super.onStart();
        //creating object of intent
        // Bind to LocalService
        Intent intent = new Intent(this, BoundService.class);
        bindService(intent,serviceConnection,BIND_AUTO_CREATE);

    }
    //creating object of serviceConnection
    ServiceConnection serviceConnection = new ServiceConnection() {
        public void onServiceDisconnected(ComponentName name) {
            Toast.makeText(MainActivity.this, "Service is disconnected", Toast.LENGTH_SHORT).show();
            serviceBound=false;

        }
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {//creating method onServiceConected
            Toast.makeText(MainActivity.this, "Service is connected", Toast.LENGTH_SHORT).show();
            serviceBound = true;
            // Here we are bounding  the LocalService,
            BoundService.LocalBinder localBinder = (BoundService.LocalBinder) iBinder;
            //getting localService Instance
            boundService = localBinder.getService();
        }
    };


        protected void onStop(){
            MainActivity.super.onStop();
            // Unbind from the service
            if (serviceBound)
            {
                unbindService(serviceConnection);
                serviceBound= false;

            }
        }
    }
