package algo;

import java.util.ArrayList;

public class WaveDay
{

    private String day = "";
    private Wave[] waves = new Wave[0];
    private int lastEntryIndex = -1;

    public WaveDay(Wave[] waves, String Day)
    {
        this.waves = waves;
        for (Wave der : waves)
        {
            if (der == null)
            {
                break;
            }
            lastEntryIndex++;
        }
        day = Day;
    }

    public WaveDay(int waves, String Day)
    {
        this.waves = new Wave[waves];
        day = Day;
    }

    public void trim()
    {
        Wave[] temp = new Wave[lastEntryIndex + 1];

        int i = 0;
        for (Wave d : waves)
        {
            temp[i] = d;
            i++;
        }

        waves = temp;
    }

    public void trimToSize(int lookBackPeriod)
    {

        Wave[] ders = new Wave[this.getLastEntryIndex()+ 1];
        boolean valueAdded = false;
        for (int i = 0; i < this.getLastEntryIndex() + 1; i++)
        {
            if (this.getWaves()[i] != null)
            {
                valueAdded = true;
            }
            ders[i] = this.getWaves()[i];
        }
        if (valueAdded)
        {
            this.setWaves(ders);
        }
        else
        {
            this.setWaves(new Wave[0]);
            this.setLastEntryIndex(-1);
        }
    }

    public WaveDay(String Day)
    {
        day = Day;
    }

    public WaveDay(ArrayList<InstanceData> isd, String Day)
    {
        this.waves = new Wave[isd.size()];
        day = Day;
    }

    public void newWavesArray(int size)
    {
        waves = new Wave[size];
        lastEntryIndex = -1;
    }

    public void addWave(Wave wave)
    {
        lastEntryIndex++;
        if (lastEntryIndex < waves.length)
        {
            waves[lastEntryIndex] = wave;
        }
        else
        {
            Wave[] tempDers = new Wave[waves.length + 1];
            int pos = 0;
            for (Wave temp : waves)
            {
                tempDers[pos] = temp;
                pos++;
            }
            tempDers[pos] = wave;
            waves = tempDers;

        }
    }

    public void replaceLast(Wave der)
    {
        waves[lastEntryIndex] = der;
    }

    public int getLastEntryIndex()
    {
        return lastEntryIndex;
    }

    public void setLastEntryIndex(int lastEntryIndex)
    {
        this.lastEntryIndex = lastEntryIndex;
    }

    public void initialise(Wave[] waves, String Day)
    {
        this.day = Day;
        this.waves = new Wave[waves.length];
        for (int i = 0; i < waves.length; i++)
        {
            this.waves[i] = null;
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

    public Wave[] getWaves()
    {
        return waves;
    }

    public void setWaves(Wave[] waves)
    {
        for (Wave der : waves)
        {
            lastEntryIndex++;
            if (der == null)
            {
                break;
            }
        }
        this.waves = waves;
    }

    public void addToWaves(Wave wave)
    {
        if ((lastEntryIndex + 1) >= waves.length)
        {
            Wave[] temp = new Wave[waves.length + 1];
            int i = 0;

            for (Wave d : waves)
            {
                temp[i] = d;
                i++;
            }
            temp[i + 1] = wave;
        }
        else
        {
            lastEntryIndex++;
            waves[lastEntryIndex] = wave;
        }

    }

}
