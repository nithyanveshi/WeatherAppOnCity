package com.raparthiss.weatherapponcity;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.raparthiss.weatherapponcity.data.Channel;
import com.raparthiss.weatherapponcity.service.CallBackInterface;
import com.raparthiss.weatherapponcity.service.YahooWeatherService;

public class MainActivity extends AppCompatActivity implements CallBackInterface {
    private ImageView weatherIcon;
    private TextView temperatureText;
    private TextView conditionText;
    private TextView locationText;
    private EditText cityTextName;

    private YahooWeatherService yahoo;
    private ProgressDialog dialog;
    public String city;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        weatherIcon = (ImageView) findViewById(R.id.weatherIconImageView);
        temperatureText = (TextView) findViewById(R.id.temperatureTextView);
        conditionText = (TextView) findViewById(R.id.conditionTextView);
        locationText = (TextView) findViewById(R.id.locationTextView);

        yahoo = new YahooWeatherService(this);
        dialog = new ProgressDialog(this);
        dialog.setMessage("Loading...");
        dialog.show();

        yahoo.refresher("Kansas City");

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    //@TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void success(Channel channel) {
        dialog.hide();

        int image = getResources().getIdentifier("drawable/icon_"+channel.getItem().getCondition().getCode(), null, getPackageName());
        @SuppressWarnings("deprecation")
        Drawable icon = getResources().getDrawable(image);

        weatherIcon.setImageDrawable(icon);
        temperatureText.setText(channel.getItem().getCondition().getTemp() + "\u00B0" + channel.getUnits().getTempUnit());
        conditionText.setText(channel.getItem().getCondition().getNotes());
        locationText.setText(yahoo.getLocation());
    }

    @Override
    public void failure(Exception exp) {
        dialog.hide();
        Toast.makeText(this, exp.getMessage(), Toast.LENGTH_LONG).show();
    }

    public void refreshWeather(View v){
        cityTextName = (EditText) findViewById(R.id.cityText);
        city = cityTextName.getText().toString();
        System.out.println(city);

        ((EditText) findViewById(R.id.cityText)).setText("");

        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                InputMethodManager.HIDE_NOT_ALWAYS);

        dialog.show();

        yahoo.refresher(city);
    }
}
