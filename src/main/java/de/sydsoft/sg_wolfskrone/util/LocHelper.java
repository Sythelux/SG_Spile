/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.sydsoft.sg_wolfskrone.util;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author sythelux
 */
public class LocHelper {

    public static String roundTwoDecimals(double d) {
        DecimalFormat twoDForm = new DecimalFormat("#.##");
        return twoDForm.format(d);
    }

    public static String roundTwoDecimals(float f) {
        DecimalFormat twoDForm = new DecimalFormat("#.##");
        return twoDForm.format(f);
    }

    public static String timeAsChannelStamp() {
        DateFormat dateFormat = new SimpleDateFormat("[HH:mm:ss]");
        Date date = new Date();
        return dateFormat.format(date);
    }
}
