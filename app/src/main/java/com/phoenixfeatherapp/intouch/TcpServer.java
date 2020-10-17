package com.phoenixfeatherapp.intouch;

import android.app.Activity;
import android.util.Log;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

public class TcpServer extends Thread {
    private static final String TAG = "MainActivity_TcpServer";
    private int LISTEN_PORT;
    private MainActivity activityRef;
    private TcpSession session;
    private ServerSocket serverSock;
    private Socket connectionSock;
    private boolean clientConnectionActive;

    public boolean isServerRunning() {
        return isRunning;
    }

    private boolean isRunning;

    public void closeServerSocket() throws IOException {
        Log.i(TAG,"closeServerSocket() - closing server socket");
        this.serverSock.close();
    }
    public TcpSession getSession() {
        return session;
    }

    public boolean isClientConnectionActive() {
        return clientConnectionActive;
    }

    TcpServer() {
        Log.i(TAG,"Constructor #0 no args");
        clientConnectionActive = false;
        LISTEN_PORT = -1;
        isRunning = false;
    }
    TcpServer(int port, MainActivity activityId) {
        Log.i(TAG,"Constructor #1: Port[" + port + "], Activity[" + activityId.getClass().getSimpleName() + "]");
        this.LISTEN_PORT = port;
        this.activityRef = activityId;
        clientConnectionActive = false;
        isRunning = false;
    }

    @Override
    public void run() {
        Log.i(TAG,"run() - Entered");
        try {
            serverSock = new ServerSocket(LISTEN_PORT);
        } catch (IOException e) {
            e.printStackTrace();
        }

        if(serverSock != null) {
            //activityRef.runOnUiThread();
            Log.i(TAG,"run() - ServerSocket created");
            while (!this.isInterrupted()) {
                Log.i(TAG,"run() - Entered while() for accepting connections");
                if(clientConnectionActive) {
                    try {
                        Log.i(TAG,"run() - A client connection is already active, sleep for 10 seconds");
                        sleep(10000);
                        continue;
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                try {
                    Log.i(TAG,"run() - Calling accept()");
                    isRunning = true;
                    connectionSock = serverSock.accept();
                    if(connectionSock != null) {
                        Log.i(TAG,"run() - Client connection received! ClientIPAddress[" + connectionSock.getInetAddress().toString() +"]" + "ClientConnectionPort[" + connectionSock.getPort() + "]");
                        clientConnectionActive = true;
                        activityRef.setActiveConnection(true);  //Disable 'Connect' button as we have an active connection now
                        session = new TcpSession(connectionSock);
                        session.start();
                        final Socket tmpSock = connectionSock;
                        activityRef.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                //Update TextView below 'Connect' button
                                Log.i(TAG,"runOnUiThread() - Client connection received! ClientConnectionPort[" + tmpSock.getPort() + "]");
                                activityRef.updateClientConnectionLog(tmpSock.getInetAddress().toString());
                            }
                        });
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(serverSock != null) {
                try {
                    serverSock.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
