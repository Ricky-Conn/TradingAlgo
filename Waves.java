/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algo;

import java.util.ArrayList;

/**
 *
 * @author Rael's PC
 */
public class Waves
{

    private String wavesDetails = "";
    private WavesPerShare[] wavesPerShare;

    public Waves(ArrayList<InstanceDataPerShare> instanceDataPerSharePerShare)//sets the size of wavesPerShare and initilised all child objects and elements
    {
        this.wavesPerShare = new WavesPerShare[instanceDataPerSharePerShare.size()];
        for (int i = 0; i < instanceDataPerSharePerShare.size(); i++)
        {
            this.wavesPerShare[i] = new WavesPerShare(instanceDataPerSharePerShare.get(i).getSymbol());
        }
    }

    public Waves()
    {

    }

    public String getWavesDetails()
    {
        return wavesDetails;
    }

    public void setWavesDetails(String wavesDetails)
    {
        this.wavesDetails = wavesDetails;
    }

    public WavesPerShare[] getWavesPerShare()
    {
        return wavesPerShare;
    }

    public void setWavesPerShare(WavesPerShare[] wavesPerShare)
    {
        this.wavesPerShare = wavesPerShare;
    }

}
