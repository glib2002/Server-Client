/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaserver.client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Глеб
 */
public class Server implements Runnable {

    private static final int SERVER_TIMEOUT = 1000;

    private int port;
    private ServerSocket serverSocket;
    private boolean listen;

    public Socket accept;

    public Server(int port) throws IOException {
        this.port = port;
        this.serverSocket = new ServerSocket(port);
        this.serverSocket.setSoTimeout(SERVER_TIMEOUT);
    }

    public void start() throws IOException {
        try {
            connecting();

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void connecting() throws IOException {
        try {
            accept = serverSocket.accept();
            accept.setSoLinger(true, 500); // Waiting before close connection
            accept.setSoTimeout(1000);
            //Socket accept = serverSocket.accept();
            //String clientAddress = accept.getInetAddress().getHostAddress();
            String clientAddress = accept.getInetAddress().getHostAddress();
            System.out.println("CONNECTED: " + clientAddress);
            send();
        } catch (SocketTimeoutException ex) {
            System.err.println("CONNECTION STOPED");
        }
    }

    private void getFromServer() throws IOException {
        DataInputStream dis = new DataInputStream(accept.getInputStream());
        System.out.println("" + dis.readUTF());
    }

    private void send() throws IOException {

        Scanner scn = new Scanner(System.in);

        while (true) {

            System.out.println("ENTER FOR CLIENT(for exit enter 'exit')");
            DataOutputStream dos = new DataOutputStream(accept.getOutputStream());
            dos.writeUTF(scn.nextLine());
            dos.flush();

            if (scn.nextLine().equals("exit")) {
                System.exit(0);
            }
            getFromServer();
        }
    }

    @Override
    public void run() {

        try {
            start();
        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
