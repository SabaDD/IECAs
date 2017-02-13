/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import Server.ServerStuff;
import Client.ClientSocket;
import java.net.Socket;
import java.util.HashMap;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Sab
 */
public class ClientSocketTest {

    public String Ip;
    public int gPort;
    ServerStuff s = new ServerStuff();

    @org.junit.Before
    public void setUp() {
        //Connecting to the server
        s.setgPort(8081);
        s.setgIP("188.166.78.119");

    }

    @Test
    public void test1() {
        String result = "Flight: IR 452 Departure: 18:50 Arrival: 17:40 Airplane: M80\n"
                + "Class: B Price: 17000\n"
                + "Class: Y Price: 9000\n"
                + "Class: M Price: 13000\n"
                + "***\n"
                + "Flight: IR 822 Departure: 08:40 Arrival: 07:30 Airplane: 351\n"
                + "Class: C Price: 13000\n"
                + "Class: F Price: 9000\n"
                + "Class: Y\n";
        assertEquals(result, s.receiveRequest("search THR MHD 05Feb 2 1 1"));
    }

    @Test
    public void test2() {
        String result = "Flight: IR 452 Departure: 18:50 Arrival: 17:40 Airplane: M80\n"
                + "Class: B Price: 36000\n"
                + "Class: Y\n"
                + "Class: M Price: 28000\n"
                + "***\n"
                + "Flight: IR 822 Departure: 08:40 Arrival: 07:30 Airplane: 351\n"
                + "Class: C Price: 28000\n"
                + "Class: F Price: 20000\n"
                + "Class: Y\n";
        assertEquals(result, s.receiveRequest("search THR MHD 05Feb 5 2 1"));
    }

    @Test
    public void test3() {
        String result = "Flight: IR 452 Departure: 18:50 Arrival: 17:40 Airplane: M80\n"
                + "Class: B Price: 14000\n"
                + "Class: Y Price: 6000\n"
                + "Class: M Price: 10000\n"
                + "***\n"
                + "Flight: IR 822 Departure: 08:40 Arrival: 07:30 Airplane: 351\n"
                + "Class: C Price: 10000\n"
                + "Class: F Price: 6000\n"
                + "Class: Y\n";
        assertEquals(result, s.receiveRequest("search THR MHD 05Feb 0 2 2"));
    }
}
