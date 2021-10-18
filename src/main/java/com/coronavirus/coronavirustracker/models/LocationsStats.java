package com.coronavirus.coronavirustracker.models;

import java.util.List;

public class LocationsStats {
    private String state;
    private String county;
    private int latestTotalCases;
    private int diffFromPreviousDay;

    public int getDiffFromPreviousDay() {
        return diffFromPreviousDay;
    }

    public void setDiffFromPreviousDay(int diffFromPreviousDay) {
        this.diffFromPreviousDay = diffFromPreviousDay;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public int getLatestTotalCases() {
        return latestTotalCases;
    }

    public void setLatestTotalCases(int latestTotalCases) {
        this.latestTotalCases = latestTotalCases;
    }


    @Override
    public String toString() {
        return "LocationsStats{" +
                "state='" + state + '\'' +
                ", county='" + county + '\'' +
                ", latestTotalCases=" + latestTotalCases +
                '}';
    }
}

