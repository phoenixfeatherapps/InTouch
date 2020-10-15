package com.phoenixfeatherapp.intouch;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.util.Log;
import android.widget.EditText;
import android.widget.Button;
import android.widget.Toast;

import java.net.InetAddress;

import static com.phoenixfeatherapp.intouch.Utility.validateIPAddress;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "MainActivity";
    private String partnerIpAddress = "";
    private String ownIpAddress = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(TAG,"onCreate() - Entered");
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.splash_screen);
        setContentView(R.layout.activity_main);
        Button connectButton = (Button) findViewById(R.id.connectButton);
        connectButton.setOnClickListener(this);
        EditText enterPartnerDeviceEditText = (EditText) findViewById(R.id.enterPartnerDeviceId);
       // enterPartnerDeviceEditText.setFocusable(true);
       // enterPartnerDeviceEditText.setFocusableInTouchMode(true);
        enterPartnerDeviceEditText.setOnClickListener(this);
        FocusListener focusListenerObj = new FocusListener(this.getApplicationContext());
        focusListenerObj.setFocusChangeListener((View)enterPartnerDeviceEditText);
        /*View.OnFocusChangeListener editTextListener = new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                Log.i(TAG,"onFocusChange() - Entered - id == " + v.getId());
                if(!hasFocus) {
                    Log.i(TAG,"onFocusChange() - does not have focus!");
                    EditText editTextField = (EditText) v;
                    if((editTextField.getText().toString() == "")) {
                        Log.i(TAG,"onFocusChange() - field is empty!");
                        editTextField.setText(R.string.input_ip_address);
                    }


                }
            }
        };
        enterPartnerDeviceEditText.setOnFocusChangeListener(editTextListener);
*/

    }

    @Override
    public void onClick(View v) {
        Log.i(TAG,"onClick() - Entered");
        EditText editTextObj = (EditText) findViewById(R.id.enterPartnerDeviceId);
        switch(v.getId()) {
            case R.id.enterPartnerDeviceId:
                Log.i(TAG,"onClick() - enterPartnerDeviceId");
                if(editTextObj.getText().toString().equals(getString(R.string.input_ip_address)))
                    editTextObj.setText("");
                break;
            case R.id.connectButton:
                Log.i(TAG,"onClick() - connectButton");
                editTextObj.clearFocus();
                //validate input string, if invalid, create Error Toast & change focus to EditText, select text, popup keyboard
                if(!validateIPAddress(editTextObj.getText().toString())) {
                    Log.i(TAG,"onClick() - Invalid IP address");
                    Toast t = Toast.makeText(this.getApplicationContext(),"Invalid IP address!",Toast.LENGTH_SHORT);
                    t.show();
                    editTextObj.requestFocus();
                    editTextObj.selectAll();
                    Utility.showSoftKeyboard(v);

                }
                else {
                    Log.i(TAG, "onClick() - Valid IP address. Setting partnerIpAddress to " + editTextObj.getText().toString());
                    partnerIpAddress = editTextObj.getText().toString();
                    ownIpAddress = Utility.getLocalIpAddress();
                    Log.i(TAG, "onClick() - Own device IP address is  " + ownIpAddress);
                }
                break;
        }

    }

    public int connectToPartnerDevice() {
        return 1;
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


}