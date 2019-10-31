/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algo;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.security.Key;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Arrays;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.logging.Level;
import java.util.logging.Logger;
import sun.java2d.pipe.OutlineTextRenderer;

//create method to find if there are gaps in the data
public class Algo
{

    Scanner c = new Scanner(System.in);
    boolean shareInitialised = false;
    Share[] shares;
    double high = 0;
    double low = 0;
    double open = 0;
    static int debugNum = 0;
    double requiredProfitPerDay = 50; //*100 = % per day
    static int shortPeriod = 6;//EMA
    static int mediumPeriod = 12;
    static int longPeriod = 18;
    static int numLinesPerFile = 24000;

    ArrayList<Indicator> indicators = new ArrayList<>();
    MappedOutliersPerShare[] mappedOutliersPerShare;
    ArrayList<ProfitableOpportunity> profitableDownwardOpportunitiesArrayList = new ArrayList<>(); //profitable movement in Euro/Dollar pair
    ArrayList<ProfitableOpportunity> profitableUpwardOpportunitiesArrayList = new ArrayList<>();
    ArrayList<Share> elements = new ArrayList<>(); //initial read unprocessed elements
    ArrayList<Derivative[]> derivativesArrayList = new ArrayList<>();
    ArrayList<Derivative[]> closesArrayList = new ArrayList<>();
    ArrayList<InstanceDataPerShare> instanceDataPerShareDerivatives = new ArrayList<>();
    ArrayList<InstanceDataPerShare> instanceDataPerShare = new ArrayList<>();
    ArrayList<ArrayList<DerivativesPerShare>> waveLevelsOfDetail = new ArrayList<>();
    ArrayList<Waves> wavesPerShareArrayList = new ArrayList<>();
    ArrayList<DerivativesPerShare> closePerShareArrayList = new ArrayList<>();
    ArrayList<DerivativesPerShare> derivativesPerShareArrayList = new ArrayList<>();
    //ArrayList<PopOutsPerProfitableOpportunity> popOutsArrayList = new ArrayList<>();
    ArrayList<PopOutsPerProfitableOpportunity> largestIntersections = new ArrayList<>();// all the largest intersecting pairs for each Profitable Opportunity
    ArrayList<LargestIntersection> intersections = new ArrayList<>();
    ArrayList<LargestIntersection[]> intersectionWaterfall = new ArrayList();
    ArrayList<ArrayList<LargestIntersection[]>> intersectionWaterfallArray = new ArrayList<>();
    ArrayList<Strategy> strategySubsets = new ArrayList<>();//contains a list of all strategies organised into subsets and supersets
    ArrayList<Strategy> strategies = new ArrayList<>();//contains a list of all strategies
    DerivativesPerShare[] topOutliers;
    DerivativesPerShare[] bottomOutliers;
    ArrayList<DerivativesPerShare> bottomOutliersArrayList = new ArrayList<>();
    ArrayList<DerivativesPerShare> topOutliersArrayList = new ArrayList<>();

    /**
     * Looks through the intersections ArrayList and identify the combination of
     * elements that would indicate that the EUR/USD pair should be bought or
     * sold. The program should then look through the top and bottom outlier
     * derivatives arrays to find the same combination of elements; whenever the
     * matching combination of elements arise the program should invest in the
     * EUR/USD pair and calculate overall profit
     */
    public void backtest()
    {

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        ArrayList<String> timesFiltered = new ArrayList<>();//array of times checked

        for (Strategy strategy : strategySubsets)
        {
            int confirmed = 0; //number of confirmed elements in strategy
            for (MappedOutliersPerShare outlierPerShare : mappedOutliersPerShare)//look through elements
            {
                if (strategy.directionOfContainedSymbol(outlierPerShare.getSymbol()).contains("top"))
                {
                    for (int i = 0; i < outlierPerShare.topOutliersDayMap.size(); i++)
                    {

                    }
                }
                else if (strategy.directionOfContainedSymbol(outlierPerShare.getSymbol()).contains("bottom"))
                {
                    for (int i = 0; i < outlierPerShare.bottomOutliersDayMap.size(); i++)
                    {

                    }
                }
            }
        }

        int upwardOpportunityIndex = 0;
        int matchCount = 0;

        System.out.println("Top Outliers & Upward Profitable Opportunities");

        topOutlierLoop:
        for (int topOutlierIndex = 0; topOutlierIndex < topOutliers.length; topOutlierIndex++)//iterating through top outliers
        {
            DerivativesPerShare topOutlier = topOutliers[topOutlierIndex];
            upwardOpportunityIndex = 0;
            Day topOutlierDay = topOutlier.getNextDay("raw", "TopOutliers");
            day:
            while (topOutlierDay != null)
            {
                derivative:
                for (int outlierDayIndex = 0; outlierDayIndex < topOutlierDay.getDerivatives().length; outlierDayIndex++)
                {
                    Derivative topOutlierDerivative = topOutlierDay.getDerivatives()[outlierDayIndex];
                }
                topOutlierDay = topOutlier.getNextDay("raw", "TopOutliers");
            }
        }

        System.out.println("Bottom Outliers & Upward Profitable Opportunities");

        for (int bottomOutlierIndex = 0; bottomOutlierIndex < bottomOutliers.length; bottomOutlierIndex++)//iterating through bottom outliers
        {
            DerivativesPerShare bottomOutlier = bottomOutliers[bottomOutlierIndex];
            upwardOpportunityIndex = 0;
            Day bottomOutlierDay = bottomOutlier.getNextDay("raw", "BottomOutliers");
            day:
            while (bottomOutlierDay != null)
            {
                derivative:
                for (int outlierDayIndex = 0; outlierDayIndex < bottomOutlierDay.getDerivatives().length; outlierDayIndex++)
                {
                    Derivative bottomOutlierDerivative = bottomOutlierDay.getDerivatives()[outlierDayIndex];

                }
                bottomOutlierDay = bottomOutlier.getNextDay("raw", "BottomOutliers");
            }
        }

        int downwardOpportunityIndex = 0;
        matchCount = 0;

        System.out.println("Top Outliers & Downward Profitable Opportunities");

        topOutlierLoop:
        for (int topOutlierIndex = 0; topOutlierIndex < topOutliers.length; topOutlierIndex++)//iterating through top outliers
        {
            DerivativesPerShare topOutlier = topOutliers[topOutlierIndex];
            downwardOpportunityIndex = 0;
            Day topOutlierDay = topOutlier.getNextDay("raw", "TopOutliers");
            day:
            while (topOutlierDay != null)
            {
                derivative:
                for (int outlierDayIndex = 0; outlierDayIndex < topOutlierDay.getDerivatives().length; outlierDayIndex++)
                {
                    Derivative topOutlierDerivative = topOutlierDay.getDerivatives()[outlierDayIndex];
                }
                topOutlierDay = topOutlier.getNextDay("raw", "TopOutliers");
            }
        }

        System.out.println("Bottom Outliers & Downward Profitable Opportunities");

        for (int bottomOutlierIndex = 0; bottomOutlierIndex < bottomOutliers.length; bottomOutlierIndex++)//iterating through bottom outliers
        {
            DerivativesPerShare bottomOutlier = bottomOutliers[bottomOutlierIndex];
            downwardOpportunityIndex = 0;
            Day bottomOutlierDay = bottomOutlier.getNextDay("raw", "BottomOutliers");
            day:
            while (bottomOutlierDay != null)
            {
                derivative:
                for (int outlierDayIndex = 0; outlierDayIndex < bottomOutlierDay.getDerivatives().length; outlierDayIndex++)
                {
                    Derivative bottomOutlierDerivative = bottomOutlierDay.getDerivatives()[outlierDayIndex];
                }
                bottomOutlierDay = bottomOutlier.getNextDay("raw", "BottomOutliers");
            }
        }

        ArrayList<PopOutsPerProfitableOpportunity> tempPopOutsArrayList = new ArrayList<>();
        for (PopOutsPerProfitableOpportunity popOutTemp : popOutsArrayList) //trim all popOuts and add back all PopOutsPerProfitableOpportunity with at least 2 popouts
        {
            for (int i = 0; i < popOutTemp.getBottomPopouts().length; i++)
            {
                if (popOutTemp.getBottomPopouts()[i] == null)
                {
                    popOutTemp.setBottomPopouts(Arrays.copyOfRange(popOutTemp.getBottomPopouts(), 0, i));
                    break;
                }
            }
            for (int i = 0; i < popOutTemp.getTopPopouts().length; i++)
            {
                if (popOutTemp.getTopPopouts()[i] == null)
                {
                    popOutTemp.setTopPopouts(Arrays.copyOfRange(popOutTemp.getTopPopouts(), 0, i));
                    break;
                }
            }
            if ((popOutTemp.getBottomPopouts().length + popOutTemp.getTopPopouts().length) > 1)
            {
                tempPopOutsArrayList.add(popOutTemp);
            }
        }
        tempPopOutsArrayList.trimToSize();
        System.out.println("check");
        //popOutsArrayList = tempPopOutsArrayList;
    }

    public void calculateMovingAverageRelatedIndicators(int period)
    {
        double sum = 0.0;

        for (int i = 0; i < instanceDataPerShareDerivatives.size(); i++)
        {
            InstanceDataPerShare instanceDataShare = new InstanceDataPerShare(instanceDataPerShareDerivatives.get(i));

            Indicator movingAverageIndicator = new Indicator("Moving Average", instanceDataShare.getSymbol(), instanceDataPerShareDerivatives);
            Indicator STDIndicator = new Indicator("STD Indicator", instanceDataShare.getSymbol(), instanceDataPerShareDerivatives);
            Indicator UpperBollingerIndicator1 = new Indicator("Upper BollingerBand 1", instanceDataShare.getSymbol(), instanceDataPerShareDerivatives);
            Indicator UpperBollingerIndicator2 = new Indicator("Upper BollingerBand 2", instanceDataShare.getSymbol(), instanceDataPerShareDerivatives);
            Indicator LowerBollingerIndicator1 = new Indicator("Lower BollingerBand 1", instanceDataShare.getSymbol(), instanceDataPerShareDerivatives);
            Indicator LowerBollingerIndicator2 = new Indicator("Lower BollingerBand 2", instanceDataShare.getSymbol(), instanceDataPerShareDerivatives);

            if (i == 0)
            {
                indicators.add(movingAverageIndicator);
                indicators.add(STDIndicator);
                indicators.add(UpperBollingerIndicator1);
                indicators.add(UpperBollingerIndicator2);
                indicators.add(LowerBollingerIndicator1);
                indicators.add(LowerBollingerIndicator2);
            }

            InstanceDataDay day = instanceDataShare.getNextDay();
            day:
            while (day != null)
            {
                sum = 0;
                for (int k = 0; k < day.getInstanceData().length; k++)
                {
                    InstanceData instanceData = day.getInstanceData()[k];

                    sum += instanceData.getClose();

                    if (k >= period - 1)
                    {
                        if (k != period - 1)
                        {
                            sum -= day.getInstanceData()[k - period].getClose();
                        }
                        if (movingAverageIndicator.getOut() != null)
                        {
                            DerivativesPerShare name = movingAverageIndicator.getDerivativesPerShare()[i];
                            if (name == null)
                            {
                                continue;
                            }
                            movingAverageIndicator.addDerivative(new Derivative((sum / period), instanceData.getTimestamp()), instanceDataShare.getSymbol());
                        }

                        DerivativesPerShare name = movingAverageIndicator.getDerivativesPerShare()[i];
                        if (name == null)
                        {
                            continue;
                        }
                        movingAverageIndicator.addDerivative(new Derivative((sum / period), instanceData.getTimestamp()), instanceDataShare.getSymbol());

                        double stdSquared = 0.0;
                        for (int l = period - 1; l > -1; l--)
                        {
                            stdSquared += Math.pow((day.getInstanceData()[k - l].getClose() - (sum / period)), 2);
                        }
                        stdSquared /= period - 1;
                        double std = Math.sqrt(stdSquared);

                        if (STDIndicator.getOut() != null)
                        {
                            STDIndicator.addDerivative(new Derivative(std, instanceData.getTimestamp()), instanceDataShare.getSymbol());
                        }

                        if (UpperBollingerIndicator1.getOut() != null)
                        {
                            if ((sum / period) + (1.5 * std) < instanceData.getClose())
                            {
                                UpperBollingerIndicator1.addDerivative(new Derivative((sum / period) + (1.5 * std), instanceData.getTimestamp()), instanceDataShare.getSymbol());
                            }
                        }

                        if (UpperBollingerIndicator2.getOut() != null)
                        {
                            if ((sum / period) + (1.7 * std) < instanceData.getClose())
                            {
                                UpperBollingerIndicator2.addDerivative(new Derivative((sum / period) + (2 * std), instanceData.getTimestamp()), instanceDataShare.getSymbol());
                            }
                        }

                        if (LowerBollingerIndicator1.getOut() != null)
                        {
                            if ((sum / period) - (1.5 * std) > instanceData.getClose())
                            {
                                LowerBollingerIndicator1.addDerivative(new Derivative((sum / period) - (1.5 * std), instanceData.getTimestamp()), instanceDataShare.getSymbol());
                            }
                        }

                        if (LowerBollingerIndicator2.getOut() != null)
                        {
                            if ((sum / period) - (1.7 * std) > instanceData.getClose())
                            {
                                LowerBollingerIndicator2.addDerivative(new Derivative((sum / period) - (2 * std), instanceData.getTimestamp()), instanceDataShare.getSymbol());
                            }
                        }

                    }
                    else
                    {
                        if (movingAverageIndicator.getOut() != null)
                        {
                            movingAverageIndicator.addDerivative(new Derivative(0.0, instanceData.getTimestamp()), instanceDataShare.getSymbol());
                        }
                        if (STDIndicator.getOut() != null)
                        {
                            STDIndicator.addDerivative(new Derivative(0.0, instanceData.getTimestamp()), instanceDataShare.getSymbol());
                        }
                        if (UpperBollingerIndicator1.getOut() != null)
                        {
                            UpperBollingerIndicator1.addDerivative(new Derivative(0.0, instanceData.getTimestamp()), instanceDataShare.getSymbol());
                        }
                        if (UpperBollingerIndicator2.getOut() != null)
                        {
                            UpperBollingerIndicator2.addDerivative(new Derivative(0.0, instanceData.getTimestamp()), instanceDataShare.getSymbol());
                        }
                        if (LowerBollingerIndicator1.getOut() != null)
                        {
                            LowerBollingerIndicator1.addDerivative(new Derivative(0.0, instanceData.getTimestamp()), instanceDataShare.getSymbol());
                        }
                        if (LowerBollingerIndicator2.getOut() != null)
                        {
                            LowerBollingerIndicator2.addDerivative(new Derivative(0.0, instanceData.getTimestamp()), instanceDataShare.getSymbol());
                        }
                    }
                }
                day = instanceDataShare.getNextDay();
            }
        }
    }

