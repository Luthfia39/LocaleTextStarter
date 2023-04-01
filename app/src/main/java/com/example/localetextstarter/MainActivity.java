package com.example.localetextstarter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {
    private TextView textView_price_100_packs;
    private EditText input_price;
    private double price;
    private double price_100_packs;

    private final static double USD_TO_IDR_EXCHANGE_RATE = 14968.65;
    private final static double USD_TO_SAR_EXCHANGE_RATE = 3.75;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showHelp();
            }
        });

//    menentukan tgl kadaluarsa (misal 5 hari setelah hari ini)
        expiredDate();

        input_price = findViewById(R.id.edt_price);
        textView_price_100_packs = findViewById(R.id.price100);

        findViewById(R.id.btn_submit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                convertCurrency();
            }
        });
    }

    private void convertCurrency() {
        price = Double.parseDouble(input_price.getText().toString());
        NumberFormat format = NumberFormat.getCurrencyInstance();
        String toCurrency = Locale.getDefault().getCountry();

        if (toCurrency == "ID"){
            price_100_packs = price*100*USD_TO_IDR_EXCHANGE_RATE;
        } else if (toCurrency == "EG") {
            price_100_packs = price*100*USD_TO_SAR_EXCHANGE_RATE;
        }else {
            price_100_packs = price*100;
        }
        textView_price_100_packs.setText(format.format(price_100_packs));
    }

    /**
     * Shows the Help screen.
     */
    private void showHelp() {
        // Create the intent.
        Intent helpIntent = new Intent(this, HelpActivity.class);
        // Start the HelpActivity.
        startActivity(helpIntent);
    }

    /**
     * Creates the options menu and returns true.
     *
     * @param menu       Options menu
     * @return boolean   True after creating options menu.
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    /**
     * Handles options menu item clicks.
     *
     * @param item      Menu item
     * @return boolean  True if menu item is selected.
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle options menu item clicks here.
        int id = item.getItemId();
        if (id == R.id.action_help) {
            Intent intent = new Intent(this, HelpActivity.class);
            startActivity(intent);
            return true;
        }else if (id == R.id.action_language){
            Intent languageIntent = new Intent(Settings.ACTION_LOCALE_SETTINGS);
            startActivity(languageIntent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void expiredDate(){
        Date myDate = new Date();
        long expiredDate = myDate.getTime() + TimeUnit.DAYS.toMillis(5);
        myDate.setTime(expiredDate);

        String formatDate = DateFormat.getDateInstance().format(myDate);
        TextView expiredDateTexView = findViewById(R.id.date);
        expiredDateTexView.setText(formatDate);
    }
}