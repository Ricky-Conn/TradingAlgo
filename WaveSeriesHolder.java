/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algo;

import java.util.Dictionary;
import java.util.Hashtable;

/**
 *
 * @author Adie
 */
public class WaveSeriesHolder
{

    //private Derivative[] values;
    private String[] tagOrder;
    private int lastInsertIndex = -1;
    private Dictionary<String, Derivative> dictionary = new Hashtable<String, Derivative>();

    public WaveSeriesHolder(Derivative[] values, String[] tags)
    {
        if (tags.length == values.length)
        {
            for (String tag : tags)
            {
                if (tag == null)
                {
                    break;
                }
                lastInsertIndex++;
                dictionary.put(tag, values[lastInsertIndex]);
            }
        }
        else
        {
            System.out.println("Lengths tags and value differ");
        }
        this.tagOrder = tags;
    }

    public WaveSeriesHolder(String[] tags, Derivative[] values)
    {
        if (tags.length == values.length)
        {
            for (String tag : tags)
            {
                if (tag == null)
                {
                    break;
                }
                lastInsertIndex++;
                dictionary.put(tag, values[lastInsertIndex]);
            }
        }
        else
        {
            System.out.println("Lengths tags and value differ");
        }
        this.tagOrder = tags;
    }

    public WaveSeriesHolder(int size)
    {
        this.tagOrder = new String[size];
    }

    public Derivative getElement(int pos)
    {
        return dictionary.get(tagOrder[pos]);
    }

    public Derivative getLastElement()
    {
        return getElement(lastInsertIndex);
    }

    public String[] getTagOrder()
    {
        return tagOrder;
    }

    public void setTagOrder(String[] tagOrder)
    {
        this.tagOrder = tagOrder;
    }

    public Dictionary<String, Derivative> getDictionary()
    {
        return dictionary;
    }

    public void setDictionary(Dictionary<String, Derivative> dictionary)
    {
        this.dictionary = dictionary;
    }

    public Derivative getElement(String tag)
    {
        return dictionary.get(tag);
    }

    public String getDerivativeTimeByTag(String tag)
    {
        return dictionary.get(tag).getTime();
    }

    public Double getDerivativeValueByTag(String tag)
    {
        return dictionary.get(tag).getDerivative();
    }

    /*
    Gets the name ("tag") of the next wave
    */
    public String getNextWaveInSeries()
    {
        for (String tag : tagOrder)
        {
            if(dictionary.get(tag) == null)
            {
                return tag;
            }
        }
        System.out.println("End of wave series");
        return null;
    }

    public void addToDictionary(Derivative der, String tag)
    {
        dictionary.put(tag, der);
        lastInsertIndex++;
        if (lastInsertIndex < tagOrder.length)
        {
            tagOrder[lastInsertIndex] = tag;
        }
        else
        {
            String[] tempDers = new String[tagOrder.length + 1];
            int pos = 0;
            for (String temp : tagOrder)
            {
                tempDers[pos] = temp;
                pos++;
            }
            tempDers[pos] = tag;
            tagOrder = tempDers;

        }
    }

    public void addToDictionary(String tag, Derivative der)
    {
        dictionary.put(tag, der);
        lastInsertIndex++;
        if (lastInsertIndex < tagOrder.length)
        {
            tagOrder[lastInsertIndex] = tag;
        }
        else
        {
            String[] tempDers = new String[tagOrder.length + 1];
            int pos = 0;
            for (String temp : tagOrder)
            {
                tempDers[pos] = temp;
                pos++;
            }
            tempDers[pos] = tag;
            tagOrder = tempDers;

        }
    }

}
