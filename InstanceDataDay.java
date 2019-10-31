package algo;

import java.util.ArrayList;

public class InstanceDataDay
{

    private String day = "";
    private InstanceData[] instanceData;
    private int lastEntryIndex = -1;

    public InstanceDataDay(String[] timeStamps, ArrayList<Double[]> derivatives, String day)
    {
        this.day = day;
        instanceData = new InstanceData[derivatives.get(0).length];
        for (int j = 0; j < derivatives.get(0).length; j++)
        {
            String volumeS = derivatives.get(0)[j] + "";
            int volume = Integer.parseInt(volumeS.substring(0, volumeS.indexOf(".")));
            instanceData[j] = new InstanceData(timeStamps[j], derivatives.get(0)[j], derivatives.get(1)[j], derivatives.get(2)[j], derivatives.get(3)[j], volume);
            lastEntryIndex = j;
            if (j == 206535)
            {
                System.out.println("");
            }
        }
    }
    
    public InstanceDataDay(InstanceData[] InstanceData, String day, int lastIndex)
    {
        this.day = day;
        lastEntryIndex = lastIndex;
        instanceData = InstanceData;
    }

    public InstanceDataDay(int instanceSize, String Day)
    {
        this.instanceData = new InstanceData[instanceSize];
        day = Day;
    }

    public InstanceDataDay()
    {
    }

    public String getDay()
    {
        return day;
    }

    public void setDay(String day)
    {
        this.day = day;
    }

    public void addDerivative(InstanceData bar)
    {
        lastEntryIndex++;
        if (lastEntryIndex < instanceData.length)
        {
            instanceData[lastEntryIndex] = bar;
        }
        else
        {
            InstanceData[] tempInst = new InstanceData[instanceData.length + 1];
            int pos = 0;
            for (InstanceData temp : instanceData)
            {
                tempInst[pos] = temp;
                pos++;
            }
            tempInst[pos] = bar;
            instanceData = tempInst;

        }
    }

    public void trim()
    {
        InstanceData[] array = new InstanceData[lastEntryIndex + 1];
        for (int i = 0; i < array.length; i++)
        {
            array[i] = instanceData[i];
        }
        instanceData = array;
    }

    public InstanceData[] getInstanceData()
    {
        return instanceData;
    }

    public void setDerivatives(InstanceData[] derivatives)
    {
        this.instanceData = derivatives;
        for (int i = 0; i < derivatives.length; i++)
        {
            lastEntryIndex++;
            if (derivatives[i] == null)
            {
                break;
            }
        }
    }

    public int getLastEntryIndex()
    {
        return lastEntryIndex;
    }

    public void setLastEntryIndex(int lastEntryIndex)
    {
        this.lastEntryIndex = lastEntryIndex;
    }

    public void addToInstanceData(InstanceData instanceData1)
    {
        if ((lastEntryIndex + 1) >= this.instanceData.length)
        {
            InstanceData[] temp = new InstanceData[this.instanceData.length + 1];
            int i = 0;

            for (InstanceData d : temp)
            {
                temp[i] = d;
                i++;
            }
            temp[i + 1] = instanceData1;
            lastEntryIndex = i++;
            instanceData = temp;

        }
        else
        {
            lastEntryIndex++;
            instanceData[lastEntryIndex] = instanceData1;
        }

    }

}
