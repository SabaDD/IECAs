/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Client;

import Common.Passenger;
import Server.ServerStuff;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;


public class ClientSocket {
    private static final String FILENAME = "E:\\IT\\Semester 10\\IE\\Projects\\CA1.1\\IECAs\\CA1\\test.txt";
    public static ServerStuff s;
    public static void main(String[] args) throws IOException {
        //"reserve THR MHD 05Feb IR 452 M 1 0 0\nAli Ghol 123";
        //search THR MHD 15Feb 10 2 0
        s = new ServerStuff();
        String command;
        String token= "";
        command = "reserve THR MHD 05Feb IR 452 M 1 0 0\nAli Ghol 123";
        token = searchForCommand(command);
        String[] tokenTokens = Tokenizer(token, " ");
        command = "search THR MHD 05Feb 10 2 0";
        searchForCommand(command);
        command = "finalize "+tokenTokens[0];
        searchForCommand(command);
    }

    public static String[] Tokenizer(String command, String delim) {
        String[] token = command.split(delim);
        return token;
    }

    private static String searchForCommand(String command) {
        String result = "";
        //while ((command = br.readLine()) != null) {
        String[] token = Tokenizer(command, " ");
        if (token[0].equalsIgnoreCase("search")) {
            sendSearchCommand(command);
            showSearchResult();
        } else if (token[0].equals("reserve")) {
            result = sendReserveCommand(command);
        } else if (token[0].equals("finalize")) {
            sendFinalizeCommand(command);
            ShowFinalizeResult();
        }
        return result;
    }

    private static void sendSearchCommand(String searchInfo) {
        //ServerStuff s = new ServerStuff();
        String result = s.receiveRequest(searchInfo);
        System.out.println(result);
    }

    private static void showSearchResult() {

    }

    private static String sendReserveCommand(String reserveInfo) {
        //ServerStuff s = new ServerStuff();
        String result = s.receiveRequest(reserveInfo);
        System.out.println(result);
        return result;
    }
    //public static waitforFinalize

    private static void ShowReserveResult() {

    }

    private static void sendFinalizeCommand(String finalizeInfo) {
        //ServerStuff s = new ServerStuff();
        String result = s.receiveRequest(finalizeInfo);
        System.out.println(result);
    }

    private static void ShowFinalizeResult() {

    }


}