    public void calculateRSI(int EMAPeriod)
    {
        double upwardEMA = 0.0;
        double downwardEMA = 0.0;
        Day[] days;

        for (int i = 0; i < instanceDataPerShareDerivatives.size(); i++)
        {
            InstanceDataPerShare instanceDataShare = new InstanceDataPerShare(instanceDataPerShareDerivatives.get(i));
            Indicator RSIBelowIndicator = new Indicator("RSIBelow30", instanceDataShare.getSymbol(), instanceDataPerShareDerivatives);
            Indicator RSIAboveIndicator = new Indicator("RSIAbove70", instanceDataShare.getSymbol(), instanceDataPerShareDerivatives);
            if (i == 0)
            {
                indicators.add(RSIBelowIndicator);
                indicators.add(RSIAboveIndicator);
            }
            if (RSIBelowIndicator.getOut() == null && RSIAboveIndicator.getOut() == null)
            {
                continue;
            }
            InstanceDataDay day = instanceDataShare.getNextDay();
            day:
            while (day != null)
            {
                upwardEMA = 0.0;
                downwardEMA = 0.0;
                for (int k = 0; k < day.getInstanceData().length; k++)
                {
                    InstanceData instanceData = day.getInstanceData()[k];

                    double upwardMovement = 0.0;
                    double downwardMovement = 0.0;

                    if (k > 0)
                    {
                        if (instanceData.getClose() - day.getInstanceData()[k - 1].getClose() > 0)
                        {
                            upwardMovement = instanceData.getClose() - day.getInstanceData()[k - 1].getClose();
                        }
                        else if (day.getInstanceData()[k - 1].getClose() - instanceData.getClose() > 0)
                        {
                            downwardMovement = day.getInstanceData()[k - 1].getClose() - instanceData.getClose();
                        }
                    }
//                    else
//                    {
//                        RSIBelowIndicator.addDerivative(new Derivative(instanceData.getTimestamp()), instanceDataShare.getSymbol());
//                        continue;
//                    }

                    upwardEMA = (double) (1.0 / EMAPeriod) * upwardMovement + (double) ((EMAPeriod - 1.0) / EMAPeriod) * upwardEMA;
                    downwardEMA = (double) (1.0 / EMAPeriod) * downwardMovement + (double) ((EMAPeriod - 1.0) / EMAPeriod) * downwardEMA;

                    double rsi;
                    if (downwardEMA != 0)
                    {
                        double rs = upwardEMA / downwardEMA;
                        rsi = 100 - (100 / (1 + rs));
                    }
                    else
                    {
                        rsi = 100;
                    }

                    if (k >= EMAPeriod - 1 && rsi > 70)
                    {
                        RSIAboveIndicator.addDerivative(new Derivative(rsi, instanceData.getTimestamp()), instanceDataShare.getSymbol());
                    }
                    else if (k >= EMAPeriod - 1 && rsi < 30)
                    {
                        RSIBelowIndicator.addDerivative(new Derivative(rsi, instanceData.getTimestamp()), instanceDataShare.getSymbol());
                    }
//                    else
//                    {
//                        RSIIndicator.addDerivative(new Derivative(instanceData.getTimestamp()), instanceDataShare.getSymbol());
//                    }
                }
                day = instanceDataShare.getNextDay();
            }
        }
    }

    public void addPopoutToArray(PopOut popOutMatch, String indName, ProfitableOpportunity profitableOpportunityMatch, PopOutsPerProfitableOpportunity popOutPerProfitableOpportunity)//adds the popout (found using matching time) to its respective profitable opportunity in the array of popOutsArrayList (type PopOutsPerProfitableOpportunity)
    {
        if (popOutPerProfitableOpportunity.getProfitableOpportunity().getTime().equals(profitableOpportunityMatch.getTime()))
        {
            if (popOutMatch.isTopOutlier())
            {
                popOutPerProfitableOpportunity.addToTopPopouts(popOutMatch, indName);
                return;
            }
            else
            {
                popOutPerProfitableOpportunity.addToBottomPopouts(popOutMatch, indName);
                return;
            }
        }
    }

