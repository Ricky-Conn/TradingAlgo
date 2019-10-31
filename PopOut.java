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
public class PopOut
{

    private boolean topOutlier;//is it a top or bottom outlier?
    private String symbol = "";
    private Derivative derivative = new Derivative();

    public PopOut(boolean topOutlier, String Symbol, Derivative der)
    {
        this.topOutlier = topOutlier;
        symbol = Symbol;
        derivative = der;
    }

    public String getToString()
    {
        return derivative.getTime() + "#" + this.getSymbol() + "#" + derivative.getDerivative() + ";";
    }

    public boolean isTopOutlier()
    {
        return topOutlier;
    }

    public void setTopOutlier(boolean topOutlier)
    {
        this.topOutlier = topOutlier;
    }

    public PopOut()
    {
    }

    public void setSymbol(String symbol)
    {
        this.symbol = symbol;
    }

    public void setDerivative(Derivative derivative)
    {
        this.derivative = derivative;
    }

    public String getSymbol()
    {
        return symbol;
    }

    public Derivative getDerivative()
    {
        return derivative;
    }

}
