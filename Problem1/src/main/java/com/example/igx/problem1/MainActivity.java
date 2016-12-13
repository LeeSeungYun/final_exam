package com.example.igx.problem1;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity /* implements Something1, Something2 */ {

    SensorManager SensorManager;
    Sensor gyro;
    Sensor accel;
    Sensor temp;
    Sensor light;
    GPSlocation gps = new GPSlocation(MainActivity.this);
    String string;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btn_getLocation = (Button) findViewById(R.id.btn_getLocation);
        Button btn_getSensors = (Button) findViewById(R.id.btn_getSensors);
        Button btn_sendMessage = (Button) findViewById(R.id.btn_sendMessage);

        final TextView text_selectedData = (TextView) findViewById(R.id.text_selectedData);
        final TextView text_selectedType = (TextView) findViewById(R.id.text_selectedType);
        final EditText edit_phoneNumber = (EditText) findViewById(R.id.edit_phoneNumber);

        SensorManager = (SensorManager)getSystemService(Context.SENSOR_SERVICE);
        gyro= SensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        temp = SensorManager.getDefaultSensor(Sensor.TYPE_TEMPERATURE);
        light = SensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        accel = SensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        SensorManager.registerListener(mySensorListener,gyro,SensorManager.SENSOR_DELAY_NORMAL);
        SensorManager.registerListener(mySensorListener,temp,SensorManager.SENSOR_DELAY_NORMAL);
        SensorManager.registerListener(mySensorListener,light,SensorManager.SENSOR_DELAY_NORMAL);
        SensorManager.registerListener(mySensorListener,accel,SensorManager.SENSOR_DELAY_NORMAL);

        btn_getLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                text_selectedType.setText("LOCATION");
                onStop();
                string = String.valueOf(gps.getLatitude())+" "+String.valueOf(gps.getLongitude());
                text_selectedData.setText(String.valueOf(gps.getLatitude())+" "+String.valueOf(gps.getLongitude()));
            }
        });

        btn_getSensors.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                text_selectedType.setText("SENSORS");
                onStart();
                text_selectedData.setText(string);
            }
        });

        btn_sendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str = edit_phoneNumber.getText().toString();

                Intent it = new Intent();
                it.setAction(Intent.ACTION_SENDTO);
                it.setData(Uri.parse("smsto:"+str));
                it.putExtra("sms_body", string);
                startActivity(it);

            }
        });
    }

    protected void onStart(){
        super.onStart();
        SensorManager.registerListener(mySensorListener,gyro,SensorManager.SENSOR_DELAY_NORMAL);
        SensorManager.registerListener(mySensorListener,temp,SensorManager.SENSOR_DELAY_NORMAL);
        SensorManager.registerListener(mySensorListener,accel,SensorManager.SENSOR_DELAY_NORMAL);
        SensorManager.registerListener(mySensorListener,light,SensorManager.SENSOR_DELAY_NORMAL);
    }

    protected void onStop(){
        super.onStop();
        SensorManager.unregisterListener(mySensorListener);
        SensorManager.unregisterListener(mySensorListener);
        SensorManager.unregisterListener(mySensorListener);
        SensorManager.unregisterListener(mySensorListener);
    }

    SensorEventListener mySensorListener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent sensorEvent) {
            switch (sensorEvent.sensor.getType()){
                case Sensor.TYPE_GYROSCOPE:
                    string = "자이로스코프\n";
                    string += String.valueOf(sensorEvent.values[0]+"\n");
                    string += String.valueOf(sensorEvent.values[1]+"\n");
                    string += String.valueOf(sensorEvent.values[2]+"\n");
                case Sensor.TYPE_TEMPERATURE:
                    string += "온도\n";
                    string += String.valueOf(sensorEvent.values[0]+"\n");
                    string += String.valueOf(sensorEvent.values[1]+"\n");
                    string += String.valueOf(sensorEvent.values[2]+"\n");
                case Sensor.TYPE_LIGHT:
                    string += "조도\n";
                    string += String.valueOf(sensorEvent.values[0]+"\n");
                    string += String.valueOf(sensorEvent.values[1]+"\n");
                    string += String.valueOf(sensorEvent.values[2]+"\n");
                case Sensor.TYPE_ACCELEROMETER:
                    string += "가속\n";
                    string += String.valueOf(sensorEvent.values[0]+"\n");
                    string += String.valueOf(sensorEvent.values[1]+"\n");
                    string += String.valueOf(sensorEvent.values[2]+"\n");
            }
        }
        @Override
        public void onAccuracyChanged(Sensor sensor, int i) {
        }
    };

}
