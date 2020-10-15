package com.phoenixfeatherapp.intouch;

import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.content.Context;
import org.apache.commons.validator.routines.InetAddressValidator;
import android.util.Log;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

public class Utility {
    public static final String TAG = "MainActivity:Utility";
    public static void hideSoftKeyboard(View v) {
        InputMethodManager inputMethodManager =
                (InputMethodManager) v.getContext().getSystemService(
                        Context.INPUT_METHOD_SERVICE);
        if(inputMethodManager != null) {
            Log.i(TAG," hideSoftKeyboard(v) - inputMethodManager is NOT null");
            inputMethodManager.hideSoftInputFromWindow(v.getWindowToken(), 0);
        }
    }

    public static void showSoftKeyboard(View v) {
        InputMethodManager inputMethodManager =
                (InputMethodManager) v.getContext().getSystemService(
                        Context.INPUT_METHOD_SERVICE);
        if(inputMethodManager != null) {
            Log.i(TAG," showSoftKeyboard(v) - inputMethodManager is NOT null");


                inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);

        }
    }

    public static String getLocalIpAddress() {
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements();) {
                NetworkInterface intf = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements();) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress() && inetAddress instanceof Inet4Address) {
                        return inetAddress.getHostAddress();
                    }
                }
            }
        } catch (SocketException ex) {
            ex.printStackTrace();
        }
        return null;
    }
    public static boolean validateIPAddress(String ipAddress) {
        InetAddressValidator validatorObj = InetAddressValidator.getInstance();
        return validatorObj.isValidInet4Address(ipAddress);
    }
}
