package com.raparthiss.weatherapponcity.service;

import android.net.Uri;
import android.os.AsyncTask;

import com.raparthiss.weatherapponcity.data.Channel;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by rapar on 2/3/2016.
 */
public class YahooWeatherService {
    private CallBackInterface callBack;
    private String location;
    private Exception exp;

    public YahooWeatherService(CallBackInterface callBack) {
        this.callBack = callBack;
    }

    public String getLocation() {
        return location;
    }

    public void refresher(final String location){
        this.location = location;
        new AsyncTask<String, Void, String>() {
            @Override
            protected String doInBackground(String... strings) {
                //gets the data from the yahoo for the given country
                String query = String.format("select * from weather.forecast where woeid in (select woeid from geo.places(1) where text=\"%s\")", strings[0]);
                //formatting the data into readable JSON
                String invoker = String.format("https://query.yahooapis.com/v1/public/yql?q=%s&format=json", Uri.encode(query));

                try {
                    URL invokeURL = new URL(invoker);
                    //creating a connection and getting the data
                    URLConnection newConnection = invokeURL.openConnection();
                    InputStream ips = newConnection.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(ips));
                    StringBuilder completeData = new StringBuilder();
                    String unfinishedData;

                    while((unfinishedData = reader.readLine()) != null){
                        completeData.append(unfinishedData);
                    }
                return completeData.toString();

                } catch (Exception e) {
                    //e.printStackTrace();
                    exp = e;
                    return null;
                }
            }

            @Override
            protected void onPostExecute(String input) {
                if(input == null && exp != null){
                    callBack.failure(exp);
                    return;
                }
                try {
                    JSONObject jsonInput = new JSONObject(input);
                    JSONObject result = jsonInput.optJSONObject("query");
                    int cnt = result.optInt("count");
                    if(cnt==0) {
                        Exception exp2 = new Exception(location+": No location is present with this name");
                        callBack.failure(exp2);
                        return;
                    }
                    Channel channel = new Channel();
                    channel.populate(result.optJSONObject("results").optJSONObject("channel"));

                    callBack.success(channel);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.execute(location);

    }
}
