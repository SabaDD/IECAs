package Common;

/**
 * Created by Paniz on 2/13/2017.
 */
public class TicketInfo {
    String FirstName;
    String SureName;
    String NationalID;
    String ReferenceCode;
    String TicketNumber;
    String OriginCode;
    String DestinationCode;
    String Date;
    String AirlineCode;
    String FlightNo;
    String DepartureTime;
    String ArrivalTime;
    String AirplaneModel;
    String SeatClase;
//    public Map<Character, Character> ClassSeat;
//    public Map<Character, Integer> ClassPrice;
//    public String count;

    public String getNationalID() {
        return NationalID;
    }

    public void setNationalID(String nationalID) {
        NationalID = nationalID;
    }

    public String getSeatClase() {
        return SeatClase;
    }

    public void setSeatClase(String seatClase) {
        SeatClase = seatClase;
    }

    public String getFirstName() {
        return FirstName;
    }

    public void setFirstName(String firstName) {
        FirstName = firstName;
    }

    public String getSureName() {
        return SureName;
    }
    //

    public void setSureName(String sureName) {
        SureName = sureName;
    }

    public String getReferenceCode() {
        return ReferenceCode;
    }

    public void setReferenceCode(String referenceCode) {
        ReferenceCode = referenceCode;
    }

    public String getTicketNumber() {
        return TicketNumber;
    }

    public void setTicketNumber(String ticketNumber) {
        TicketNumber = ticketNumber;
    }

    public String getOriginCode() {
        return OriginCode;
    }

    public void setOriginCode(String originCode) {
        OriginCode = originCode;
    }

    public String getDestinationCode() {
        return DestinationCode;
    }

    public void setDestinationCode(String destinationCode) {
        DestinationCode = destinationCode;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getAirlineCode() {
        return AirlineCode;
    }

    public void setAirlineCode(String airlineCode) {
        AirlineCode = airlineCode;
    }

    public String getFlightNo() {
        return FlightNo;
    }

    public void setFlightNo(String flightNo) {
        FlightNo = flightNo;
    }

    public String getDepartureTime() {
        return DepartureTime;
    }

    public void setDepartureTime(String departureTime) {
        DepartureTime = departureTime;
    }

    public String getArrivalTime() {
        return ArrivalTime;
    }

    public void setArrivalTime(String arrivalTime) {
        ArrivalTime = arrivalTime;
    }

    public String getAirplaneModel() {
        return AirplaneModel;
    }

    public void setAirplaneModel(String airplaneModel) {
        AirplaneModel = airplaneModel;
    }
}
