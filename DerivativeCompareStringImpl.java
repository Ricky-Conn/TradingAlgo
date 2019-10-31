/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algo;

import java.util.Comparator;

public class DerivativeCompareStringImpl implements Comparator<Derivative>
{

    @Override
    public int compare(Derivative p1, Derivative p2) {
        return p1.getTime().compareTo(p2.getTime());
    }  

}
