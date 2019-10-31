/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algo;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;

public class DerivativeCompareByTimeImpl implements Comparator<Derivative>
{

    @Override
    public int compare(Derivative d1, Derivative d2)
    {

        Calendar time1 = Calendar.getInstance();
        String[] date1 = d1.getTime().split(" ");
        String[] dateFirst1 = {date1[0].substring(0, 4),date1[0].substring(4, 6),date1[0].substring(6, 8)};
        String[] dateSecond1 = date1[1].split(":");
        time1.setTime(new Date(Integer.parseInt(dateFirst1[0]), Integer.parseInt(dateFirst1[1]), Integer.parseInt(dateFirst1[2]), Integer.parseInt(dateSecond1[0]), Integer.parseInt(dateSecond1[1]), Integer.parseInt(dateSecond1[2])));
        Calendar time2 = Calendar.getInstance();
        String[] date2 = d2.getTime().split(" ");
        String[] dateFirst2 = {date2[0].substring(0, 4),date2[0].substring(4, 6),date2[0].substring(6, 8)};
        String[] dateSecond2 = date2[1].split(":");
        time2.setTime(new Date(Integer.parseInt(dateFirst2[0]), Integer.parseInt(dateFirst2[1]), Integer.parseInt(dateFirst2[2]), Integer.parseInt(dateSecond2[0]), Integer.parseInt(dateSecond2[1]), Integer.parseInt(dateSecond2[2])));
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");

        Algo.debugNum++;

        if (Integer.parseInt(String.valueOf(time1.get(Calendar.YEAR))) < Integer.parseInt(String.valueOf(time2.get(Calendar.YEAR))))
        {
            return -1;
        }
        else if (Integer.parseInt(String.valueOf(time1.get(Calendar.YEAR))) > Integer.parseInt(String.valueOf(time2.get(Calendar.YEAR))))
        {
            return 1;
        }
        else if (Integer.parseInt(String.valueOf(time1.get(Calendar.MONTH))) < Integer.parseInt(String.valueOf(time2.get(Calendar.MONTH))))
        {
            return -1;
        }
        else if (Integer.parseInt(String.valueOf(time1.get(Calendar.MONTH))) > Integer.parseInt(String.valueOf(time2.get(Calendar.MONTH))))
        {
            return 1;
        }
        else if (Integer.parseInt(String.valueOf(time1.get(Calendar.DAY_OF_MONTH))) < Integer.parseInt(String.valueOf(time2.get(Calendar.DAY_OF_MONTH))))
        {
            return -1;
        }
        else if (Integer.parseInt(String.valueOf(time1.get(Calendar.DAY_OF_MONTH))) > Integer.parseInt(String.valueOf(time2.get(Calendar.DAY_OF_MONTH))))
        {
            return 1;
        }
        else if (Integer.parseInt(String.valueOf(time1.get(Calendar.HOUR_OF_DAY))) < Integer.parseInt(String.valueOf(time2.get(Calendar.HOUR_OF_DAY))))
        {
            return -1;
        }
        else if (Integer.parseInt(String.valueOf(time1.get(Calendar.HOUR_OF_DAY))) > Integer.parseInt(String.valueOf(time2.get(Calendar.HOUR_OF_DAY))))
        {
            return 1;
        }
        else if (Integer.parseInt(String.valueOf(time1.get(Calendar.MINUTE))) < Integer.parseInt(String.valueOf(time2.get(Calendar.MINUTE))))
        {
            return -1;
        }
        else if (Integer.parseInt(String.valueOf(time1.get(Calendar.MINUTE))) > Integer.parseInt(String.valueOf(time2.get(Calendar.MINUTE))))
        {
            return 1;
        }
        else if (Integer.parseInt(String.valueOf(time1.get(Calendar.SECOND))) < Integer.parseInt(String.valueOf(time2.get(Calendar.SECOND))))
        {
            return -1;
        }
        else if (Integer.parseInt(String.valueOf(time1.get(Calendar.SECOND))) > Integer.parseInt(String.valueOf(time2.get(Calendar.SECOND))))
        {
            return 1;
        }
        else if ((Integer.parseInt(String.valueOf(time1.get(Calendar.YEAR))) == Integer.parseInt(String.valueOf(time2.get(Calendar.YEAR)))) && (Integer.parseInt(String.valueOf(time1.get(Calendar.MONTH))) == Integer.parseInt(String.valueOf(time2.get(Calendar.MONTH)))) && (Integer.parseInt(String.valueOf(time1.get(Calendar.DAY_OF_MONTH))) == Integer.parseInt(String.valueOf(time2.get(Calendar.DAY_OF_MONTH)))) && (Integer.parseInt(String.valueOf(time1.get(Calendar.HOUR_OF_DAY))) == Integer.parseInt(String.valueOf(time2.get(Calendar.HOUR_OF_DAY)))) && (Integer.parseInt(String.valueOf(time1.get(Calendar.MINUTE))) == Integer.parseInt(String.valueOf(time2.get(Calendar.MINUTE))) && (Integer.parseInt(String.valueOf(time1.get(Calendar.SECOND))) == Integer.parseInt(String.valueOf(time2.get(Calendar.SECOND))))))
        {
            return 0;
        }
        else
        {
            return 1;
        }

    }

}
