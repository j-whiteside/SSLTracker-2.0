package com.example.jeremy.ssltracker;

import java.io.Serializable;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by Jeremy on 4/11/2016.
 */
public class WebTrackerData implements Serializable
{
    private String name;
    private GregorianCalendar renewal;
    private int type;

    public WebTrackerData(String name, GregorianCalendar renewal, int type)
    {
        this.name = name;
        this.renewal = renewal;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public GregorianCalendar getRenewal() {
        return renewal;
    }

    public void setRenewal(GregorianCalendar renewal) {
        this.renewal = renewal;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
