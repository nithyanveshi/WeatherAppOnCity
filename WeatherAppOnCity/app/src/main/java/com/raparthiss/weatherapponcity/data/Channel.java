package com.raparthiss.weatherapponcity.data;

import org.json.JSONObject;

/**
 * Created by rapar on 2/3/2016.
 */
public class Channel implements JSONPopulator{
    private Units units;
    private Item item;
    private String location;

    public Units getUnits() {
        return units;
    }

    public Item getItem() {
        return item;
    }

    public String getLocation() {
        return location;
    }

    @Override
    public void populate(JSONObject receivedData) {
        //gets the temperature units - f or c
        units = new Units();
        units.populate(receivedData.optJSONObject("units"));

        //gets the temperature value, sets image and comments weeather
        item = new Item();
        item.populate(receivedData.optJSONObject("item"));

        //gets location data from JSON
        JSONObject locationData = receivedData.optJSONObject("location");

        String region = locationData.optString("region");
        String country = locationData.optString("country");

        location = String.format("%s, %s", locationData.optString("city"), (region.length() != 0 ? region : country));

    }
}
