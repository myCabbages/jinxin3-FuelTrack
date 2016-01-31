package com.example.kx.jinxin3_fueltrack;


/**
 * Created by kx on 2016-01-21.
 */
public class Logs {
    protected String date;
    protected String station;
    protected String odometer;
    protected String grade;
    protected String amount;
    protected String unitcost;
    protected Float totalcost;

    public Logs() {
    }

    public String getDate() {
        return date;
    }

    public String getStation() {
        return station;
    }

    public String getGrade() {
        return grade;
    }

    public String getOdometer() {
        return odometer;
    }

    public String getAmount() {
        return amount;
    }

    public String getUnitcost() {
        return unitcost;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setTotalcost(float totalcost) {
        this.totalcost = totalcost;
    }

    public void setUnitcost(String unitcost) {
        this.unitcost = unitcost;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public void setOdormeter(String odormeter) {
        this.odometer = odormeter;
    }

    public void setStation(String station) {
        this.station = station;
    }

    public void calculateTotal(){
        totalcost= Float.valueOf(amount) * Float.valueOf(unitcost);
    }

    public String toLog(){
        return date + " | " + station + "|" + grade
                 + "|" + odometer + "|" + amount +
                "|" + unitcost + "|" + String.format("%.2f", totalcost) + "dollars";}
}
