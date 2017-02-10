package Server;

import org.apache.log4j.Logger;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;


/**
 * Created by Paniz on 2/10/2017.
 */
public class ServerStuff {
    private static final Logger LOGGER =
            Logger.getLogger(ServerStuff.class.toString());
//            LOGGER.debug("Low priority.");
//            LOGGER.info("Next level up.");
//            LOGGER.warn("High priority.");
//            LOGGER.error("Higher'priority.");


    public static String request(String command, String IP, int port) throws IOException {
        InetAddress addr = InetAddress.getByName(IP);
        Socket socket = new Socket(addr, port);
        LOGGER.info("Socket to server is connected.");
        try {
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(new BufferedWriter(
                    new OutputStreamWriter(socket.getOutputStream())), true);
                String[] parts = command.split("\n");
                for (int i=0; i<parts.length; i++) {
                    out.println(parts[i]);
                }
                String response = "";
                String line;
                response = response + in.readLine() + "\n";
                while (in.ready()) {
                    response = response + in.readLine() + "\n";
                }
                return removeLastChar(response);
        } finally {
            LOGGER.info("Socket to server is closing. Returning the response");
            socket.close();
        }
    }

    public static String removeLastChar(String str) {
        if (str != null && str.length() > 0 && str.charAt(str.length() - 1) == '\n') {
            str = str.substring(0, str.length() - 1);
        }
        return str;
    }

    public static void main(String[] args) {
        try {
            //System.out.print("RES THR MHD 05Feb IR 452 M 1 0 0\nAli Ghol 123");
            //"AV THR MHD 05Feb"
            //"PRICE THR MHD IR M"
            //"FIN 76d2b2fa-24bb-3ff8-9580-c9867ced3ce9"
            System.out.print(request("FIN 6a4d00ca-57b4-e21e-e4f6-afced1f92e0a", "188.166.78.119", 8081));
        } catch (IOException e) {
            LOGGER.error("Problem in reading/writing from/to sockets: "+e,e);
        }
    }

}
