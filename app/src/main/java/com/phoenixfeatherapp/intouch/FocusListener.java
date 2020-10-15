package com.phoenixfeatherapp.intouch;

import android.content.res.Resources;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.content.Context;
import android.view.View;

import static com.phoenixfeatherapp.intouch.Utility.validateIPAddress;

public class FocusListener implements View.OnFocusChangeListener {
    private static final String TAG = "MainActivity";
    private Context appContext = null;

    public FocusListener() {  }
    public FocusListener(Context context) {
        this.appContext = context;
    }
    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        Log.i(TAG,"onFocusChange() - Entered - id == " + v.getId());

        if(v.getId() == R.id.enterPartnerDeviceId) {
            EditText editTextField = (EditText) v;
            if (!hasFocus) {
                Log.i(TAG, "onFocusChange() - does not have focus! - str == " + editTextField.getText().toString());
                if ((editTextField.getText().toString().isEmpty())) {
                    Log.i(TAG, "onFocusChange() - field is empty!");
                    editTextField.setTextColor(0xAFAFAFAF);
                    editTextField.setText(R.string.input_ip_address);
                }
                Utility.hideSoftKeyboard(v);
            }
            else {
                Log.i(TAG, "onFocusChange() - has focus! - str == " + editTextField.getText().toString());
                if(editTextField.getText().toString().equals(appContext.getResources().getString(R.string.input_ip_address))) {
                    Log.i(TAG, "onFocusChange() - clearing field!");
                    editTextField.setText("");
                    editTextField.setTextColor(0xFF000000);
                  //  Utility.showSoftKeyboard(v);
                }
            }

        }
    }

    public void setFocusChangeListener(View v) {
        v.setOnFocusChangeListener(this);
    }
}
