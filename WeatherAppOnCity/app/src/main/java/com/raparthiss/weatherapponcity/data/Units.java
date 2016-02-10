package com.raparthiss.weatherapponcity.data;

import org.json.JSONObject;

/**
 * Created by rapar on 2/3/2016.
 */
public class Units implements JSONPopulator{
    private String tempUnit;

    public String getTempUnit() {
        return tempUnit;
    }


    @Override
    public void populate(JSONObject receivedData) {
        tempUnit = receivedData.optString("temperature");
    }
}
