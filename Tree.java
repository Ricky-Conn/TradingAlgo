/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algo;

public class Tree
{
    private PopOutsPerProfitableOpportunity popOutsPerProfitableOpportunity;
    private Tree[] tree;

    public Tree(Tree[] tree)
    {
        this.tree = tree.clone();
    }

    public Tree(PopOutsPerProfitableOpportunity popOutsPerProfitableOpportunity, Tree[] tree)
    {
        this.popOutsPerProfitableOpportunity = popOutsPerProfitableOpportunity;
        this.tree = tree;
    }

    
    
    public Tree(PopOutsPerProfitableOpportunity popOutsPerProfitableOpportunity)
    {
        this.popOutsPerProfitableOpportunity = popOutsPerProfitableOpportunity;
    }

    
    
    public PopOutsPerProfitableOpportunity getPopOutsPerProfitableOpportunity()
    {
        return popOutsPerProfitableOpportunity;
    }

    public void setPopOutsPerProfitableOpportunity(PopOutsPerProfitableOpportunity popOutsPerProfitableOpportunity)
    {
        this.popOutsPerProfitableOpportunity = popOutsPerProfitableOpportunity;
    }

    public Tree[] getTree()
    {
        return tree;
    }

    public void setTree(Tree[] tree)
    {
        this.tree = tree;
    }
    
    
    
}
