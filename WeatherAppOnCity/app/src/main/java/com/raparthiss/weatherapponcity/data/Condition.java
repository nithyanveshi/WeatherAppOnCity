package com.raparthiss.weatherapponcity.data;

import org.json.JSONObject;

/**
 * Created by rapar on 2/3/2016.
 */
public class Condition implements JSONPopulator{
    private int code;
    private int temp;
    private String notes;

    public int getCode() {
        return code;
    }

    public int getTemp() {
        return temp;
    }

    public String getNotes() {
        return notes;
    }
    @Override
    public void populate(JSONObject receivedData) {
        code = receivedData.optInt("code");
        temp = receivedData.optInt("temp");
        notes = receivedData.optString("text");
    }
}
