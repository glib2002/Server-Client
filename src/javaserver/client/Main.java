/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaserver.client;

import java.io.IOException;

/**
 *
 * @author Глеб
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        Server s = new Server(5678);
        Client c = new Client(); 
        Thread t = new Thread(s);
        Thread t2 = new Thread(c);
        t.start();
        t2.start();
    }
    
}
