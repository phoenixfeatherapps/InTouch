package com.phoenixfeatherapp.intouch;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class TcpSession extends Thread {
    private Socket connectionSocket;
    private BufferedReader input;
    private PrintWriter output;

    TcpSession(Socket conn) throws IOException {
        this.connectionSocket = conn;
        input = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
        output = new PrintWriter(connectionSocket.getOutputStream());
    }

    @Override
    public void run() {

    }
}
