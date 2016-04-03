package me.kulam.pagefetch;

import android.content.Context;
import android.content.res.Resources;

import java.util.Arrays;

/**
 * Created by Sharu on 31/03/2016.
 */
public class StringUsage
{
    public static String stdFormat(String inputString){
        char firstChar = inputString.charAt(0);
        String returnVal = "";
        firstChar = Character.toUpperCase(firstChar);
        inputString = inputString.toLowerCase().trim();
        returnVal+=firstChar;
        returnVal+=inputString.substring(1,inputString.length());
        return returnVal;
    }

    public static boolean URLchecker(String urlInput){
        String[] urlSplit = urlInput.split("\\.");

        if(urlSplit.length < 3){
            return false;
        }
        else{
            return true;
        }
     }
}
