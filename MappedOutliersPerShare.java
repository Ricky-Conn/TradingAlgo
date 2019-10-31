/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algo;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Rael Alexander
 */
public class MappedOutliersPerShare
{

    private String symbol = "";
    Map<String, Double> topOutliersDayMap = new HashMap<>();
    Map<String, Double> bottomOutliersDayMap = new HashMap<>();

    public MappedOutliersPerShare()
    {
    }

    public MappedOutliersPerShare(String symbol)
    {
        this.symbol = symbol;
    }

    public String getSymbol()
    {
        return symbol;
    }

    public void setSymbol(String symbol)
    {
        this.symbol = symbol;
    }

    public Map<String, Double> getTopOutliersMap()
    {
        return topOutliersDayMap;
    }

    public void setTopOutliersMap(Map<String, Double> topOutliersDayMap)
    {
        this.topOutliersDayMap = topOutliersDayMap;
    }

    public Map<String, Double> getBottomOutliersMap()
    {
        return bottomOutliersDayMap;
    }

    public void setBottomOutliersMap(Map<String, Double> bottomOutliersDayMap)
    {
        this.bottomOutliersDayMap = bottomOutliersDayMap;
    }

    public static Map<String, Double> convertToOutliersMap(Day topOutliersDay)
    {
        if (topOutliersDay == null)
        {
            return null;
        }
        Map<String, Double> tempDayMap = new HashMap<>();
        for (Derivative der : topOutliersDay.getDerivatives())
        {
            tempDayMap.put(der.getTime(), der.getDerivative());
        }
        return tempDayMap;
    }

    public static Map<String, Double> convertToOutliersMapShifted(Day topOutliersDay, int numSecondsShiftedForward)
    {
        if (topOutliersDay == null)
        {
            return null;
        }
        Map<String, Double> tempDayMap = new HashMap<>();
        for (Derivative der : topOutliersDay.getDerivatives())
        {
            tempDayMap.put(convertTimePlusSeconds(der.getTime(), numSecondsShiftedForward), der.getDerivative());
        }
        return tempDayMap;
    }

    public static String convertTimePlusSeconds(String time, int secondsAdded)
    {
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd HH:mm:ss");
        try
        {
            date = formatter.parse(time);
        }
        catch (ParseException ex)
        {
            Logger.getLogger(MappedOutliersPerShare.class.getName()).log(Level.SEVERE, null, ex);
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.setTimeInMillis(calendar.getTimeInMillis() + (1000 * secondsAdded));
        return formatter.format(calendar.getTime());
    }

    public void addToBottomOutliersMap(String key, double derivative)
    {
        this.bottomOutliersDayMap.put(key, derivative);
    }

    public void addToTopOutliersMap(String key, double derivative)
    {
        this.topOutliersDayMap.put(key, derivative);
    }

}
