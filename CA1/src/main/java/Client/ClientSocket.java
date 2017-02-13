/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Client;

import Common.Passenger;
import Server.ServerStuff;

import org.apache.log4j.Logger;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class ClientSocket {

    private static final String FILENAME = "E:\\IT\\Semester 10\\IE\\Projects\\CA1.1\\IECAs\\CA1\\test.txt";
    private static final Logger LOGGER = Logger.getLogger(ClientSocket.class.toString());
    private static String helper_server_ip ;
    private static int helper_server_port ;
    
    public static void main(String[] args) throws IOException {
        int port_number = Integer.parseInt(args[0]);
        helper_server_ip = args[1];
        helper_server_port = Integer.parseInt(args[2]);
        ServerSocket socket = new ServerSocket(port_number);
        Socket clientSocket = socket.accept();
        searchForCommand(clientSocket);
        clientSocket.close();
        socket.close();
    }

    public static String[] Tokenizer(String command, String delim) {
        String[] token = command.split(delim);
        return token;
    }

    private static void searchForCommand(Socket client) throws IOException {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()))) {
            PrintWriter out = new PrintWriter(client.getOutputStream(), true);
            //try (BufferedReader br = new BufferedReader(new FileReader(FILENAME))) {
            String sCurrentLine;
            while((sCurrentLine = in.readLine())!=null){
                System.out.println("in the ready while");
                LOGGER.info("in the ready while");
                //String sCurrentLine = in.readLine();
                System.out.println(sCurrentLine);
        //while ((sCurrentLine = br.readLine()) != null) {
                //"reserve THR MHD 05Feb IR 452 M 1 0 0\nAli Ghol 123";
                //String sCurrentLine = "search THR MHD 05Feb 10 2 0";
                //sCurrentLine = "reserve THR MHD 05Feb IR 452 M 1 0 0\nAli Ghol 123";
                String[] token = Tokenizer(sCurrentLine, " ");
                //System.out.println(token[0]);
                if (token[0].equalsIgnoreCase("search")) {
                    //String searchInfo = sCurrentLine.replace(token[0]+" ","");
                    sendSearchCommand(sCurrentLine,out);
                    showSearchResult();
                } else if (token[0].equals("reserve")) {
                    
                        int CounterForPassanger= Integer.parseInt(token[7])+Integer.parseInt(token[8])+Integer.parseInt(token[9]);
//                        Passenger[] listOfPassenger = new Passenger[CounterForPassanger];
//                        String reserveInfo = sCurrentLine.replace(token[0]+" ", "");
                        for(int i = 0 ; i<CounterForPassanger; i++){
                            sCurrentLine = sCurrentLine+"\n"+ in.readLine();
//                            String[] pp = Tokenizer(sCurrentLine," ");
//                            listOfPassenger[i] = new Passenger(pp[0],pp[1],pp[2]);
                        }
//                        sendReserveCommand(reserveInfo,listOfPassenger);
//                        ShowReserveResult();
                        System.out.println(sCurrentLine);
                    sendReserveCommand(sCurrentLine,out);
                } else if (token[0].equals("finalize")) {
                    sendFinalizeCommand(sCurrentLine,out);
                    ShowFinalizeResult();
                }
            }
            // }

//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static void sendSearchCommand(String searchInfo, PrintWriter out) {
        ServerStuff s = new ServerStuff();
        s.setgIP(helper_server_ip);
        s.setgPort(helper_server_port);
        String result = s.receiveRequest(searchInfo);
        System.out.print(result);
        out.print(result);
        
    }

    private static void showSearchResult() {

    }

    private static void sendReserveCommand(String reserveInfo,PrintWriter out) {
        ServerStuff s = new ServerStuff();
        s.setgIP(helper_server_ip);
        s.setgPort(helper_server_port);
        String result = s.receiveRequest(reserveInfo);
        System.out.print(result);
        out.print(result);
    }
    //public static waitforFinalize

    private static void ShowReserveResult() {

    }

    private static void sendFinalizeCommand(String finalizeInfo, PrintWriter out) {
        ServerStuff s = new ServerStuff();
        s.setgIP(helper_server_ip);
        s.setgPort(helper_server_port);
        String result = s.receiveRequest(finalizeInfo);
        System.out.print(result);
        out.print(result);
    }

    private static void ShowFinalizeResult() {

    }


}
