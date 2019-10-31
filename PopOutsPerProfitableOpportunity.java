package algo;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PopOutsPerProfitableOpportunity
{

    private int bottomPopoutsIndex = 0;//position of last inserted element
    private int topPopoutsIndex = 0;//position of last inserted element
    private boolean upwardOpportunity;//is it a upward or downward opportunity?
    private ProfitableOpportunity profitableOpportunity;
    private PopOut[] bottomPopouts = new PopOut[0];
    private PopOut[] topPopouts = new PopOut[0];
    private FileWriter fstream;
    static BufferedWriter out;
    private BufferedReader f;
    private String buffer = "";
    private String dateLastWritten = "";
    private PopOutsPerProfitableOpportunity current;
    private ProfitableOpportunity currentProfitableOpportunity;

    public PopOutsPerProfitableOpportunity(boolean upwardOpportunity, String symbol)
    {
        this.upwardOpportunity = upwardOpportunity;
        String indicatorName = "";
        if (upwardOpportunity)
        {
            indicatorName = "PopOutsPerOpportunityUp";
        }
        else
        {
            indicatorName = "PopOutsPerOpportunityDown";
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
                fstream = new FileWriter(indicatorName + " - " + symbol + ".txt", true);
                out = new BufferedWriter(fstream);
            }
            catch (IOException ex1)
            {
                Logger.getLogger(Indicator.class.getName()).log(Level.SEVERE, null, ex1);
            }
        }
    }

    public PopOutsPerProfitableOpportunity(boolean upwardOpportunity, ProfitableOpportunity profitableOpportunity, String symbol)
    {
        this.upwardOpportunity = upwardOpportunity;
        this.profitableOpportunity = profitableOpportunity;
        String indicatorName = "";
        if (upwardOpportunity)
        {
            indicatorName = "PopOutsPerOpportunityUp";
        }
        else
        {
            indicatorName = "PopOutsPerOpportunityDown";
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
                fstream = new FileWriter(indicatorName + " - " + symbol + ".txt", true);
                out = new BufferedWriter(fstream);
            }
            catch (IOException ex1)
            {
                Logger.getLogger(Indicator.class.getName()).log(Level.SEVERE, null, ex1);
            }
        }
    }

    /*
     returns a Day full of derivatives based on the type passed in as a parameter
     derivative = typeOfDerivative of close,open,high,or low
     */
    public PopOutsPerProfitableOpportunity getNextDay(String indicatorName, String symbol)
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
                if (current == null)
                {
                    current = new PopOutsPerProfitableOpportunity(upwardOpportunity, new ProfitableOpportunity());
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

    public void InitialiseWritePopOutsPerProfitableOpportunity(String symbol)
    {
        String indicatorName = "";
        if (upwardOpportunity)
        {
            indicatorName = "Upward Opportunity";
        }
        else
        {
            indicatorName = "Downward Opportunity";
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
                fstream = new FileWriter(indicatorName + " - " + symbol + ".txt", true);
                out = new BufferedWriter(fstream);
            }
            catch (IOException ex1)
            {
                Logger.getLogger(Indicator.class.getName()).log(Level.SEVERE, null, ex1);
            }
        }
    }

    public PopOutsPerProfitableOpportunity(ProfitableOpportunity profitableOpportunity, String symbol)
    {
        this.profitableOpportunity = profitableOpportunity;
        String indicatorName = "";
        if (upwardOpportunity)
        {
            indicatorName = "Upward Opportunity";
        }
        else
        {
            indicatorName = "Downward Opportunity";
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
                fstream = new FileWriter(indicatorName + " - " + symbol + ".txt", true);
                out = new BufferedWriter(fstream);
            }
            catch (IOException ex1)
            {
                Logger.getLogger(Indicator.class.getName()).log(Level.SEVERE, null, ex1);
            }
        }
    }

    public boolean hasMorePopoutsThan(PopOutsPerProfitableOpportunity that)
    {
        return (this.topPopouts.length + this.bottomPopouts.length) > (that.topPopouts.length + that.bottomPopouts.length);
    }

    public int getBottomPopoutsIndex()
    {
        return bottomPopoutsIndex;
    }

    public void setBottomPopoutsIndex(int bottomPopoutsIndex)
    {
        this.bottomPopoutsIndex = bottomPopoutsIndex;
    }

    public int getTopPopoutsIndex()
    {
        return topPopoutsIndex;
    }

    public void setTopPopoutsIndex(int topPopoutsIndex)
    {
        this.topPopoutsIndex = topPopoutsIndex;
    }

    public FileWriter getFstream()
    {
        return fstream;
    }

    public void setFstream(FileWriter fstream)
    {
        this.fstream = fstream;
    }

    public BufferedWriter getOut()
    {
        return out;
    }

    public void setOut(BufferedWriter out)
    {
        this.out = out;
    }

    public BufferedReader getF()
    {
        return f;
    }

    public void setF(BufferedReader f)
    {
        this.f = f;
    }

    public String getBuffer()
    {
        return buffer;
    }

    public void setBuffer(String buffer)
    {
        this.buffer = buffer;
    }

    public String getDateLastWritten()
    {
        return dateLastWritten;
    }

    public void setDateLastWritten(String dateLastWritten)
    {
        this.dateLastWritten = dateLastWritten;
    }

    public boolean hasSameNumPopoutsAs(PopOutsPerProfitableOpportunity that)
    {
        return (this.topPopouts.length + this.bottomPopouts.length) == (that.topPopouts.length + that.bottomPopouts.length);
    }

    public PopOutsPerProfitableOpportunity intersect(PopOutsPerProfitableOpportunity that)
    {
        if (!that.getBottomPopouts()[0].getSymbol().equals(this.getBottomPopouts()[0].getSymbol()))
        {
            return null;
        }
        String symbol = that.getBottomPopouts()[0].getSymbol();

        PopOutsPerProfitableOpportunity intersection = new PopOutsPerProfitableOpportunity(this.profitableOpportunity, symbol);

        for (PopOut thisTopPopout : this.topPopouts)
        {
            for (PopOut thatTopPopout : that.topPopouts)
            {
                if (thisTopPopout.getSymbol().equals(thatTopPopout.getSymbol()))
                {
                    //intersection.addToTopPopouts(thatTopPopout);
                    System.out.println("correct this");
                }
            }
        }

        for (PopOut thisBottomPopout : this.bottomPopouts)
        {
            for (PopOut thatBottomPopout : that.bottomPopouts)
            {
                if (thisBottomPopout.getSymbol().equalsIgnoreCase(thatBottomPopout.getSymbol()))
                {
                    //intersection.addToBottomPopouts(thatBottomPopout);
                    System.out.println("correct this");
                }
            }
        }

        return intersection;
    }

    public int getNumPopOuts()
    {
        return bottomPopouts.length + topPopouts.length;
    }

    public boolean isUpwardOpportunity()
    {
        return upwardOpportunity;
    }

    public void setUpwardOpportunity(boolean upwardOpportunity)
    {
        this.upwardOpportunity = upwardOpportunity;
    }

    public ProfitableOpportunity getProfitableOpportunity()
    {
        return profitableOpportunity;
    }

    public void setProfitableOpportunity(ProfitableOpportunity profitableOpportunity)
    {
        this.profitableOpportunity = profitableOpportunity;
    }

    public PopOut[] getBottomPopouts()
    {
        return bottomPopouts;
    }

    public void setBottomPopouts(PopOut[] bottomPopouts)
    {
        this.bottomPopouts = bottomPopouts;
    }
    
    public void addToPopouts(PopOut popOut, String indName)
    {
        if (upwardOpportunity)
        {

        }
        this.profitableOpportunity = profitableOpportunity;
        String indicatorName = "PopOutsPerOpportunity";
        if (upwardOpportunity)
        {
            indicatorName += "Up";
        }
        else
        {
            indicatorName += "Down";
        }
        try
        {
            if (dateLastWritten.equals(""))
            {
                dateLastWritten = popOut.getDerivative().getTime();
            }

            out.write(indName + "#" + popOut.getToString());
            out.newLine();

            if (dateLastWritten.substring(4, 6).equals(popOut.getDerivative().getTime().substring(4, 6)))
            {
                out.flush();
                fstream.flush();
                out.close();
                fstream.close();
                fstream = new FileWriter(indicatorName + " - " + popOut.getSymbol() + ".txt", true);
                out = new BufferedWriter(fstream);
            }
        }
        catch (IOException ex)
        {
            Logger.getLogger(PopOutsPerProfitableOpportunity.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

//    //trim array after all elements added
//    public void addToBottomPopouts(PopOut popOut, String indName)
//    {
//        if (upwardOpportunity)
//        {
//
//        }
//        this.profitableOpportunity = profitableOpportunity;
//        String indicatorName = "PopOutsPerOpportunity";
//        if (upwardOpportunity)
//        {
//            indicatorName += "UpBottom";
//        }
//        else
//        {
//            indicatorName += "DownBottom";
//        }
//        try
//        {
//            if (dateLastWritten.equals(""))
//            {
//                dateLastWritten = popOut.getDerivative().getTime();
//            }
//
//            out.write(indName + "#" + popOut.getToString());
//            out.newLine();
//
//            if (dateLastWritten.substring(4, 6).equals(popOut.getDerivative().getTime().substring(4, 6)))
//            {
//                out.flush();
//                fstream.flush();
//                out.close();
//                fstream.close();
//                fstream = new FileWriter(indicatorName + " - " + popOut.getSymbol() + ".txt", true);
//                out = new BufferedWriter(fstream);
//            }
//        }
//        catch (IOException ex)
//        {
//            Logger.getLogger(PopOutsPerProfitableOpportunity.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }

    /**
     * if total popOuts == 0 returns true
     */
    public boolean isPopoutsEmpty()
    {
        return (topPopouts.length + bottomPopouts.length) == 0;
    }

    /**
     * if total popOuts == numPopouts returns true
     */
    public boolean hasSameNumPopoutsAs(int numPopouts)
    {
        return (topPopouts.length + bottomPopouts.length) == numPopouts;
    }

    public PopOut[] getTopPopouts()
    {
        return topPopouts;
    }

    public void setTopPopouts(PopOut[] topPopouts)
    {
        this.topPopouts = topPopouts;
    }

//    //trim array after all elements added
//    public void addToTopPopouts(PopOut popOut, String indName)
//    {
//        String indicatorName = "PopOutsPerOpportunity";
//        if (currentProfitableOpportunity != null && !currentProfitableOpportunity.getTime().equals(profitableOpportunity.getTime()))
//        {
//            currentProfitableOpportunity = profitableOpportunity;
//            this.profitableOpportunity = profitableOpportunity;
//            if (upwardOpportunity)
//            {
//                indicatorName += "UpTop";
//            }
//            else
//            {
//                indicatorName += "DownTop";
//            }
//
//            dateLastWritten = popOut.getDerivative().getTime();
//            try
//            {
//                if (out != null)
//                {
//                    out.flush();
//                }
//                if (fstream != null)
//                {
//                    fstream.flush();
//                }
//                if (out != null)
//                {
//                    out.close();
//                }
//                if (fstream != null)
//                {
//                    fstream.close();
//                }
//
//                fstream = new FileWriter(indicatorName + " - " + popOut.getSymbol() + ".txt", true);
//                out = new BufferedWriter(fstream);
//                out.write(profitableOpportunity.getNumMinutesProfitReached() + "");
//                out.newLine();
//            }
//            catch (IOException ex)
//            {
//                Logger.getLogger(PopOutsPerProfitableOpportunity.class.getName()).log(Level.SEVERE, null, ex);
//            }
//        }
//
//        try
//        {
//            if (dateLastWritten.equals(""))
//            {
//                dateLastWritten = popOut.getDerivative().getTime();
//                //out.newLine();
//            }
//            out.write(indName + "#" + popOut.getToString());
//            out.newLine();
//
//            if (!dateLastWritten.substring(9, 11).equals(popOut.getDerivative().getTime().substring(9, 11)))
//            {
//                dateLastWritten = popOut.getDerivative().getTime();
//                if (out != null)
//                {
//                    out.flush();
//                }
//                if (fstream != null)
//                {
//                    fstream.flush();
//                }
//                if (out != null)
//                {
//                    out.close();
//                }
//                if (fstream != null)
//                {
//                    fstream.close();
//                }
//                fstream = new FileWriter(indicatorName + " - " + popOut.getSymbol() + ".txt", true);
//                out = new BufferedWriter(fstream);
//                dateLastWritten = popOut.getDerivative().getTime();
//            }
//        }
//        catch (IOException ex)
//        {
//            System.out.println("excepton:" + ex);
//            Logger.getLogger(PopOutsPerProfitableOpportunity.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }

}
