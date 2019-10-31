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
public class WaveIndicator
{

    private Derivative[] values;
    private String[] tags;
    private int lastInsertIndex = -1;

    public WaveIndicator(Derivative[] values, String[] tags)
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
            }
        }
        else
        {
            System.out.println("Lengths tags and value differ");
        }
        this.values = values;
        this.tags = tags;
    }

    public WaveIndicator(String[] tags, Derivative[] values)
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
            }
        }
        else
        {
            System.out.println("Lengths tags and value differ");
        }
        this.values = values;
        this.tags = tags;
    }

    public WaveIndicator(int size)
    {
        this.values = new Derivative[size];
        this.tags = new String[size];
    }

    public WaveIndicator getElement(int pos)
    {
        Derivative[] tempD = new Derivative[1];
        tempD[0] = values[pos];
        String[] tempS = new String[1];
        tempS[0] = tags[pos];
        WaveIndicator temp = new WaveIndicator(tempD, tempS);
        return temp;
    }

    public WaveIndicator getLastElement()
    {
        return getElement(lastInsertIndex);
    }

    public void addTags(Derivative der, String tag)
    {
        lastInsertIndex++;
        if (values == null)
        {
            values = new Derivative[1];
            values[0] = der;
            tags = new String[1];
            tags[0] = tag;
        }
        else if (lastInsertIndex < values.length)
        {
            values[lastInsertIndex] = der;
            tags[lastInsertIndex] = tag;
        }
        else
        {
            Derivative[] tempValD = new Derivative[values.length + 1];
            int pos = 0;
            for (Derivative temp : values)
            {
                tempValD[pos] = temp;
                pos++;
            }
            String[] tempTagsS = new String[values.length + 1];
            pos = 0;
            for (String temp : tags)
            {
                tempTagsS[pos] = temp;
                pos++;
            }
            tempValD[pos] = der;
            tempTagsS[pos] = tag;
            values = tempValD;
            tags = tempTagsS;
        }
    }

}
