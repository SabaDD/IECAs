package Server;

//import Client.ClientSocket;
import Common.FlightInfo;
import Common.TicketInfo;
//import org.apache.log4j.Logger;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.util.*;
import jdk.nashorn.internal.codegen.CompilerConstants;


/**
 * Created by Paniz on 2/10/2017.
 */
public class ServerStuff {
    //private static final Logger LOGGER =
       //     Logger.getLogger(ServerStuff.class.toString());
    public static String gIP = "188.166.78.119";
    public static int gPort = 8081;
    static HashMap<Socket, String> socketTokens;
    static HashMap<String, String> tokensInfo;

    public static HashMap<Socket, String> getSocketTokens() {
        return socketTokens;
    }

    public static void setSocketTokens(HashMap<Socket, String> socketTokens) {
        ServerStuff.socketTokens = socketTokens;
    }

    public ServerStuff() {
        socketTokens = new HashMap<Socket, String>();
        tokensInfo = new HashMap<String, String>();
    }

    public String getgIP() {
        return gIP;
    }

    public int getgPort() {
        return gPort;
    }
    public void setgIP(String gIP){
        this.gIP = gIP;
    }
    public void setgPort(int gPort){
        this.gPort = gPort;
    }

    public String requestToHelperServer(String command, String IP, int port) throws IOException {
        InetAddress addr = InetAddress.getByName(IP);
        String response = "";
        String[] spaceParts = Tokenizer(command, " ");
        Socket socket = new Socket(addr, port);
       // LOGGER.info("Socket to server is connected.");
        try {
            if (spaceParts[0].equals("FIN")){
                if(getSocketTokens().containsValue(spaceParts[1])){
                    for(Map.Entry<Socket, String> entry: socketTokens.entrySet()) {
                        if (entry.getValue().equals(spaceParts[1])){
                            socket = entry.getKey();
                            break;
                        }
                    }
                } else{
                    return response;
                }
            }
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(new BufferedWriter(
                    new OutputStreamWriter(socket.getOutputStream())), true);
            String[] nLParts = Tokenizer(command, "\n");
            for (int i = 0; i < nLParts.length; i++) {
                out.println(nLParts[i]);
            }
            String line;
            response = response + in.readLine() + "\n";
            while (in.ready()) {
                response = response + in.readLine() + "\n";
            }
            if (spaceParts[0].equals("RES")) {
                String[] responseTokens = Tokenizer(response, " ");
                socketTokens.put(socket, responseTokens[0]);
                tokensInfo.put(responseTokens[0],command);
            }
            return removeLastChar(response);
        } finally {
            if (!spaceParts[0].equals("RES")) {
//                LOGGER.info("Socket to server is closing. Returning the response");
                socket.close();
            }
        }
    }

    public String removeLastChar(String str) {
        if (str != null && str.length() > 0 && str.charAt(str.length() - 1) == '\n') {
            str = str.substring(0, str.length() - 1);
        }
        return str;
    }

    public String[] Tokenizer(String command, String delim) {
        String[] token = command.split(delim);
        return token;
    }

    public String receiveRequest(String clientRequest) {
        String[] tokens = Tokenizer(clientRequest, " ");
        String info = clientRequest.replace(tokens[0] + " ", "");
        String response = "";
        if (tokens[0].equals("search"))
            response = flightInfosToString(searchRequest(info));
        else if (tokens[0].equals("reserve"))
            response = reserveRequest(info);
        else if (tokens[0].equals("finalize")) {
            response = finalizeRequest(info);
        }
        return response;
    }

