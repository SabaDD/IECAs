/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import java.net.Socket;
import java.util.HashMap;
import org.junit.Test;

/**
 *
 * @author Sab
 */
public class ticketPriceText {
    public String Ip;
    public int gPort;

    public ticketPriceText() {
        
    }
    
    @org.junit.Before
    public void setUp(){
        //Connecting to the server
        this.gPort = 8081;
        this.Ip = "188.166.78.119";
       
    }

    @org.junit.After
    public void tearDown() throws Exception {
        //Closing the streams and connections
    }

    @Test
    public void test1() {
        assert(true);
    }

    @Test
    public void test2() {
        assert(false);
    }
}
