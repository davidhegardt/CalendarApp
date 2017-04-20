package com.example.dave.calendar;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.joda.time.DateTime;
import org.joda.time.Days;
import org.w3c.dom.Text;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateActivity extends AppCompatActivity {

    private CalendarView calendarView;
    private CalendarView calendarView2;
    private String fileName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date);

        Intent intent = getIntent();
        String message = intent.getStringExtra(StartActivity.EXTRA_MESSAGE);       // Get the date from Start-activity
        //message = message.replace("DateActivity://","");
        //textView.setText(message);
        Calendar myCal = Calendar.getInstance();
        myCal.set(Calendar.YEAR,2017);
        myCal.set(Calendar.MONTH,4);
        myCal.set(Calendar.DATE,23);
        int day = myCal.get(Calendar.DAY_OF_WEEK);
        SimpleDateFormat newDateFormat = new SimpleDateFormat("dd/MM/yyyy");        // set the date format to conform to message-standard
        try {
            Date MyDate = newDateFormat.parse(message);                             // Parse the date from the message and create date object
            newDateFormat.applyPattern("EEEE d MMM yyyy");
            String stringDate = newDateFormat.format(MyDate);

            calendarView = (CalendarView) findViewById(R.id.calendarView);

            calendarView.setDate(MyDate);                                           // apply the parsed date object

            calendarView2 = (CalendarView) findViewById(R.id.calendarView2);

            calendarView2.setDate(MyDate);                                          // apply the parsed date object

            try {
                generateFileName();                                                 // create filename based on date
                readFromFile();                                                     // call to read memo, if any
            } catch (IOException e) {
                e.printStackTrace();
            }


        } catch (ParseException e) {
            e.printStackTrace();
        }

        findViewById(R.id.calendarView2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {               // set listener on right calendar-object
                calendarView2.nextDay();                            // show the next date
                changeDuration();                                   // change the duration counter
            }
        });

        findViewById(R.id.calendarView).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {               // set listener on left calendar-object
                calendarView.prevDay();                             // show the previous day
                changeDuration();
            }
        });

        findViewById(R.id.btn_shake).setOnClickListener(new View.OnClickListener(){
            @Override
            //On click function
            public void onClick(View view) {            // set onclick of animation button
                    Animation shake = AnimationUtils.loadAnimation(getBaseContext(),R.anim.shake);              // Create new shake animation
                    findViewById(R.id.calendarView).startAnimation(shake);                      // Start animation for left calendar and right
                    findViewById(R.id.calendarView2).startAnimation(shake);
                    writeToFile();                                              // Run animation and also save memo
            }
        });
    }

    /** Generates file name to save a memo on this date */
    private void generateFileName(){
        Date thisDate = calendarView.getDate();
        SimpleDateFormat newDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        newDateFormat.applyPattern("ddMM");
        fileName = newDateFormat.format(thisDate);
        fileName += ".txt";
    }
    /** Check if there is a memo and display it */
    private void readFromFile() throws IOException{

        String memo;
        memo = Helper.readFromAssets(DateActivity.this,fileName);
        if (!memo.isEmpty()){
            TextView output = (TextView)findViewById(R.id.outPut);

            output.append(memo);
            showToast("Memo Read successfully");
        }
    }
    /** Function to save memo to internal storage if use inputs and presses save */
    private void writeToFile() {
        EditText input = (EditText)findViewById(R.id.editText);
        String memo = input.getText().toString();
        if (!memo.isEmpty()){
            Helper.writeToAssets(DateActivity.this,fileName,memo);
            showToast("Memo Saved to file");
        }
    }

    /** Function to calculate number of days between the calendar view dates */
    private void changeDuration(){
        Date test1 = calendarView.getDate();
        Date test2 = calendarView2.getDate();
        DateTime dateTime1 = new DateTime(test1);
        DateTime dateTime2 = new DateTime(test2);
        Days d = Days.daysBetween(dateTime1,dateTime2);
        int days = d.getDays();


        TextView t = (TextView)findViewById(R.id.textView2);
        t.setText("Duration between : " + days);
    }


    public void showToast(CharSequence message) {
        Context thisContext = getApplicationContext();

        int duration = Toast.LENGTH_LONG;

        Toast toast = Toast.makeText(thisContext, message,duration);        // Create new toast with message
        toast.show();                                                       // Display the toast
    }
}
