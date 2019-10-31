/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algo;

/**
 *
 * @author Rael Alexander
 */
public class ProfitableOpportunity
{

    private String time = "";
    private String symbol = "";
    private double numMinutesProfitReached = 0.0;

    public double getNumMinutesProfitReached()
    {
        return numMinutesProfitReached;
    }

    public void setNumMinutesProfitReached(double numMinutesProfitReached)
    {
        this.numMinutesProfitReached = numMinutesProfitReached;
    }

    public String getSymbol()
    {
        return symbol;
    }

    public void setSymbol(String symbol)
    {
        this.symbol = symbol;
    }

    public ProfitableOpportunity(String symbol)
    {
        this.symbol = symbol;
    }

    public ProfitableOpportunity(String t, String symbol)
    {
        time = t;
        this.symbol = symbol;
    }

    public ProfitableOpportunity(String time, String symbol, Double numMinsReached)
    {
        this.time = time;
        this.symbol = symbol;
        this.numMinutesProfitReached = numMinsReached;
    }

    public String getTime()
    {
        return time;
    }

    public void setTime(String time)
    {
        this.time = time;
    }

}
