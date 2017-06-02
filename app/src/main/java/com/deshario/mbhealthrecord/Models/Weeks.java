package com.deshario.mbhealthrecord.Models;

/**
 * Created by Deshario on 1/14/2017.
 */

public class Weeks {

    private int id;
    private double weight;
    private double height;
    private int week_no;

    public Weeks(int weight, int week_no) {
        this.weight = weight;
        this.week_no = week_no;
    }
    public Weeks(double weight, double height, int week_no) {
        this.weight = weight;
        this.height = height;
        this.week_no = week_no;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public int getWeek_no() {
        return week_no;
    }

    public void setWeek_no(int week_no) {
        this.week_no = week_no;
    }


}
