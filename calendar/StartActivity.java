package com.example.dave.calendar;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class StartActivity extends AppCompatActivity {
public static final String EXTRA_MESSAGE = "com.example.calendar.MESSAGE";
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        WebView start = (WebView)findViewById(R.id.webView);
        start.setInitialScale(300);
        start.clearCache(true);
        start.getSettings().setJavaScriptEnabled(true);

        start.loadUrl("file:///android_asset/www/index.html");              // Load the HTML calendar


        /** Function to load and read URI-links and create new view based on this */
        start.setWebViewClient(new WebViewClient()
        {
            // Override URL
            public boolean shouldOverrideUrlLoading(WebView view, String url)
            {
                String month = "";
                if (url.endsWith("_0")){                        // Check the URI-link format (javascript controlled)
                    month = "1";                                // ending determines selected month
                } else if (url.endsWith("_1")){
                    month = "2";
                } else if (url.endsWith("_2")){
                    month = "3";
                } else if (url.endsWith("_3")){
                    month = "4";
                } else if (url.endsWith("_4")){
                    month = "5";
                } else if (url.endsWith("_5")){
                    month = "6";
                } else if (url.endsWith("_6")){
                    month = "7";
                } else if (url.endsWith("_7")){
                    month = "8";
                } else if (url.endsWith("_8")){
                    month = "9";
                } else if (url.endsWith("_9")){
                    month = "10";
                } else if (url.endsWith("_10")){
                    month = "11";
                } else {
                    month = "12";
                }

                /** Functions matchees RegEx expressions - activityname/day/month to determine date to display in DateActivity */
                if (url.matches("[a-zA-Z]{12}:\\D\\D\\d\\d\\w\\d")){                    // days 10 - 31
                    Intent intent = new Intent(getBaseContext(), DateActivity.class);
                    String date = Character.toString(url.charAt(15)) + Character.toString(url.charAt(16));
                    //intent.putExtra(EXTRA_MESSAGE,date+ " " + month);
                    intent.putExtra(EXTRA_MESSAGE,date+ "/" + month + "/" + "2017");
                    startActivity(intent);
                } else if (url.matches("[a-zA-Z]{12}:\\D\\D\\d\\w\\d")){                // days 1 - 9
                    Intent intent = new Intent(getBaseContext(), DateActivity.class);
                    String date = "0";
                    date += Character.toString(url.charAt(15));
                    intent.putExtra(EXTRA_MESSAGE,date+ "/" + month + "/" + "2017");
                    startActivity(intent);
                }

                Log.e("URL","URL "+url);
                return true;
            }
        });
    }
}
