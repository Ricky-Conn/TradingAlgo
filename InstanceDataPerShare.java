/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algo;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;
import static javafx.scene.input.KeyCode.T;

/**
 *
 * @author Rael Alexander
 */
public class InstanceDataPerShare
{

    private String symbol = "";
    private InstanceDataDay currentDay;
    private FileInputStream fis;
    private BufferedReader in;

    public InstanceDataPerShare(String symbol)
    {
        this.symbol = symbol;
        try
        {
            File fin = new File(symbol + ".txt");
            fis = new FileInputStream(fin);
            if (in != null)
            {
                in.close();
            }
            in = new BufferedReader(new InputStreamReader(fis));
        }
        catch (IOException ex)
        {
            Logger.getLogger(InstanceDataPerShare.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public InstanceDataPerShare(InstanceDataPerShare that)
    {
        symbol = that.getSymbol();
        if (that.getCurrentDay() != null)
        {
            currentDay = new InstanceDataDay(that.getCurrentDay().getInstanceData(), that.getCurrentDay().getDay(), that.getCurrentDay().getLastEntryIndex());
        }
        try
        {
            File fin = new File(symbol + ".txt");
            fis = new FileInputStream(fin);
            if (in != null)
            {
                in.close();
            }
            in = new BufferedReader(new InputStreamReader(fis));
        }
        catch (IOException ex)
        {
            Logger.getLogger(InstanceDataPerShare.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String getSymbol()
    {
        return symbol;
    }

    public void setSymbol(String symbol)
    {
        this.symbol = symbol;
    }

    public InstanceDataDay getNextDay()
    {
        InstanceDataDay tempDay = null;
        try
        {
            boolean foundDay = false;
            String lastBar = "";
            String aLine = null;

            while ((aLine = in.readLine()) != null)
            {
                if (tempDay == null)
                {
                    tempDay = new InstanceDataDay(Algo.numLinesPerFile,aLine.substring(29, 37));
                }
                else if (!tempDay.getDay().equals(aLine.substring(29, 37)))
                {
                    if (tempDay != null)
                    {
                        tempDay.trim();
                    }
                    return tempDay;
                }
                tempDay.addDerivative(getInstance(aLine, symbol));
            }
        }
        catch (IOException ex)
        {
            Logger.getLogger(InstanceDataPerShare.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (tempDay != null)
        {
            tempDay.trim();
        }
        return tempDay;
    }

    /*
    DISBANDED - Slower than getNextDay() method
    returns a Day full of derivatives based on the type passed in as a parameter
     derivative = typeOfDerivative of close,open,high,or low
     */
    public InstanceDataDay getNextDayReRead()
    {
        InstanceDataDay tempDay = null;
        try
        {
            boolean foundDay = false;
            String lastBar = "";
            String aLine = null;
            while ((aLine = in.readLine()) != null)
            {
                //Process each line and add output to Dest.txt file
                if (currentDay == null)
                {
                    if (aLine.contains("2"))
                    {
                        currentDay = new InstanceDataDay(Algo.numLinesPerFile, aLine.substring(29, 37));
                        tempDay = new InstanceDataDay(Algo.numLinesPerFile, aLine.substring(29, 37));
                        foundDay = true;
                    }
                    else
                    {
                        continue;
                    }
                }
                else if (!foundDay && aLine.contains(currentDay.getDay()) && tempDay == null)
                {
                    foundDay = true;
                }
                else if (foundDay && aLine.indexOf(currentDay.getDay()) == -1 && tempDay == null)
                {
                    tempDay = new InstanceDataDay(Algo.numLinesPerFile, aLine.substring(29, 37));
                }
                if (foundDay && tempDay != null && aLine.indexOf(tempDay.getDay()) != -1)
                {
                    tempDay.addDerivative(getInstance(aLine, symbol));
                }
                else if (foundDay && tempDay != null && aLine.indexOf(tempDay.getDay()) == -1)
                {
                    break;
                }
                lastBar = aLine;
            }
            // do not forget to close the buffer reader
            //in.close();
        }
        catch (IOException ex)
        {
            Logger.getLogger(DerivativesPerShare.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (tempDay != null)
        {
            tempDay.trim();
        }
        else
        {
            return null;
        }
        currentDay = tempDay;
        if (currentDay == null)
        {
            try
            {
                in.close();
            }
            catch (IOException ex)
            {
                Logger.getLogger(Indicator.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return tempDay;
    }

    public InstanceData getInstance(String currentBar, String symbol)
    {
        String[] temp = null;
        int volume = -1;
        Double open;
        Double high;
        Double low;
        Double close;

        //symbol;nr;timestamp;open;high;low;close;volume
        if (currentBar.contains(";"))
        {
            temp = currentBar.split(";");
            volume = Integer.parseInt(temp[7]);
            open = Double.parseDouble(temp[3].replace(",", ".").substring(temp[1].replace(",", ".").indexOf(" ") + 1));
            high = Double.parseDouble(temp[4].replace(",", ".").substring(temp[1].replace(",", ".").indexOf(" ") + 1));
            low = Double.parseDouble(temp[5].replace(",", ".").substring(temp[1].replace(",", ".").indexOf(" ") + 1));
            close = Double.parseDouble(temp[6].replace(",", ".").substring(temp[1].replace(",", ".").indexOf(" ") + 1));
            return new InstanceData(temp[2], open, high, low, close, volume);
        }
        else
        {
            temp = currentBar.split(", ");
            if (temp[5].contains("-1"))
            {
                volume = -1;
            }
            else
            {
                volume = Integer.parseInt(temp[5].split(" ")[1]);
            }
            close = Double.parseDouble(temp[4].replace(",", ".").substring(temp[4].replace(",", ".").indexOf(" ") + 1));
            open = Double.parseDouble(temp[1].replace(",", ".").substring(temp[1].replace(",", ".").indexOf(" ") + 1));
            high = Double.parseDouble(temp[2].replace(",", ".").substring(temp[2].replace(",", ".").indexOf(" ") + 1));
            low = Double.parseDouble(temp[3].replace(",", ".").substring(temp[3].replace(",", ".").indexOf(" ") + 1));
            return new InstanceData(temp[0].split(" ")[4] + " " + temp[0].split(" ")[5], open, high, low, close, volume);
        }
    }

    public InstanceDataDay getCurrentDay()
    {
        return currentDay;
    }

    public void setCurrentDay(InstanceDataDay currentDay)
    {
        this.currentDay = currentDay;
    }
}
