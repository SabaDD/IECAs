/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Client;

import Server.ServerStuff;
import Client.ClientSocket;
import java.net.Socket;
import java.util.HashMap;
import org.junit.Test;

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

    @org.junit.After
    public void tearDown() throws Exception {
        //Closing the streams and connections
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
        //s.flightInfosToString(s.searchRequest("search THR MHD 05Feb 2 1 1"));
//        assertEquals(result, s.flightInfosToString(s.searchRequest("search THR MHD 05Feb 2 1 1")));
//        assertEquals(2,2);
    }

    @Test
    public void test2() {
        assert (false);
    }
}