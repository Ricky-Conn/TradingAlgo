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
public class Derivative
{

    /*derivative when used as wave: local maxima or minima that ended the current wave
      derivative when used as derivative: difference between the 2 closes.
     */
    private double derivative = Double.NaN;
    private String time;

    public Derivative()
    {
    }

    public Derivative(String time)
    {
        this.time = time;
    }

//    public void convertDerivativeToOneMinute(int numSeconds)
//    {
//        double d = derivative * (double)((double)60/(double)numSeconds);
//        derivative = d;
//    }
    public Derivative(double derivative)
    {
        this.derivative = derivative;
    }

    public Derivative(double derivative, String time)
    {
        this.derivative = derivative;
        this.time = time;
    }

    public double getDerivative()
    {
        return derivative;
    }

    public void setDerivative(double derivative)
    {
        this.derivative = derivative;
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