    public  ArrayList<FlightInfo> searchRequest(String info) {
        String result = null;
        String[] tokens = Tokenizer(info, " ");
        String finalResult = "";
        ArrayList<FlightInfo> flightInfos = new ArrayList<>();
        try {
            result = requestToHelperServer("AV " + tokens[0] + " " + tokens[1] + " " + tokens[2], gIP, gPort);
        } catch (IOException e) {
//            LOGGER.error("Problem in reading/writing from/to sockets: " + e, e);
        }
        if (result.length() != 0) {
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
                    for (int j = 0; j < iPresultsTokens.length; j++) {
                        f.getClassSeat().put(iPresultsTokens[j].charAt(0), iPresultsTokens[j].charAt(1));
                    }
                    flightInfos.add(f);
                }
                for (int i = 0; i < flightInfos.size(); i++) {
                    Iterator it = flightInfos.get(i).getClassSeat().entrySet().iterator();
                    while (it.hasNext()) {
                        Map.Entry entry = (Map.Entry) it.next();
                        if ((Character) entry.getValue() == 'A' ||
                                Character.getNumericValue((Character) entry.getValue()) > (flightInfos.get(i).getAdultCount() +
                                        flightInfos.get(i).getChildCount() + flightInfos.get(i).getInfantCount())) {
                            String priceQuery = "PRICE " + flightInfos.get(i).getOriginCode() + " " +
                                    flightInfos.get(i).getDestinationCode() + " " + flightInfos.get(i).getAirlineCode() + " " +
                                    entry.getKey();
                            String priceResults = null;
                            try {
                                priceResults = requestToHelperServer(priceQuery, getgIP(), getgPort());
                            } catch (IOException e) {
//                                LOGGER.error("Problem in reading/writing from/to sockets: " + e, e);
                            }
                            String[] priceResultsTokens = Tokenizer(priceResults, " ");
                            int adult = flightInfos.get(i).getAdultCount() * Integer.parseInt(priceResultsTokens[0]);
                            int child = flightInfos.get(i).getChildCount() * Integer.parseInt(priceResultsTokens[1]);
                            int infant = flightInfos.get(i).getInfantCount() * Integer.parseInt(priceResultsTokens[2]);
                            char temp = (Character) entry.getKey();
                            int sum = adult + child + infant;
                            flightInfos.get(i).getClassPrice().put(temp, sum);
                        } else {
                            flightInfos.get(i).getClassPrice().put((Character) entry.getKey(), -1);
                        }
                    }

                }
            }
            finalResult = flightInfosToString(flightInfos);
        }
        return flightInfos;
    }

    public String flightInfosToString(ArrayList<FlightInfo> fs) {
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

        return finalResult;
    }

    public  String reserveRequest(String info) {
        String request = "RES " + info;
        String result = "";
        String finalResult = "";
        try {
            result = requestToHelperServer(request, gIP, gPort);
        } catch (IOException e) {
//            LOGGER.error("Problem in reading/writing from/to sockets: " + e, e);
        }
        if (!result.equals("")){
            int totalPrice = calculateReservePrice(info, result);
            String[] resultTokens = Tokenizer(result, " ");
            finalResult = resultTokens[0]+" "+totalPrice+"\n";
        }
        return finalResult;
    }
    public int calculateReservePrice(String info, String result){
        int totalPrice = 0;
        String[] infoTokens = Tokenizer(info, "\n");
        String[] firstLine = Tokenizer(infoTokens[0], " ");
        String[] resultTokens = Tokenizer(result, " ");
        int adultC = Integer.parseInt(firstLine [6]);
        int childC = Integer.parseInt(firstLine[7]);
        int infantC = Integer.parseInt(firstLine[8]);
        int adultP = Integer.parseInt(resultTokens[1]);
        int childP = Integer.parseInt(resultTokens[2]);
        int infantP = Integer.parseInt(resultTokens[3]);
        totalPrice = (adultC*adultP) + (childC*childP) + (infantC*infantP);
        return totalPrice;
    }
    public String finalizeRequest(String info){
        String request = "FIN "+ info;
        String result = "";
        try {
            result = requestToHelperServer(request, gIP, gPort);
        } catch (IOException e) {
//            LOGGER.error("Problem in reading/writing from/to sockets: " + e, e);
        }
        String finalResult = "";
        if(!result.equals(""))
            finalResult = finalizeResponseToTicketInfo(info, result);
        return finalResult;
    }
    public String finalizeResponseToTicketInfo(String info, String refrence){
        String ticketInfo = "";
        String reserveInfo = "";
        String[] referenceTokens = Tokenizer(refrence, "\n");
        for(Map.Entry<String, String> entry: tokensInfo.entrySet()) {
            if (entry.getKey().equals(info)){
                reserveInfo = entry.getValue();
                break;
            }
        }
        //
        String[] reserveInfoTokens = Tokenizer(reserveInfo, "\n");
        ArrayList<TicketInfo> tis = new ArrayList<>();
        String[] firstLineTokens = Tokenizer(reserveInfoTokens[0], " ");
        for(int i=1;i<reserveInfoTokens.length;i++){
            String[] lineTokens = Tokenizer(reserveInfoTokens[i]," ");
            TicketInfo ti = new TicketInfo();
            ti.setFirstName(lineTokens[0]);
            ti.setSureName(lineTokens[1]);
            ti.setNationalID(lineTokens[2]);
            ti.setOriginCode(firstLineTokens[1]);
            ti.setDestinationCode(firstLineTokens[2]);
            ti.setDate(firstLineTokens[3]);
            ti.setAirlineCode(firstLineTokens[4]);
            ti.setFlightNo(firstLineTokens[5]);
            ti.setSeatClase(firstLineTokens[6]);
            ti.setReferenceCode(referenceTokens[0]);
            ti.setTicketNumber(referenceTokens[i]);
            ArrayList<FlightInfo> fi = searchRequest(ti.getOriginCode()+" "+ti.getDestinationCode()+" "+ti.getDate()+" 0 0 0");
            for(FlightInfo f:fi){
                if(ti.getFlightNo().equals(f.getFlightNo())){
                    ti.setArrivalTime(f.getArrivalTime());
                    ti.setDepartureTime(f.getDepartureTime());
                    ti.setAirplaneModel(f.getAirplaneModel());
                }
            }
            tis.add(ti);
        }
        ticketInfo = ticketInfoToString(tis);
        return ticketInfo;
    }
    public String ticketInfoToString(ArrayList<TicketInfo> ts) {
        String finalResult = "";
        for (int i = 0 ; i<ts.size(); i++){
            TicketInfo t = ts.get(i);
            String dTime = t.getDepartureTime().substring(0, 2) + ":" + t.getDepartureTime().substring(2, 4);
            String aTime = t.getArrivalTime().substring(0, 2) + ":" + t.getArrivalTime().substring(2, 4);
            finalResult = finalResult + t.getFirstName()+" "+ t.getSureName()+" "+t.getReferenceCode()+
                    " "+t.getTicketNumber()+" "+t.getOriginCode()+" "+t.getDestinationCode()+" "+t.getDate()+" "
                    +t.getFlightNo()+" "+ t.getAirlineCode() + " " + t.getFlightNo() +
                    " " +t.getSeatClase()+" "+ dTime + " " + aTime + " " + t.getAirplaneModel() + "\n";

        }

        return finalResult;
    }


}
