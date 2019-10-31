package algo;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Strategy
{

    private int intersections = 0;
    private PopOut[] bottomPopouts = new PopOut[0];
    private PopOut[] topPopouts = new PopOut[0];
    private double profit = 0.0; //per year
    private int numOccurances = 0; //per year
    private ArrayList<Strategy> subsets = new ArrayList<>();

    public Strategy()
    {
    }

    public Strategy(int thatIntersection)
    {
        intersections = thatIntersection;
    }

    public ArrayList<Strategy> getSubsets()
    {
        return subsets;
    }

    public void setSubsets(ArrayList<Strategy> subsets)
    {
        boolean smaller = true;
        for (Strategy strat : subsets)
        {
            if (strat.getNumPopOuts() >= getNumPopOuts())
            {
                smaller = false;
            }
        }
        if (smaller)
        {
            this.subsets = subsets;
        }
    }

    public void addToSubsets(Strategy temp)
    {
        subsets.add(temp);
        for (int i = 0; i < Algo.algo.strategySubsets.size(); i++)
        {
            Strategy strat = Algo.algo.strategySubsets.get(i);
            if (strat.hasSamePopOuts(temp))
            {
                Algo.algo.strategySubsets.remove(i);
                i--;
            }
        }
    }

    public boolean containedIn(ArrayList<Strategy> temp)
    {
        for (Strategy strat : temp)
        {
            if (hasSamePopOuts(strat))
            {
                return true;
            }
        }
        return false;
    }

    public double getProfit()
    {
        return profit;
    }

    public void setProfit(double profit)
    {
        this.profit = profit;
    }

    public int getNumOccurances()
    {
        return numOccurances;
    }

    public void setNumOccurances(int numOccurances)
    {
        this.numOccurances = numOccurances;
    }

    public int getIntersections()
    {
        return intersections;
    }

    public void setIntersections(int intersections)
    {
        this.intersections = intersections;
    }

    public boolean hasMorePopoutsThan(PopOutsPerProfitableOpportunity that)
    {
        return (this.topPopouts.length + this.bottomPopouts.length) > (that.getTopPopouts().length + that.getBottomPopouts().length);
    }

    public boolean hasLessPopoutsThan(Strategy that)
    {
        return (this.topPopouts.length + this.bottomPopouts.length) < (that.getTopPopouts().length + that.getBottomPopouts().length);
    }

    public boolean hasSameNumPopoutsAs(PopOutsPerProfitableOpportunity that)
    {
        return (this.topPopouts.length + this.bottomPopouts.length) == (that.getTopPopouts().length + that.getBottomPopouts().length);
    }

    public boolean hasSameNumPopoutsAs(Strategy that)
    {
        return (this.topPopouts.length + this.bottomPopouts.length) == (that.getTopPopouts().length + that.getBottomPopouts().length);
    }

    public Strategy intersect(PopOutsPerProfitableOpportunity that)
    {

        Strategy intersection = new Strategy();

        for (PopOut thisTopPopout : this.topPopouts)
        {
            for (PopOut thatTopPopout : that.getTopPopouts())
            {
                if (thisTopPopout.getSymbol().equals(thatTopPopout.getSymbol()))
                {
                    intersection.addToTopPopouts(thatTopPopout);
                }
            }
        }

        for (PopOut thisBottomPopout : this.bottomPopouts)
        {
            for (PopOut thatBottomPopout : that.getBottomPopouts())
            {
                if (thisBottomPopout.getSymbol().equalsIgnoreCase(thatBottomPopout.getSymbol()))
                {
                    intersection.addToBottomPopouts(thatBottomPopout);
                }
            }
        }

        return intersection;
    }

    /**
     * returns "not contained" if the symbol is not found in the intersection
     * returns "bottom" if matching symbol's direction is bottom PopOut
     * returns "top" if matching symbol's direction is bottom PopOut returns "top bottom" if 2 elements found with same symbol but different directions
     */
    public String directionOfContainedSymbol(String symbol)
    {
        String returnStr = "not contained";

        for (PopOut bottomPopOut : this.getBottomPopouts())
        {
            if (bottomPopOut.getSymbol().equalsIgnoreCase(symbol))
            {
                returnStr = "top";
            }
        }

        for (PopOut topPopOut : this.getTopPopouts())
        {
            if (topPopOut.getSymbol().equalsIgnoreCase(symbol) && returnStr.contains("top"))
            {
                returnStr += " bottom";
            }
            else if (topPopOut.getSymbol().equalsIgnoreCase(symbol))
            {
                returnStr = "bottom";
            }
        }

        return returnStr;

    }

    /*
     Must have less popouts than strategy
     */
    public boolean isSubsetof(Strategy that)
    {
        if (this.getNumPopOuts() >= that.getNumPopOuts())
        {
            return false;
        }
        else if (this.intersect(that).hasSameNumPopoutsAs(this) && this.hasLessPopoutsThan(that));
        {
            return true;
        }
    }

    public Strategy intersect(Strategy that)
    {

        Strategy intersection = new Strategy();

        for (PopOut thisTopPopout : this.topPopouts)
        {
            for (PopOut thatTopPopout : that.getTopPopouts())
            {
                if (thisTopPopout.getSymbol().equals(thatTopPopout.getSymbol()))
                {
                    intersection.addToTopPopouts(thatTopPopout);
                }
            }
        }

        for (PopOut thisBottomPopout : this.bottomPopouts)
        {
            for (PopOut thatBottomPopout : that.getBottomPopouts())
            {
                if (thisBottomPopout.getSymbol().equalsIgnoreCase(thatBottomPopout.getSymbol()))
                {
                    intersection.addToBottomPopouts(thatBottomPopout);
                }
            }
        }

        return intersection;
    }

    public boolean hasSamePopOuts(Strategy that)
    {

        Strategy intersection = new Strategy();

        for (PopOut thisTopPopout : this.topPopouts)
        {
            for (PopOut thatTopPopout : that.getTopPopouts())
            {
                if (thisTopPopout.getSymbol().equals(thatTopPopout.getSymbol()))
                {
                    intersection.addToTopPopouts(thatTopPopout);
                }
            }
        }

        for (PopOut thisBottomPopout : this.bottomPopouts)
        {
            for (PopOut thatBottomPopout : that.getBottomPopouts())
            {
                if (thisBottomPopout.getSymbol().equalsIgnoreCase(thatBottomPopout.getSymbol()))
                {
                    intersection.addToBottomPopouts(thatBottomPopout);
                }
            }
        }

        return intersection.hasSameNumPopoutsAs(this.getNumPopOuts()) && intersection.hasSameNumPopoutsAs(that.getNumPopOuts());
    }

    public int getNumPopOuts()
    {
        return bottomPopouts.length + topPopouts.length;
    }

    public PopOut[] getBottomPopouts()
    {
        return bottomPopouts;
    }

    public void setBottomPopouts(PopOut[] bottomPopouts)
    {
        this.bottomPopouts = bottomPopouts;
    }

    //trim array after all elements added
    public void addToBottomPopouts(PopOut popOut)
    {
        PopOut[] temp = new PopOut[bottomPopouts.length + 1];
        for (int i = 0; i < bottomPopouts.length; i++)
        {
            temp[i] = bottomPopouts[i];
        }
        temp[bottomPopouts.length] = popOut;
        bottomPopouts = temp;
    }

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

    //trim array after all elements added
    public void addToTopPopouts(PopOut popOut)
    {
        PopOut[] temp = new PopOut[topPopouts.length + 1];
        for (int i = 0; i < topPopouts.length; i++)
        {
            temp[i] = topPopouts[i];
        }
        temp[topPopouts.length] = popOut;
        topPopouts = temp;
    }

}