    public void identifyPopouts()
    {
        int upwardOpportunityIndex = 0;
        int matchCount = 0;

        //////////////////////////////////////////////////////
        System.out.println("Top Outliers & Upward Profitable Opportunities");

        boolean upwardOpportunity = true;
        topOutlierLoop:
        for (int instanceDataPerShareIndex = 0; instanceDataPerShareIndex < instanceDataPerShare.size(); instanceDataPerShareIndex++)//iterating through top outliers
        {
            Indicator profitableUpwardOpportunities = new Indicator("ProfitableUpwardOpportunities", instanceDataPerShare.get(instanceDataPerShareIndex).getSymbol(), instanceDataPerShare);
            PopOutsPerProfitableOpportunity temp = new PopOutsPerProfitableOpportunity(upwardOpportunity, instanceDataPerShare.get(instanceDataPerShareIndex).getSymbol());
            if (temp.getOut() == null)
            {
                continue;
            }
            upwardOpportunityLoop:
            if (profitableUpwardOpportunities.getF() != null)
            {
                Day profitableOpportunityDay = profitableUpwardOpportunities.getNextDay(instanceDataPerShareIndex, "ProfitableUpwardOpportunities");
                opportunityDay:
                while (profitableOpportunityDay != null)
                {
                    Map<String, Map<String, Double>> days = new HashMap<>();

                    for (Indicator indicator : indicators)
                    {
                        if (indicator.getF() != null)
                        {
                            int i = 0;
                            System.out.println(indicator.getIndicatorName());
                            Day currentDay = null;
                            while (profitableUpwardOpportunities.getDerivativesPerShare()[instanceDataPerShareIndex].getCurrentDay() == null || (currentDay == null && i == 0) || (currentDay != null && (t1RelativeToT2(currentDay.getDay() + " 00:00:00", profitableUpwardOpportunities.getDerivativesPerShare()[instanceDataPerShareIndex].getCurrentDay().getDay() + " 00:00:00", 0) < 0)))
                            {
                                currentDay = indicator.getNextDay(instanceDataPerShareIndex, indicator.getIndicatorName());
                                i++;
                            }
                            if (indicator.getDerivativesPerShare()[instanceDataPerShareIndex].getCurrentDay() != null && indicator.getDerivativesPerShare()[instanceDataPerShareIndex].getCurrentDay().getDay().split(" ")[0].equals(profitableUpwardOpportunities.getDerivativesPerShare()[instanceDataPerShareIndex].getCurrentDay().getDay().split(" ")[0]))
                            {
                                days.put(indicator.getDerivativesPerShare()[instanceDataPerShareIndex].getSymbol() + ";;" + indicator.getIndicatorName(), MappedOutliersPerShare.convertToOutliersMapShifted(currentDay, 1));
                            }
                        }
                    }

                    for (Derivative profitableOpportunityDer : profitableOpportunityDay.getDerivatives())
                    {
                        ProfitableOpportunity tempProfitableOpportunity = new ProfitableOpportunity(profitableOpportunityDer.getTime(), instanceDataPerShare.get(instanceDataPerShareIndex).getSymbol(), profitableOpportunityDer.getDerivative());

                        for (String key : days.keySet())
                        {
                            Map<String, Double> indicatorDay = days.get(key);
                            if (indicatorDay.get(profitableOpportunityDer.getTime()) == null)
                            {
                                continue;
                            }
                            Derivative indicatorDer = new Derivative(indicatorDay.get(profitableOpportunityDer.getTime()), profitableOpportunityDer.getTime());

                            PopOut popOut = new PopOut();
                            popOut.setSymbol(instanceDataPerShare.get(instanceDataPerShareIndex).getSymbol());
                            popOut.setDerivative(indicatorDer);
                            popOut.setTopOutlier(true);
                            if (temp.getOut() == null)
                            {
                                temp.InitialiseWritePopOutsPerProfitableOpportunity(popOut.getSymbol());
                            }
                            temp.setProfitableOpportunity(tempProfitableOpportunity);

                            addPopoutToArray(popOut, key, tempProfitableOpportunity, temp);
                        }
                        try
                        {
                            PopOutsPerProfitableOpportunity.out.write("////");
                            PopOutsPerProfitableOpportunity.out.newLine();
                        }
                        catch (IOException ex)
                        {
                            Logger.getLogger(Algo.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                    profitableOpportunityDay = profitableUpwardOpportunities.getNextDay(instanceDataPerShareIndex, "ProfitableUpwardOpportunities");
                }
            }
            ////////////////////////////////////////////////////////

            System.out.println("Bottom Outliers & Upward Profitable Opportunities");
            upwardOpportunity = false;
            for (instanceDataPerShareIndex = 0; instanceDataPerShareIndex < instanceDataPerShare.size(); instanceDataPerShareIndex++)//iterating through top outliers
            {
                profitableUpwardOpportunities = new Indicator("ProfitableUpwardOpportunities", instanceDataPerShare.get(instanceDataPerShareIndex).getSymbol(), instanceDataPerShare);
                temp = new PopOutsPerProfitableOpportunity(upwardOpportunity, instanceDataPerShare.get(instanceDataPerShareIndex).getSymbol());
                upwardOpportunityLoop:
                if (profitableUpwardOpportunities.getF() != null)
                {
                    Day profitableOpportunityDay = profitableUpwardOpportunities.getNextDay(instanceDataPerShareIndex, "ProfitableUpwardOpportunities");
                    opportunityDay:
                    while (profitableOpportunityDay != null)
                    {
                        Map<String, Map<String, Double>> days = new HashMap<>();

                        for (Indicator indicator : indicators)
                        {
                            if (indicator.getF() != null)
                            {
                                int i = 0;
                                System.out.println(indicator.getIndicatorName());
                                Day currentDay = null;
                                while (profitableUpwardOpportunities.getDerivativesPerShare()[instanceDataPerShareIndex].getCurrentDay() == null || (currentDay == null && i == 0) || (currentDay != null && (t1RelativeToT2(currentDay.getDay() + " 00:00:00", profitableUpwardOpportunities.getDerivativesPerShare()[instanceDataPerShareIndex].getCurrentDay().getDay() + " 00:00:00", 0) < 0)))
                                {
                                    currentDay = indicator.getNextDay(instanceDataPerShareIndex, indicator.getIndicatorName());
                                    i++;
                                }
                                if (indicator.getDerivativesPerShare()[instanceDataPerShareIndex].getCurrentDay() != null && indicator.getDerivativesPerShare()[instanceDataPerShareIndex].getCurrentDay().getDay().split(" ")[0].equals(profitableUpwardOpportunities.getDerivativesPerShare()[instanceDataPerShareIndex].getCurrentDay().getDay().split(" ")[0]))
                                {
                                    days.put(indicator.getDerivativesPerShare()[instanceDataPerShareIndex].getSymbol() + ";;" + indicator.getIndicatorName(), MappedOutliersPerShare.convertToOutliersMapShifted(currentDay, 1));
                                }
                            }
                        }

                        for (Derivative profitableOpportunityDer : profitableOpportunityDay.getDerivatives())
                        {
                            ProfitableOpportunity tempProfitableOpportunity = new ProfitableOpportunity(profitableOpportunityDer.getTime(), instanceDataPerShare.get(instanceDataPerShareIndex).getSymbol(), profitableOpportunityDer.getDerivative());

                            for (String key : days.keySet())
                            {
                                Map<String, Double> indicatorDay = days.get(key);
                                if (indicatorDay.get(profitableOpportunityDer.getTime()) == null)
                                {
                                    continue;
                                }
                                Derivative indicatorDer = new Derivative(indicatorDay.get(profitableOpportunityDer.getTime()), profitableOpportunityDer.getTime());

                                PopOut popOut = new PopOut();
                                popOut.setSymbol(instanceDataPerShare.get(instanceDataPerShareIndex).getSymbol());
                                popOut.setDerivative(indicatorDer);
                                popOut.setTopOutlier(true);
                                if (temp.getOut() == null)
                                {
                                    temp.InitialiseWritePopOutsPerProfitableOpportunity(popOut.getSymbol());
                                }
                                temp.setProfitableOpportunity(tempProfitableOpportunity);

                                addPopoutToArray(popOut, key, tempProfitableOpportunity, temp);
                            }
                            try
                            {
                                PopOutsPerProfitableOpportunity.out.write("////");
                                PopOutsPerProfitableOpportunity.out.newLine();
                            }
                            catch (IOException ex)
                            {
                                Logger.getLogger(Algo.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                        profitableOpportunityDay = profitableUpwardOpportunities.getNextDay(instanceDataPerShareIndex, "ProfitableUpwardOpportunities");
                    }
                }
                ////////////////////////////////////////////////////////
/*
                 int downwardOpportunityIndex = 0;
                 matchCount = 0;

                 System.out.println("Top Outliers & Downward Profitable Opportunities");

                 topOutlierLoop:
                 for (int instanceDataPerShareIndex = 0; instanceDataPerShareIndex < instanceDataPerShare.size(); instanceDataPerShareIndex++)//iterating through top outliers
                 {
                 Indicator topOutlier = new Indicator("TopOutliers", instanceDataPerShare.get(instanceDataPerShareIndex).getSymbol(), instanceDataPerShare);

                 Day topOutlierDay = topOutlier.getNextDay(instanceDataPerShareIndex, "TopOutliers");
                 day:
                 while (topOutlierDay != null)
                 {
                 topOutlierDay.sortByTime();
                 derivative:
                 for (int outlierDayIndex = 0; outlierDayIndex < topOutlierDay.getDerivatives().length; outlierDayIndex++)
                 {
                 Derivative topOutlierDerivative = topOutlierDay.getDerivatives()[outlierDayIndex];
                 downwardOpportunityLoop:
                 while (downwardOpportunityIndex < profitableDownwardOpportunities.length)
                 {
                 ProfitableOpportunity downwardOpportunity = profitableDownwardOpportunities[downwardOpportunityIndex];
                 int T1RelativeToT2 = t1RelativeToT2(downwardOpportunity.getTime(), topOutlierDerivative.getTime(), 1);
                 while (T1RelativeToT2 != 0)
                 {
                 if ((outlierDayIndex + 1) == topOutlierDay.getDerivatives().length)
                 {
                 //System.out.println("break day");
                 break derivative;
                 }

                 if (T1RelativeToT2 > 0)//profitable opportunity after outlier derivative
                 {
                 if ((outlierDayIndex + 1) < topOutlierDay.getDerivatives().length)
                 {
                 outlierDayIndex++;
                 topOutlierDerivative = topOutlierDay.getDerivatives()[outlierDayIndex];
                 }
                 else
                 {
                 System.out.println("break downward");
                 break downwardOpportunityLoop;
                 }
                 }

                 if (T1RelativeToT2 < 0)//profitable opportunity before outlier derivative
                 {
                 if ((downwardOpportunityIndex + 1) < profitableDownwardOpportunities.length)
                 {
                 downwardOpportunityIndex++;
                 downwardOpportunity = profitableDownwardOpportunities[downwardOpportunityIndex];
                 }
                 else
                 {
                 System.out.println("break topOut");
                 break day;
                 }
                 }
                 T1RelativeToT2 = t1RelativeToT2(downwardOpportunity.getTime(), topOutlierDerivative.getTime(), 1);
                 }

                 PopOut popOut = new PopOut();
                 popOut.setSymbol(instanceDataPerShare.get(instanceDataPerShareIndex).getSymbol());
                 popOut.setDerivative(topOutlierDerivative);
                 popOut.setTopOutlier(true);
                 addPopoutToArray(popOut, downwardOpportunity);
                 //popOutsArrayList.add(popOut);

                 downwardOpportunityIndex++;
                 }

                 }
                 topOutlierDay = topOutlier.getNextDay(instanceDataPerShareIndex, "TopOutliers");
                 }
                 }

                 System.out.println("Bottom Outliers & Downward Profitable Opportunities");

                 downwardOpportunityIndex = 0;

                 for (int instanceDataPerShareIndex = 0; instanceDataPerShareIndex < instanceDataPerShare.size(); instanceDataPerShareIndex++)//iterating through top outliers
                 {
                 bottomOutlier = new Indicator("BottomOutliers", instanceDataPerShare.get(instanceDataPerShareIndex).getSymbol(), instanceDataPerShare);

                 Day bottomOutlierDay = bottomOutlier.getNextDay(instanceDataPerShareIndex, "BottomOutliers");
                 day:
                 while (bottomOutlierDay != null)
                 {
                 bottomOutlierDay.sortByTime();
                 derivative:
                 for (int outlierDayIndex = 0; outlierDayIndex < bottomOutlierDay.getDerivatives().length; outlierDayIndex++)
                 {
                 Derivative bottomOutlierDerivative = bottomOutlierDay.getDerivatives()[outlierDayIndex];
                 downwardOpportunityLoop:
                 while (downwardOpportunityIndex < profitableDownwardOpportunities.length)
                 {
                 ProfitableOpportunity downwardOpportunity = profitableDownwardOpportunities[downwardOpportunityIndex];
                 int T1RelativeToT2 = t1RelativeToT2(downwardOpportunity.getTime(), bottomOutlierDerivative.getTime(), 1);
                 while (T1RelativeToT2 != 0)
                 {
                 if ((outlierDayIndex + 1) == bottomOutlierDay.getDerivatives().length)
                 {
                 //System.out.println("break day");
                 break derivative;
                 }

                 if (T1RelativeToT2 > 0)//profitable opportunity after outlier derivative
                 {
                 if ((outlierDayIndex + 1) < bottomOutlierDay.getDerivatives().length)
                 {
                 outlierDayIndex++;
                 bottomOutlierDerivative = bottomOutlierDay.getDerivatives()[outlierDayIndex];
                 }
                 else
                 {
                 System.out.println("break downward");
                 break downwardOpportunityLoop;
                 }
                 }

                 if (T1RelativeToT2 < 0)//profitable opportunity before outlier derivative
                 {
                 if ((downwardOpportunityIndex + 1) < profitableDownwardOpportunities.length)
                 {
                 downwardOpportunityIndex++;
                 downwardOpportunity = profitableDownwardOpportunities[downwardOpportunityIndex];
                 }
                 else
                 {
                 System.out.println("break bottomOut");
                 break day;
                 }
                 }
                 T1RelativeToT2 = t1RelativeToT2(downwardOpportunity.getTime(), bottomOutlierDerivative.getTime(), 1);
                 }

                 PopOut popOut = new PopOut();
                 popOut.setSymbol(instanceDataPerShare.get(instanceDataPerShareIndex).getSymbol());
                 popOut.setDerivative(bottomOutlierDerivative);
                 popOut.setTopOutlier(false);
                 addPopoutToArray(popOut, downwardOpportunity);
                 //popOutsArrayList.add(popOut);

                 downwardOpportunityIndex++;
                 }
                 }
                 bottomOutlierDay = bottomOutlier.getNextDay(instanceDataPerShareIndex, "BottomOutliers");
                 }
                 }

                 ArrayList<PopOutsPerProfitableOpportunity> tempPopOutsArrayList = new ArrayList<>();
                 for (PopOutsPerProfitableOpportunity popOutTemp : popOutsArrayList) //trim all popOuts and add back all PopOutsPerProfitableOpportunity with at least 2 popouts
                 {
                 for (int i = 0; i < popOutTemp.getBottomPopouts().length; i++)
                 {
                 if (popOutTemp.getBottomPopouts()[i] == null)
                 {
                 popOutTemp.setBottomPopouts(Arrays.copyOfRange(popOutTemp.getBottomPopouts(), 0, i));
                 break;
                 }
                 }
                 for (int i = 0; i < popOutTemp.getTopPopouts().length; i++)
                 {
                 if (popOutTemp.getTopPopouts()[i] == null)
                 {
                 popOutTemp.setTopPopouts(Arrays.copyOfRange(popOutTemp.getTopPopouts(), 0, i));
                 break;
                 }
                 }
                 if ((popOutTemp.getBottomPopouts().length + popOutTemp.getTopPopouts().length) > 1)
                 {
                 tempPopOutsArrayList.add(popOutTemp);
                 }
                 }
                 */
                //tempPopOutsArrayList.trimToSize();
                //popOutsArrayList = tempPopOutsArrayList;

            }

        }
    }

    public MappedOutliersPerShare createMapOfOutliers(Day y, String symbol)
    {
        MappedOutliersPerShare tempMappedOutliersPerShare = new MappedOutliersPerShare();
        int count = 0;
        for (DerivativesPerShare i : topOutliersArrayList)
        {
            if (!i.getSymbol().equalsIgnoreCase(symbol))
            {
                continue;
            }
            tempMappedOutliersPerShare = new MappedOutliersPerShare(i.getSymbol());
            for (Derivative d : y.getDerivatives())
            {
                tempMappedOutliersPerShare.addToTopOutliersMap(d.getTime(), d.getDerivative());
            }
            count++;
        }

        count = 0;
        for (DerivativesPerShare i : bottomOutliersArrayList)
        {
            if (!i.getSymbol().equalsIgnoreCase(tempMappedOutliersPerShare.getSymbol()))
            {
                continue;
            }
            for (Derivative d : y.getDerivatives())
            {
                tempMappedOutliersPerShare.addToBottomOutliersMap(d.getTime(), d.getDerivative());
            }
            count++;
        }

        return tempMappedOutliersPerShare;

    }

    public MappedOutliersPerShare[] cleanOutlierMaps(MappedOutliersPerShare[] mappedOutliersPerShare1)
    {
        for (MappedOutliersPerShare mapped1 : mappedOutliersPerShare1)
        {
            for (MappedOutliersPerShare mapped2 : mappedOutliersPerShare1)
            {
                if (mapped1 == mapped2)
                {
                    continue;
                }
                for (int i = 0; i < mapped2.getTopOutliersMap().size(); i++)
                {
                    String[] ks = (String[]) mapped2.getTopOutliersMap().keySet().toArray(new String[mapped2.getTopOutliersMap().keySet().size()]);
                    for (String key : ks)
                    {
                        if (!mapped1.getTopOutliersMap().containsKey(key))
                        {
                            mapped2.getTopOutliersMap().remove(key);
                        }
                    }
                }

                for (int i = 0; i < mapped2.getBottomOutliersMap().size(); i++)
                {
                    String[] ks = (String[]) mapped2.getBottomOutliersMap().keySet().toArray(new String[mapped2.getBottomOutliersMap().keySet().size()]);
                    for (String key : ks)
                    {
                        if (!mapped1.getBottomOutliersMap().containsKey(key))
                        {
                            mapped2.getBottomOutliersMap().remove(key);
                        }
                    }
                }
            }
        }
        return mappedOutliersPerShare1;
    }

    /*compares all profitable opportunities with popouts to find the largest intersection for 
     each profitable opportunity. The largest intersection for each popout is then added to the LargestIntersectionArray*/
    public void findLargestIntersect()
    {
        for (int i = 0; i < popOutsArrayList.size(); i++)//used to compare with all other profitable opportunities
        {
            PopOutsPerProfitableOpportunity profitableOpportunityFixed = popOutsArrayList.get(i);
            ArrayList<PopOutsPerProfitableOpportunity> equalSizedLargestIntersectionArray = new ArrayList<>();
            for (int j = 0; j < popOutsArrayList.size(); j++)
            {
                PopOutsPerProfitableOpportunity profitableOpportunityVariable = popOutsArrayList.get(j);
                if (profitableOpportunityFixed.intersect(profitableOpportunityVariable).hasSameNumPopoutsAs(profitableOpportunityFixed))
                {
                    continue;
                }
                else if (equalSizedLargestIntersectionArray.isEmpty())
                {
                    if (!profitableOpportunityFixed.intersect(profitableOpportunityVariable).isPopoutsEmpty())
                    {
                        equalSizedLargestIntersectionArray.add(profitableOpportunityFixed.intersect(profitableOpportunityVariable));
                    }
                }
                else if (profitableOpportunityFixed.intersect(profitableOpportunityVariable).hasMorePopoutsThan(equalSizedLargestIntersectionArray.get(0)))
                {
                    equalSizedLargestIntersectionArray = new ArrayList<>();
                    equalSizedLargestIntersectionArray.add(profitableOpportunityFixed.intersect(profitableOpportunityVariable));
                }
                else if (profitableOpportunityFixed.intersect(profitableOpportunityVariable).hasSameNumPopoutsAs(equalSizedLargestIntersectionArray.get(0)))
                {
                    equalSizedLargestIntersectionArray.add(profitableOpportunityFixed.intersect(profitableOpportunityVariable));
                }
            }
            ArrayList<PopOutsPerProfitableOpportunity> tempOccurances = new ArrayList<>();
            ArrayList<Integer> tempNumOccurances = new ArrayList<>();
            if (equalSizedLargestIntersectionArray.size() > 1)
            {
                for (PopOutsPerProfitableOpportunity largestIntersectItem : equalSizedLargestIntersectionArray)//check how many occurances there are to determine which largest intersect has the most occurances of the same variables
                {
                    int pos = -1;
                    for (int k = 0; k < tempNumOccurances.size(); k++)
                    {
                        PopOutsPerProfitableOpportunity tempOccurance = tempOccurances.get(k);
                        if (tempOccurance.intersect(largestIntersectItem).hasSameNumPopoutsAs(tempOccurance))
                        {
                            pos = k;
                            tempNumOccurances.set(k, (tempNumOccurances.get(k) + 1));
                        }
                    }
                    if (pos == -1)
                    {
                        tempOccurances.add(largestIntersectItem);
                        tempNumOccurances.add(1);
                    }
                }
                int biggest = 0;
                int posBiggest = -1;
                int count = 0;
                for (int occurances : tempNumOccurances)
                {
                    if (occurances > biggest)
                    {
                        posBiggest = count;
                        biggest = occurances;
                    }
                    count++;
                }

                largestIntersections.add(tempOccurances.get(posBiggest));
            }
            else if (equalSizedLargestIntersectionArray.size() == 1)
            {
                largestIntersections.add(equalSizedLargestIntersectionArray.get(0));
            }

        }
    }

    /*
     */
    public void findLargest()
    {
        ArrayList<LargestIntersection> equalSizedLargestIntersectionArray = new ArrayList<>();
        int largestPopouts = 0;

        intersectionWaterfall = new ArrayList<>();
        //intersections = equalSizedLargestIntersectionArray;
        LargestIntersection[] tempIntersections = new LargestIntersection[intersections.size()];
        for (int t = 0; t < intersections.size(); t++)
        {
            tempIntersections[t] = intersections.get(t);
        }

        for (LargestIntersection waterfallHead : tempIntersections)
        {
            ArrayList<LargestIntersection> tempHeadIntersection = new ArrayList<>();
            tempHeadIntersection.add(waterfallHead);
            LargestIntersection[] headIntersection = new LargestIntersection[1];
            headIntersection[0] = waterfallHead;
            ArrayList<LargestIntersection[]> tempWaterfall = new ArrayList<>();
            tempWaterfall.add(headIntersection);
            intersectionWaterfall(tempHeadIntersection, tempWaterfall);
            intersectionWaterfallArray.add(intersectionWaterfall(tempHeadIntersection, tempWaterfall));
        }
    }

    public void waterfall()
    {
        System.out.println("Find Largest Intersection");
        findLargestIntersect();//finds the largest intersection for each PopOutsPerProfitableOpportunity and adds them to a list of all largestIntersections
        System.out.println("Filter");
        filterIntersections(intersectionWaterfall);//identifies unique pairings of variables and the number of occurances
        System.out.println("Find largest only");
        findLargest();
    }

    public ArrayList<LargestIntersection[]> intersectionWaterfall(ArrayList<LargestIntersection> intersections, ArrayList<LargestIntersection[]> intersectionWaterfall)
    {
        do
        {
            intersections = new ArrayList<>();
            int posIW = 0;
            if (!intersectionWaterfall.isEmpty())
            {
                for (LargestIntersection li : intersectionWaterfall.get(intersectionWaterfall.size() - 1))
                {
                    intersections.add(li);
                    posIW++;
                }
            }
            largestIntersections = new ArrayList<>();
            for (LargestIntersection intersectionsFixed : intersections)//used to compare with all popouts per 
            {
                ArrayList<LargestIntersection> equalSizedLargestIntersectionArray = new ArrayList<>();
                for (PopOutsPerProfitableOpportunity popout : popOutsArrayList)
                {
                    if (intersectionsFixed.intersect(popout).hasSameNumPopoutsAs(intersectionsFixed.getPopOutsPerProfitableOpportunity()) || intersectionsFixed.intersect(popout).hasSameNumPopoutsAs(0)) // if fixed and variable intersection have the same popouts
                    {
                        continue;
                    }
                    else if (equalSizedLargestIntersectionArray.isEmpty()) //else if the equal largest intersection array is empty and if the equal largest intersection array's first element has no popoutsperShare
                    {
                        LargestIntersection temp = new LargestIntersection();
                        temp.setPopOutsPerProfitableOpportunity(intersectionsFixed.intersect(popout));
                        equalSizedLargestIntersectionArray.add(temp);
                    }
                    else if (equalSizedLargestIntersectionArray.get(0).getPopOutsPerProfitableOpportunity() == null) //else if intersection has no popouts
                    {
                        continue;
                    }
                    else if (intersectionsFixed.intersect(popout).hasMorePopoutsThan(equalSizedLargestIntersectionArray.get(0).getPopOutsPerProfitableOpportunity())) // else if this intersection has more popouts than the current largest intersection
                    {
                        equalSizedLargestIntersectionArray = new ArrayList<>();
                        LargestIntersection temp = new LargestIntersection();
                        temp.setPopOutsPerProfitableOpportunity(intersectionsFixed.intersect(popout));
                        equalSizedLargestIntersectionArray.add(temp);
                    }
                    else if (intersectionsFixed.intersect(popout).hasSameNumPopoutsAs(equalSizedLargestIntersectionArray.get(0).getPopOutsPerProfitableOpportunity())) // else if this intersection is equal to the largest intersection
                    {
                        LargestIntersection temp = new LargestIntersection();
                        temp.setPopOutsPerProfitableOpportunity(intersectionsFixed.intersect(popout));
                        equalSizedLargestIntersectionArray.add(temp);
                    }
                }

                ArrayList<LargestIntersection> tempOccurances = new ArrayList<>();
                ArrayList<Integer> tempNumOccurances = new ArrayList<>();
                if (equalSizedLargestIntersectionArray.size() > 1)
                {
                    for (LargestIntersection largestIntersectItem : equalSizedLargestIntersectionArray)//check how many occurances there are to determine which largest intersect has the most occurances of the same variables
                    {
                        int pos = -1;
                        for (int i = 0; i < tempNumOccurances.size(); i++)
                        {
                            LargestIntersection tempOccurance = tempOccurances.get(i);
                            if (tempOccurance.intersect(largestIntersectItem).hasSameNumPopoutsAs(tempOccurance.getPopOutsPerProfitableOpportunity()))
                            {
                                pos = i;
                                tempNumOccurances.set(i, (tempNumOccurances.get(i) + 1));
                            }
                        }
                        if (pos == -1)
                        {
                            tempOccurances.add(largestIntersectItem);
                            tempNumOccurances.add(1);
                        }
                    }
                    int biggest = 0;
                    int posBiggest = -1;
                    int count = 0;
                    for (int occurances : tempNumOccurances)
                    {
                        tempOccurances.get(count).setNumOccurances(occurances);
                        if (occurances > biggest)
                        {
                            posBiggest = count;
                            biggest = occurances;
                        }
                        count++;
                    }
                    largestIntersections.add(tempOccurances.get(posBiggest).getPopOutsPerProfitableOpportunity());
                }
                else if (equalSizedLargestIntersectionArray.size() == 1)
                {
                    largestIntersections.add(equalSizedLargestIntersectionArray.get(0).getPopOutsPerProfitableOpportunity());
                }

            }
            if (intersections.size() == 1)
            {
                int lenBefore = intersectionWaterfall.size();
                intersectionWaterfall = filterIntersections(intersectionWaterfall);
                if (lenBefore == intersectionWaterfall.size())
                {
                    break;
                }
            }
            else
            {
                break;
            }
        }
        while (intersections.size() > 0 && intersections.get(0).getNumOccurances() > 0);

        if (intersectionWaterfallArray.size() > 0)
        {
            ArrayList<LargestIntersection[]> tempIntersectWaterfall = intersectionWaterfallArray.get(intersectionWaterfallArray.size() - 1);
        }
        return intersectionWaterfall;
    }

    /*Find all unique largest intersects and return an array containing them*/
    public ArrayList<LargestIntersection[]> filterIntersections(ArrayList<LargestIntersection[]> intersectionWaterfall)
    {
        for (int i = 0; i < largestIntersections.size(); i++)
        {
            if (largestIntersections.get(i) == null)
            {
                largestIntersections.remove(i);
                i--;
            }
        }
        intersections = new ArrayList<>(); //may be source of issue
        for (int j = 0; j < largestIntersections.size(); j++)
        {
            PopOutsPerProfitableOpportunity largestIntersectFixed = largestIntersections.get(j);
            LargestIntersection temp = new LargestIntersection();
            temp.setPopOutsPerProfitableOpportunity(largestIntersectFixed);
            temp.setNumOccurances(1);
            for (int i = j + 1; i < largestIntersections.size(); i++)
            {
                PopOutsPerProfitableOpportunity largestIntersectVariable = largestIntersections.get(i);
                if (largestIntersectFixed.intersect(largestIntersectVariable).hasSameNumPopoutsAs(largestIntersectFixed) && largestIntersectFixed.intersect(largestIntersectVariable).hasSameNumPopoutsAs(largestIntersectVariable))
                {
                    temp.incrementNumOccurances();
                    largestIntersections.remove(i);
                    i--;
                }
            }
            if (temp.getNumOccurances() >= 1)
            {
                intersections.add(temp);
            }
        }

        LargestIntersection[] temp = new LargestIntersection[intersections.size()];
        for (int i = 0; i < temp.length; i++)
        {
            temp[i] = intersections.get(i);
        }

        if (intersectionWaterfall.isEmpty() || ((temp.length > 0) && (temp[0].getPopOutsPerProfitableOpportunity().getNumPopOuts() < intersectionWaterfall.get(intersectionWaterfall.size() - 1)[0].getPopOutsPerProfitableOpportunity().getNumPopOuts())))
        {
            intersectionWaterfall.add(temp);
        }
        return intersectionWaterfall;

    }

    //tested and working
    /**
     * shift t1 'numMinutes' minutes ahead e.g. if numMinutes passed as 5 method
     * only find t1 and t2 to be equal if t1 is 5 minutes ahead of t2 0 if equal
     * less than 0 if t1 is before t2 greater than 0 if t1 is after t2;
     *
     * @param t1
     * @param t2
     * @return
     */
    public int t1RelativeToT2(String t1, String t2, int numSeconds)//greater than 0 if t1 is after t2
    {
        if (t1.equals(""))
        {
            System.out.println("t1 = ''");
        }
        if (t2.equals(""))
        {
            System.out.println("t2 = ''");
        }
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date thisDate = new Date();
        Date anotherDate = new Date();
        try
        {
            thisDate = formatter.parse(t1);
            anotherDate = formatter.parse(t2);
        }
        catch (Exception e)
        {
        }

        Calendar anotherCal = Calendar.getInstance();
        anotherCal.setTime(anotherDate);
        Calendar thisCal = Calendar.getInstance();
        thisCal.setTime(thisDate);
        long thisTime = thisCal.getTimeInMillis() - numSeconds * 1000;

        long anotherTime = anotherCal.getTimeInMillis();

        if (thisTime < anotherTime)
        {
            return -1;
        }
        else if (thisTime == anotherTime)
        {
            return 0;
        }
        else
        {
            return 1;
        }
    }

    //reads lines one by one and converts them into share objects
    public void read(String fileName)
    {
        try
        {
            BufferedReader f = new BufferedReader(new FileReader("elements.txt"));
            String s = f.readLine();

            int sharesSize = 0;
            while (s != null)
            {
                sharesSize++;
                s = f.readLine();
            }

            f = new BufferedReader(new FileReader("elements.txt"));
            s = f.readLine();
            shares = new Share[sharesSize];
            sharesSize = 0;
            while (s != null)
            {
                shares[sharesSize] = new Share(s);
                sharesSize++;
                s = f.readLine();
            }
        }
        catch (Exception e)
        {
            System.out.println("Exception: " + e);
        }

    }

    public InstanceData convertToInstance(String s)
    {
        //symbol;nr;timestamp;open;high;low;close;volume
        String[] temp = s.split(";");
        String pos = temp[1];
        String timeStamp = temp[2];
        Double open = Double.parseDouble(temp[3].replace(",", "."));
        Double high = Double.parseDouble(temp[4].replace(",", "."));
        Double low = Double.parseDouble(temp[5].replace(",", "."));
        Double close = Double.parseDouble(temp[6].replace(",", "."));
        int volume = Integer.parseInt(temp[7].replace(",", "."));

        return new InstanceData(timeStamp, open, high, low, close, volume);
    }

    public InstanceData convertToShare(String s, Share share)
    {
        //symbol;nr;timestamp;open;high;low;close;volume
        String[] temp = s.split(";");
        String pos = temp[1];
        String timeStamp = temp[2];
        Double open = Double.parseDouble(temp[3].replace(",", "."));
        Double high = Double.parseDouble(temp[4].replace(",", "."));
        Double low = Double.parseDouble(temp[5].replace(",", "."));
        Double close = Double.parseDouble(temp[6].replace(",", "."));
        int volume = Integer.parseInt(temp[7].replace(",", "."));

        return new InstanceData(timeStamp, open, high, low, close, volume);
    }

    public void findProfittableOpportunities(double searchPeriod)//search period is how many historical data bars to look for profitable opportunities 
    {
        for (int i = 0; i < derivativesPerShareArrayList.size(); i++)
        {
            InstanceDataPerShare idps = new InstanceDataPerShare(derivativesPerShareArrayList.get(i).getSymbol());

            Indicator profitableUpwardOpportunities = new Indicator("ProfitableUpwardOpportunities", idps.getSymbol(), instanceDataPerShare);
            Indicator profitableDownwardOpportunities = new Indicator("ProfitableDownwardOpportunities", idps.getSymbol(), instanceDataPerShare);
            if (i == 0)
            {
                indicators.add(profitableUpwardOpportunities);
                indicators.add(profitableDownwardOpportunities);
            }
            if (profitableUpwardOpportunities.getOut() == null || profitableDownwardOpportunities.getOut() == null)
            {
                continue;
            }
            InstanceDataDay day = idps.getNextDay();
            while (day != null)
            {
                for (int j = 0; j < day.getInstanceData().length - 1; j++)
                {
                    for (int k = 1; k < searchPeriod + 1; k++)//j represents how far forward we are looking for the profit to be reached
                    {
                        if (j + k >= day.getInstanceData().length)
                        {
                            break;
                        }
                        InstanceData shareInstance1 = day.getInstanceData()[j];
                        InstanceData shareInstance2 = day.getInstanceData()[j + k];
                        if (shareInstance1 == null || shareInstance2 == null)
                        {
                            continue;
                        }
                        double rUpwardMovement = shareInstance2.getHigh() - shareInstance1.getClose();
                        double rDownwardMovement = shareInstance1.getClose() - shareInstance2.getLow();
                        double requiredMovement = shareInstance1.getClose() * (Math.pow(2, k / 101400) - 1);

                        if (rUpwardMovement > requiredMovement)//upward movement
                        {
                            ProfitableOpportunity profitableOpportunity = new ProfitableOpportunity(shareInstance1.getTimestamp());
                            profitableOpportunity.setNumMinutesProfitReached(k);
                            profitableUpwardOpportunities.addDerivative(new Derivative(k, shareInstance1.getTimestamp()), idps.getSymbol());
                            //profitableUpwardOpportunitiesArrayList.add(profitableOpportunity);
                            break;
                        }
                        else if (Math.abs(rDownwardMovement) > requiredMovement)//downward movemen
                        {
                            ProfitableOpportunity profitableOpportunity = new ProfitableOpportunity(shareInstance1.getTimestamp());
                            profitableOpportunity.setNumMinutesProfitReached(k);
//                    profitableDownwardOpportunitiesArrayList.add(profitableOpportunity);
                            profitableDownwardOpportunities.addDerivative(new Derivative(k, shareInstance1.getTimestamp()), idps.getSymbol());
                            break;
                        }
                    }
                }
                profitableDownwardOpportunities.addDerivative(null, idps.getSymbol() + "close");
                profitableUpwardOpportunities.addDerivative(null, idps.getSymbol() + "close");
                day = idps.getNextDay();
            }
        }
    }

    /**
     * sorts array of derivatives (all changes in price from one data point to the next) 
     * and finds the top and bottom 10 percent of derivatives for each day 
     * by taking the first and last 10 percent of elements in the sorted array
     */
    public void identifyOutliers()
    {
        int l = -1;
        for (DerivativesPerShare dps : derivativesPerShareArrayList) //iterating through shares
        {
            l++;
            Indicator topOutliers = new Indicator("TopOutliers", dps.getSymbol(), instanceDataPerShare);
            Indicator bottomOutliers = new Indicator("BottomOutliers", dps.getSymbol(), instanceDataPerShare);

            if (l == 0)//add the 2 indicators to the indicator ArrayList
            {
                indicators.add(topOutliers);
                indicators.add(bottomOutliers);
            }

            if (topOutliers.getOut() == null) //if the indicator has already been calculated for this share skip this share
            {
                System.out.println("Top Out File Found - skip");
                continue;
            }
            else if (bottomOutliers.getOut() == null)
            {
                System.out.println("Bottom Out File Found - skip");
                continue;
            }
            int tenPercent;
            DerivativesPerShare dpsTempTop = new DerivativesPerShare();
            DerivativesPerShare dpsTempBottom = new DerivativesPerShare();
            dpsTempTop.setSymbol(dps.getSymbol());
            dpsTempBottom.setSymbol(dps.getSymbol());

            Day day = dps.getNextDay("close", dps.getSymbol()); //processing one day at a time so that we don't run out of memory
            while (day != null)
            {
                day.sortByDerivative(); //sorting the derivatives of the prices for the day
                tenPercent = (int) (day.getDerivatives().length * 0.1);
                Day temp = new Day("");
                Derivative[] ders = new Derivative[tenPercent]; //creating a derivitaives array to hold the top 10 and bottom 10 percent of derivative values
                if (topOutliers.getOut() == null)
                {
                    System.arraycopy(day.getDerivatives(), 0, ders, 0, tenPercent);//copying the top 10 percent of derivatives into the ders array
                    temp.setDerivatives(ders);
                    temp.sortByTime(); // sorting the array by time so that the can work with the data sequencially
                    ders = temp.getDerivatives();
                    for (Derivative d : ders)
                    {
                        topOutliers.addDerivative(d, dps.getSymbol()); //this formats the values in a standardised format and adds them to a buffer to be written to a file periodically (to increase write efficiency)
                    }
                    topOutliers.addDerivative(null, dps.getSymbol() + "close");//indicates that the BufferWriter must be flushed
                    //bottomPopOut.add(day);
                }
                if (bottomOutliers.getOut() == null)
                {
                    ders = new Derivative[tenPercent];
                    int count = 0;

                    for (int i = day.getDerivatives().length - tenPercent; i < day.getDerivatives().length; i++)
                    {
                        ders[count] = day.getDerivatives()[i];
                        count++;
                    }
                    temp = new Day("");
                    temp.setDerivatives(ders);
                    temp.sortByTime();;
                    ders = temp.getDerivatives();
                    for (Derivative d : ders)
                    {
                        bottomOutliers.addDerivative(d, dps.getSymbol());
                    }
                    bottomOutliers.addDerivative(null, dps.getSymbol() + "close");
                    day = dps.getNextDay("close", dps.getSymbol());
                }
                System.out.println("");
            }
            
            //add the top and bottom outliers per share to a global array for further processing
            topOutliersArrayList.add(dpsTempTop); 
            bottomOutliersArrayList.add(dpsTempBottom);
        }
    }

    public Derivative[] mergeSort(Derivative[] A)
    {
        if (A.length > 1)
        {
            int q = A.length / 2;

            Derivative[] leftArray = Arrays.copyOfRange(A, 0, q - 1);
            Derivative[] rightArray = Arrays.copyOfRange(A, q, A.length - 1);

            mergeSort(leftArray);
            mergeSort(rightArray);

            merge(A, leftArray, rightArray);
        }

        return A;
    }

    static Derivative[] merge(Derivative[] a, Derivative[] l, Derivative[] r)
    {
        int totElem = l.length + r.length;
        //int[] a = new int[totElem];
        int i, li, ri;
        i = li = ri = 0;
        while (i < totElem)
        {
            if ((li < l.length) && (ri < r.length))
            {
                if (l[li].getDerivative() < r[ri].getDerivative())
                {
                    a[i] = l[li];
                    i++;
                    li++;
                }
                else
                {
                    a[i] = r[ri];
                    i++;
                    ri++;
                }
            }
            else
            {
                if (li >= l.length)
                {
                    while (ri < r.length)
                    {
                        a[i] = r[ri];
                        i++;
                        ri++;
                    }
                }
                if (ri >= r.length)
                {
                    while (li < l.length)
                    {
                        a[i] = l[li];
                        li++;
                        i++;
                    }
                }
            }
        }
        return a;
    }

    public void readElementList()
    {
        try
        {
            BufferedReader f = new BufferedReader(new FileReader("elements.txt"));
            String s = f.readLine();

            //looking through elements file (with all elements file names listed)
            while (s != null)
            {
                Share share = null;

                try
                {
                    BufferedReader ff = new BufferedReader(new FileReader(s + ".txt"));
                    ff.readLine();
                    share = new Share(s);
                }
                catch (Exception e)
                {
                }

                if (share != null)
                {
                    elements.add(share);
                }
                s = f.readLine();
            }
        }
        catch (Exception e)
        {
            System.out.println("Exception: " + e);
        }
    }

    /**/
    //get derivativesArrayList for each day
    //disbanded
    public void calculateDerivitives()/////////////////
    {
        for (Share share : elements)
        {
            DerivativesPerShare dps = new DerivativesPerShare();
            InstanceDataPerShare idpsDer = new InstanceDataPerShare(share.getSymbol());
            InstanceDataPerShare idps = new InstanceDataPerShare(share.getSymbol());
            dps.setSymbol(share.getSymbol());
            idpsDer.setSymbol(share.getSymbol());
            idps.setSymbol(share.getSymbol());
            derivativesPerShareArrayList.add(dps);
            instanceDataPerShareDerivatives.add(idpsDer);
            instanceDataPerShare.add(idps);

        }
    }

    /////////////////////////////////
    /**/
    /**/
    //disbanded
    ////////////////////////////////
    public void calculateCloses()
    {
        for (Share share : elements)
        {
            DerivativesPerShare dps = new DerivativesPerShare();
            dps.setSymbol(share.getSymbol());
            closePerShareArrayList.add(dps);
        }
    }

    /**/
    public int numSeconds(String time1, String time2)
    {
        Calendar t1 = Calendar.getInstance();
        String[] date1 = time1.split(" ");
        String[] dateFirst1 = date1[0].split("-");
        String[] dateSecond1 = date1[1].split(":");
        t1.setTime(new Date(Integer.parseInt(dateFirst1[0]), Integer.parseInt(dateFirst1[1]), Integer.parseInt(dateFirst1[2]), Integer.parseInt(dateSecond1[0]), Integer.parseInt(dateSecond1[1]), Integer.parseInt(dateSecond1[2])));
        Calendar t2 = Calendar.getInstance();
        String[] date2 = time2.split(" ");
        String[] dateFirst2 = date2[0].split("-");
        String[] dateSecond2 = date2[1].split(":");
        t2.setTime(new Date(Integer.parseInt(dateFirst2[0]), Integer.parseInt(dateFirst2[1]), Integer.parseInt(dateFirst2[2]), Integer.parseInt(dateSecond2[0]), Integer.parseInt(dateSecond2[1]), Integer.parseInt(dateSecond2[2])));
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
        long diff = (t2.getTimeInMillis() - t1.getTimeInMillis()) / 1000;

        return Integer.parseInt(diff + "");
    }

//    private void transferFromArrayLists()
//    {
//        int pos = 0;
//        profitableUpwardOpportunities = new ProfitableOpportunity[profitableUpwardOpportunitiesArrayList.size()];
//        for (ProfitableOpportunity tempUPO : profitableUpwardOpportunitiesArrayList) {
//            profitableUpwardOpportunities[pos] = tempUPO;
//            pos++;
//        }
//
//        pos = 0;
//        profitableDownwardOpportunities = new ProfitableOpportunity[profitableDownwardOpportunitiesArrayList.size()];
//        for (ProfitableOpportunity tempDPO : profitableDownwardOpportunitiesArrayList) {
//            profitableDownwardOpportunities[pos] = tempDPO;
//            pos++;
//        }
//
//        pos = 0;
//        topOutliers = new DerivativesPerShare[topOutliersArrayList.size()];
//        for (DerivativesPerShare tempTO : topOutliersArrayList) {
//            topOutliers[pos] = tempTO;
//            pos++;
//        }
//
//        pos = 0;
//        bottomOutliers = new DerivativesPerShare[bottomOutliersArrayList.size()];
//        for (DerivativesPerShare tempBO : bottomOutliersArrayList) {
//            bottomOutliers[pos] = tempBO;
//            pos++;
//        }
//
//    }
    public void transferStrategies()
    {
        for (ArrayList<LargestIntersection[]> tempIntersectionWaterfallArray : intersectionWaterfallArray)
        {
            for (LargestIntersection[] tempComboArray : tempIntersectionWaterfallArray)
            {
                for (LargestIntersection tempCombo : tempComboArray)
                {
                    Strategy strategy = new Strategy();
                    strategy.setBottomPopouts(tempCombo.getPopOutsPerProfitableOpportunity().getBottomPopouts());
                    strategy.setTopPopouts(tempCombo.getPopOutsPerProfitableOpportunity().getTopPopouts());
                    boolean found = false;

                    for (Strategy tempStrategy : strategies)
                    {
                        if (tempStrategy.intersect(tempCombo.getPopOutsPerProfitableOpportunity()).hasSameNumPopoutsAs(tempCombo.getPopOutsPerProfitableOpportunity()) && tempStrategy.intersect(tempCombo.getPopOutsPerProfitableOpportunity()).hasSameNumPopoutsAs(strategy.getNumPopOuts()))
                        {
                            found = true;
                        }
                    }

                    if (!found)
                    {
                        strategies.add(strategy);
                    }
                }
            }
        }
    }

    public void createSubsetTreeArray()
    {
        int largest = findLargestPopout(strategies);

        for (Strategy strat : strategies)
        {
            strategySubsets.add(strat);
        }

        recursiveSubsetTreeBuilder(strategySubsets, largest);
    }

    //size is the number of popouts in the subset
    public ArrayList<Strategy> recursiveSubsetTreeBuilder(ArrayList<Strategy> tempStrategies, int size)
    {
        if (tempStrategies.isEmpty())
        {
            return tempStrategies;
        }
        for (int i = 0; i < tempStrategies.size(); i++)
        {
            Strategy tempEqualSubset = tempStrategies.get(i);
            for (Strategy tempStrategy : strategies)
            {
                int nextLargestSubset = findLargestPopout(strategies, tempEqualSubset);
                if (nextLargestSubset == tempStrategy.intersect(tempEqualSubset).getNumPopOuts() && tempStrategy.isSubsetof(tempEqualSubset) && tempStrategy.intersect(tempEqualSubset).getNumPopOuts() > 0)
                {
                    tempEqualSubset.addToSubsets(tempStrategy);
                    System.out.println(tempEqualSubset.getSubsets());
                }
            }
            recursiveSubsetTreeBuilder(tempEqualSubset.getSubsets(), tempEqualSubset.getNumPopOuts());
        }

        return tempStrategies;
    }

    public int findSmallestPopout(ArrayList<Strategy> tempStrategies)
    {
        int smallest = 0;
        for (Strategy tempStrategy : tempStrategies)
        {
            if (tempStrategy.getNumPopOuts() < smallest)
            {
                smallest = tempStrategy.getNumPopOuts();
            }
        }
        return smallest;
    }

    public int findLargestPopout(ArrayList<Strategy> tempStrategies)
    {
        int biggest = 0;
        for (Strategy tempStrategy : tempStrategies)
        {
            if (tempStrategy.getNumPopOuts() > biggest)
            {
                biggest = tempStrategy.getNumPopOuts();
            }
        }
        return biggest;
    }

    public void calculateVolitilityMACDcrosses()
    {
        double EMAshort = 0.0;
        double EMAmedium = 0.0;
        double EMAlong = 0.0;

        double prevEMAshort = 0.0;
        double prevEMAmedium = 0.0;
        double prevEMAlong = 0.0;

        Day[] days;

        for (int i = 0; i < instanceDataPerShareDerivatives.size(); i++)
        {
            InstanceDataPerShare instanceDataShare = new InstanceDataPerShare(instanceDataPerShareDerivatives.get(i));

            Indicator MACDscamVolitilityIndicator = new Indicator("scam Volitility", instanceDataShare.getSymbol(), instanceDataPerShareDerivatives);//short crossing to above medium macd
            Indicator MACDscbmVolitilityIndicator = new Indicator("scbm Volitility", instanceDataShare.getSymbol(), instanceDataPerShareDerivatives);//short crossing to below medium macd
            //MACDscbmVolitilityIndicator.setIndicatorName("short volitility crossing to below medium volitility");
            Indicator MACDmcalVolitilityIndicator = new Indicator("mcal Volitility", instanceDataShare.getSymbol(), instanceDataPerShareDerivatives);//medium crossing to above long macd
            //MACDmcalVolitilityIndicator.setIndicatorName("medium volitility crossing to above long volitility");
            Indicator MACDmcblVolitilityIndicator = new Indicator("mcbl Volitility", instanceDataShare.getSymbol(), instanceDataPerShareDerivatives);//medium crossing to below long macd
            //MACDmcblVolitilityIndicator.setIndicatorName("medium volitility crossing to below long volitility");
            Indicator MACDlmcbsVolitilityIndicator = new Indicator("mcbs Volitility", instanceDataShare.getSymbol(), instanceDataPerShareDerivatives);//difference between (short and medium) crossing to below long macd
            //MACDlmcbsVolitilityIndicator.setIndicatorName("difference between (short and medium) volitility crossing to below long volitility");
            Indicator MACDlmcasVolitilityIndicator = new Indicator("mcas Volitility", instanceDataShare.getSymbol(), instanceDataPerShareDerivatives);//difference between short and medium crossing to above long macd
            //MACDlmcasVolitilityIndicator.setIndicatorName("difference between (short and medium) volitility crossing to above long volitility");

            if (i == 0)
            {
                indicators.add(MACDscamVolitilityIndicator);
                indicators.add(MACDscbmVolitilityIndicator);
                indicators.add(MACDmcalVolitilityIndicator);
                indicators.add(MACDmcblVolitilityIndicator);
                indicators.add(MACDlmcbsVolitilityIndicator);
                indicators.add(MACDlmcasVolitilityIndicator);
            }

            if (MACDscamVolitilityIndicator.getOut() == null || MACDscbmVolitilityIndicator.getOut() == null || MACDmcalVolitilityIndicator.getOut() == null || MACDmcblVolitilityIndicator.getOut() == null || MACDlmcbsVolitilityIndicator.getOut() == null || MACDlmcasVolitilityIndicator.getOut() == null)
            {
                continue;
            }
            InstanceDataDay day = instanceDataShare.getNextDay();
            while (day != null)
            {
                EMAshort = 0.0;
                EMAmedium = 0.0;
                EMAlong = 0.0;

                for (int k = 0; k < day.getInstanceData().length; k++)
                {
                    InstanceData instanceData = day.getInstanceData()[k];

                    if (k != 0)
                    {
                        EMAshort = (double) (1.0 / shortPeriod) * (instanceData.getHigh() - instanceData.getLow()) + (double) ((shortPeriod - 1.0) / shortPeriod) * EMAshort;
                        EMAmedium = (double) (1.0 / mediumPeriod) * (instanceData.getHigh() - instanceData.getLow()) + (double) ((mediumPeriod - 1.0) / mediumPeriod) * EMAmedium;
                        EMAlong = (double) (1.0 / longPeriod) * (instanceData.getHigh() - instanceData.getLow()) + (double) ((longPeriod - 1.0) / longPeriod) * EMAlong;

                        if (k >= mediumPeriod)
                        {
                            if ((EMAshort - EMAmedium > 0) && (prevEMAshort - prevEMAmedium < 0))
                            {
                                MACDscamVolitilityIndicator.addDerivative(new Derivative(0.0, instanceData.getTimestamp()), instanceDataShare.getSymbol());
                            }

                            if ((EMAshort - EMAmedium < 0) && (prevEMAshort - prevEMAmedium > 0))
                            {
                                MACDscbmVolitilityIndicator.addDerivative(new Derivative(0.0, instanceData.getTimestamp()), instanceDataShare.getSymbol());
                            }
                        }

                        if (k >= longPeriod)
                        {
                            if ((EMAlong - EMAmedium > 0) && (prevEMAlong - prevEMAmedium < 0))
                            {
                                MACDmcblVolitilityIndicator.addDerivative(new Derivative(0.0, instanceData.getTimestamp()), instanceDataShare.getSymbol());
                            }

                            if ((EMAlong - EMAmedium < 0) && (prevEMAlong - prevEMAmedium > 0))
                            {
                                MACDmcalVolitilityIndicator.addDerivative(new Derivative(0.0, instanceData.getTimestamp()), instanceDataShare.getSymbol());
                            }

                            if (((EMAlong - EMAmedium) - EMAshort > 0) && ((prevEMAlong - prevEMAmedium) - prevEMAshort < 0))
                            {
                                MACDlmcbsVolitilityIndicator.addDerivative(new Derivative(0.0, instanceData.getTimestamp()), instanceDataShare.getSymbol());
                            }

                            if (((EMAlong - EMAmedium) - EMAshort < 0) && ((prevEMAlong - prevEMAmedium) - prevEMAshort > 0))
                            {
                                MACDlmcasVolitilityIndicator.addDerivative(new Derivative(0.0, instanceData.getTimestamp()), instanceDataShare.getSymbol());
                            }
                        }
                    }
                    else
                    {
                        EMAshort = (instanceData.getHigh() - instanceData.getLow());
                        EMAmedium = (instanceData.getHigh() - instanceData.getLow());
                        EMAlong = (instanceData.getHigh() - instanceData.getLow());
                    }

                    prevEMAshort = EMAshort;
                    prevEMAmedium = EMAmedium;
                    prevEMAlong = EMAlong;
                }
                day = instanceDataShare.getNextDay();
            }
        }
    }

    /*
     n1 < n2
     */
    public void calculateMACDcrosses()
    {
        double EMAshort = 0.0;
        double EMAmedium = 0.0;
        double EMAlong = 0.0;

        double prevEMAshort = 0.0;
        double prevEMAmedium = 0.0;
        double prevEMAlong = 0.0;

        Day[] days;

        for (int i = 0; i < instanceDataPerShareDerivatives.size(); i++)
        {
            InstanceDataPerShare instanceDataShare = new InstanceDataPerShare(instanceDataPerShareDerivatives.get(i));;
            Indicator MACDscamIndicator = new Indicator("scam MACD", instanceDataShare.getSymbol(), instanceDataPerShareDerivatives);//short crossing to above medium macd
            //MACDscamIndicator.setIndicatorName("short crossing to above medium");
            Indicator MACDscbmIndicator = new Indicator("scbm MACD", instanceDataShare.getSymbol(), instanceDataPerShareDerivatives);//short crossing to below medium macd
            //MACDscbmIndicator.setIndicatorName("short crossing to below medium");
            Indicator MACDmcalIndicator = new Indicator("mcal MACD", instanceDataShare.getSymbol(), instanceDataPerShareDerivatives);//medium crossing to above long macd
            //MACDmcalIndicator.setIndicatorName("medium crossing to above long");
            Indicator MACDmcblIndicator = new Indicator("mcbl MACD", instanceDataShare.getSymbol(), instanceDataPerShareDerivatives);//medium crossing to below long macd
            //MACDmcblIndicator.setIndicatorName("medium crossing to below long");
            Indicator MACDlmcbsIndicator = new Indicator("lmcbs MACD", instanceDataShare.getSymbol(), instanceDataPerShareDerivatives);//difference between (short and medium) crossing to below long macd
            //MACDlmcbsIndicator.setIndicatorName("difference between (short and medium) crossing to below long");
            Indicator MACDlmcasIndicator = new Indicator("lmcasscam MACD", instanceDataShare.getSymbol(), instanceDataPerShareDerivatives);//difference between short and medium crossing to above long macd
            //MACDlmcasIndicator.setIndicatorName("difference between short and medium crossing to above long");

            if (i == 0)
            {
                indicators.add(MACDscamIndicator);
                indicators.add(MACDscbmIndicator);
                indicators.add(MACDmcalIndicator);
                indicators.add(MACDmcblIndicator);
                indicators.add(MACDlmcbsIndicator);
                indicators.add(MACDlmcasIndicator);
            }

            if (MACDscamIndicator.getOut() == null || MACDscbmIndicator.getOut() == null || MACDmcalIndicator.getOut() == null || MACDmcblIndicator.getOut() == null || MACDlmcbsIndicator.getOut() == null || MACDlmcasIndicator.getOut() == null)
            {
                continue;
            }
            InstanceDataDay day = instanceDataShare.getNextDay();
            while (day != null)
            {
                EMAshort = 0.0;
                EMAmedium = 0.0;
                EMAlong = 0.0;

                for (int k = 0; k < day.getInstanceData().length; k++)
                {
                    InstanceData instanceData = day.getInstanceData()[k];

                    if (k != 0)
                    {
                        EMAshort = (double) (1.0 / shortPeriod) * instanceData.getClose() + (double) ((shortPeriod - 1.0) / shortPeriod) * EMAshort;
                        EMAmedium = (double) (1.0 / mediumPeriod) * instanceData.getClose() + (double) ((mediumPeriod - 1.0) / mediumPeriod) * EMAmedium;
                        EMAlong = (double) (1.0 / longPeriod) * instanceData.getClose() + (double) ((longPeriod - 1.0) / longPeriod) * EMAlong;

                        if (k >= mediumPeriod)
                        {
                            if ((EMAshort - EMAmedium > 0) && (prevEMAshort - prevEMAmedium < 0))
                            {
                                MACDscamIndicator.addDerivative(new Derivative(0.0, instanceData.getTimestamp()), instanceDataShare.getSymbol());
                            }

                            if ((EMAshort - EMAmedium < 0) && (prevEMAshort - prevEMAmedium > 0))
                            {
                                MACDscbmIndicator.addDerivative(new Derivative(0.0, instanceData.getTimestamp()), instanceDataShare.getSymbol());
                            }
                        }

                        if (k >= longPeriod)
                        {
                            if ((EMAlong - EMAmedium > 0) && (prevEMAlong - prevEMAmedium < 0))//middle crosses below long
                            {
                                MACDmcblIndicator.addDerivative(new Derivative(0.0, instanceData.getTimestamp()), instanceDataShare.getSymbol());
                            }

                            if ((EMAlong - EMAmedium < 0) && (prevEMAlong - prevEMAmedium > 0))//middle crosses above long
                            {
                                MACDmcalIndicator.addDerivative(new Derivative(0.0, instanceData.getTimestamp()), instanceDataShare.getSymbol());
                            }

                            if (((EMAlong - EMAmedium) - EMAshort > 0) && ((prevEMAlong - prevEMAmedium) - prevEMAshort < 0))
                            {
                                MACDlmcbsIndicator.addDerivative(new Derivative(0.0, instanceData.getTimestamp()), instanceDataShare.getSymbol());
                            }

                            if (((EMAlong - EMAmedium) - EMAshort < 0) && ((prevEMAlong - prevEMAmedium) - prevEMAshort > 0))
                            {
                                MACDlmcasIndicator.addDerivative(new Derivative(0.0, instanceData.getTimestamp()), instanceDataShare.getSymbol());
                            }
                        }
                    }
                    else
                    {
                        EMAshort = instanceData.getClose();
                        EMAmedium = instanceData.getClose();
                        EMAlong = instanceData.getClose();
                    }

                    prevEMAshort = EMAshort;
                    prevEMAmedium = EMAmedium;
                    prevEMAlong = EMAlong;
                }
                day = instanceDataShare.getNextDay();
            }
        }
    }

    public void calculateIchimoku()//can be made more efficient by using single 52 length queque
    {
        Day[] days;

        Indicator Tenkansen = null;
        Indicator Kijunsen = null;
        Indicator SenkouA = null;
        Indicator SenkouB = null;
        Indicator TenkansenCrossesKijunsen = null;
        Indicator KijunsenCrossesTenkansen = null;
        Indicator SenkouACrossesSenkouB = null;
        Indicator SenkouBCrossesSenkouA = null;
        Indicator TenkansenCrossesKijunsenInsideIchimokuCloudATop = null;
        Indicator KijunsenCrossesTenkansenInsideIchimokuCloudATop = null;
        Indicator TenkansenCrossesKijunsenInsideIchimokuCloudBTop = null;
        Indicator KijunsenCrossesTenkansenInsideIchimokuCloudBTop = null;

        for (int i = 0; i < instanceDataPerShareDerivatives.size(); i++)
        {
            InstanceDataPerShare instanceDataShare = new InstanceDataPerShare(instanceDataPerShareDerivatives.get(i));

            Tenkansen = new Indicator("Tenkansen", instanceDataShare.getSymbol(), instanceDataPerShareDerivatives);//short crossing to above medium macd
            Kijunsen = new Indicator("Kijunsen", instanceDataShare.getSymbol(), instanceDataPerShareDerivatives);//difference between short and medium crossing to above long macd
            SenkouA = new Indicator("Senkou A", instanceDataShare.getSymbol(), instanceDataPerShareDerivatives);//difference between short and medium crossing to above long macd
            SenkouB = new Indicator("Senkou B", instanceDataShare.getSymbol(), instanceDataPerShareDerivatives);//difference between short and medium crossing to above long macd
            TenkansenCrossesKijunsen = new Indicator("Tenkan Cross Kijun", instanceDataShare.getSymbol(), instanceDataPerShareDerivatives);//short crossing to above medium macd
            KijunsenCrossesTenkansen = new Indicator("Kijun Cross Tenkan", instanceDataShare.getSymbol(), instanceDataPerShareDerivatives);//difference between short and medium crossing to above long macd
            SenkouACrossesSenkouB = new Indicator("Senkou A Crosses B", instanceDataShare.getSymbol(), instanceDataPerShareDerivatives);//difference between short and medium crossing to above long macd
            SenkouBCrossesSenkouA = new Indicator("Senkou B Crosses A", instanceDataShare.getSymbol(), instanceDataPerShareDerivatives);//difference between short and medium crossing to above long macd
            TenkansenCrossesKijunsenInsideIchimokuCloudATop = new Indicator("Tenkan Cross Kijun in Cloud A top", instanceDataShare.getSymbol(), instanceDataPerShareDerivatives);//short crossing to above medium macd
            //Tenkansen.setIndicatorName("Tenkan-sen Crosses Kijun-sen inside Ichimoku Cloud A top");
            KijunsenCrossesTenkansenInsideIchimokuCloudATop = new Indicator("Kijun Cross Tenkan in Cloud A top", instanceDataShare.getSymbol(), instanceDataPerShareDerivatives);//difference between short and medium crossing to above long macd
            //Kijunsen.setIndicatorName("Kijun-sen Crosses Tenkan-sen inside Ichimoku Cloud A top");
            TenkansenCrossesKijunsenInsideIchimokuCloudBTop = new Indicator("Tenkan Cross Kijun in Cloud B top", instanceDataShare.getSymbol(), instanceDataPerShareDerivatives);//short crossing to above medium macd
            //Tenkansen.setIndicatorName("Tenkan-sen Crosses Kijun-sen inside Ichimoku Cloud B top");
            KijunsenCrossesTenkansenInsideIchimokuCloudBTop = new Indicator("Kijun Cross Tenkan in Cloud B top", instanceDataShare.getSymbol(), instanceDataPerShareDerivatives);//difference between short and medium crossing to above long macd
            //Kijunsen.setIndicatorName("Kijun-sen Crosses Tenkan-sen inside Ichimoku Cloud B top");
            if (i == 0)
            {
                indicators.add(KijunsenCrossesTenkansenInsideIchimokuCloudATop);
                indicators.add(KijunsenCrossesTenkansenInsideIchimokuCloudBTop);
                indicators.add(KijunsenCrossesTenkansen);
                indicators.add(SenkouBCrossesSenkouA);
                indicators.add(SenkouACrossesSenkouB);
                indicators.add(TenkansenCrossesKijunsenInsideIchimokuCloudBTop);
                indicators.add(TenkansenCrossesKijunsenInsideIchimokuCloudATop);
                indicators.add(TenkansenCrossesKijunsen);
                indicators.add(Tenkansen);
                indicators.add(Kijunsen);
                indicators.add(SenkouA);
                indicators.add(SenkouB);
            }

            if (SenkouA.getOut() != null)
            {
                InstanceDataDay day = instanceDataShare.getNextDay();
                while (day != null)
                {
                    Queue<Derivative> nineHigh = new LinkedList<>();
                    Queue<Derivative> nineLow = new LinkedList<>();

                    Queue<Derivative> twentySixHigh = new LinkedList<>();
                    Queue<Derivative> twentySixLow = new LinkedList<>();

                    Queue<Derivative> fiftyTwoHigh = new LinkedList<>();
                    Queue<Derivative> fiftyTwoLow = new LinkedList<>();

                    Derivative nineHighD = new Derivative(-10000000000.0);
                    Derivative nineLowD = new Derivative(10000000000.0);

                    Derivative twentySixHighD = new Derivative(-10000000000.0);
                    Derivative twentySixLowD = new Derivative(10000000000.0);

                    Derivative fiftyTwoHighD = new Derivative(-10000000000.0);
                    Derivative fiftyTwoLowD = new Derivative(10000000000.0);

                    for (int k = 0; k < day.getInstanceData().length; k++)
                    {
                        InstanceData instanceData = day.getInstanceData()[k];

                        Tenkansen.addDerivative(new Derivative(instanceData.getTimestamp()), instanceDataShare.getSymbol());
                        Kijunsen.addDerivative(new Derivative(instanceData.getTimestamp()), instanceDataShare.getSymbol());
                        SenkouA.addDerivative(new Derivative(instanceData.getTimestamp()), instanceDataShare.getSymbol());
                        SenkouB.addDerivative(new Derivative(instanceData.getTimestamp()), instanceDataShare.getSymbol());
                        TenkansenCrossesKijunsen.addDerivative(new Derivative(instanceData.getTimestamp()), instanceDataShare.getSymbol());
                        KijunsenCrossesTenkansen.addDerivative(new Derivative(instanceData.getTimestamp()), instanceDataShare.getSymbol());
                        SenkouACrossesSenkouB.addDerivative(new Derivative(instanceData.getTimestamp()), instanceDataShare.getSymbol());
                        SenkouBCrossesSenkouA.addDerivative(new Derivative(instanceData.getTimestamp()), instanceDataShare.getSymbol());
                        TenkansenCrossesKijunsenInsideIchimokuCloudATop.addDerivative(new Derivative(instanceData.getTimestamp()), instanceDataShare.getSymbol());
                        KijunsenCrossesTenkansenInsideIchimokuCloudATop.addDerivative(new Derivative(instanceData.getTimestamp()), instanceDataShare.getSymbol());
                        TenkansenCrossesKijunsenInsideIchimokuCloudBTop.addDerivative(new Derivative(instanceData.getTimestamp()), instanceDataShare.getSymbol());
                        KijunsenCrossesTenkansenInsideIchimokuCloudBTop.addDerivative(new Derivative(instanceData.getTimestamp()), instanceDataShare.getSymbol());

                        if (instanceData.getHigh() > nineHighD.getDerivative())
                        {
                            nineHighD = new Derivative(instanceData.getHigh(), instanceData.getTimestamp());
                        }
                        nineHigh.add(new Derivative(instanceData.getHigh(), instanceData.getTimestamp()));

                        if (instanceData.getLow() < nineLowD.getDerivative())
                        {
                            nineLowD = new Derivative(instanceData.getLow(), instanceData.getTimestamp());
                        }
                        nineLow.add(new Derivative(instanceData.getLow(), instanceData.getTimestamp()));

                        if (instanceData.getHigh() > twentySixHighD.getDerivative())
                        {
                            twentySixHighD = new Derivative(instanceData.getHigh(), instanceData.getTimestamp());
                        }
                        twentySixHigh.add(new Derivative(instanceData.getHigh(), instanceData.getTimestamp()));

                        if (instanceData.getLow() < twentySixLowD.getDerivative())
                        {
                            twentySixLowD = new Derivative(instanceData.getLow(), instanceData.getTimestamp());
                        }
                        twentySixLow.add(new Derivative(instanceData.getLow(), instanceData.getTimestamp()));

                        if (instanceData.getHigh() > fiftyTwoHighD.getDerivative())
                        {
                            fiftyTwoHighD = new Derivative(instanceData.getHigh(), instanceData.getTimestamp());
                        }
                        fiftyTwoHigh.add(new Derivative(instanceData.getHigh(), instanceData.getTimestamp()));

                        if (instanceData.getLow() < fiftyTwoLowD.getDerivative())
                        {
                            fiftyTwoLowD = new Derivative(instanceData.getLow(), instanceData.getTimestamp());
                        }
                        fiftyTwoLow.add(new Derivative(instanceData.getLow(), instanceData.getTimestamp()));

                        if (k > 8)
                        {
                            if (nineHighD.getDerivative() == nineHigh.poll().getDerivative())
                            {
                                // System.out.println("9 High " + k + " : " + nineHighD.getDerivative());
                                Queue<Derivative> tempNineHigh = new LinkedList<>(nineHigh);
                                nineHighD = new Derivative(-10000000000.0);
                                for (int h = 0; h < nineHigh.size() + 1; h++)
                                {
                                    if (tempNineHigh.peek() != null)
                                    {
                                        if (nineHighD.getDerivative() < tempNineHigh.peek().getDerivative())
                                        {
                                            nineHighD = tempNineHigh.remove();
                                        }
                                        else
                                        {
                                            tempNineHigh.remove();
                                        }
                                    }
                                }
                            }

                            if (nineLowD.getDerivative() == nineLow.poll().getDerivative())
                            {
                                //System.out.println("9 Low " + k + " : " + nineLowD.getDerivative());
                                Queue<Derivative> tempNineLow = new LinkedList<>(nineLow);
                                nineLowD = new Derivative(10000000000.0);
                                for (int l = 0; l < nineLow.size() + 1; l++)
                                {
                                    if (tempNineLow.peek() != null)
                                    {
                                        if (nineLowD.getDerivative() > tempNineLow.peek().getDerivative())
                                        {
                                            nineLowD = tempNineLow.remove();
                                        }
                                        else
                                        {
                                            tempNineLow.remove();
                                        }
                                    }
                                }
                            }

                            if (k > 25)
                            {
                                if (twentySixHighD.getDerivative() == twentySixHigh.poll().getDerivative())
                                {
                                    //  System.out.println("26 High " + k + " : " + twentySixHighD.getDerivative());
                                    Queue<Derivative> tempTwentySixHigh = new LinkedList<>(twentySixHigh);
                                    twentySixHighD = new Derivative(-10000000000.0);
                                    for (int h = 0; h < twentySixHigh.size() + 1; h++)
                                    {
                                        if (tempTwentySixHigh.peek() != null)
                                        {
                                            //  System.out.print(tempTwentySixHigh.peek().getDerivative());
                                            if (twentySixHighD.getDerivative() < tempTwentySixHigh.peek().getDerivative())
                                            {
                                                twentySixHighD = tempTwentySixHigh.remove();
                                            }
                                            else
                                            {
                                                tempTwentySixHigh.remove();
                                            }
                                        }
                                    }
                                }

                                if (twentySixLowD.getDerivative() == twentySixLow.poll().getDerivative())
                                {
                                    //System.out.println("26 Low " + k + " : " + twentySixLowD.getDerivative());
                                    Queue<Derivative> tempTwentySixLow = new LinkedList<>(twentySixLow);
                                    twentySixLowD = new Derivative(10000000000.0);
                                    for (int l = 0; l < twentySixLow.size() + 1; l++)
                                    {
                                        if (tempTwentySixLow.peek() != null)
                                        {
                                            if (twentySixLowD.getDerivative() > tempTwentySixLow.peek().getDerivative())
                                            {
                                                twentySixLowD = tempTwentySixLow.remove();
                                            }
                                            else
                                            {
                                                tempTwentySixLow.remove();
                                            }
                                        }
                                    }
                                }

                                double kijun = (twentySixHighD.getDerivative() + twentySixLowD.getDerivative()) / 2;

                                Kijunsen.replaceLastLine(new Derivative(kijun, instanceData.getTimestamp()), instanceDataShare.getSymbol());

                                if (k + 26 < day.getInstanceData().length)
                                {
                                    SenkouA.replaceLastLine(new Derivative((((nineHighD.getDerivative() + nineLowD.getDerivative()) / 2) + kijun) / 2, day.getInstanceData()[k + 26].getTimestamp()), instanceDataShare.getSymbol());
                                }

                                if (k > 51)
                                {
                                    if (fiftyTwoHighD.getDerivative() == fiftyTwoHigh.poll().getDerivative())
                                    {
                                        //  System.out.println("26 High " + k + " : " + fiftyTwoHighD.getDerivative());
                                        Queue<Derivative> tempFiftyTwoHigh = new LinkedList<>(fiftyTwoHigh);
                                        fiftyTwoHighD = new Derivative(-10000000000.0);
                                        for (int h = 0; h < fiftyTwoHigh.size() + 1; h++)
                                        {
                                            if (tempFiftyTwoHigh.peek() != null)
                                            {
                                                //  System.out.print(tempFiftyTwoHigh.peek().getDerivative());
                                                if (fiftyTwoHighD.getDerivative() < tempFiftyTwoHigh.peek().getDerivative())
                                                {
                                                    fiftyTwoHighD = tempFiftyTwoHigh.remove();
                                                }
                                                else
                                                {
                                                    tempFiftyTwoHigh.remove();
                                                }
                                            }
                                        }
                                    }

                                    if (fiftyTwoLowD.getDerivative() == fiftyTwoLow.poll().getDerivative())
                                    {
                                        //System.out.println("26 Low " + k + " : " + fiftyTwoLowD.getDerivative());
                                        Queue<Derivative> tempFiftyTwoLow = new LinkedList<>(fiftyTwoLow);
                                        fiftyTwoLowD = new Derivative(10000000000.0);
                                        for (int l = 0; l < fiftyTwoLow.size() + 1; l++)
                                        {
                                            if (tempFiftyTwoLow.peek() != null)
                                            {
                                                if (fiftyTwoLowD.getDerivative() > tempFiftyTwoLow.peek().getDerivative())
                                                {
                                                    fiftyTwoLowD = tempFiftyTwoLow.remove();
                                                }
                                                else
                                                {
                                                    tempFiftyTwoLow.remove();
                                                }
                                            }
                                        }
                                    }

                                    if (k + 26 < day.getInstanceData().length)
                                    {
                                        SenkouB.replaceLastLine(new Derivative((fiftyTwoHighD.getDerivative() + fiftyTwoLowD.getDerivative()) / 2, day.getInstanceData()[k + 26].getTimestamp()), instanceDataShare.getSymbol());
                                    }
                                }
                            }

                            Tenkansen.replaceLastLine(new Derivative((nineHighD.getDerivative() + nineLowD.getDerivative()) / 2, instanceData.getTimestamp()), instanceDataShare.getSymbol());
                        }
                    }
                    day = instanceDataShare.getNextDay();
                }
            }
        }

        for (int i = 0; i < SenkouB.getDerivativesPerShare().length; i++)
        {
            DerivativesPerShare instanceDataShare = SenkouB.getDerivativesPerShare()[i];

//            if ( SenkouACrossesSenkouB.getOut() == null || TenkansenCrossesKijunsen.getOut() == null )
//            {
//                continue;
//            }
            Day senkouBDay = SenkouB.getNextDay(i, SenkouB.getIndicatorName());
            Day senkouADay = SenkouA.getNextDay(i, SenkouA.getIndicatorName());
            Day tenkansenDay = Tenkansen.getNextDay(i, Tenkansen.getIndicatorName());
            Day kijunsenDay = Kijunsen.getNextDay(i, Kijunsen.getIndicatorName());

            while (senkouBDay != null)
            {
                for (int k = 1; k < senkouBDay.getDerivatives().length; k++)
                {
                    Double senkouB = senkouBDay.getDerivatives()[k].getDerivative();

                    if (senkouB != null)
                    {
                        Double senkouA = senkouADay.getDerivatives()[k].getDerivative();
                        Double prevSenkouA = senkouADay.getDerivatives()[k - 1].getDerivative();
                        Double prevSenkouB = senkouBDay.getDerivatives()[k - 1].getDerivative();
                        Double tenkansen = tenkansenDay.getDerivatives()[k].getDerivative();
                        Double kijunsen = kijunsenDay.getDerivatives()[k].getDerivative();
                        Double prevTenkansen = tenkansenDay.getDerivatives()[k - 1].getDerivative();
                        Double prevKijunsen = kijunsenDay.getDerivatives()[k - 1].getDerivative();

                        if (tenkansen - kijunsen < 0 && prevTenkansen - prevKijunsen > 0 && TenkansenCrossesKijunsen.getOut() != null)
                        {
                            TenkansenCrossesKijunsen.addDerivative(new Derivative(0.0, kijunsenDay.getDerivatives()[k].getTime()), instanceDataShare.getSymbol());
                        }

                        if (kijunsen - tenkansen < 0 && prevKijunsen - prevTenkansen > 0 && KijunsenCrossesTenkansen.getOut() != null)
                        {
                            KijunsenCrossesTenkansen.addDerivative(new Derivative(0.0, kijunsenDay.getDerivatives()[k].getTime()), instanceDataShare.getSymbol());
                        }

                        if (senkouA - senkouB < 0 && prevSenkouA - prevSenkouB > 0 && SenkouACrossesSenkouB.getOut() != null)
                        {
                            SenkouACrossesSenkouB.addDerivative(new Derivative(0.0, senkouADay.getDerivatives()[k].getTime()), instanceDataShare.getSymbol());
                        }

                        if (senkouB - senkouA < 0 && prevSenkouB - prevSenkouA > 0 && SenkouBCrossesSenkouA.getOut() != null)
                        {
                            SenkouBCrossesSenkouA.addDerivative(new Derivative(0.0, senkouBDay.getDerivatives()[k].getTime()), instanceDataShare.getSymbol());
                        }

                        double estimatedMidpoint = (senkouA + senkouB + prevKijunsen + prevTenkansen) / 4;

                        if ((senkouA <= estimatedMidpoint || prevSenkouA <= estimatedMidpoint)
                                && (estimatedMidpoint <= senkouB || estimatedMidpoint <= prevSenkouB))
                        {
                            if (tenkansen - kijunsen < 0 && prevTenkansen - prevKijunsen > 0 && TenkansenCrossesKijunsenInsideIchimokuCloudBTop.getOut() != null)
                            {
                                TenkansenCrossesKijunsenInsideIchimokuCloudBTop.addDerivative(new Derivative(0.0, senkouADay.getDerivatives()[k].getTime()), instanceDataShare.getSymbol());
                            }

                            if (kijunsen - tenkansen < 0 && prevKijunsen - prevTenkansen > 0 && KijunsenCrossesTenkansenInsideIchimokuCloudBTop.getOut() != null)
                            {
                                KijunsenCrossesTenkansenInsideIchimokuCloudBTop.addDerivative(new Derivative(0.0, senkouBDay.getDerivatives()[k].getTime()), instanceDataShare.getSymbol());
                            }
                        }

                        if ((senkouB <= estimatedMidpoint || prevSenkouB <= estimatedMidpoint)
                                && (estimatedMidpoint <= senkouA || estimatedMidpoint <= prevSenkouA))
                        {
                            if (tenkansen - kijunsen < 0 && prevTenkansen - prevKijunsen > 0 && TenkansenCrossesKijunsenInsideIchimokuCloudATop.getOut() != null)
                            {
                                TenkansenCrossesKijunsenInsideIchimokuCloudATop.addDerivative(new Derivative(0.0, kijunsenDay.getDerivatives()[k].getTime()), instanceDataShare.getSymbol());
                            }

                            if (kijunsen - tenkansen < 0 && prevKijunsen - prevTenkansen > 0)
                            {
                                KijunsenCrossesTenkansenInsideIchimokuCloudATop.addDerivative(new Derivative(0.0, kijunsenDay.getDerivatives()[k].getTime()), instanceDataShare.getSymbol());
                            }
                        }

                    }
                }
                senkouBDay = SenkouB.getNextDay(i, SenkouB.getIndicatorName());
                senkouADay = SenkouA.getNextDay(i, SenkouA.getIndicatorName());
                tenkansenDay = Tenkansen.getNextDay(i, Tenkansen.getIndicatorName());
                kijunsenDay = Kijunsen.getNextDay(i, Kijunsen.getIndicatorName());
            }
        }
    }

    /*retractmentPercentage = the perecentage a share has to retrace in order for the wave to terminate*/
    public void identifyWaves(double shareRetracementMultiplier)
    {
        Waves waves = new Waves(instanceDataPerShare);

        for (int i = 0; i < instanceDataPerShare.size(); i++)
        {
            InstanceDataPerShare instanceDataShare = instanceDataPerShare.get(i);
            Indicator wavesIndictor = new Indicator("waves", instanceDataShare.getSymbol(), instanceDataPerShare);

            if (wavesIndictor.getOut() == null)
            {
                continue;
            }
            InstanceDataDay day = instanceDataShare.getNextDay();
            while (day != null)
            {
                double shareRetracement = shareRetracementMultiplier * day.getInstanceData()[0].getClose();
                int lastWave = 0;// set to 1 if up and -1 if down
                double value = 0.0;
                for (int k = 1; k < day.getInstanceData().length; k++)
                {
                    InstanceData instanceData = day.getInstanceData()[k];

                    if (lastWave == -1)
                    {
                        if (((instanceData.getHigh() - value) / day.getInstanceData()[0].getOpen()) > shareRetracement)
                        {
                            wavesIndictor.addWave(new Wave(instanceData.getHigh(), instanceData.getTimestamp(), 1), instanceDataShare.getSymbol());
                            value = instanceData.getHigh();
                        }
                        else if (value > instanceData.getLow())
                        {
                            value = instanceData.getLow();
                        }
                    }
                    else if (lastWave == 1)
                    {
                        if (((value - instanceData.getLow()) / day.getInstanceData()[0].getOpen()) > shareRetracement)
                        {
                            wavesIndictor.addWave(new Wave(instanceData.getLow(), instanceData.getTimestamp(), -1), instanceDataShare.getSymbol());
                            value = instanceData.getLow();
                        }
                        else if (value < instanceData.getHigh())
                        {
                            value = instanceData.getHigh();
                        }
                    }
                    else if (lastWave == 0)
                    {
                        if (((instanceData.getHigh() - day.getInstanceData()[0].getOpen()) / day.getInstanceData()[0].getOpen()) >= shareRetracement)
                        {
                            wavesIndictor.addWave(new Wave(instanceData.getHigh(), instanceData.getTimestamp(), 1), instanceDataShare.getSymbol());
                            value = instanceData.getHigh();
                            lastWave = 1;
                        }
                        else if (((day.getInstanceData()[0].getOpen() - instanceData.getLow()) / day.getInstanceData()[0].getOpen()) >= shareRetracement)
                        {
                            wavesIndictor.addWave(new Wave(instanceData.getLow(), instanceData.getTimestamp(), -1), instanceDataShare.getSymbol());
                            value = instanceData.getLow();
                            lastWave = -1;
                        }
                    }
                }
                day = instanceDataShare.getNextDay();
            }
        }
    }

    Indicator ElliotIndicator;

    //Indicator ElliotIndicator = new Indicator(instanceDataPerShare);
    public void ElliotWave()
    {
        WaveSeriesHolder fibSeriesHolder = new WaveSeriesHolder(14);
        String[] tagOrder
                =
                {
                    "1", "2", "3", "3A", "4", "4A", "5", "5A", "A", "AA", "B", "BA", "C", "CA"
                };
        fibSeriesHolder.setTagOrder(tagOrder);

        for (int h = 0; h < wavesPerShareArrayList.size(); h++)
        {
            WavesPerShare[] wavesPerShares = wavesPerShareArrayList.get(h).getWavesPerShare();
            for (int i = 0; i < wavesPerShares.length; i++)
            {
                WavesPerShare waveShare = wavesPerShares[i];
                ElliotIndicator = new Indicator("Elliot Indicator", waveShare.getSymbol(), instanceDataPerShare);
                if (i == 0)
                {
                    indicators.add(ElliotIndicator);
                }
                if (ElliotIndicator.getOut() == null)
                {
                    continue;
                }

                WaveDay day = wavesPerShares[i].getNextDay();
                while (day != null)
                {
                    //for loop to find starting point of first wave
                    outer:
                    for (int k = 0; k < day.getWaves().length; k++)//outerWave looping through mins
                    {
                        boolean hitGreenUp = false;
                        boolean hitGreenDown = false;
                        Wave outerWave = day.getWaves()[k];
                        Wave high = new Wave(-Double.MAX_VALUE);
                        Wave low = new Wave(Double.MAX_VALUE);
                        boolean upWave = false;
                        if (k + 1 < day.getLastEntryIndex())
                        {
                            upWave = (outerWave.getLocalExtremeValue() < day.getWaves()[k + 1].getLocalExtremeValue()) ? true : false;
                        }
                        //for loop to find starting of second wave and third wave
                        for (int m = k + 1; m < day.getWaves().length; m++)//innerWave looping through all extreme
                        {
                            Wave innerWave = day.getWaves()[m];
                            if (innerWave.getLocalExtremeValue() > high.getLocalExtremeValue())
                            {
                                high = innerWave;
                            }
                            if (innerWave.getLocalExtremeValue() < low.getLocalExtremeValue())
                            {
                                low = innerWave;
                            }
                            Double upperLimitUp = (0.51 * (high.getLocalExtremeValue() - outerWave.getLocalExtremeValue())) + outerWave.getLocalExtremeValue();
                            Double lowerLimitUp = (0.37 * (high.getLocalExtremeValue() - outerWave.getLocalExtremeValue())) + outerWave.getLocalExtremeValue();
                            Double upperLimitDown = (0.63 * (outerWave.getLocalExtremeValue()) - low.getLocalExtremeValue()) + low.getLocalExtremeValue();
                            Double lowerLimitDown = (0.49 * (outerWave.getLocalExtremeValue()) - low.getLocalExtremeValue()) + low.getLocalExtremeValue();

                            if (upWave)
                            {
                                //System.out.println(upperLimitUp + " " + high.getLocalExtremeValue());
                                if ((innerWave.getLocalExtremeValue() < upperLimitUp) && (innerWave.getLocalExtremeValue() > lowerLimitUp))
                                {
                                    hitGreenUp = true;
                                }
                                else if (hitGreenUp && innerWave.getLocalExtremeValue() < lowerLimitUp)
                                {
                                    hitGreenUp = false;
                                    int temp = i;
                                    fibSeriesHolder.addToDictionary(new Derivative(outerWave.getLocalExtremeValue(), outerWave.getTime()), fibSeriesHolder.getNextWaveInSeries());
                                    fibSeriesHolder.addToDictionary(new Derivative(high.getLocalExtremeValue(), high.getTime()), fibSeriesHolder.getNextWaveInSeries());
                                    fibSeriesHolder.addToDictionary(new Derivative(innerWave.getLocalExtremeValue(), innerWave.getTime()), fibSeriesHolder.getNextWaveInSeries());
                                    lookForNextWaves(fibSeriesHolder, temp, m);
                                }
                            }
                            else /*if downWave*/ {
                                //System.out.println(lowerLimitDown + " " + low.getLocalExtremeValue());
                                if ((innerWave.getLocalExtremeValue() < upperLimitDown) && (innerWave.getLocalExtremeValue() > lowerLimitDown))
                                {
                                    hitGreenDown = true;
                                }
                                else if (hitGreenDown && innerWave.getLocalExtremeValue() > upperLimitUp)
                                {
                                    hitGreenDown = false;
                                    int temp = i;
                                    fibSeriesHolder.addToDictionary(new Derivative(outerWave.getLocalExtremeValue(), outerWave.getTime()), fibSeriesHolder.getNextWaveInSeries());
                                    fibSeriesHolder.addToDictionary(new Derivative(low.getLocalExtremeValue(), high.getTime()), fibSeriesHolder.getNextWaveInSeries());
                                    fibSeriesHolder.addToDictionary(new Derivative(innerWave.getLocalExtremeValue(), innerWave.getTime()), fibSeriesHolder.getNextWaveInSeries());
                                    lookForNextWaves(fibSeriesHolder, temp, m);
                                }
                            }
                        }
                    }
                    day = wavesPerShares[i].getNextDay();
                }
            }
        }
    }

    public void lookForNextWaves(WaveSeriesHolder waveSeries, int pos, int previousEndingPos)
    {
        /**
         * *************************************************************************************************
         * setting upper and lower bounds based on current wave
         * ************************************************************************************************
         */
        if (previousEndingPos == -1)
        {
            return;
        }
        int DPSpos = pos;

        String waveNum = "";

        for (String tag : waveSeries.getTagOrder())
        {
            if (waveSeries.getElement(tag) == null)
            {
                break;
            }
            if (waveNum.equals(""))
            {
                waveNum = tag.substring(0, 1);
            }
            else if (tag.substring(0, 1).equals("C"))
            {
                waveNum = tag.substring(0, 1);
            }
            else if (tag.substring(0, 1).equals("B") && !waveNum.substring(0, 1).equals("C"))
            {
                waveNum = tag.substring(0, 1);
            }
            else if (tag.substring(0, 1).equals("A") && !waveNum.substring(0, 1).equals("C") && !waveNum.substring(0, 1).equals("B"))
            {
                waveNum = tag.substring(0, 1);
            }
            else if (!waveNum.substring(0, 1).equals("C") && !waveNum.substring(0, 1).equals("B") && !waveNum.substring(0, 1).equals("A"))
            {
                if (Integer.parseInt(waveNum) < Integer.parseInt(tag.substring(0, 1)))
                {
                    waveNum = tag.substring(0, 1);
                }
            }
        }

        boolean upWave = false;
        int onLookOut = -1;
        Double[] upperLimit
                =
                {
                    null, null, null
                };
        Double[] lowerLimit
                =
                {
                    null, null, null
                };

        switch (waveNum)
        {
            case "3":
                upperLimit[0] = (1.65 * (waveSeries.getDerivativeValueByTag("2") - waveSeries.getDerivativeValueByTag("1"))) + waveSeries.getDerivativeValueByTag("3");
                lowerLimit[0] = (1.59 * (waveSeries.getDerivativeValueByTag("2") - waveSeries.getDerivativeValueByTag("1"))) + waveSeries.getDerivativeValueByTag("3");
                upperLimit[1] = (2.65 * (waveSeries.getDerivativeValueByTag("2") - waveSeries.getDerivativeValueByTag("1"))) + waveSeries.getDerivativeValueByTag("3");
                lowerLimit[1] = (2.59 * (waveSeries.getDerivativeValueByTag("2") - waveSeries.getDerivativeValueByTag("1"))) + waveSeries.getDerivativeValueByTag("3");
                upperLimit[2] = (4.28 * (waveSeries.getDerivativeValueByTag("2") - waveSeries.getDerivativeValueByTag("1"))) + waveSeries.getDerivativeValueByTag("3");
                lowerLimit[2] = (4.22 * (waveSeries.getDerivativeValueByTag("2") - waveSeries.getDerivativeValueByTag("1"))) + waveSeries.getDerivativeValueByTag("3");
                upWave = true;
                break;
            case "4":
                upperLimit[0] = (0.765 * (waveSeries.getDerivativeValueByTag("4") - waveSeries.getDerivativeValueByTag("3"))) + waveSeries.getDerivativeValueByTag("3");
                lowerLimit[0] = (0.615 * (waveSeries.getDerivativeValueByTag("4") - waveSeries.getDerivativeValueByTag("3"))) + waveSeries.getDerivativeValueByTag("3");
                upperLimit[1] = null;
                lowerLimit[1] = null;
                upperLimit[2] = null;
                lowerLimit[2] = null;
                break;
            case "5":
                upWave = true;

                if ((waveSeries.getDerivativeValueByTag("4") - waveSeries.getDerivativeValueByTag("3")) >= 1.6 * (waveSeries.getDerivativeValueByTag("2") - waveSeries.getDerivativeValueByTag("1")))
                {
                    upperLimit[0] = (1.02 * (waveSeries.getDerivativeValueByTag("2") - waveSeries.getDerivativeValueByTag("1"))) + waveSeries.getDerivativeValueByTag("5");
                    lowerLimit[0] = (0.98 * (waveSeries.getDerivativeValueByTag("2") - waveSeries.getDerivativeValueByTag("1"))) + waveSeries.getDerivativeValueByTag("5");

                    upperLimit[1] = (1.64 * (waveSeries.getDerivativeValueByTag("2") - waveSeries.getDerivativeValueByTag("1"))) + waveSeries.getDerivativeValueByTag("5");
                    lowerLimit[1] = (1.6 * (waveSeries.getDerivativeValueByTag("2") - waveSeries.getDerivativeValueByTag("1"))) + waveSeries.getDerivativeValueByTag("5");

                    upperLimit[2] = (2.64 * (waveSeries.getDerivativeValueByTag("2") - waveSeries.getDerivativeValueByTag("1"))) + waveSeries.getDerivativeValueByTag("5");
                    lowerLimit[2] = (2.6 * (waveSeries.getDerivativeValueByTag("2") - waveSeries.getDerivativeValueByTag("1"))) + waveSeries.getDerivativeValueByTag("5");
                }
                else
                {
                    upperLimit[0] = (0.64 * (waveSeries.getDerivativeValueByTag("4") - waveSeries.getDerivativeValueByTag("1"))) + waveSeries.getDerivativeValueByTag("5");
                    lowerLimit[0] = (0.6 * (waveSeries.getDerivativeValueByTag("4") - waveSeries.getDerivativeValueByTag("1"))) + waveSeries.getDerivativeValueByTag("5");
                    upperLimit[1] = (1.64 * (waveSeries.getDerivativeValueByTag("4") - waveSeries.getDerivativeValueByTag("1"))) + waveSeries.getDerivativeValueByTag("5");
                    lowerLimit[1] = (1.6 * (waveSeries.getDerivativeValueByTag("4") - waveSeries.getDerivativeValueByTag("1"))) + waveSeries.getDerivativeValueByTag("5");
                    upperLimit[2] = null;
                    lowerLimit[2] = null;
                }
                break;
            case "A":
                upperLimit[0] = (0.4 * (waveSeries.getDerivativeValueByTag("6") - waveSeries.getDerivativeValueByTag("5"))) + waveSeries.getDerivativeValueByTag("1");
                lowerLimit[0] = (0.36 * (waveSeries.getDerivativeValueByTag("6") - waveSeries.getDerivativeValueByTag("5"))) + waveSeries.getDerivativeValueByTag("1");
                upperLimit[1] = (0.52 * (waveSeries.getDerivativeValueByTag("6") - waveSeries.getDerivativeValueByTag("5"))) + waveSeries.getDerivativeValueByTag("1");
                lowerLimit[1] = (0.48 * (waveSeries.getDerivativeValueByTag("6") - waveSeries.getDerivativeValueByTag("5"))) + waveSeries.getDerivativeValueByTag("1");
                upperLimit[2] = null;
                lowerLimit[2] = null;
                break;
            case "B":
                upperLimit[0] = (0.52 * (waveSeries.getDerivativeValueByTag("6") - waveSeries.getDerivativeValueByTag("A"))) + waveSeries.getDerivativeValueByTag("A");
                lowerLimit[0] = (0.48 * (waveSeries.getDerivativeValueByTag("6") - waveSeries.getDerivativeValueByTag("A"))) + waveSeries.getDerivativeValueByTag("A");
                upperLimit[1] = (0.64 * (waveSeries.getDerivativeValueByTag("6") - waveSeries.getDerivativeValueByTag("A"))) + waveSeries.getDerivativeValueByTag("A");
                lowerLimit[1] = (0.6 * (waveSeries.getDerivativeValueByTag("6") - waveSeries.getDerivativeValueByTag("A"))) + waveSeries.getDerivativeValueByTag("A");
                upperLimit[2] = null;
                lowerLimit[2] = null;
                upWave = true;
                break;
        }
        /* ***********************************************************************************************/
        /* ***********************************************************************************************/

        /* ***********************************************************************************************
         Running through extremes looking for next wave and calling this method recursively if next wave is found
         *************************************************************************************************/
        for (int g = 0; g < wavesPerShareArrayList.size(); g++)
        {
            Wave outerWave = wavesPerShareArrayList.get(g).getWavesPerShare()[DPSpos].getNextDay().getWaves()[previousEndingPos];
            for (int h = 0; h < wavesPerShareArrayList.size(); h++)
            {
                Waves wavesPerShares = wavesPerShareArrayList.get(h);
                for (int i = DPSpos; i < wavesPerShares.getWavesPerShare().length; i++)
                {
                    WavesPerShare waveShare = wavesPerShares.getWavesPerShare()[i];
                    WaveDay day = waveShare.getNextDay();

                    Derivative high = new Derivative(-Double.MAX_VALUE, "");
                    Derivative low = new Derivative(Double.MAX_VALUE, "");
                    //for loop to find starting point of first wave
                    outer:
                    for (int k = previousEndingPos + 1; k < day.getWaves().length; k++)//outerWave looping through mins
                    {
                        Wave innerWave = day.getWaves()[k];

                        if (upWave)
                        {
                            for (int m = 0; m < upperLimit.length; i++)
                            {
                                if (upperLimit[m] == null)
                                {
                                    break;
                                }
                                if ((innerWave.getLocalExtremeValue() < upperLimit[m]) && (innerWave.getLocalExtremeValue() > lowerLimit[m]))
                                {
                                    onLookOut = m;
                                }
                                if (onLookOut > -1)
                                {
                                    if (high.getDerivative() < innerWave.getLocalExtremeValue())
                                    {
                                        high = new Derivative(innerWave.getLocalExtremeValue(), innerWave.getTime());
                                    }
                                    if (innerWave.getLocalExtremeValue() < lowerLimit[onLookOut])
                                    {
                                        //add to waveSeries e.g. 5 and 5A
                                        waveSeries.addToDictionary(high, waveSeries.getNextWaveInSeries());
                                        waveSeries.addToDictionary(new Derivative(innerWave.getLocalExtremeValue(), innerWave.getTime()), waveSeries.getNextWaveInSeries());
                                        lookForNextWaves(waveSeries, pos, previousEndingPos);
                                    }
                                    else if (innerWave.getLocalExtremeValue() > upperLimit[onLookOut])
                                    {
                                        //reset high to 0
                                        high = new Derivative(-Double.MAX_VALUE, "");
                                        onLookOut = -1;
                                    }
                                }
                                if (upperLimit[m + 1] == null && innerWave.getLocalExtremeValue() > upperLimit[m])
                                {
                                    for (String tag : waveSeries.getTagOrder())
                                    {
                                        ElliotIndicator.addDerivative(waveSeries.getElement(tag), waveShare.getSymbol());
                                    }
                                    return;
                                }
                                if (innerWave.getLocalExtremeValue() < outerWave.getLocalExtremeValue())
                                {
                                    for (String tag : waveSeries.getTagOrder())
                                    {
                                        ElliotIndicator.addDerivative(waveSeries.getElement(tag), waveShare.getSymbol());
                                    }
                                    return;
                                }
                            }
                        }
                        else
                        {
                            for (int m = 0; m < upperLimit.length; i++)
                            {
                                if (upperLimit[m] == null)
                                {
                                    break;
                                }
                                if ((innerWave.getLocalExtremeValue() < upperLimit[m]) && (innerWave.getLocalExtremeValue() > lowerLimit[m]))
                                {
                                    onLookOut = m;
                                }
                                if (onLookOut > -1)
                                {
                                    //update low
                                    if (low.getDerivative() > innerWave.getLocalExtremeValue())
                                    {
                                        low = new Derivative(innerWave.getLocalExtremeValue(), innerWave.getTime());
                                    }
                                    if (innerWave.getLocalExtremeValue() > upperLimit[onLookOut])
                                    {
                                        //add to waveSeries e.g. 5 and 5A
                                        waveSeries.addToDictionary(low, waveSeries.getNextWaveInSeries());
                                        waveSeries.addToDictionary(new Derivative(innerWave.getLocalExtremeValue(), innerWave.getTime()), waveSeries.getNextWaveInSeries());
                                        lookForNextWaves(waveSeries, pos, previousEndingPos);
                                    }
                                    else if (innerWave.getLocalExtremeValue() < lowerLimit[onLookOut])
                                    {
                                        //reset low to 0
                                        low = new Derivative(Double.MAX_VALUE, "");
                                        onLookOut = -1;
                                    }
                                }
                                if (innerWave.getLocalExtremeValue() < lowerLimit[0])
                                {
                                    for (String tag : waveSeries.getTagOrder())
                                    {
                                        ElliotIndicator.addDerivative(waveSeries.getElement(tag), waveShare.getSymbol());
                                    }
                                    return;
                                }
                                if (innerWave.getLocalExtremeValue() > outerWave.getLocalExtremeValue())
                                {
                                    for (String tag : waveSeries.getTagOrder())
                                    {
                                        ElliotIndicator.addDerivative(waveSeries.getElement(tag), waveShare.getSymbol());
                                    }
                                    return;
                                }
                            }
                        }
                    }
                    for (String tag : waveSeries.getTagOrder())
                    {
                        ElliotIndicator.addDerivative(waveSeries.getElement(tag), waveShare.getSymbol());
                    }
                    return;

                }
            }
            /* ***********************************************************************************************/
        }
    }

    /*
     *returns tne size of the profitable strategy with the largest number of popouts
     *the second parameter restricts the method from returning a value less han the passed parameter "lessThan"
     */
    public int findLargestPopout(ArrayList<Strategy> tempStrategies, Strategy superset)
    {
        int biggest = 0;
        for (Strategy subset : tempStrategies)
        {
            if (subset.intersect(superset).getNumPopOuts() > biggest && subset.intersect(superset).getNumPopOuts() < superset.getNumPopOuts() && subset.getNumPopOuts() == superset.intersect(subset).getNumPopOuts())
            {
                biggest = subset.intersect(superset).getNumPopOuts();
            }
        }
        return biggest;
    }

    static Algo algo = new Algo();

    public static void main(String[] args)
    {
        Scanner c = new Scanner(System.in);

        String fileName = "";

        algo.readElementList();//reads elements
        algo.calculateDerivitives();//calculates derivatives
        algo.read(fileName);
        algo.findProfittableOpportunities(4);//finds all profitable opportunities in the EUR/USD pair
        algo.identifyWaves(1 / 10000);
        if (false)
        {
            System.out.println("calculateMovingAverageRelatedIndicators");
            algo.calculateMovingAverageRelatedIndicators(5);
            System.out.println("out");
            algo.calculateIchimoku();
            System.out.println("Ichimoku");
            algo.calculateRSI(30);
            System.out.println("RSI");
        }
        algo.calculateMACDcrosses();
        System.out.println("MACD");
        algo.calculateVolitilityMACDcrosses();
        System.out.println("volitility");
        algo.identifyOutliers();//finds the top 10 and bottom 10 percent of derivtives and adds them to the outlier array
        //algo.sortByTime();//sorts outliers by time. This will allow the pop out method to do checks faster
        //algo.transferFromArrayLists();//converts from ArrayList to generic array for speed
        System.out.println("Identify Popouts");
        algo.identifyPopouts();//identifies all of the popout variables, where outliers have times equal to the time of the profitable opportunity
        algo.ElliotWave();
        algo.waterfall();

        algo.transferStrategies();
        algo.createSubsetTreeArray();
        algo.createMapOfOutliers(new Day(0,""), "");
        System.out.println("Backtest");
        algo.backtest();

        System.out.println("");

        /*
         To Do List:
         Take the data cleaning out to make it consistently a minute in between
        
         Possible improvements:
         1. Make intersection waterfall recursive (so that all of the equal largest intersections have their own waterfall)
         */
    }
}
