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
public class WavesPerShare
{

    private String symbol = "";
    private WaveDay currentDay;

    public WavesPerShare(String symb)
    {
        this.symbol = symb;
    }

    public WavesPerShare()
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

    public WaveDay getCurrentDay()
    {
        return currentDay;
    }

    public void setCurrentDay(WaveDay currentDay)
    {
        this.currentDay = currentDay;
    }

    /*
    returns a Day full of derivatives based on the type passed in as a parameter
     derivative = typeOfDerivative of close,open,high,or low
     */
    public WaveDay getNextDay()
    {
        WaveDay tempDay = null;
        FileInputStream fis = null;
        try
        {
            File fin = new File(this.symbol + ".txt");
            fis = new FileInputStream(fin);
            BufferedReader in = new BufferedReader(new InputStreamReader(fis));
            String aLine = null;
            boolean foundDay = false;
            String lastBar = "";
            while ((aLine = in.readLine()) != null)
            {
                //Process each line and add output to Dest.txt file
                if (aLine.indexOf(currentDay.getDay()) != -1)
                {
                    foundDay = true;
                }
                else if (foundDay && aLine.indexOf(currentDay.getDay()) != -1)
                {
                    tempDay = new WaveDay(Algo.numLinesPerFile, aLine.substring(29, 37));
                }
                if (foundDay && tempDay != null && aLine.indexOf(tempDay.getDay()) != -1)
                {
                    tempDay.addWave(getWave(aLine, symbol));
                }
                else if (foundDay && tempDay != null && aLine.indexOf(currentDay.getDay()) == -1)
                {
                    break;
                }
                lastBar = aLine;
            }
            // do not forget to close the buffer reader
            in.close();
        }
        catch (Exception ex)
        {
            Logger.getLogger(DerivativesPerShare.class.getName()).log(Level.SEVERE, null, ex);
        }
        return tempDay;
    }

    public Wave getWave(String currentBar, String symbol)
    {
        String[] temp = currentBar.split(", ");
        return new Wave(Double.parseDouble(temp[1]), temp[2], Integer.parseInt(temp[3]));
    }
}
