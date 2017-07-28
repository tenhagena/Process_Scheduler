package com.company;

import java.util.ArrayList;

public class Ghant {
    private ArrayList<Integer> chart;
    private int totalTime;

    public ArrayList<Integer> getChart() {
        return chart;
    }

    public void setChart(ArrayList<Integer> chart) {
        this.chart = chart;
    }

    public void addToChart(int index){this.chart.add(index);}

    public int getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(int totalTime) {
        this.totalTime = totalTime;
    }

    public Ghant(){
        chart = new ArrayList<Integer>();
    }




    @Override
    public String toString() {
        String result = "";
        String copy = "";
        int time = 0;
        int idle = 0;
        double idlePercent = 0;
        String nextOut;
        for ( int i : chart) {
            if(i == 0){
                idle++;
            }
            if(result.equals("")){
                result = result.concat("[");
                copy = copy.concat(Integer.toString(time)+"[");
                result = result.concat(Integer.toString(i));
                if(i == 0){
                    copy = copy.concat("IDLE");
                }else {
                    copy = copy.concat("P" + Integer.toString(i));
                }
            }else if(result.endsWith(Integer.toString(i))){
                result = result.concat(Integer.toString(i));
                copy = copy.concat(" ");
            }else{
                copy = copy.concat("]"+Integer.toString(time)+"[");
                result = result.concat(Integer.toString(i));
                if(i == 0){
                    copy = copy.concat("IDLE");
                }else {
                    copy = copy.concat("P" + Integer.toString(i));
                }
            }
            time++;
        }
        copy = copy.concat("]"+ Integer.toString(time));

        if(idle > 0){
            idlePercent = (double)idle/(double)time;
            nextOut = "*                  System usage:";
            nextOut = nextOut.concat(Double.toString(1-idlePercent));
            while(nextOut.length() < 57){
                nextOut = nextOut.concat(" ");
            }
            nextOut = nextOut.concat("*");

            copy = copy.concat("\n" + nextOut);
        }




        return copy;
    }
}
