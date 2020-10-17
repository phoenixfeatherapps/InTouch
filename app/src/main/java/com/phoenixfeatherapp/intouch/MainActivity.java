package com.phoenixfeatherapp.intouch;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.util.Log;
import android.widget.EditText;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.net.InetAddress;

import static com.phoenixfeatherapp.intouch.Utility.validateIPAddress;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "MainActivity";
    private static final int SERVER_PORT = 7865;
    private String partnerIpAddress = "";
    private String ownIpAddress = "";
    private TcpServer myTcpServerThread;
    private boolean activeConnection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(TAG,"onCreate() - Entered");
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.splash_screen);
        setContentView(R.layout.activity_main);
        myTcpServerThread = new TcpServer(SERVER_PORT,this);
        myTcpServerThread.start();
        Button connectButton = (Button) findViewById(R.id.connectButton);
        connectButton.setOnClickListener(this);
        EditText enterPartnerDeviceEditText = (EditText) findViewById(R.id.enterPartnerDeviceId);
        enterPartnerDeviceEditText.setOnClickListener(this);
        FocusListener focusListenerObj = new FocusListener(this.getApplicationContext());
        focusListenerObj.setFocusChangeListener((View)enterPartnerDeviceEditText);
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
                if(activeConnection) {
                    Log.i(TAG,"onClick() - Disabling 'connect' button as there is an active connection already");
                    Toast t = Toast.makeText(this.getApplicationContext(),"Already connected to another device!",Toast.LENGTH_SHORT);
                    t.show();
                    break;
                }
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
                    if(!ownIpAddress.isEmpty()) {
                        Log.i(TAG, "onClick() - Own device IP address is  " + ownIpAddress);

                    }
                    else {
                        Log.i(TAG, "onClick() - Own device IP address is NULL. ERROR!");
                        break;
                    }
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
        myTcpServerThread.interrupt();
        Log.i(TAG,"onDestroy() - Called myTcpServerThread.interrupt()");
        try {
            myTcpServerThread.closeServerSocket();
            Log.i(TAG,"onDestroy() - ServerSocket closed");
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            Log.i(TAG,"onDestroy() - Now wait 5 secs for TcpServer thread to join");
            myTcpServerThread.join(50000);
            Log.i(TAG,"onDestroy() - myTcpServerThread joined!!!");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Log.i(TAG,"onDestroy() - Leaving");
    }

    public void updateClientConnectionLog(String clientIPAddress) {
        Log.i(TAG,"updateClientConnectionLog() - Entered");
        TextView tv = (TextView) findViewById(R.id.connectionStartupWindow);
        tv.setText("Connection received from client IP " + clientIPAddress);
        Log.i(TAG,"updateClientConnectionLog() - updated textView from TcpServer using runOnUiThread()");
    }

    public void setActiveConnection(boolean val) {
        activeConnection = val;
        Log.i(TAG,"setActiveConnection() - activeConnection set to " + activeConnection);
    }
}