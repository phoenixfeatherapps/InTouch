package com.phoenixfeatherapp.intouch;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.util.Log;
import android.widget.EditText;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(TAG,"onCreate() - Entered");
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.splash_screen);
        setContentView(R.layout.activity_main);
        Button connectButton = (Button) findViewById(R.id.connectButton);
        connectButton.setOnClickListener(this);
        EditText enterPartnerDeviceEditText = (EditText) findViewById(R.id.enterPartnerDeviceId);
        enterPartnerDeviceEditText.setOnClickListener(this);
        enterPartnerDeviceEditText.setFocusable(true);
        enterPartnerDeviceEditText.setFocusableInTouchMode(true);
        /*
        View.OnFocusChangeListener editTextListener = new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                Log.i(TAG,"onFocusChange() - Entered");
                if(!hasFocus) {
                    Log.i(TAG,"onFocusChange() - does not have focus!");
                    EditText editTextField = (EditText) findViewById(R.id.enterPartnerDeviceId);
                    if((editTextField.getText().toString() == "")) {
                        editTextField.setText(R.string.input_ip_address);
                    }
                }
            }
        };
        enterPartnerDeviceEditText.setOnFocusChangeListener(editTextListener);
        */
    }

    @Override
    protected void onStart() {
        Log.i(TAG,"onStart() - Entered");
        super.onStart();
    }

    @Override
    protected void onResume() {
        Log.i(TAG,"onResume() - Entered");
        super.onResume();
    }
    @Override
    protected void onStop() {
        Log.i(TAG,"onStop() - Entered");
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        Log.i(TAG,"onDestroy() - Entered");
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {
        Log.i(TAG,"onClick() - Entered");
        switch(v.getId()) {
            case R.id.enterPartnerDeviceId:
                Log.i(TAG,"onClick() - enterPartnerDeviceId");
                EditText editTextObj = (EditText) v;
                if(editTextObj.getText().toString().equals(getString(R.string.input_ip_address)))
                    editTextObj.setText("");
                else if(editTextObj.getText().toString().isEmpty())
                    editTextObj.setText(R.string.input_ip_address);
                break;
            case R.id.connectButton:
                Log.i(TAG,"onClick() - connectButton");
                break;
        }

    }

}