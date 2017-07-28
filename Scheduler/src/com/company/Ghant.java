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
        //variable declaration
        String result = "";
        String copy = "";
        int time = 0;
        int idle = 0;
        double idlePercent = 0;
        String nextOut;
        //for each item in the chart
        for ( int i : chart) {
            //adds to the number of idle of ticks
            if(i == 0){
                idle++;
            }
            //if its at the start
            if(result.equals("")){
                //sets the first left parnes
                result = result.concat(" [");
                //labels the time and sets first left parens
                copy = copy.concat(Integer.toString(time)+"[");
                result = result.concat(Integer.toString(i));
                if(i == 0){
                    //sets the name of the process
                    copy = copy.concat("IDLE");
                }else {
                    //sets the name of the process
                    copy = copy.concat("P" + Integer.toString(i));
                }
            }else if(result.endsWith(Integer.toString(i))){
                //adds spaces to the returning string equal to the run time.
                result = result.concat(Integer.toString(i));
                copy = copy.concat(" ");
            }else{
                //ends the current block, adds the time and then starts a new block
                copy = copy.concat("] "+Integer.toString(time)+" [");
                result = result.concat(Integer.toString(i));
                if(i == 0){
                    //sets the name of the process
                    copy = copy.concat("IDLE");
                }else {
                    //sets the name of the process
                    copy = copy.concat("P" + Integer.toString(i));
                }
            }
            //increments time
            time++;
        }
        //adds the closing parens
        copy = copy.concat("] "+ Integer.toString(time));
        //if there is idle, calculates the cpu usage
        if(idle > 0){
            idlePercent = (double)idle/(double)time;
            nextOut = "*                  System usage:";
            nextOut = nextOut.concat(Double.toString(1-idlePercent));
            //fills end of sting with spaces to match formatting
            while(nextOut.length() < 57){
                nextOut = nextOut.concat(" ");
            }
            nextOut = nextOut.concat("*");
            //adds the cpu usage to a new line of the output
            copy = copy.concat("\n" + nextOut);
        }

        return copy;
    }
}
