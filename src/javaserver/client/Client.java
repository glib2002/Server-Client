/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaserver.client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Глеб
 */
public class Client implements Runnable {

    public Socket socket;
    public DataInputStream in;
    public DataOutputStream out;
    public static final String IP = "193.156.1.99";
    public static final int PORT = 5678;
    public Scanner scn;
    public String massage;

    public void start() {
        try {
            connecting();
            send();
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void send() throws IOException {
        scn = new Scanner(System.in);

        while (true) {
            System.out.println("ENTER MASSAGE TO SERVER(IF YOU WANT TO EXIT ENTER 'exit')");
            out.writeUTF(massage = scn.nextLine());
            out.flush();
            if (massage.equals("exit")) {
                System.exit(0);
            }
            getMassageFromServer();
        }
    }

    private void connecting() throws IOException {
        socket = new Socket();
        System.out.println("CLIENT CONNECTING...");
        InetSocketAddress serverAddress = new InetSocketAddress(IP, PORT);
        socket.connect(serverAddress);
        out = new DataOutputStream(socket.getOutputStream());
        in = new DataInputStream(socket.getInputStream());
        System.out.println("CONNECTION ESTABLISHED FOR CLIENT");
    }

    private void getMassageFromServer() throws IOException {

        String answer = in.readUTF();
        System.out.println("RESPONSE FROM SERVER: " + answer);
    }

    @Override
    public void run() {
        start();
    }
}
