/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algo;

/**
 *
 * @author Adie
 */
public class LargestIntersection
{
    private PopOutsPerProfitableOpportunity popOutsPerProfitableOpportunity;
    private int numOccurances = 0;

    public boolean contains(String symbol)
    {
        for (PopOut bottomPopOut : popOutsPerProfitableOpportunity.getBottomPopouts())
        {
            if (bottomPopOut.getSymbol().equalsIgnoreCase(symbol))
            {
                return true;
            }
        }

        for (PopOut topPopOut : popOutsPerProfitableOpportunity.getTopPopouts())
        {
            if (topPopOut.getSymbol().equalsIgnoreCase(symbol))
            {
                return true;
            }
        }

        return false;

    }

    public PopOutsPerProfitableOpportunity intersect(LargestIntersection that)
    {
        return this.popOutsPerProfitableOpportunity.intersect(that.popOutsPerProfitableOpportunity);
    }

    public PopOutsPerProfitableOpportunity intersect(PopOutsPerProfitableOpportunity that)
    {
        return this.popOutsPerProfitableOpportunity.intersect(that);
    }

    public PopOutsPerProfitableOpportunity getPopOutsPerProfitableOpportunity()
    {
        return popOutsPerProfitableOpportunity;
    }

    public void setPopOutsPerProfitableOpportunity(PopOutsPerProfitableOpportunity popOutsPerProfitableOpportunity)
    {
        this.popOutsPerProfitableOpportunity = popOutsPerProfitableOpportunity;
    }

    public int getNumOccurances()
    {
        return numOccurances;
    }

    public void setNumOccurances(int numOccurances)
    {
        this.numOccurances = numOccurances;
    }

    public void incrementNumOccurances()
    {
        this.numOccurances++;
    }
}
