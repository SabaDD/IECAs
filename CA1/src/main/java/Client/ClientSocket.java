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

    public static void main(String[] args) throws IOException {
        //Socket socket = new Socket("localhost", 8080);
        searchForCommand();
    }

    public static String[] Tokenizer(String command, String delim) {
        String[] token = command.split(delim);
        return token;
    }

    private static void searchForCommand() {
        //try (BufferedReader br = new BufferedReader(new FileReader(FILENAME))) {
        String sCurrentLine;
        //while ((sCurrentLine = br.readLine()) != null) {
        sCurrentLine = "search THR MHD 05Feb 10 2 0";
        String[] token = Tokenizer(sCurrentLine, " ");
        System.out.println(token[0]);
        if (token[0].equalsIgnoreCase("search")) {
            //String searchInfo = sCurrentLine.replace(token[0]+" ","");
            sendSearchCommand(sCurrentLine);
            showSearchResult();
        } else if (token[0].equals("reserve")) {
//                        int CounterForPassanger= Integer.parseInt(token[7])+Integer.parseInt(token[8])+Integer.parseInt(token[9]);
//                        Passenger[] listOfPassenger = new Passenger[CounterForPassanger];
//                        String reserveInfo = sCurrentLine.replace(token[0]+" ", "");
//                        for(int i = 0 ; i<CounterForPassanger; i++){
//                            sCurrentLine = br.readLine();
//                            String[] pp = Tokenizer(sCurrentLine," ");
//                            listOfPassenger[i] = new Passenger(pp[0],pp[1],pp[2]);
//                        }
//                        sendReserveCommand(reserveInfo,listOfPassenger);
//                        ShowReserveResult();
        } else if (token[0].equals("finalize")) {
            sendFinalizeCommand(token[1]);
            ShowFinalizeResult();
        }
        // }

//        } catch (IOException e) {
//            e.printStackTrace();
//        }

    }

    private static void sendSearchCommand(String searchInfo) {
        ServerStuff s = new ServerStuff();
        String result = s.receiveRequest(searchInfo);
        System.out.print(result);
    }

    private static void showSearchResult() {

    }

    private static void sendReserveCommand(String reserveInfo, Passenger[] listofPassenger) {

    }

    private static void ShowReserveResult() {

    }

    private static void sendFinalizeCommand(String token) {

    }

    private static void ShowFinalizeResult() {

    }


}
