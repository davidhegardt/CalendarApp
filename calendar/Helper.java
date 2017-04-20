package com.example.dave.calendar;

import android.content.Context;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by Dave on 2017-04-17.
 */

public final class Helper {

    /** Function to read from the internal storage using specifed filename */
    public static String readFromAssets(Context context, String filename) throws IOException {
        File file = context.getFileStreamPath(filename);

        if (file.exists()) {

            BufferedReader reader = new BufferedReader(new InputStreamReader(context.openFileInput(filename)));  // Use streamreader and buffered reader and open file from assets-folder

            StringBuilder sb = new StringBuilder();     // Use stringbuilder to read each line
            String mLine = reader.readLine();
            while (mLine != null) {                     // Loop through lines in file
                sb.append(mLine);                       // Add line to stringbuilder
                sb.append("\n");                        // add a new line
                mLine = reader.readLine();              // read next line
            }
            reader.close();
            return sb.toString();                       // convert stringbuilder to string and return

        } else return "";
    }
    /** Function to write to file in in the internal storage */
    public static void writeToAssets(Context context, String filename, String memo) {
        try {
            FileOutputStream fileOutput = context.openFileOutput(filename,Context.MODE_PRIVATE);
            fileOutput.write(memo.getBytes());
            fileOutput.close();
        } catch (Exception e){
            e.printStackTrace();
        }
    }


}
