/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algo;

import java.util.Comparator;

public class DerivativeCompareImpl implements Comparator<Derivative>
{

    @Override
    public int compare(Derivative p1, Derivative p2) {
        if (p1.getDerivative()< p2.getDerivative()) return -1;
        if (p1.getDerivative()> p2.getDerivative()) return 1;
        return 0;
    }  

}
