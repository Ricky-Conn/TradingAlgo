/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algo;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Rael's PC
 */
public class Indicator
{

    private String indicatorName = "";
    private DerivativesPerShare[] derivativesPerShare;
    private int count = 0;
    private FileWriter fstream;
    private BufferedWriter out;
    private BufferedReader f;
    private String buffer = "";
    private String dateLastWritten = "";

    public Indicator(String indName, String symbol, ArrayList<InstanceDataPerShare> instanceDataPerSharePerShare)//sets the size of derivativesPerShare and initilised all child objects and elements
    {
        indicatorName = indName;
        this.derivativesPerShare = new DerivativesPerShare[instanceDataPerSharePerShare.size()];
        for (int i = 0; i < instanceDataPerSharePerShare.size(); i++)
        {
            this.derivativesPerShare[i] = new DerivativesPerShare(instanceDataPerSharePerShare.get(i).getSymbol());
        }
        try
        {
            f = new BufferedReader(new FileReader(indicatorName + " - " + symbol + ".txt"));
            if (f.readLine() == null)
            {
                new BufferedReader(new FileReader(indicatorName + "kjfsdfks.txt"));
            }
        }
        catch (IOException ex)
        {
            try
            {
                fstream = new FileWriter(indName + " - " + symbol + ".txt", true);
                out = new BufferedWriter(fstream);
            }
            catch (IOException ex1)
            {
                Logger.getLogger(Indicator.class.getName()).log(Level.SEVERE, null, ex1);
            }
        }
    }

//    public Indicator(String indName, String symbol)
//    {
//
//        indicatorName = indName;
//        try
//        {
//            f = new BufferedReader(new FileReader(indicatorName + " - " + symbol + ".txt"));
//
//        }
//        catch (IOException ex)
//        {
//            try
//            {
//                fstream = new FileWriter(indName + " - " + symbol + ".txt", true);
//                out = new BufferedWriter(fstream);
//            }
//            catch (IOException ex1)
//            {
//                Logger.getLogger(Indicator.class.getName()).log(Level.SEVERE, null, ex1);
//            }
//        }
//    }
    public Day getNextDay(int derivativesPerSharePos, String indName)
    {
        return derivativesPerShare[derivativesPerSharePos].getNextDay("raw", indName);
    }

    public Day getCurrentDay(int derivativesPerSharePos, String indName)
    {
        return derivativesPerShare[derivativesPerSharePos].getNextDay("raw", indName);
    }

