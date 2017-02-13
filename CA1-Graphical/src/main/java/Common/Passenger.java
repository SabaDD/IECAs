/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Common;

/**
 *
 * @author Sab
 */
public class Passenger{
    String FirstName;
    String SurName;
    String NationalID;
    public Passenger(String FirstName,String SurName,String NationalID){
        this.FirstName = FirstName;
        this.SurName = SurName;
        this.NationalID = NationalID;
    }
    public void setFirstName(String FirstName){
        this.FirstName = FirstName;
    }
    public void setSurName(String SurName){
        this.SurName = SurName;
    }
    public void setNationalID(String NationalID){
        this.NationalID = NationalID;
    }
    public String getFirstName(){
        return this.FirstName;
    }
    public String getSurName(){
        return this.SurName;
    }
    public String getNationalID(){
        return this.NationalID;
    }
    public String ToString(){
        return FirstName+" "+SurName+" "+NationalID;
    }
}
