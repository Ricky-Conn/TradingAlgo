/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Rael Alexander
 */
public class DerivativesPerShare
{

    private String symbol = "";
    private Day currentDay;

    public DerivativesPerShare(String symb)
    {
        this.symbol = symb;
    }

//    public void initialise(DerivativesPerShare element, String symb)
//    {
//        days = new Day[days.length];
//        for (int i = 0; i < days.length; i++)
//        {
//            days[i].initialise(days[i].getDerivatives(), symb);
//        }
//        this.symbol = symb;
//    }
    public DerivativesPerShare()
    {
    }

    public String getSymbol()
    {
        return symbol;
    }

    public void setSymbol(String symbol)
    {
        this.symbol = symbol;
    }

    public Day getCurrentDay()
    {
        return currentDay;
    }

    /*
     returns a Day full of derivatives based on the type passed in as a parameter
     derivative = typeOfDerivative of close,open,high,or low
     */
    public Day getNextDay(String typeOfDerivative, String indicatorName)
    {
        Day tempDay = null;
        FileInputStream fis = null;
        try
        {
            File fin;
            if (symbol.equals(indicatorName))
            {
                fin = new File(symbol + ".txt");
            }
            else
            {
                fin = new File(indicatorName + " - " + symbol + ".txt");
            }
            fis = new FileInputStream(fin);
            BufferedReader in = new BufferedReader(new InputStreamReader(fis));
            String aLine = null;
            boolean foundDay = false;
            String lastBar = "";
            while ((aLine = in.readLine()) != null)
            {
                //Process each line and add output to Dest.txt file
                if (currentDay == null)
                {
                    if (!aLine.contains("null") && !aLine.equals(""))
                    {
                        foundDay = true;
                        if (typeOfDerivative.equals("raw"))
                        {
                            tempDay = new Day(Algo.numLinesPerFile, aLine.split("#")[1]);
                        }
                        else
                        {
                            tempDay = new Day(Algo.numLinesPerFile, aLine.split(", ")[0].substring(29, 37));
                        }
                        currentDay = tempDay;
                    }
                }
                else if (!foundDay && aLine.contains(currentDay.getDay()) && tempDay == null)
                {
                    foundDay = true;
                }
                else if (foundDay && !aLine.contains(currentDay.getDay()) && !aLine.contains("null") && tempDay == null)//check == or !=
                {
                    if (typeOfDerivative.equals("raw"))
                    {
                        tempDay = new Day(Algo.numLinesPerFile, aLine.split("#")[1].substring(0, 8));
                    }
                    else
                    {
                        tempDay = new Day(Algo.numLinesPerFile, aLine.split(", ")[0].substring(29, 37));
                    }
                }
                if (foundDay && tempDay != null && aLine.contains(tempDay.getDay().split(" ")[0]))
                {
                    if (!lastBar.equals("") && !lastBar.contains("null") && !aLine.equals("") && !aLine.contains("null"))
                    {
                        tempDay.addDerivative(getDerivative(typeOfDerivative, aLine, lastBar, symbol));
                    }
                }
                else if (!aLine.contains("null") && foundDay && tempDay != null && !aLine.contains(tempDay.getDay()))
                {// do not forget to close the buffer reader
                    in.close();
                    tempDay.trim();
                    currentDay = tempDay;
                    return tempDay;
                }
                lastBar = aLine;
            }

            // do not forget to close the buffer reader
            in.close();
            currentDay = null;
            return null;//check
        }
        catch (Exception ex)
        {
            Logger.getLogger(DerivativesPerShare.class.getName()).log(Level.SEVERE, null, ex);
        }
        tempDay.trim();
        return tempDay;
    }

    public Derivative getDerivative(String type, String currentBar, String lastBar, String symbol)
    {
        if (lastBar.contains(";;"))
        {
            lastBar = lastBar.substring(0, lastBar.indexOf(";;"));
        }
        if (lastBar.contains(";;"))
        {
            currentBar = currentBar.substring(0, currentBar.indexOf(";;"));
        }
        if (currentBar.contains("null") || lastBar.contains("null") || currentBar.equals("") || lastBar.equals(""))
        {
            return null;
        }
        //symbol;nr;timestamp;open;high;low;close;volume

        String[] temp2 = currentBar.split("#");
        String[] temp1 = lastBar.split("#");

        if (temp1.length == 1)
        {
            temp1 = lastBar.split(", ");
        }
        if (temp2.length == 1)
        {
            temp2 = currentBar.split(", ");
        }
        switch (type)
        {
            case "close":
                Double close1 = Double.parseDouble(temp1[4].replace(",", ".").substring(temp1[4].replace(",", ".").indexOf(" ") + 1));
                Double close2 = Double.parseDouble(temp2[4].replace(",", ".").substring(temp2[4].replace(",", ".").indexOf(" ") + 1));
                return new Derivative((close2 - close1) / close1, currentBar.substring(29, 46));
            case "open":
                Double open1 = Double.parseDouble(temp1[1].replace(",", ".").substring(temp1[1].replace(",", ".").indexOf(" ") + 1));
                Double open2 = Double.parseDouble(temp2[1].replace(",", ".").substring(temp2[1].replace(",", ".").indexOf(" ") + 1));
                return new Derivative((open2 - open1) / open1, currentBar.substring(29, 46));
            case "high":
                Double high1 = Double.parseDouble(temp1[2].replace(",", ".").substring(temp1[2].replace(",", ".").indexOf(" ") + 1));
                Double high2 = Double.parseDouble(temp2[2].replace(",", ".").substring(temp2[2].replace(",", ".").indexOf(" ") + 1));
                return new Derivative((high2 - high1) / high1, currentBar.substring(29, 46));
            case "low":
                Double low1 = Double.parseDouble(temp1[3].replace(",", ".").substring(temp1[3].replace(",", ".").indexOf(" ") + 1));
                Double low2 = Double.parseDouble(temp2[3].replace(",", ".").substring(temp2[3].replace(",", ".").indexOf(" ") + 1));
                return new Derivative((low2 - low1) / low1, currentBar.substring(29, 46));
            case "raw":
                if (!currentBar.contains("#"))
                {
                    return new Derivative(Double.parseDouble(temp1[2].replace(",", ".")), currentBar.substring(29, 46));
                }
                else if (!currentBar.contains("#") && temp1.length == 4)
                {
                    return new Derivative(Double.parseDouble(temp1[3].replace(",", ".")), currentBar.substring(29, 46));
                }
                else
                {
                    return new Derivative(Double.parseDouble(temp1[2].replace(",", ".")), temp1[1]);
                }

        }
        System.out.println("no type found");
        System.out.println("incorrect type. input close, high, low, or open");
        return null;
    }

}
