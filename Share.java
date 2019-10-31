/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algo;

import java.util.ArrayList;

/**
 *
 * @author Sticky
 */
public class Share
{
    private String symbol;
    ArrayList<InstanceData> instanceData;

    public Share(String symbol, ArrayList<InstanceData> instanceData)
    {
        this.symbol = symbol;
        this.instanceData = instanceData;
    }

    public String getSymbol()
    {
        return symbol;
    }
    
    public int getNumInstances()
    {
        return instanceData.size();
    }

    public void setSymbol(String symbol)
    {
        this.symbol = symbol;
    }

    public Share()
    {
        this.instanceData = new ArrayList<InstanceData>();
    }

    public Share(String symbol)
    {
        this.symbol = symbol;
        this.instanceData = new ArrayList<InstanceData>();
    }
    
    public void addInstance(InstanceData instance)
    {
        instanceData.add(instance);
        
    }

}
