package com.example.dave.calendar;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.Dimension;
import android.support.annotation.Nullable;
import android.text.format.DateUtils;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by Dave on 2017-04-14.
 */

public class CalendarView extends View {

    private RectF imageSize;
    private int imgWidth;
    private int imgHeight;
    private String monthText;
    private String dateText;
    private String dayName;
    private Rect bounds;

    private int rectColor;
    private int dateColor;
    private Date theDate;

    private Paint monthPainter;
    private Paint datePainter;
    private Paint dayPainter;

    private Bitmap mImage;
    private Bitmap reSized;

    public CalendarView(Context context) {
        super(context);
        init(null,0);
    }

    public CalendarView(Context context, AttributeSet attrs){
        super(context, attrs);
        init(attrs,0);
    }

    public CalendarView(Context context, AttributeSet attrs, int defStyle){
        super(context,attrs,defStyle);
        init(attrs, defStyle);
    }

    private void init(@Nullable  AttributeSet attrs, int defStyle){
        monthPainter = new Paint();                                             //Setup Paint objects
        datePainter = new Paint();
        dayPainter = new Paint();
        monthPainter.setAntiAlias(true);
        datePainter.setAntiAlias(true);

        theDate = new Date();                                                   // set new Date(default today)

        TypedArray ta = getContext().obtainStyledAttributes(attrs, R.styleable.CalendarView);       //Retrieve stylable attributes

        rectColor = ta.getColor(R.styleable.CalendarView_square_color, Color.GREEN);                // get color for month
        dateColor = ta.getColor(R.styleable.CalendarView_date_color, Color.BLACK);                  // get color for date
        int scaleFactorX = ta.getDimensionPixelSize(R.styleable.CalendarView_scale_factor_x, 2);    // get the scale values for view
        int scaleFactorY = ta.getDimensionPixelSize(R.styleable.CalendarView_scale_factor_y,3);


        float textSize = getResources().getDimension(R.dimen.text_size);                            // get the default textsize
        float largeTextSize = getResources().getDimension(R.dimen.text_size_large);                 // get LARGE textsize


        mImage = BitmapFactory.decodeResource(getResources(), R.drawable.cal_sheet_green);          // create a bitmap from the calendar image
        if (getScreenWidth() > getScreenHeight()) {                             // check if landscape mode, set the scaling
            imgWidth = getScreenWidth() / 4;
        } else {
            imgWidth = getScreenWidth() / scaleFactorX;                         // check if horizontal mode, set the scaling
        }
        imgHeight = getScreenHeight()/scaleFactorY;                             // Scale the image height to default scale value

        imageSize = new RectF((float)0,(float)0,imgWidth,imgHeight);            // Use to resize image (resizeBitmap not working else)

        monthText = "Some Text";                                // set default text values
        dateText = "00";
        dayName = "Someday";


        bounds = new Rect();                                    // used for determining middle of text

        if (attrs == null)                                      // Prohibit crashes
            return;



        datePainter.setTextSize(largeTextSize);                 // set text size for drawing the date
        dayPainter.setTextSize(textSize);                       // set text size for drawing the dayname
        monthPainter.setTextSize(textSize);                     // set text size for drawing the month name

        monthPainter.setColor(rectColor);                       // set color for the monthname

        ta.recycle();
    }

    public void setDate(Date currDate){
        theDate = currDate;
        setMonthText(currDate);
    }

    private void setMonthText( Date currDate) {

        String monthname=(String)android.text.format.DateFormat.format("MMMM", currDate);
        dateText =(String)android.text.format.DateFormat.format("dd", currDate);
        dayName = (String)android.text.format.DateFormat.format("EEEE", currDate);
        monthText = monthname;

        postInvalidate();                       // Set the month text based on date and redraw view
    }

    /** Function to update view to next day */
    public void nextDay(){
        Calendar c = Calendar.getInstance();
        c.setTime(theDate);
        c.add(Calendar.DATE,1);
        Date nextDay = c.getTime();
        setDate(nextDay);

    }
    /** Function to update view to previous day */
    public void prevDay(){
        Calendar c = Calendar.getInstance();
        c.setTime(theDate);
        c.add(Calendar.DATE,-1);
        Date prevDay = c.getTime();
        setDate(prevDay);
    }

    public Date getDate(){
        return theDate;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int w = 0;
        int h = 0;
        if (getScreenWidth() > getScreenHeight()) {
            w = getScreenWidth() / 4;                                       // Set view size to that of bitmap
            h = getScreenHeight() / 2;
        } else {
            w = getScreenWidth() / 2;
            h = getScreenHeight() / 2;
        }
        int size = Math.min(w,h);
        setMeasuredDimension(size,size);
    }

    public static int getScreenWidth() {
        return Resources.getSystem().getDisplayMetrics().widthPixels;
    }

    public static int getScreenHeight() {
        return Resources.getSystem().getDisplayMetrics().heightPixels;
    }

    @Override
    public void onDraw(Canvas canvas){
        super.onDraw(canvas);

        int bitmapWith = imgWidth;

        canvas.drawBitmap(mImage,null,imageSize,null);

        int xMiddle = (bitmapWith) / 2;                                     // calc middle horizontal
        monthPainter.setTextAlign(Paint.Align.CENTER);                      // align text in center
        datePainter.setTextAlign(Paint.Align.CENTER);
        dayPainter.setTextAlign(Paint.Align.CENTER);



        monthPainter.getTextBounds(monthText,0,monthText.length(),bounds);      // get the size of the text displayed for month

        float yMonth = imgHeight * (float)0.35;                             // set text relative to bitmap size
        float yDate = imgHeight * (float)0.7;
        float yDay = imgHeight * (float)0.8;

        int yValueMonth = Math.round(yMonth);
        int yValueDate = Math.round(yDate);
        int yValueDay = Math.round(yDay);

        canvas.drawText(monthText,xMiddle,yValueMonth,monthPainter);

        canvas.drawText(dateText,xMiddle,yValueDate,datePainter);

        canvas.drawText(dayName,xMiddle,yValueDay,dayPainter);


    }
}
