/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Common;

import java.util.ArrayList;
import java.util.Map;

/**
 * @author Sab
 */
public class FlightInfo {
    public String OriginCode;
    public String DestinationCode;
    public String Date;
    public String AirlineCode;
    public String FlightNo;
    public String DepartureTime;
    public String ArrivalTime;
    public String AirplaneModel;
    //public ArrayList<Map<Character ,Integer>> SeatClass;
    public Map<Character, Character> ClassSeat;
    public Map<Character, Integer> ClassPrice;
    public String count;

    public FlightInfo(Map<Character, Character> classSeat, Map<Character, Integer> classPrice) {
        ClassSeat = classSeat;
        ClassPrice = classPrice;
    }

    public Map<Character, Integer> getClassPrice() {
        return ClassPrice;
    }

    public void setClassPrice(Map<Character, Integer> classPrice) {
        ClassPrice = classPrice;
    }


    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getOriginCode() {
        return OriginCode;
    }

    public void setOriginCode(String OriginCode) {
        this.OriginCode = OriginCode;
    }

    public void setDestinationCode(String DestinationCode) {
        this.DestinationCode = DestinationCode;
    }

    public void setDate(String Date) {
        this.Date = Date;
    }

    public void setAirlineCode(String AirlineCode) {
        this.AirlineCode = AirlineCode;
    }

    public void setFlightNo(String FlightNo) {
        this.FlightNo = FlightNo;
    }

    public void setDepartureTime(String DepartureTime) {
        this.DepartureTime = DepartureTime;
    }

    public void setArrivalTime(String ArrivalTime) {
        this.ArrivalTime = ArrivalTime;
    }

    public void setAirplaneModel(String AirplaneModel) {
        this.AirplaneModel = AirplaneModel;
    }


    public String getDestinationCode() {
        return DestinationCode;

    }

    public String getDate() {
        return Date;
    }

    public String getAirlineCode() {
        return AirlineCode;
    }

    public String getFlightNo() {
        return FlightNo;
    }

    public String getDepartureTime() {
        return DepartureTime;
    }

    public String getArrivalTime() {
        return ArrivalTime;
    }

    public String getAirplaneModel() {
        return AirplaneModel;
    }

    //    public ArrayList<Map<Character, Integer>> getSeatClass() {
//        return SeatClass;
//    }
//
//    public void setSeatClass(ArrayList<Map<Character, Integer>> seatClass) {
//        SeatClass = seatClass;
//    }
    //    String adultPrice = null;
//    String infantPrice = null;
//    String childPrice = null;
    int AdultCount;
    int ChildCount;
    int InfantCount;

    public int getAdultCount() {
        return AdultCount;
    }

    public void setAdultCount(int adultCount) {
        AdultCount = adultCount;
    }

    public int getChildCount() {
        return ChildCount;
    }

    public void setChildCount(int childCount) {
        ChildCount = childCount;
    }

    public Map<Character, Character> getClassSeat() {
        return ClassSeat;
    }

    public void setClassSeat(Map<Character, Character> classSeat) {
        ClassSeat = classSeat;
    }

    public int getInfantCount() {
        return InfantCount;
    }

    public void setInfantCount(int infantCount) {
        InfantCount = infantCount;
    }
}
