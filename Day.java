package algo;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Arrays;

public class Day
{

    private String day = "";
    private Derivative[] derivatives = new Derivative[0];
    private int lastEntryIndex = -1;

    public Day(Derivative[] derivatives, String Day)
    {
        this.derivatives = derivatives;
        for (Derivative der : derivatives)
        {
            if (der == null)
            {
                break;
            }
            lastEntryIndex++;

        }
        day = Day;
    }

    public Day(int derivativesSize, String Day)
    {
        this.derivatives = new Derivative[derivativesSize];
        day = Day;
    }

    public void trim()
    {
        Derivative[] temp = new Derivative[lastEntryIndex + 1];

        int i = 0;
        for (Derivative d : derivatives)
        {
            if (d == null)
            {
                break;
            }
            temp[i] = d;
            i++;
        }

        derivatives = temp;
    }

    public Day(String Day)
    {
        day = Day;
    }

    public Day(ArrayList<InstanceData> isd, String Day)
    {
        this.derivatives = new Derivative[isd.size()];
        day = Day;
    }

    public void newDerivativesArray(int size)
    {
        derivatives = new Derivative[size];
        lastEntryIndex = -1;
    }

    public int getLastEntryIndex()
    {
        return lastEntryIndex;
    }

    public void setLastEntryIndex(int lastEntryIndex)
    {
        this.lastEntryIndex = lastEntryIndex;
    }

    public void initialise(Derivative[] derivatives, String Day)
    {
        this.day = Day;
        this.derivatives = new Derivative[derivatives.length];
        for (int i = 0; i < derivatives.length; i++)
        {
            this.derivatives[i] = null;
        }
    }

    public String getDay()
    {
        return day;
    }

    public void setDay(String day)
    {
        this.day = day;
    }

    public Derivative[] getDerivatives()
    {
        return derivatives;
    }

    public void setDerivatives(Derivative[] derivatives)
    {
        for (Derivative der : derivatives)
        {
            lastEntryIndex++;
            if (der == null)
            {
                break;
            }
        }
        this.derivatives = derivatives;
    }

    public void addToDerivatives(Derivative derivative)
    {
        if ((lastEntryIndex + 1) >= derivatives.length)
        {
            Derivative[] temp = new Derivative[derivatives.length + 1];
            int i = 0;

            for (Derivative d : derivatives)
            {
                temp[i] = d;
                i++;
            }
            temp[i + 1] = derivative;
        }
        else
        {
            lastEntryIndex++;
            derivatives[lastEntryIndex] = derivative;
        }

    }

    public void addDerivative(Derivative der)
    {
        lastEntryIndex++;
        if (lastEntryIndex < derivatives.length)
        {
            derivatives[lastEntryIndex] = der;
        }
        else
        {
            Derivative[] tempDers = new Derivative[derivatives.length + 1];
            int pos = 0;
            for (Derivative temp : derivatives)
            {
                tempDers[pos] = temp;
                pos++;
            }
            tempDers[pos] = der;
            derivatives = tempDers;
        }
    }

    public void sortByTime()
    {
        Derivative[] ds = this.getDerivatives();
        Arrays.sort(ds, new DerivativeCompareByTimeImpl());
        this.setDerivatives(ds);
    }

    public void sortByDerivative()
    {

        Derivative[] ds = this.getDerivatives();
        Arrays.sort(ds, new DerivativeCompareImpl());
        this.setDerivatives(ds);
    }

}