    public void addWave(Wave wave, String symbol)
    {
        if (wave != null && dateLastWritten.isEmpty())
        {
            dateLastWritten = wave.getTime();
        }
        if (dateLastWritten.substring(0, 10).equals(wave.getTime().substring(0, 10)))
        {
            if (wave != null)
            {
                buffer += count + "#" + wave.getDirection() + "#" + wave.getTime() + "#";
                if (!Double.isNaN(wave.getLocalExtremeValue()))
                {
                    buffer += wave.getLocalExtremeValue() + ";;";
                }
                else
                {
                    buffer += "null;;";
                }
                count++;
            }
        }
        else
        {
            try
            {
                //Process each line and add output to Dest.txt file
                for (String part : buffer.split(";;"))
                {
                    out.write(part);
                    out.newLine();
                }
                buffer = "";
                buffer += count + "#" + wave.getTime() + "#" + wave.getLocalExtremeValue() + ";;";
                dateLastWritten = wave.getTime();
            }
            catch (IOException ex)
            {
                Logger.getLogger(Indicator.class.getName()).log(Level.SEVERE, null, ex);
            }
            try
            {
                out.flush();
                fstream.flush();
                out.close();
                fstream.close();
                fstream = new FileWriter(indicatorName + " - " + symbol + ".txt", true);
                out = new BufferedWriter(fstream);
            }
            catch (IOException ex)
            {
                Logger.getLogger(Indicator.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    public void replaceLastLine(Derivative der, String symbol)
    {
        if (out == null)
        {
            return;
        }
        if (buffer.length() > 0)
        {
            String[] lines = buffer.split(";;");
            buffer = buffer.substring(0, buffer.lastIndexOf(lines[lines.length - 1]));
            buffer += count + "#" + der.getTime() + "#" + der.getDerivative() + ";;";
            if (dateLastWritten.equals(""))
            {
                dateLastWritten = der.getTime();
            }
            else if (!dateLastWritten.substring(9, 11).equals(der.getTime().substring(9, 11)))
            {
                try
                {
                    //Process each line and add output to Dest.txt file
                    if (out != null)
                    {
                        for (String part : buffer.split(";;"))
                        {
                            out.write(part);
                            out.newLine();
                        }
                        buffer = "";
                        buffer += count + "#" + der.getTime() + "#" + der.getDerivative() + ";;";
                    }
                    else
                    {
                        buffer = null;
                    }
                    dateLastWritten = der.getTime();
                }
                catch (IOException ex)
                {
                    Logger.getLogger(Indicator.class.getName()).log(Level.SEVERE, null, ex);
                }
                try
                {
                    if (out != null)
                    {
                        out.flush();
                        fstream.flush();
                        out.close();
                        fstream.close();
                    }
                    fstream = new FileWriter(indicatorName + " - " + symbol + ".txt", true);
                    out = new BufferedWriter(fstream);
                }
                catch (IOException ex)
                {
                    Logger.getLogger(Indicator.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            return;
        }
        try
        {
            File file = new File(indicatorName + " - " + symbol + ".txt");
            RandomAccessFile randomAccessFile = new RandomAccessFile(file, "rw");
            byte b;
            long length = randomAccessFile.length();
            if (length != 0)
            {
                do
                {
                    length -= 1;
                    randomAccessFile.seek(length);
                    b = randomAccessFile.readByte();
                }
                while (b != 10 && length > 0);
                randomAccessFile.setLength(length);
                randomAccessFile.close();
                try
                {
                    BufferedWriter w = new BufferedWriter(new FileWriter(indicatorName + " - " + symbol + ".txt", true));
                    if (!Double.isNaN(der.getDerivative()))
                    {
                        w.write(count + "#" + der.getTime() + "#" + der.getDerivative());
                        w.newLine();
                    }
                    else
                    {
                        out.write(count + "#" + der.getTime() + "#null");
                        count++;
                        out.newLine();
                    }
                    w.close();

                }
                catch (IOException ex)
                {
                    Logger.getLogger(Indicator.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        catch (Exception e)
        {
            System.out.println("Exception " + e);
        }

    }

    public BufferedReader getF()
    {
        return f;
    }

    public void setF(BufferedReader f)
    {
        this.f = f;
    }

    public void addDerivative(Derivative der, String symbol)
    {
        if (out == null)
        {
            return;
        }
        if (der == null && symbol.contains("close"))
        {
            try
            {
                out.flush();
                fstream.flush();
                out.close();
                fstream.close();
                fstream = new FileWriter(indicatorName + " - " + symbol.replace("close", "") + ".txt", true);
                out = new BufferedWriter(fstream);
            }
            catch (IOException ex)
            {
                Logger.getLogger(Indicator.class.getName()).log(Level.SEVERE, null, ex);
            }
            return;
        }
        if (Double.isNaN(der.getDerivative()))
        {
            buffer += count + "#" + der.getTime() + "#null;;";
            count++;
            return;
        }
        if (!Double.isNaN(der.getDerivative()) && dateLastWritten.isEmpty())
        {
            dateLastWritten = der.getTime();
        }

        if (indicatorName.toLowerCase().contains("outlier"))
        {
            try
            {
                out.write(count + "#" + der.getTime() + "#" + der.getDerivative() + ";;");
                out.newLine();
            }
            catch (IOException ex)
            {
                Logger.getLogger(Indicator.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        else if (indicatorName.contains("Profitable") && dateLastWritten.substring(9, 11).equals(der.getTime().substring(9, 11)))
        {
            if (!Double.isNaN(der.getDerivative()))
            {
                buffer += count + "#" + der.getTime() + "#" + der.getDerivative() + ";;";
                count++;
            }
        }
        else if (indicatorName.contains("Profitable") && !dateLastWritten.substring(9, 11).equals(der.getTime().substring(9, 11)))
        {
            try
            {
                if (out != null)
                {
                    //Process each line and add output to Dest.txt file
                    for (String part : buffer.split(";;"))
                    {
                        out.write(part);
                        out.newLine();
                    }
                    buffer = "";
                    buffer += count + "#" + der.getTime() + "#" + der.getDerivative() + ";;";
                }
                else
                {
                    buffer = "";
                }
                dateLastWritten = der.getTime();
            }
            catch (IOException ex)
            {
                Logger.getLogger(Indicator.class.getName()).log(Level.SEVERE, null, ex);
            }
            try
            {
                out.flush();
                fstream.flush();
                out.close();
                fstream.close();
                fstream = new FileWriter(indicatorName + " - " + symbol + ".txt", true);
                out = new BufferedWriter(fstream);
            }
            catch (IOException ex)
            {
                Logger.getLogger(Indicator.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        else if (dateLastWritten.substring(0, 8).equals(der.getTime().substring(0, 8)))
        {
            if (!Double.isNaN(der.getDerivative()))
            {
                buffer += count + "#" + der.getTime() + "#" + der.getDerivative() + ";;";
                count++;
            }
        }
        else if (!dateLastWritten.substring(0, 8).equals(der.getTime().substring(0, 8)))
        {
            try
            {
                if (out != null)
                {
                    //Process each line and add output to Dest.txt file
                    for (String part : buffer.split(";;"))
                    {
                        out.write(part);
                        out.newLine();
                    }
                    buffer = "";
                    buffer += count + "#" + der.getTime() + "#" + der.getDerivative() + ";;";
                }
                else
                {
                    buffer = "";
                }
                dateLastWritten = der.getTime();
            }
            catch (IOException ex)
            {
                Logger.getLogger(Indicator.class.getName()).log(Level.SEVERE, null, ex);
            }
            try
            {
                out.flush();
                fstream.flush();
                out.close();
                fstream.close();
                fstream = new FileWriter(indicatorName + " - " + symbol + ".txt", true);
                out = new BufferedWriter(fstream);
            }
            catch (IOException ex)
            {
                Logger.getLogger(Indicator.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    public BufferedWriter getOut()
    {
        return out;
    }

    public void setOut(BufferedWriter out)
    {
        this.out = out;
    }

    public String getIndicatorName()
    {
        return indicatorName;
    }

    public void setIndicatorName(String indicatorName)
    {
        this.indicatorName = indicatorName;
    }

    public DerivativesPerShare[] getDerivativesPerShare()
    {
        return derivativesPerShare;
    }

    public void setDerivativesPerShare(DerivativesPerShare[] derivativesPerShare)
    {
        this.derivativesPerShare = derivativesPerShare;
    }

}
