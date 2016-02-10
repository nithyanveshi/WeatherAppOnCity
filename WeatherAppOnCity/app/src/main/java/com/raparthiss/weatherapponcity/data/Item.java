package com.raparthiss.weatherapponcity.data;

import org.json.JSONObject;

/**
 * Created by rapar on 2/3/2016.
 */
public class Item implements JSONPopulator{
    private Condition condition;

    public Condition getCondition() {
        return condition;
    }

    @Override
    public void populate(JSONObject receivedData) {
        condition = new Condition();
        condition.populate(receivedData.optJSONObject("condition"));
    }
}
