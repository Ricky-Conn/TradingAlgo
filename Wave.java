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
public class Wave
{
    private int direction = 0; //1 = moved upward from a low to a high, -1 = moved downward from a high to a low.
    private double localExtremeValue;//local maxima or minima that ended the current wave
    private String time;

    public Wave()
    {
    }

    public Wave(String time)
    {
        this.time = time;
    }
    
    public Wave(double derivative)
    {
        this.localExtremeValue = derivative;
    }
    
    public Wave(double derivative, String time, int direction)
    {
        this.direction = direction;
        this.localExtremeValue = derivative;
        this.time = time;
    }

    public int getDirection()
    {
        return direction;
    }

    public void setDirection(int direction)
    {
        this.direction = direction;
    }

    public double getLocalExtremeValue()
    {
        return localExtremeValue;
    }

    public void setLocalExtremeValue(double localExtremeValue)
    {
        this.localExtremeValue = localExtremeValue;
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
