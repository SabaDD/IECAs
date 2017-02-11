package Server;

import Common.FlightInfo;
import Common.Passenger;
import com.sun.xml.internal.ws.api.ha.StickyFeature;
import org.apache.log4j.Logger;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.util.*;


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
    public static String gIP = "188.166.78.119";
    public static int gPort = 8081;

    public static String getgIP() {
        return gIP;
    }

    public static int getgPort() {
        return gPort;
    }

    public static String request(String command, String IP, int port) throws IOException {
        InetAddress addr = InetAddress.getByName(IP);
        Socket socket = new Socket(addr, port);
        LOGGER.info("Socket to server is connected.");
        try {
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(new BufferedWriter(
                    new OutputStreamWriter(socket.getOutputStream())), true);
            String[] parts = Tokenizer(command, "\n");
            for (int i = 0; i < parts.length; i++) {
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

    public static String[] Tokenizer(String command, String delim) {
        String[] token = command.split(delim);
        return token;
    }

    public static String receiveRequest(String clientRequest) {
        String[] tokens = Tokenizer(clientRequest, " ");
        String info = clientRequest.replace(tokens[0] + " ", "");
        System.out.println(tokens[0]);
        String response = "";
        if (tokens[0].equals("search"))
            response = flightInfosToString(searchRequest(info));
        else if (tokens[0].equals("reserve"))
            response = reserveRequest(info);
//                int CounterForPassanger= Integer.parseInt(token[7])+Integer.parseInt(token[8])+Integer.parseInt(token[9]);
//                Passenger[] listOfPassenger = new Passenger[CounterForPassanger];
//                String reserveInfo = sCurrentLine.replace(token[0]+" ", "");
//                for(int i = 0 ; i<CounterForPassanger; i++){
//                    sCurrentLine = br.readLine();
//                    String[] pp = Tokenizer(sCurrentLine," ");
//                    listOfPassenger[i] = new Passenger(pp[0],pp[1],pp[2]);
//                }
//                sendReserveCommand(reserveInfo,listOfPassenger);
//                ShowReserveResult();


        else if (tokens[0].equals("finalize"))
            System.out.print("fin");
        //sendFinalizeCommand(tokens[1]);
//                ShowFinalizeResult();
//                response = null;

        return response;
    }

    public static ArrayList<FlightInfo> searchRequest(String info) {
        String result = null;
        String[] tokens = Tokenizer(info, " ");
        ArrayList<FlightInfo> flightInfos = new ArrayList<>();
        try {
            result = request("AV " + tokens[0] + " " + tokens[1] + " " + tokens[2], gIP, gPort);
            //result = request("PRICE "+)
        } catch (IOException e) {
            LOGGER.error("Problem in reading/writing from/to sockets: " + e, e);
        }
        String[] results = Tokenizer(result, "\n");
        if (results.length > 0) {
            for (int i = 0; i < results.length; i += 2) {
                Map<Character, Character> m1 = new HashMap<Character, Character>();
                Map<Character, Integer> m2 = new HashMap<Character, Integer>();
                FlightInfo f = new FlightInfo(m1, m2);
                String[] iresultsTokens = Tokenizer(results[i], " ");
                String[] iPresultsTokens = Tokenizer(results[i + 1], " ");
                f.setAirlineCode(iresultsTokens[0]);
                f.setFlightNo(iresultsTokens[1]);
                f.setDate(iresultsTokens[2]);
                f.setOriginCode(iresultsTokens[3]);
                f.setDestinationCode(iresultsTokens[4]);
                f.setArrivalTime(iresultsTokens[5]);
                f.setDepartureTime(iresultsTokens[6]);
                f.setAirplaneModel(iresultsTokens[7]);
                f.setAdultCount(Integer.parseInt(tokens[3]));
                f.setChildCount(Integer.parseInt(tokens[4]));
                f.setInfantCount(Integer.parseInt(tokens[5]));
                Map<Character, Character> m = new HashMap<Character, Character>();
                //ArrayList<HashMap<Character, Integer>> ms = new ArrayList<HashMap<Character, Integer>>();
                for (int j = 0; j < iPresultsTokens.length; j++) {
                    f.getClassSeat().put(iPresultsTokens[j].charAt(0), iPresultsTokens[j].charAt(1));
                    //m.put(iPresultsTokens[j].charAt(0),iPresultsTokens[j].charAt(1));
                }
                //f.setClassSeat(m);
                flightInfos.add(f);
            }
            for (int i = 0; i < flightInfos.size(); i++) {
                Iterator it = flightInfos.get(i).getClassSeat().entrySet().iterator();
                while (it.hasNext()) {
                    Map.Entry entry = (Map.Entry) it.next();
//                    if ((Character)entry.getValue()!='C'){
                    if ((Character) entry.getValue() == 'A' ||
                            Character.getNumericValue((Character) entry.getValue()) > (flightInfos.get(i).getAdultCount() +
                                    flightInfos.get(i).getChildCount() + flightInfos.get(i).getInfantCount())) {
                        String priceQuery = "PRICE " + flightInfos.get(i).getOriginCode() + " " +
                                flightInfos.get(i).getDestinationCode() + " " + flightInfos.get(i).getAirlineCode() + " " +
                                entry.getKey();
                        String priceResults = null;
                        //age null bood --> nemikhad
                        try {
                            priceResults = request(priceQuery, getgIP(), getgPort());
                        } catch (IOException e) {
                            LOGGER.error("Problem in reading/writing from/to sockets: " + e, e);
                        }
                        String[] priceResultsTokens = Tokenizer(priceResults, " ");
                        int adult = flightInfos.get(i).getAdultCount() * Integer.parseInt(priceResultsTokens[0]);
                        int child = flightInfos.get(i).getChildCount() * Integer.parseInt(priceResultsTokens[1]);
                        int infant = flightInfos.get(i).getInfantCount() * Integer.parseInt(priceResultsTokens[2]);
                        char temp = (Character) entry.getKey();
                        int sum = adult + child + infant;
                        flightInfos.get(i).getClassPrice().put(temp, sum);
                    } else {
                        // -1 neshoon mide ke teedade darkhasti bishtar az teedad e mojud boode
                        flightInfos.get(i).getClassPrice().put((Character) entry.getKey(), -1);
                    }
                }
//                for (Map.Entry<Character, Character> entry : flightInfos.get(i).getClassSeat().entrySet())
//                {
//
//                }
            }
        }

        //System.out.print(result);
        //String finalResult = null;
        //finalResult = flightInfosToString(flightInfos);
        return flightInfos;
    }

    public static String flightInfosToString(ArrayList<FlightInfo> fs) {
        String finalResult = "";
        for (int i = 0 ; i<fs.size(); i++){
            FlightInfo f = fs.get(i);
            String dTime = f.getDepartureTime().substring(0, 2) + ":" + f.getDepartureTime().substring(2, 4);
            String aTime = f.getArrivalTime().substring(0, 2) + ":" + f.getArrivalTime().substring(2, 4);
            finalResult = finalResult + "Flight: " + f.getAirlineCode() + " " + f.getFlightNo() +
                    " Departure: " + dTime + " Arrival: " + aTime + " Airplane: " + f.getAirplaneModel() + "\n";
            Iterator it = f.getClassSeat().entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry entry = (Map.Entry) it.next();
                finalResult = finalResult + "Class: " + entry.getKey();
                if (f.getClassPrice().get(entry.getKey()) != -1)
                    finalResult = finalResult + " Price: " + f.getClassPrice().get(entry.getKey());
                
                    finalResult = finalResult + "\n";
                
            }
            if(i != fs.size()-1)
                finalResult = finalResult+"***\n";
            
        }
        if (finalResult.length()>0 && finalResult.charAt(finalResult.length()-1) == '\n')
            finalResult = finalResult.substring(0, finalResult.length() - 1);
        return finalResult;
    }

    public static String reserveRequest(String info) {
        String result = "RES " + info;
        return result;
    }

//    public static void main(String[] args) {
//
//        //try {
//        //System.out.print("RES THR MHD 05Feb IR 452 M 1 0 0\nAli Ghol 123");
//        //"AV THR MHD 05Feb"
//        //"PRICE THR MHD IR M"
//        //"FIN 76d2b2fa-24bb-3ff8-9580-c9867ced3ce9"
//        //System.out.print(request("FIN 6a4d00ca-57b4-e21e-e4f6-afced1f92e0a", "188.166.78.119", 8081));
//
////        } catch (IOException e) {
////            LOGGER.error("Problem in reading/writing from/to sockets: "+e,e);
////        }
//    }

}
