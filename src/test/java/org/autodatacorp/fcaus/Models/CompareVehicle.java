package org.autodatacorp.fcaus.Models;

public class CompareVehicle {

    private String Ccode;
    private String Llp;
    private String StateString;
    private String Vin;

    public String getCcode() {
        return Ccode;
    }

    public void setCcode(String ccode) {
        Ccode = ccode;
    }

    public String getLlp() {
        return Llp;
    }

    public void setLlp(String llp) {
        Llp = llp;
    }

    public String getStateString() {
        return StateString;
    }

    public void setStateString(String stateString) {
        StateString = stateString;
    }

    public String getVin() {
        return Vin;
    }

    public void setVin(String vin) {
        Vin = vin;
    }

    public CompareVehicle(String ccode, String llp, String stateString, String vin) {
        Ccode = ccode;
        Llp = llp;
        StateString = stateString;
        Vin = vin;
    }
}
